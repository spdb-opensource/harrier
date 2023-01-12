
package cn.spdb.harrier.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.api.model.LoginBody;
import cn.spdb.harrier.api.model.RegisterBody;
import cn.spdb.harrier.api.service.system.SysRegisterService;
import cn.spdb.harrier.api.service.system.SysTokenService;
import cn.spdb.harrier.api.utils.Result;
import cn.spdb.harrier.common.utils.JSONUtils;

/**
 * abstract controller test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MonitorApplication.class })
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private SysRegisterService registerService;

	@Autowired
	private SysTokenService tokenService;

	protected String TOKEN;

	protected String TOKEN_KEY;
	
	private final String username = "test";
	private final String password = "test1234";

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		RegisterBody user = new RegisterBody();
		user.setUsername(username);
		user.setPassword(password);
		registerService.register(user);
		createToken();
		System.out.println(TOKEN);
		System.out.println(TOKEN_KEY=tokenService.getHeader());
	}

	@After
	public void after() throws Exception {
		tokenService.delLoginUser(TOKEN);
		System.out.println("ok");
	}


	private void createToken() {
		LoginBody loginBody = new LoginBody();
		loginBody.setUsername(username);
		loginBody.setPassword(password);
		try {
			MvcResult mvcResult = mockMvc
					.perform(post("/login").content(JSON.toJSONString(loginBody))
							.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andReturn();
			String jsonstring = mvcResult.getResponse().getContentAsString();
			Result<HashMap<String, String>> clazz=new Result<HashMap<String,String>>(); 
			Result<HashMap<String, String>> rst=JSONUtils.parseObject(jsonstring, clazz.getClass());
			TOKEN = rst.getData().get("token");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
