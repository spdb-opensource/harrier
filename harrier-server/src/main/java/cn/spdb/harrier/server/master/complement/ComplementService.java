package cn.spdb.harrier.server.master.complement;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.enmus.StreamType;
import cn.spdb.harrier.common.enmus.UdsJobType;
import cn.spdb.harrier.common.utils.CollectionUtils;
import cn.spdb.harrier.common.utils.Host;
import cn.spdb.harrier.common.utils.Stopper;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.dao.entity.UdsJobComplement;
import cn.spdb.harrier.dao.entity.UdsJobDateFrequency;
import cn.spdb.harrier.dao.entity.UdsJobTimeTrigger;
import cn.spdb.harrier.dao.mapper.UdsComplementMapper;
import cn.spdb.harrier.dao.mapper.UdsJobComplementMapper;
import cn.spdb.harrier.dao.mapper.UdsJobDateFrequencyMapper;
import cn.spdb.harrier.dao.mapper.UdsJobDependencyMapper;
import cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerMapper;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.server.entity.ComplementIns;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.master.MasterManagerService;
import cn.spdb.harrier.server.master.cache.MasterMangerWorker;
import cn.spdb.harrier.server.worker.rpc.transport.WorkTransportServerInterface;

@Component
public class ComplementService implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(ComplementService.class.getSimpleName());

//	private ConcurrentHashMap<Long, ComplementIns> cacheMap = new ConcurrentHashMap<Long, ComplementIns>();

	private LinkedBlockingQueue<ComplementIns> complementQueue = new LinkedBlockingQueue<ComplementIns>();

//	private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public void addComplementIns(ComplementIns e) {
		complementQueue.add(e);
	}

	@PostConstruct
	public void start() {
		Thread thread = new Thread(this, "Master-Complement-Execute-Manager-Thread");
		thread.setDaemon(true);
		thread.start();
	}

	@Autowired
	private UdsComplementMapper complementMapper;
	@Autowired
	private UdsJobComplementMapper jobComplementMapper;
	@Autowired
	private UdsJobDateFrequencyMapper frequencyMapper;
	@Autowired
	private UdsJobTimeTriggerMapper triggerMapper;

	@Override
	public void run() {
		while (Stopper.isRunning()) {
			try {
				ComplementIns complementIns = complementQueue.take();
				UdsComplement uc = complementIns.getUdsComplement();
				uc.setId(BeanContext.getBean(MasterManagerService.class).getSingleId());
				if (ObjectUtils.isEmpty(uc.getMaxRunJob()) || uc.getMaxRunJob() <= 0) {
					uc.setMaxRunJob(1);
				}
				Host host = BeanContext.getBean(MasterMangerWorker.class)
						.getWorkerByServerName(
								Arrays.stream(uc.getServerNameRange().split(Symbol.FEN_HAO_REG)).findFirst().get())
						.getHost();
				List<JobExecutionContext> jobConList = complementIns.getJobList().stream()
						.filter(predicate -> predicate.length >= 3).map(mapper -> {
							return BeanContext.getBean(MasterManagerService.class).createJobExecutionContext(mapper[0],
									mapper[1], mapper[2]);
						}).filter(predicate -> ObjectUtils.isNotEmpty(predicate.getUdsJob())
								&& ObjectUtils.isNotEmpty(predicate.getUdsJobConfig()))
						.map(mapper -> {
							mapper.setComplementId(complementIns.getComplementId());
							if (mapper.getJobType().equals(UdsJobType.D)) {
							} else if (mapper.getJobType().equals(UdsJobType.C)) {
								List<UdsJobTimeTrigger> timetrigger = triggerMapper.select(mapper.getPlatform(),
										mapper.getSystem(), mapper.getJob());
								mapper.setTimeCornList(
										timetrigger.stream().map(fm -> fm.getCrontab()).collect(Collectors.toList()));
							} else {
								List<UdsJobDateFrequency> frequencies = frequencyMapper.select(mapper.getPlatform(),
										mapper.getSystem(), mapper.getJob());
								mapper.setCornList(
										frequencies.stream().map(fm -> fm.getCrontab()).collect(Collectors.toList()));
							}
							return mapper;
						})
						.filter(predicate1 -> predicate1.getJobType().equals(UdsJobType.D)
								|| (predicate1.getJobType().equals(UdsJobType.C)
										&& !CollectionUtils.isEmpty(predicate1.getTimeCornList()))
								|| !CollectionUtils.isEmpty(predicate1.getCornList()))
						.collect(Collectors.toList());
				jobConList.stream().forEach(action -> {
					action.getUdsJob().setStreamType(StreamType.FORCE_START.getId());
					action.setHost(host);
					complementIns.addNodeInfoJob(action);
					if (jobConList.size() > 1) {
						BeanContext.getBean(UdsJobDependencyMapper.class)
								.selectJobDeps(action.getPlatform(), action.getSystem(), action.getJob()).stream()
								.filter(predicate -> complementIns.getDag()
										.containsNode(predicate.getDepPlatform() + Symbol.XIA_HUA_XIAN
												+ predicate.getDepSystem() + Symbol.XIA_HUA_XIAN
												+ predicate.getDepJob()))
								.forEach(dep2job -> complementIns.addEdgeJob(dep2job));
					}
				});
				if (jobConList.size() <= 0) {
					uc.setLastStatus(ExecutionStatus.FAILURE.name());
					complementMapper.insert(uc);
					continue;
				} else {
					uc.setLastStatus(ExecutionStatus.READY.name());
					complementMapper.insert(uc);
				}

			
//				cacheMap.put(complementIns.getComplementId(), complementIns);
				complementIns.getDag().getNodeInfoValues().forEach(action -> {
					UdsJobComplement row = new UdsJobComplement();
					row.setComplementId(uc.getId());
					row.setPlatform(action.getPlatform());
					row.setSystems(action.getSystem());
					row.setJob(action.getJob());
					row.setLastStatus(ExecutionStatus.READY.name());
					jobComplementMapper.insert(row);
				});
//				executorService.submit(new ComplementRunable(complementIns));
				WorkTransportServerInterface workTransportServerInterface = RpcClient.getInstance()
						.create(WorkTransportServerInterface.class, new URI("spdb", host.getIp(), host.getPort()));
				UdsJobComplement row = new UdsJobComplement();
				row.setId(complementIns.getComplementId());
				if (!workTransportServerInterface.dispathcerComplement(complementIns)) {
					row.setLastStatus(ExecutionStatus.FAILURE.name());
					jobComplementMapper.updateByPrimaryKeySelective(row);
				}
			} catch (Exception e) {
				logger.error("ComplementService error", e);
			}

		}

	}

	public int updateUdsComplement(Long complementId,ExecutionStatus executionStatus) {
		UdsComplement row=new UdsComplement();
		row.setId(complementId);
		row.setLastStatus(executionStatus.name());
		return complementMapper.updateByPrimaryKeySelective(row);
	}
	public int updateUdsJobComplement(JobExecutionContext jobContext) {
		UdsJobComplement row=new UdsJobComplement();
		row.setComplementId(jobContext.getComplementId());
		row.setEndTime(jobContext.getUdsJob().getEndTime());
		row.setJob(jobContext.getJob());
		row.setJobDate(jobContext.getUdsJob().getJobDate());
		row.setLastStatus(jobContext.getUdsJob().getLastStatus());
		row.setMultiBatch(jobContext.getUdsJob().getMultiBatch());
		row.setPlatform(jobContext.getUdsJob().getPlatform());
		row.setServerName(jobContext.getUdsJob().getServerName());
		row.setStartTime(jobContext.getUdsJob().getStartTime());
		row.setSystems(jobContext.getUdsJob().getSystems());
		return jobComplementMapper.updateByPrimaryIdSelective(row);
	}

}
