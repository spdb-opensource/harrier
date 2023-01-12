package cn.spdb.harrier.api;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;

import com.alibaba.fastjson.JSONObject;

import cn.spdb.harrier.api.model.RegisterBody;
import cn.spdb.harrier.common.utils.HttpUtils;

public abstract class AbstractHttpClientTest {

	private final String username = "test";
	private final String password = "test1234";

	protected HashMap<String, String> headerMap = new HashMap<String, String>();
	
	@Before
	public void login() {
		RegisterBody user = new RegisterBody();
		user.setUsername(username);
		user.setPassword(password);
		String str = HttpUtils.sendPostHttpObject("http://127.0.0.1:12345/harrier/login", user);
		JSONObject jsonObject=JSONObject.parseObject(str);
		String token= jsonObject.getJSONObject("data").getString("token");
		headerMap.put("token", token);
		System.out.println(str);
	}

	@After
	public void logout() {
		String str = HttpUtils.sendPostHttpAddHeader("http://127.0.0.1:12345/harrier/logout", headerMap);
		System.out.println(str);
	}


}
