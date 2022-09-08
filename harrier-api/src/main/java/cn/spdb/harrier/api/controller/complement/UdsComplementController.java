package cn.spdb.harrier.api.controller.complement;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.complement.UdsComplementService;
import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.dao.entity.UdsJobComplement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@RestController
@RequestMapping("/udsComplement")
public class UdsComplementController {
    @Autowired
    private UdsComplementService service;

    @AccessLogAnnotation(isDbInstall = true)
    @PutMapping("/add")
    public int add(UdsComplement record) {
        return service.add(record);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/update")
    public int update(UdsComplement record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsComplement getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsComplement> listQuery() {
        return service.listQuery();
    }
	/**
	 * 补数记录—查询记录(分页)
	 */
	@GetMapping("/selectAll")
	public Map<String, Object> selectAll(Page<UdsComplement> page, String comName) {
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsComplement> pageinfo = service.selectAll(page, comName);
		result.put("rows", pageinfo.getRecords());
		result.put("total", pageinfo.getTotal());
		return result;
	}
}