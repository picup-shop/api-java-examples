package com.matting.api;

import com.matting.utils.HttpUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ApiMattingRequest {

    private final String apiKey;

    public ApiMattingRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    public byte[] requestMattingReturnsBinary(String requestUrl, InputStream inputStream, Boolean crop, String bgColor, String fileName) {
        MultipartEntityBuilder entityBuilder = setMultipartEntity(null, inputStream, crop, bgColor, fileName);
        Map<String, Object> map = HttpUtils.doPostByFileReturnBytes(requestUrl, getHeaders(), entityBuilder);
        if (map.containsKey("bytes")) {
            byte [] bytes = (byte[]) map.get("bytes");
            return bytes;
        }
        //请求错误时会返回JSON格式的数据
        System.out.println(map.get("json").toString());
        return null;
    }

    public byte[] requestMattingReturnsBinary(String requestUrl, File file, Boolean crop, String bgColor) {
        MultipartEntityBuilder entityBuilder = setMultipartEntity(file, null, crop, bgColor, null);
        Map<String, Object> map = HttpUtils.doPostByFileReturnBytes(requestUrl, getHeaders(), entityBuilder);
        if (map.containsKey("bytes")) {
            byte [] bytes = (byte[]) map.get("bytes");
            return bytes;
        }
        //请求错误时会返回JSON格式的数据
        System.out.println(map.get("json").toString());
        return null;
    }

    public byte[] requestMattingReturnsBinary(String requestUrl, File file) {
        MultipartEntityBuilder entityBuilder = setMultipartEntity(file, null, null, null, null);
        Map<String, Object> map = HttpUtils.doPostByFileReturnBytes(requestUrl, getHeaders(), entityBuilder);
        if (map.containsKey("bytes")) {
            byte [] bytes = (byte[]) map.get("bytes");
            return bytes;
        }
        System.out.println(map.get("json").toString());
        return null;
    }

    public byte[] requestMattingReturnsBinary(String requestUrl, InputStream inputStream, String fileName) {
        MultipartEntityBuilder entityBuilder = setMultipartEntity(null, inputStream, null, null, fileName);
        Map<String, Object> map = HttpUtils.doPostByFileReturnBytes(requestUrl, getHeaders(), entityBuilder);
        if (map.containsKey("bytes")) {
            byte [] bytes = (byte[]) map.get("bytes");
            return bytes;
        }
        System.out.println(map.get("json").toString());
        return null;
    }

    public String requestMattingReturnsBase64(String requestUrl, InputStream inputStream, Boolean crop, String bgColor, String fileName) {
        MultipartEntityBuilder entityBuilder = setMultipartEntity(null, inputStream, crop, bgColor, fileName);
        return HttpUtils.doPostByFileReturnStr(requestUrl, getHeaders(), entityBuilder);
    }

    public String requestMattingReturnsBase64(String requestUrl, File file, Boolean crop, String bgColor) {
        MultipartEntityBuilder entityBuilder = setMultipartEntity(file, null, crop, bgColor, null);
        return HttpUtils.doPostByFileReturnStr(requestUrl, getHeaders(), entityBuilder);
    }

    public String requestMattingReturnsBase64(String requestUrl, File file) {
        MultipartEntityBuilder entityBuilder = setMultipartEntity(file, null, null, null, null);
        return HttpUtils.doPostByFileReturnStr(requestUrl, getHeaders(), entityBuilder);
    }

    public String requestMattingReturnsBase64(String requestUrl, InputStream inputStream, String fileName) {
        MultipartEntityBuilder entityBuilder = setMultipartEntity(null, inputStream, null, null, fileName);
        return HttpUtils.doPostByFileReturnStr(requestUrl, getHeaders(), entityBuilder);
    }

    public String requestMattingIdPhoto(String requestUrl, Map<String, Object> params) {
        Map<String, String> headers = getHeaders();
        headers.put("Content-type", ContentType.APPLICATION_JSON.toString());
        return HttpUtils.doPost(requestUrl, headers, params);
    }

    public String requestMattingByImageUrl(String requestUrl, Boolean crop, String bgColor, String imageUrl, Integer mattingType) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(requestUrl);
        uriBuilder.addParameter("mattingType", String.valueOf(mattingType));
        uriBuilder.addParameter("url", imageUrl);
        if (crop != null) {
            uriBuilder.addParameter("crop", String.valueOf(crop));
        }
        if (bgColor != null && !bgColor.equals("")) {
            uriBuilder.addParameter("bgcolor", bgColor);
        }
        return HttpUtils.doGet(uriBuilder.build(), getHeaders());
    }

    public String requestMattingImageFix(String requestUrl, Map<String, Object> params) {
        Map<String, String> headers = getHeaders();
        headers.put("Content-type", ContentType.APPLICATION_JSON.toString());
        return HttpUtils.doPost(requestUrl, headers, params);
    }

    public String requestMattingStyleTransfer(String requestUrl, Map<String, Object> params) {
        Map<String, String> headers = getHeaders();
        headers.put("Content-type", ContentType.APPLICATION_JSON.toString());
        return HttpUtils.doPost(requestUrl, headers, params);
    }

    private MultipartEntityBuilder setMultipartEntity(File file, InputStream inputStream, Boolean crop, String bgColor, String fileName) {
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        //浏览器兼容模式
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (crop != null) {
            entityBuilder.addTextBody("crop", String.valueOf(crop));
        }
        if (bgColor != null && !bgColor.equals("")) {
            entityBuilder.addTextBody("bgcolor", bgColor);
        }
        if (file != null) {
            entityBuilder.addBinaryBody("file", file);
        }
        if (inputStream != null) {
            entityBuilder.addBinaryBody("file", inputStream, ContentType.DEFAULT_BINARY, fileName);
        }
        //此处也可以不加，默认就是MULTIPART_FORM_DATA类型
        entityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);
        return entityBuilder;
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("APIKEY", apiKey);
        return headers;
    }

}
