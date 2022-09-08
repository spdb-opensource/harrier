package cn.spdb.harrier.api.controller.job;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.model.TreeJobNode;
import cn.spdb.harrier.api.rpc.transport.MasterTransportServerInterfasce;
import cn.spdb.harrier.api.rpc.transport.WorkTransportServerInterface;
import cn.spdb.harrier.api.service.job.UdsJobService;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobMenu;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.service.db.DbRegistryService;

@RestController
@RequestMapping("/udsJob")
public class UdsJobController {
	@Autowired
	private UdsJobService service;
	@Autowired
	private DbRegistryService dbRegistryService;

	@AccessLogAnnotation(isDbInstall = true)
	@PutMapping("/add")
	public int add(UdsJob record) {
		return service.add(record);
	}
	@AccessLogAnnotation(isDbInstall = true)
	@DeleteMapping("/delete")
	public int delete(Long id) {
		return service.delete(id);
	}
	@AccessLogAnnotation(isDbInstall = true)
	@PostMapping("/update")
	public int update(UdsJob record) {
		return service.update(record);
	}

	@GetMapping("/getDetail")
	public UdsJob getDetail(Long id) {
		return service.getDetail(id);
	}

	@GetMapping("/listQuery")
	public List<UdsJob> listQuery() {
		return service.listQuery();
	}

	/**
	 * 作业管理—查询所有作业(分页)
	 */
	@GetMapping("/selectAll")
	public Map<String, Object> selectAll(Page<UdsJobMenu> page, String platform, String systems, String job,
			String lastStatus) {
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsJobMenu> pageinfo = service.selectAll(page, platform, systems, job, lastStatus);
		result.put("rows", pageinfo.getRecords());
		result.put("total", pageinfo.getTotal());
		return result;
	}

	/**
	 * 作业列表-查询作业详情
	 */
	@GetMapping("/selectJobDetail")
	public Map<String, Object> selectJobDetail(String platform, String systems, String job) {
		Map<String, Object> result = new HashMap<String, Object>();
		UdsJobMenu datas = service.selectJobDetail(platform, systems, job);
		result.put("rows", datas);
		return result;
	}

	/**
	 * 作业列表-更新作业详情
	 */
	@AccessLogAnnotation(isDbInstall = true)
	@PostMapping("/updateJobDetail")
	public int updateJobDetail(UdsJobMenu udsJobMenu) {
		return service.updateJobDetail(udsJobMenu);
	}

	/**
	 * 作业列表-查询作业上游(一层)
	 * 
	 * @return
	 */
	@GetMapping(path="UpjobList")
	public Page<UdsJobMenu> queryUpjobList(Page<UdsJobMenu> page, String job) {
    	return service.listUpJobs(page, job);
	}

	/**
	 * 作业列表-查询作业上游
	 * 
	 * @return
	 */
	@GetMapping(path = "allUpjobList")
	public List<TreeJobNode> queryAllUpjobList(String job, Integer level) {
		return service.listAllUpJobs(job, level);
	}

	/**
	 * 作业列表-查询作业下游
	 */
	@GetMapping(path = "allDownjobList")
	public List<TreeJobNode> queryAllDownjobList(String job, Integer level) {
		return service.listAllDownjobs(job, level);
	}

	/**
	 * 作业列表-invoke作业
	 */
	@AccessLogAnnotation(isDbInstall = true)
	@PostMapping("/invokeJob")
	public int invokeJob(String platform, String systems, String job,
			@DateTimeFormat(pattern = "yyyyMMdd") LocalDate jobdate, Integer multibatch) {

		return service.invokeJob(platform, systems, job, jobdate, multibatch);
	}

	/**
	 * 作业列表-kill作业
	 */
	@AccessLogAnnotation(isDbInstall = true)
	@PostMapping("/killJob")
	public int killJob(String platform, String systems, String job, Integer multibatch) {
		String host = null;
		int port = 0;
		try {
			/**
			 * 主节点
			 */
			WorkTransportServerInterface tran = RpcClient.getInstance().create(WorkTransportServerInterface.class,
					new URI("spdb", host, port));
			Long taskInstanceId = null;
			tran.killJob(taskInstanceId);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 作业列表-补数
	 */
	@PostMapping("/forceStartJob")
	@AccessLogAnnotation(isDbInstall = true)
	public int forceStartJob(String platform, String systems, String job, Integer multibatch, @DateTimeFormat(pattern = "yyyyMMdd") LocalDate localDate) {
		/**
		 * 主节点
		 */
		UdsServer udsServer = dbRegistryService.getMaster();
		try {
			MasterTransportServerInterfasce client = RpcClient.getInstance().create(
					MasterTransportServerInterfasce.class, new URI("spdb", udsServer.getIp(), udsServer.getPort()));
			List<String[]> jobList = new ArrayList<String[]>();
			jobList.add(new String[] { platform, systems, job });
			UdsComplement complement = new UdsComplement();
			complement.setBatchRange(multibatch.toString());
			complement.setStartTime(localDate.atStartOfDay());
			complement.setEndTime(localDate.atStartOfDay());
			complement.setComName(platform + Symbol.XIA_HUA_XIAN + systems + Symbol.XIA_HUA_XIAN + job);
			complement.setServerNameRange("");
			client.complementJob(jobList, complement);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return 1;
	}

}