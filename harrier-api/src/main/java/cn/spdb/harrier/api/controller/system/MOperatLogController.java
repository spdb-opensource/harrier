package cn.spdb.harrier.api.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.system.MOperatLogService;
import cn.spdb.harrier.dao.entity.MOperatLog;

@RestController
@RequestMapping("/mOperatLog")
public class MOperatLogController {
	@Autowired
    private MOperatLogService service;
	
	@GetMapping("/listQuery")
    public List<MOperatLog> listQuery() {
        return service.listQuery();
    }
    
	@GetMapping("/selectAll")
    public Page<MOperatLog> selectAll(Page<MOperatLog> page, String userCode, String operatType, String job){
		List<OrderItem> orders = new ArrayList<OrderItem>();
		orders.add(new OrderItem("operat_date", false));
		page.setOrders(orders);
		return service.search(page, userCode, operatType, job);
    }
}
