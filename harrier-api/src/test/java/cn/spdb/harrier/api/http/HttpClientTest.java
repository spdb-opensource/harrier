package cn.spdb.harrier.api.http;

import java.util.HashMap;

import org.junit.Test;

import cn.spdb.harrier.api.AbstractHttpClientTest;
import cn.spdb.harrier.common.utils.HttpUtils;

public class HttpClientTest extends AbstractHttpClientTest {

	@Test
	public void getMrole() {

		HashMap<String, String> param = new HashMap<String, String>();
//		param.put("platfrom", "BDP");
//		param.put("systems", "*");
		String str = HttpUtils.sendGetHttp("http://127.0.0.1:12345/harrier/menu", headerMap, param);
		System.out.println(str);
		str = HttpUtils.sendGetHttp("http://127.0.0.1:12345/harrier/menu", headerMap, param);
		System.out.println(str);
	}
}
