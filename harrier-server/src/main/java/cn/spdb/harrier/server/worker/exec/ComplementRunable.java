package cn.spdb.harrier.server.worker.exec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.enmus.UdsJobType;
import cn.spdb.harrier.common.utils.DateUtils;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.server.entity.ComplementIns;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.worker.WorkerManagerThread;

public class ComplementRunable implements Runnable {

	private ComplementIns complementIns = null;

	private AtomicInteger integer = new AtomicInteger();

	private LinkedBlockingQueue<Integer> queue =new LinkedBlockingQueue<Integer>();
	
	
	public void offer() {
		queue.offer(integer.decrementAndGet());
	}

	@Override
	public void run() {
		StringBuffer cron = new StringBuffer();
		for (LocalDateTime start = complementIns.getUdsComplement().getStartTime(); start
				.compareTo(complementIns.getUdsComplement().getEndTime()) <= 0; start = start.plusDays(1)) {
			LocalDate jobDate = start.toLocalDate();
			for (int batch : complementIns.getbatchList()) {
				for (JobExecutionContext jobExe : complementIns.getBeginJob()) {
					if ((jobExe.getUdsJob().getMultiBatch() == 0 && batch != 0)
							|| (jobExe.getUdsJob().getMultiBatch() != 0 && batch == 0)) {
						continue;
					}
					Date date = DateUtils.localDateTime2Date(jobDate.atStartOfDay());
					if (jobExe.getJobType().equals(UdsJobType.D)) {
					} else if (jobExe.getJobType().equals(UdsJobType.C)) {
						if (!jobExe.getTimeCornList().stream().map(mapper -> {
							int i = 0;
							cron.setLength(0);
							for (String sc : mapper.split(Symbol.KONG_GE)) {
								if (i++ < 3) {
									cron.append("0").append(Symbol.KONG_GE);
								} else {
									cron.append(sc).append(Symbol.KONG_GE);
								}
							}
							return cron.toString();
						}).anyMatch(predicate -> DateUtils.isSatisfiedBy(cron.toString(), date))) {
							continue;
						}
					} else {
						if (!jobExe.getCornList().stream()
								.anyMatch(predicate -> DateUtils.isSatisfiedBy(predicate, date))) {
							continue;
						}
					}
					jobExe.getUdsJob().setJobDate(jobDate);
					jobExe.setBatch(batch);
					exec(jobExe.getJobId());
				}
			}
		}
	}

	public void exec(String node) {
		try {
			queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (complementIns.getPrevious(node).isEmpty()) {
			JobExecutionContext jec = complementIns.getDag().getNode(node);
			jec.setExecutionStatus(ExecutionStatus.RUNING);
			jec.setTaskInstanceId(BeanContext.getBean(WorkerManagerThread.class).getSingleId());
			ComplementExecuteRunnable cer = new ComplementExecuteRunnable(complementIns.getComplementId(),this);
			cer.setJobExecutionContext(jec);
			BeanContext.getBean(WorkerManagerThread.class).offer(cer);
			complementIns.getSubsequent(node).stream().forEach(action -> exec(action.getJobId()));
		} else {
			if (complementIns.getPrevious(node).stream().allMatch(predicate -> {
				return checkDependency(node, predicate.getJobId());
			})) {
				JobExecutionContext jec = complementIns.getDag().getNode(node);
				jec.setExecutionStatus(ExecutionStatus.RUNING);
				ComplementExecuteRunnable cer = new ComplementExecuteRunnable(complementIns.getComplementId(),this);
				cer.setJobExecutionContext(jec);
				BeanContext.getBean(WorkerManagerThread.class).offer(cer);
				complementIns.getSubsequent(node).stream().forEach(action -> exec(action.getJobId()));
			}
		}
	}


	public ComplementRunable(ComplementIns complementIns) {
		this.complementIns = complementIns;
		for (int i = 0; i < complementIns.getUdsComplement().getMaxRunJob(); i++) {
			queue.offer(integer.incrementAndGet());
		}
	}

	private boolean checkDependency(String node, String depNode) {
		UdsJob job = complementIns.getDag().getNode(node).getUdsJob();
		UdsJob depJob = complementIns.getDag().getNode(depNode).getUdsJob();
		if (depJob.getMultiBatch() == 0) {
			if (depJob.getNextJobDate().compareTo(job.getJobDate()) > 0) {
				return true;
			}
		} else {
			if (job.getMultiBatch() == 0) {
				int depBatch = complementIns.getDag().getSubsequentEdgeInfo(node, depNode);
				if (depJob.getMultiBatch() > depBatch || (depJob.getMultiBatch() == depBatch
						&& depJob.getLastStatus().equals(ExecutionStatus.SUCCESS.name()))) {
					if (depJob.getNextJobDate().compareTo(job.getJobDate()) > 0) {
						return true;
					}
				}
				if (depJob.getMultiBatch() < depBatch && depJob.getJobDate().compareTo(job.getJobDate()) > 0) {
					return true;
				}
			} else {
				if (depJob.getMultiBatch() > job.getMultiBatch() || (depJob.getMultiBatch() == job.getMultiBatch()
						&& depJob.getLastStatus().equals(ExecutionStatus.SUCCESS.name()))) {
					if (depJob.getNextJobDate().compareTo(job.getJobDate()) > 0) {
						return true;
					}
				}
				if (depJob.getMultiBatch() < job.getMultiBatch()
						&& depJob.getJobDate().compareTo(job.getJobDate()) > 0) {
					return true;
				}
			}
		}
		return false;
	}
	public LinkedBlockingQueue<Integer> getQueue() {
		return queue;
	}

	public void setQueue(LinkedBlockingQueue<Integer> queue) {
		this.queue = queue;
	}
}
