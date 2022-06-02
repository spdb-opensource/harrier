
package cn.spdb.harrier.alarm.send.http;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.spdb.harrier.alarm.enums.SendStatus;
import cn.spdb.harrier.alarm.send.modle.AlarmResult;
import cn.spdb.harrier.common.uitls.JSONUtils;

/**
 * http  send message
 */
public class HttpSender {

    public static final Logger logger = LoggerFactory.getLogger(HttpSender.class);

    private String url;

    private final String headerParams;

    private final String bodyParams;

    private final String contentField;

    private final String requestType;

    private HttpRequestBase httpRequest;


    private static final String URL_SPLICE_CHAR = "?";

    /**
     * request type post
     */
    private static final String REQUEST_TYPE_POST = "POST";

    /**
     * request type get
     */
    private static final String REQUEST_TYPE_GET = "GET";

    private static final String DEFAULT_CHARSET = "utf-8";

    public HttpSender(Map<String, String> paramsMap) {
        url = paramsMap.get(HttpAlertConstants.URL);
        headerParams = paramsMap.get(HttpAlertConstants.HEADER_PARAMS);
        bodyParams = paramsMap.get(HttpAlertConstants.BODY_PARAMS);
        contentField = paramsMap.get(HttpAlertConstants.CONTENT_FIELD);
        requestType = paramsMap.get(HttpAlertConstants.REQUEST_TYPE);
    }

	public AlarmResult send(String msg) {

        AlarmResult alertResult = new AlarmResult();

        createHttpRequest(msg);

        if (httpRequest == null) {
            alertResult.setStatus(SendStatus.FAIL.name());
            alertResult.setMessage("Request types are not supported");
            return alertResult;
        }

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(httpRequest);
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity, DEFAULT_CHARSET);
            alertResult.setStatus(SendStatus.SUCC.name());
            alertResult.setMessage(resp);
        } catch (Exception e) {
            logger.error("send http alert msg  exception : {}", e.getMessage());
            alertResult.setStatus(SendStatus.FAIL.name());
            alertResult.setMessage("send http request  alert fail.");
        }

        return alertResult;
    }

    private void createHttpRequest(String msg) {
        if (REQUEST_TYPE_POST.equals(requestType)) {
            httpRequest = new HttpPost(url);
            setHeader();
            //POST request add param in request body
            setMsgInRequestBody(msg);
        } else if (REQUEST_TYPE_GET.equals(requestType)) {
            //GET request add param in url
            setMsgInUrl(msg);
            httpRequest = new HttpGet(url);
            setHeader();
        }
    }

    /**
     * add msg param in url
     */
    private void setMsgInUrl(String msg) {

        if (StringUtils.isNotBlank(contentField)) {
            String type = "&";
            //check splice char is & or ?
            if (!url.contains(URL_SPLICE_CHAR)) {
                type = URL_SPLICE_CHAR;
            }
            url = String.format("%s%s%s=%s", url, type, contentField, msg);
        }
    }

    /**
     * set header params
     */
    private void setHeader() {

        if (httpRequest == null) {
            return;
        }

        HashMap<String, Object> map = JSONUtils.parseObject(headerParams, HashMap.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            httpRequest.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
        }
    }

    /**
     * set body params
     */
    private void setMsgInRequestBody(String msg)  {
        ObjectNode objectNode = JSONUtils.parseObject(bodyParams);
        //set msg content field
        objectNode.put(contentField, msg);
        try {
            StringEntity entity = new StringEntity(bodyParams, DEFAULT_CHARSET);
            ((HttpPost)httpRequest).setEntity(entity);
        } catch (Exception e) {
            logger.error("send http alert msg  exception : {}", e.getMessage());
        }
    }

}
