package cn.spdb.harrier.server.worker.task;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.spdb.harrier.server.entity.JobStepBean;

public class HttpTask extends AbstractTask {

	public HttpTask(JobStepBean jobStepBean) {
		super(jobStepBean);
	}

	@Override
	public void init() {
		logger.info("classetask start");
	}

	@Override
	public void handle() {
		int statusCode=903;
		try (CloseableHttpClient client = createHttpClient(); CloseableHttpResponse response = sendRequest(client)) {
			if (response == null) {
				logger.error("response is null");
				return;
			}
			statusCode = response.getStatusLine().getStatusCode();
			logger.info("response statuscode:{}", statusCode);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
			logger.info("body start :");
			logger.info(body);
			logger.info("body end :");
			if(HttpStatus.SC_OK==statusCode) {
				statusCode=0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("http error", e);
		}finally {
			setExitStatusCode(statusCode);			
		}
	}

	private CloseableHttpResponse sendRequest(CloseableHttpClient httpClient) {
		CloseableHttpResponse closeableHttpResponse = null;
		try {
			RequestBuilder requestBuilder = createRequsetBuilder();
			if (requestBuilder == null) {
				return null;
			}
			requestBuilder.setHeader("Content-Type", "application/json;charset=utf-8");
			logger.info("uri:{}", jobStepBean.getCmd());
			URI uri = URI.create(jobStepBean.getCmd());
			if (StringUtils.isNotEmpty(jobStepBean.getEnvs())) {
				requestBuilder.setEntity(jobStepBean.getHttpEntityOfEnv());
			}
			logger.info("entity:{}", requestBuilder.getEntity());
			if (StringUtils.isNotEmpty(jobStepBean.getPararmeter())) {
				requestBuilder.addParameters(jobStepBean.getNameValuePairOfParameter());
			}
			logger.info("parameter:{}", requestBuilder.getParameters());
			HttpUriRequest request = requestBuilder.setUri(uri).build();
			logger.info("exect request");
			closeableHttpResponse = httpClient.execute(request);
			logger.info("receive response");
		} catch (ClientProtocolException e) {
			logger.error("exce error", e);
		} catch (IOException e) {
			logger.error("io error", e);
		}
		return closeableHttpResponse;
	}

	private CloseableHttpClient createHttpClient() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
		HttpClientBuilder httpClientBuilder = HttpClients.custom().setDefaultRequestConfig(requestConfig);
		return httpClientBuilder.build();
	}

	private RequestBuilder createRequsetBuilder() {
		switch (jobStepBean.getStepType()) {
		case "HTTP_POST":
			return RequestBuilder.post();
		case "HTTP_GET":
			return RequestBuilder.get();
		case "HTTP_DELETE":
			return RequestBuilder.delete();
		case "HTTP_PUT":
			return RequestBuilder.put();
		case "HTTP_HEAD":
			return RequestBuilder.head();
		default:
			break;
		}
		logger.error("step type not httptype {}", jobStepBean.getStepType());
		return null;
	}
}
