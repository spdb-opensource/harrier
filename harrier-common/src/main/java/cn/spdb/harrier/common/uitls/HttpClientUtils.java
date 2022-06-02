package cn.spdb.harrier.common.uitls;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpClientUtils {

	private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
			.setConnectionRequestTimeout(5000).setSocketTimeout(5000).setRedirectsEnabled(true).build();

	/**
	 * 
	 * @param path
	 * @param object 可序列化对象
	 * @return
	 */
	public static String sendPostHttpObject(String path, Object object) {
		return sendPostHttp(path, null, null, object);
	}

	/**
	 * 
	 * @param path
	 * @param paramsMap UIR 字符串产参数
	 * @return
	 */
	public static String sendPostHttp(String path, HashMap<String, String> paramsMap) {
		return sendPostHttp(path, null, paramsMap, null);
	}

	/**
	 * 直接发请求
	 * 
	 * @param path
	 * @return
	 */
	public static String sendPostHttp(String path) {
		return sendPostHttp(path, null, null, null);
	}

	public static String sendPostHttpAddHeader(String path, HashMap<String, String> headerMap) {
		return sendPostHttp(path, headerMap, null, null);
	}

	public static String sendPostHttpObjectAddHeader(String path, HashMap<String, String> headerMap, Object object) {
		return sendPostHttp(path, headerMap, null, object);
	}

	/**
	 * 
	 * @param path
	 * @param headerMap
	 * @param paramsMap
	 * @param object
	 * @return
	 */
	public static String sendPostHttp(String path, HashMap<String, String> headerMap, HashMap<String, String> paramsMap,
			Object object) {
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		if (paramsMap != null) {
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			URI uri = new URIBuilder().setPath(path).setParameters(paramsList).build();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setConfig(requestConfig);

			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			httpPost.setHeader("Content-Type", "application/json;charset=utf-8");

			if (object != null) {
				if (Map.class.isInstance(object)) {
					paramsList.clear();
					HashMap<String, Object> objectMap = (HashMap<String, Object>) object;
					for (Entry<String, Object> entry : objectMap.entrySet()) {
						String value = JSON.toJSONString(entry.getValue());
						paramsList.add(new BasicNameValuePair(entry.getKey(), value));
					}
					UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsList, StandardCharsets.UTF_8);
					httpPost.setEntity(formEntity);

				} else {
					StringEntity stringEntity = new StringEntity(JSONUtils.toJsonString(object),
							ContentType.APPLICATION_JSON);
					httpPost.setEntity(stringEntity);
				}

			}

			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity responsEntity = response.getEntity();
			if (responsEntity != null) {
				String string = EntityUtils.toString(responsEntity, StandardCharsets.UTF_8);
				return string;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param path uir
	 * @return
	 */
	public static String sendGetHttp(String path) {
		return sendGetHttp(path, null, null);
	}

	public static String sendGetHttp(String path, HashMap<String, String> headerMap) {
		return sendGetHttp(path, headerMap, null);
	}

	/**
	 * 
	 * @param path
	 * @param paramsMap uir 参数
	 * @return
	 */
	public static String sendGetHttp(String path, HashMap<String, String> headerMap,
			HashMap<String, String> paramsMap) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (paramsMap != null) {
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			URI uri = new URIBuilder().setPath(path).setParameters(params).build();
			HttpGet httpGet = new HttpGet(uri);
			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}
			httpGet.setHeader("Content-Type", "application/json;charset=utf-8");
			httpGet.setConfig(requestConfig);
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity responsEntity = response.getEntity();
			if (responsEntity != null) {
				String string = EntityUtils.toString(responsEntity, StandardCharsets.UTF_8);
				return string;
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Boolean downLoadFormUrl(String urlStr, String filePath) {
		try {
			URL url = new URL(urlStr);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setConnectTimeout(3 * 1000);
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			byte[] buffer = new byte[1024];
			int len = 0;
			try (InputStream inputStream = conn.getInputStream();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
				outputStream.flush();
				buffer = outputStream.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			try (FileOutputStream fileOutputStream = new FileOutputStream(file);) {
				fileOutputStream.write(buffer);
				fileOutputStream.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
