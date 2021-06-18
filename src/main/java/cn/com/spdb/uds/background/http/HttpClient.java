package cn.com.spdb.uds.background.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import io.netty.util.CharsetUtil;

public class HttpClient {

	private static final String IP = "127.0.0.1";
	private static final int PROT = 7878;

	private static URIBuilder URIBUILDER = null;

	private static RequestConfig requestConfig = null;

	public HttpClient() {
		URIBUILDER = new URIBuilder().setScheme("http").setHost(IP).setPort(PROT);
		requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).setRedirectsEnabled(true).build();
	}

	/**
	 * Body key : value
	 * 
	 * @param path
	 * @param baseObjecHashMap
	 *            基本类型对象
	 * @return
	 */
	public String sendPostHttpBody(String path, HashMap<String, Object> baseObjecHashMap) {
		return sendPostHttp(path, null, baseObjecHashMap);
	}

	/**
	 * 
	 * @param path
	 * @param baseObjectList
	 *            可序列化对象
	 * @return
	 */
	public String sendPostHttp(String path, List<Object> baseObjectList) {
		return sendPostHttpObject(path, null, baseObjectList);
	}

	/**
	 * 
	 * @param path
	 * @param paramsHashMap
	 *            UIR 字符串产参数
	 * @return
	 */
	public String sendPostHttp(String path, HashMap<String, String> paramsHashMap) {
		return sendPostHttpObject(path, paramsHashMap, null);
	}

	/**
	 * 直接发请求
	 * 
	 * @param path
	 * @return
	 */
	public String sendPostHttp(String path) {
		return sendPostHttpObject(path, null, null);
	}

	/**
	 * 
	 * @param path
	 *            UIR
	 * @param paramsHashMap
	 *            UIR 参数书
	 * @param baseObjecHashMap
	 *            基本数据类对象
	 * @return
	 */
	public String sendPostHttp(String path, HashMap<String, String> paramsHashMap, HashMap<String, Object> baseObjecHashMap) {
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		if (paramsHashMap != null) {
			for (Entry<String, String> entry : paramsHashMap.entrySet()) {
				paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			URI uri = URIBUILDER.setPath(path).setParameters(paramsList).build();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setConfig(requestConfig);

			if (baseObjecHashMap != null) {
				paramsList.clear();
				for (Entry<String, Object> entry : baseObjecHashMap.entrySet()) {
					String value = JSON.toJSONString(entry.getValue());
					paramsList.add(new BasicNameValuePair(entry.getKey(), value));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsList, CharsetUtil.UTF_8);
				httpPost.setEntity(formEntity);
			}
			httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity responsEntity = response.getEntity();
			if (responsEntity != null) {
				String string = EntityUtils.toString(responsEntity, CharsetUtil.UTF_8);
				return string;
			}
		} catch (URISyntaxException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} catch (ClientProtocolException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} catch (IOException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 
	 * @param path
	 *            UIR
	 * @param paramsHashMap
	 *            UIR参数
	 * @param baseObjectList
	 *            可序列化对象
	 * @return
	 */
	public String sendPostHttpObject(String path, HashMap<String, String> paramsHashMap, List<Object> baseObjectList) {
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		if (paramsHashMap != null) {
			for (Entry<String, String> entry : paramsHashMap.entrySet()) {
				paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			URI uri = URIBUILDER.setPath(path).setParameters(paramsList).build();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setConfig(requestConfig);

			if (baseObjectList != null) {
				paramsList.clear();
				for (Object object : baseObjectList) {
					String key = object.getClass().getName();
					String value = JSON.toJSONString(object);
					paramsList.add(new BasicNameValuePair(key, value));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsList, CharsetUtil.UTF_8);
				httpPost.setEntity(formEntity);
			}
			httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity responsEntity = response.getEntity();
			if (responsEntity != null) {
				String string = EntityUtils.toString(responsEntity, CharsetUtil.UTF_8);
				return string;
			}
		} catch (URISyntaxException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} catch (ClientProtocolException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} catch (IOException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 
	 * @param path
	 *            uir
	 * @return
	 */
	public String sendGetHttp(String path) {
		return sendGetHttp(path, null);
	}

	/**
	 * 
	 * @param path
	 * @param paramsHashMap
	 *            uir 参数
	 * @return
	 */
	public String sendGetHttp(String path, HashMap<String, String> paramsHashMap) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (paramsHashMap != null) {
			for (Entry<String, String> entry : paramsHashMap.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			URI uri = URIBUILDER.setPath(path).setParameters(params).build();
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setConfig(requestConfig);
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity responsEntity = response.getEntity();
			if (responsEntity != null) {
				String string = EntityUtils.toString(responsEntity, CharsetUtil.UTF_8);
				return string;
			}

		} catch (URISyntaxException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} catch (ClientProtocolException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} catch (IOException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			}
		}
		return null;
	}

	public static void main(String[] args) {
		HttpClient client = new HttpClient();
		HashMap<String, Object> paramsHashMap = new HashMap<String, Object>();
		String str="[{\"max_run_job\":30,\"system\":\"AAB\",\"platform\":\"HHA\"},{\"max_run_job\":30,\"system\":\"AAA\",\"platform\":\"HHA\"}]";
		paramsHashMap.put("systemlist", str);
	
		String httpEntity = client.sendPostHttpBody("/load/udssystem", paramsHashMap);

		// List<Map<String, Object>> list = new ArrayList<Map<String,
		// Object>>();
		// list.add(paramsHashMap);
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("list", list);
		System.out.println(httpEntity);
	}
}
