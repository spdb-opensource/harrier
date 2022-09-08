package cn.spdb.harrier.api.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.system.MDictionaryService;
import cn.spdb.harrier.dao.entity.MDictionary;

@RestController
@RequestMapping("/mDictionary")
public class MDictionaryController {
	@Autowired
	private MDictionaryService service;

	@PreAuthorize("@HarrierPermission.hasAnyRoles('admin')")
	@RequestMapping(method = RequestMethod.GET)
	public Page<MDictionary> SearchClause(Page<MDictionary> page, String dicCode, String dicKey) {
		return service.search(page, dicCode, dicKey);
	}

	@AccessLogAnnotation(isDbInstall = true)
	@PutMapping("/add")
	public int add(MDictionary record) {
		return service.add(record);
	}

	@AccessLogAnnotation(isDbInstall = true,ignoreResponse = false)
	@DeleteMapping("/delete")
	public int delete(Long id) {
		return service.delete(id);
	}

	@AccessLogAnnotation(isDbInstall = true,ignoreResponse = false)
	@PostMapping("/update")
	public int update(MDictionary record) {
		return service.update(record);
	}

	@GetMapping("/getDetail")
	public MDictionary getDetail(Long id) {
		return service.getDetail(id);
	}

	@GetMapping("/listQuery")
	public List<MDictionary> listQuery() {
		return service.listQuery();
	}

	@GetMapping("/selectDicCode")
	public List<MDictionary> selectDicCode(String dicCode, String dicKey) {
		return service.selectDicCode(dicCode, dicKey);
	}

	@AccessLogAnnotation(isDbInstall = true,ignoreResponse = false)
	@DeleteMapping("{ids}/delete")
	public int delete(@PathVariable(value = "ids") Long[] ids) {
		return service.delete(ids);
	}
}