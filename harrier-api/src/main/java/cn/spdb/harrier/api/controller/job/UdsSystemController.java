package cn.spdb.harrier.api.controller.job;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.rpc.transport.MasterTransportServerInterfasce;
import cn.spdb.harrier.api.service.job.UdsSystemService;
import cn.spdb.harrier.common.CommandConstant;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.UdsSystem;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.rpc.common.RpcMethod;
import cn.spdb.harrier.service.db.DbRegistryService;

@RestController
@RequestMapping("/udsSystem")
public class UdsSystemController {
	@Autowired
	private UdsSystemService service;

	@AccessLogAnnotation(isDbInstall = true)
	@PutMapping("/add")
	public int add(UdsSystem record) {
		return service.add(record);
	}

	@AccessLogAnnotation(isDbInstall = true)
	@DeleteMapping("/delete")
	public int delete(Integer id) {
		return service.delete(id);
	}

	@AccessLogAnnotation(isDbInstall = true)
	@PostMapping("/update")
	public int update(UdsSystem record) {
		return service.update(record);
	}

	@GetMapping("/getDetail")
	public UdsSystem getDetail(Integer id) {
		return service.getDetail(id);
	}

	@GetMapping("/listQuery")
	public List<UdsSystem> listQuery() {
		return service.listQuery();
	}

	@GetMapping("/selectAll")
	public Map<String, Object> selectAll(Page<UdsSystem> page, String platform, String systems, Boolean usePlatform,
			String server_role_name_group) {
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsSystem> pageinfo = service.selectAll(page, platform, systems, usePlatform, server_role_name_group);
		result.put("rows", pageinfo.getRecords());
		result.put("total", pageinfo.getTotal());
		return result;
	}

	@AccessLogAnnotation(isDbInstall = true)
	@PostMapping("/updateConcurrencyState")
	public int updateConcurrencyState(UdsSystem udsSystem) {
		int num = service.updateConcurrencyState(udsSystem);
		if (num > 0) {
			DbRegistryService dbRegistryService = BeanContext.getBean(DbRegistryService.class);
			if (ObjectUtils.isNotEmpty(dbRegistryService)) {
				dbRegistryService.getUdsServerList().stream()
						.filter(predicate -> predicate.getNodeClusterType().equals(Constants.THREAD_NAME_MASTER_SERVER)
								&& predicate.getIsEnable())
						.forEach(action -> {
							try {
								MasterTransportServerInterfasce client = RpcClient.getInstance().create(
										MasterTransportServerInterfasce.class,
										new URI("spdb", action.getIp(), action.getPort()));
								client.command(CommandConstant.LOAD_SYSTEM, udsSystem.getPlatform(),
										udsSystem.getSystems());
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e) {
								e.printStackTrace();
							}
						});
			}
		}
		return num;

	}

	@GetMapping("/selectSystem")
	public List<UdsSystem> selectSystem(String platform) {
		List<UdsSystem> list = service.selectByPlatform(platform);
		return list;
	}

	@AccessLogAnnotation(isDbInstall = true)
	@DeleteMapping("{ids}/delete")
	public int delete(@PathVariable(value = "ids") Integer[] ids) {
		return service.delete(ids);
	}
}