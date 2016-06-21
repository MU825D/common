package com.dxy.common.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpRequestUtils {
	private static final Logger LOGGER = Logger.getLogger(HttpRequestUtils.class);

	private static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL = 200;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_PER_ROUTE = 400;

	private static PoolingHttpClientConnectionManager cm = null;
	
	static {
		SSLContext sslContext = SSLContexts.createSystemDefault();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		        sslContext,
		        NoopHostnameVerifier.INSTANCE);
		
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()  
		           .register("http", PlainConnectionSocketFactory.getSocketFactory())  
		           .register("https", sslsf)  
		           .build();
		cm = new PoolingHttpClientConnectionManager(registry);
		
		cm.setMaxTotal(MAX_TOTAL);
		cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
	}

	public static CloseableHttpClient getCloseableHttpClient() {
		
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		return httpClient;

	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数(Json格式) 不需要返回结果
	 * @return
	 * @throws WxErrorException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpRequest(String url, String jsonParam) {
		// post请求返回结果
		CloseableHttpClient client = getCloseableHttpClient();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			StringEntity paramEntity = new StringEntity(jsonParam, CHARSET);
			httpPost.setEntity(paramEntity);
			response = client.execute(httpPost);
			int resultCode = response.getStatusLine().getStatusCode();
			// 返回码200，请求成功；其他情况都为请求出现错误
			if (HttpStatus.SC_OK == resultCode) {
				HttpEntity entity = response.getEntity();
				String resultJson = EntityUtils.toString(entity, CHARSET);
				return resultJson;
			} else {
				LOGGER.warn("-----------------请求出现错误，错误码:{}-----------------" + resultCode);
			}
		} catch (ClientProtocolException e) {
			LOGGER.error("ClientProtocolException:", e);
			LOGGER.warn("-----------------请求出现异常:{}-----------------", e);
		} catch (IOException e) {
			LOGGER.error("IOException:", e);
			LOGGER.warn("-----------------请求出现IO异常:{}-----------------", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(client);
		}
		return null;
	}

	/**
	 * 发送httpsPost请求
	 * 
	 * @param url
	 *            路径
	 * @throws WxErrorException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpsPost(String url, String jsonParam) {
		CloseableHttpClient client = getCloseableHttpClient();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			if(jsonParam != null){
				StringEntity paramEntity = new StringEntity(jsonParam, CHARSET);
				httpPost.setEntity(paramEntity);
			}
			response = client.execute(httpPost);
			int resultCode = response.getStatusLine().getStatusCode();
			// 返回码200，请求成功；其他情况都为请求出现错误
			if (HttpStatus.SC_OK == resultCode) {
				HttpEntity entity = response.getEntity();
				String resultJson = EntityUtils.toString(entity, CHARSET);
				return resultJson;
			} else {
				LOGGER.warn("-----------------请求出现错误，错误码:{}-----------------" + resultCode);
			}
		} catch (ClientProtocolException e) {
			LOGGER.error("ClientProtocolException:", e);
			LOGGER.warn("-----------------请求出现异常:{}-----------------", e);
		} catch (IOException e) {
			LOGGER.error("IOException:", e);
			LOGGER.warn("-----------------请求出现IO异常:{}-----------------", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(client);
		}
		return null;
	}

}
