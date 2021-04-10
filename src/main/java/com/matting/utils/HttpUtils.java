package com.matting.utils;

import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Http Request
 */
public class HttpUtils {

    private static CloseableHttpClient client;

    static {
        client = HttpClientBuilder.create().build();
    }

    /**
     * Post Request To UploadFile
     * @param requestUrl request url
     * @param headers request headers
     * @param builder MultipartEntityBuilder
     * @return
     */
    public static Map<String, Object> doPostByFileReturnBytes(String requestUrl, Map<String, String> headers, MultipartEntityBuilder builder) {
        HttpPost post = new HttpPost(requestUrl);
        Map<String, Object> map = new HashMap();
        CloseableHttpResponse response = null;
        try {
            if (headers != null) {
                for (String key : headers.keySet()) {
                    post.addHeader(key, headers.get(key));
                }
            }
            try {
                post.setEntity(builder.build());
                response = client.execute(post);
                if (response.getStatusLine().getStatusCode() == 200) {
                    if (response.getEntity().getContentType().getValue().equals("application/json;charset=UTF-8")) {
                        map.put("json", EntityUtils.toString(response.getEntity(), "UTF-8"));
                    } else {
                        map.put("bytes", EntityUtils.toByteArray(response.getEntity()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Post Request To UploadFile
     * @param requestUrl request url
     * @param headers request headers
     * @param builder MultipartEntityBuilder
     * @return
     */
    public static String doPostByFileReturnStr(String requestUrl, Map<String, String> headers, MultipartEntityBuilder builder) {
        HttpPost post = new HttpPost(requestUrl);
        String result = "";
        CloseableHttpResponse response = null;
        try {
            if (headers != null) {
                for (String key : headers.keySet()) {
                    post.addHeader(key, headers.get(key));
                }
            }
            try {
                post.setEntity(builder.build());
                response = client.execute(post);
                if (response.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Post Request
     * @param requestUrl  request url
     * @param headers request headers
     * @param params request params
     * @return
     */
    public static String doPost(String requestUrl, Map<String, String> headers, Map<String, Object> params) {
        HttpPost post = new HttpPost(requestUrl);
        String result = null;
        CloseableHttpResponse response = null;
        try {
            if (headers != null) {
                for (String key : headers.keySet()) {
                    post.addHeader(key, headers.get(key));
                }
            }
            try {
                StringEntity entity = new StringEntity(new Gson().toJson(params), "UTF-8");
                post.setEntity(entity);
                response = client.execute(post);
                if (response.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get Request
     * @param uri request url
     * @param headers request headers
     * @return
     */
    public static String doGet(URI uri, Map<String, String> headers) throws URISyntaxException {
        HttpGet get = new HttpGet(uri);
        String result = null;
        CloseableHttpResponse response = null;
        try {
            if (headers != null) {
                for (String key : headers.keySet()) {
                    get.addHeader(key, headers.get(key));
                }
            }
            try {
                response = client.execute(get);
                if (response.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
