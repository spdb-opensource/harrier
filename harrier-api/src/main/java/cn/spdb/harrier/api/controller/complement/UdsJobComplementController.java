package cn.spdb.harrier.api.controller.complement;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.complement.UdsJobComplementService;
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
@RequestMapping("/udsJobComplement")
public class UdsJobComplementController {
    @Autowired
    private UdsJobComplementService service;
    @AccessLogAnnotation(isDbInstall = true)
    @PutMapping("/add")
    public int add(UdsJobComplement record) {
        return service.add(record);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/update")
    public int update(UdsJobComplement record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobComplement getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobComplement> listQuery() {
        return service.listQuery();
    }
    
    
	/**
	 * 补数记录—查询记录(分页)
	 */
	@GetMapping("/selectAll")
	public Map<String, Object> selectAll(Page<UdsJobComplement> page, String platform, String systems, String job,
			String lastStatus, Long complementId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsJobComplement> pageinfo = service.selectAll(page, platform, systems, job, lastStatus,complementId);
		result.put("rows", pageinfo.getRecords());
		result.put("total", pageinfo.getTotal());
		return result;
	}
}