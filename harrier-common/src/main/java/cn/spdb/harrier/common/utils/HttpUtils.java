package cn.spdb.harrier.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.common.Constants;

public class HttpUtils {
	public static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
			.setExpectContinueEnabled(Boolean.TRUE)
			.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
			.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
			.setConnectTimeout(Constants.HTTP_CONNECT_TIMEOUT).setSocketTimeout(Constants.SOCKET_TIMEOUT)
			.setConnectionRequestTimeout(Constants.HTTP_CONNECTION_REQUEST_TIMEOUT).setRedirectsEnabled(true).build();

	public static CloseableHttpClient getInstance() {
		return HttpClientInstance.httpClient;
	}

	private static class HttpClientInstance {
		private static final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
				.setDefaultRequestConfig(requestConfig).build();
	}

	private static PoolingHttpClientConnectionManager cm;

	private static SSLContext ctx = null;

	private static SSLConnectionSocketFactory socketFactory;

	private static Registry<ConnectionSocketFactory> socketFactoryRegistry;

	private static X509TrustManager xtm = new X509TrustManager() {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	static {
		try {
			ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
			ctx.init(null, new TrustManager[] { xtm }, null);
		} catch (NoSuchAlgorithmException e) {
			logger.error("SSLContext init with NoSuchAlgorithmException", e);
		} catch (KeyManagementException e) {
			logger.error("SSLContext init with KeyManagementException", e);
		}

		socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);

		socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();

		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setDefaultMaxPerRoute(60);
		cm.setMaxTotal(100);

	}

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
		CloseableHttpClient httpClient = getInstance();
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
		CloseableHttpClient httpClient = getInstance();
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

	public static String getResponseContentString(HttpRequestBase httpRequestBase, CloseableHttpClient httpClient) {
		String responseContent = null;
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpRequestBase);
			// check response status is 200
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseContent = EntityUtils.toString(entity, Constants.UTF_8);
				} else {
					logger.warn("http entity is null");
				}
			} else {
				logger.error("http get:{} response status code is not 200!", response.getStatusLine().getStatusCode());
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		} finally {
			try {
				if (response != null) {
					EntityUtils.consume(response.getEntity());
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			if (!httpRequestBase.isAborted()) {
				httpRequestBase.releaseConnection();
				httpRequestBase.abort();
			}
		}
		return responseContent;
	}

	public static String get(String url) {
		CloseableHttpClient httpclient = HttpUtils.getInstance();
		HttpGet httpget = new HttpGet(url);
		return getResponseContentString(httpget, httpclient);
	}

}
