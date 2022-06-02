package cn.spdb.harrier.api.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.system.MDictionaryService;
import cn.spdb.harrier.dao.entity.MDictionary;

@RestController
@RequestMapping("/mDictionary")
public class MDictionaryController {
	@Autowired
	private MDictionaryService service;

	@RequestMapping(method = RequestMethod.GET)
	public Page<MDictionary> SearchClause(Page<MDictionary> page) {
		return service.search(page);
	}

	@PutMapping("/add")
	public int add(MDictionary record) {
		return service.add(record);
	}

	@DeleteMapping("/delete")
	public int delete(Long id) {
		return service.delete(id);
	}

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
}