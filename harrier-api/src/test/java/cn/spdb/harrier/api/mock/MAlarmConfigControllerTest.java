package cn.spdb.harrier.api.mock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.api.AbstractControllerTest;
import cn.spdb.harrier.dao.entity.MAlarmConfig;

public class MAlarmConfigControllerTest extends AbstractControllerTest {

	
	@Test
	public void addConfig() {
		try {
			MAlarmConfig m= new MAlarmConfig();
			m.setSystems("*");
			m.setPlatform("EDW");
			MvcResult mvcResult = mockMvc
					.perform(post("/alarm/config/update")
							.header(TOKEN_KEY, TOKEN)
							.content(JSON.toJSONString(m))
//							.param("id", JSON.toJSONString(m))
							
							.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andReturn();
			String jsonstring = mvcResult.getResponse().getContentAsString();
			System.out.println(jsonstring);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
