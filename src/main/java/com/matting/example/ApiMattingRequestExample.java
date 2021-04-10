package com.matting.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.matting.api.ApiMattingRequest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ApiMattingRequestExample {

    //请登录picup.shop查看你的API密钥
    public static final String API_KEY;
    //请求地址
    public static final String REQUEST_URL;
    //json
    public static Gson gson;
    //API请求方法
    public static ApiMattingRequest apiMattingRequest;
    //输入的图片路径
    public static String INPUT_IMAGE_PATH;
    //输出指定路径 例如:new FileOutputStream(OUT_PUT_PATH + imageName + ".png");
    public static String OUT_PUT_PATH;
    //图片URL
    public static String IMAGE_URL;

    //初始化
    static {
        API_KEY = "";
        REQUEST_URL = "http://www.picup.shop/api/v1";
        gson = new GsonBuilder().create();
        apiMattingRequest = new ApiMattingRequest(API_KEY);
        INPUT_IMAGE_PATH = "/xxx/xxx/xx.jpeg";
        OUT_PUT_PATH = "/xxx/xxx/";
        IMAGE_URL = "";
    }

    /**
     * 人像抠图（返回二进制文件流）
     */
    public void portraitReturnsBinary() {
        String url = REQUEST_URL + "/matting";
        File file = new File(INPUT_IMAGE_PATH);
        //是否裁剪至最小非透明区域 （非必填）
        Boolean crop = null;
        //填充背景色 （非必填）
        String bgColor = "";
        try {
            //使用inputStream方式上传图片，注:使用此方式必须传filName参数
            //InputStream inputStream = new FileInputStream(file);
            //String fileName = file.getName();
            //apiMattingRequest.requestMattingReturnsBinary(url, inputStream, crop, bgColor, "fileName");
            byte[] bytes = apiMattingRequest.requestMattingReturnsBinary(url, file, crop, bgColor);
            if (bytes != null) {
                FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "");
                outputStream.write(bytes);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("portrait matting error:" + e.getMessage());
        }
    }

    /**
     * 人像抠图（返回Base64字符串）
     */
    public void portraitReturnsBase64() {
        String url = REQUEST_URL + "/matting2";
        File file = new File(INPUT_IMAGE_PATH);
        //是否裁剪（非必填）
        Boolean crop = null;
        //填充背景色 （非必填）
        String bgColor = "1E90FF";
        try {
            String resultData = apiMattingRequest.requestMattingReturnsBase64(url, file, crop, bgColor);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("portrait matting error:" + e.getMessage());
        }
    }

    /**
     * 人像抠图（通过图片URL返回Base64字符串）
     */
    public void portraitByImageUrl() {
        String url = REQUEST_URL + "/mattingByUrl";
        //抠图类型 1-人像抠图
        Integer mattingType = 1;
        //是否裁剪 （非必填）
        Boolean crop = null;
        //填充背景色 （非必填）
        String bgColor = "";
        //图片网络地址 (必填)
        String imageUrl = IMAGE_URL;
        try {
            String resultData = apiMattingRequest.requestMattingByImageUrl(url, crop, bgColor, imageUrl, mattingType);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) { // 请求成功正确返回数据
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("portrait matting error:" + e.getMessage());
        }
    }

    /**
     * 物体抠图（返回二进制文件流）
     */
    public void objectReturnsBinary() {
        String url = REQUEST_URL + "/matting?mattingType=2";
        File file = new File(INPUT_IMAGE_PATH);
        //是否裁剪至最小非透明区域 （非必填）
        Boolean crop = true;
        //填充背景色 （非必填）
        String bgColor = "BA55D3";
        try {
            byte[] bytes = apiMattingRequest.requestMattingReturnsBinary(url, file, crop, bgColor);
            if (bytes != null) {
                FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out put image path");
                outputStream.write(bytes);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("object matting error:" + e.getMessage());
        }
    }

    /**
     * 物体抠图（返回base64字符串）
     */
    public void objectReturnsBase64() {
        String url = REQUEST_URL + "/matting2?mattingType=2";
        File file = new File(INPUT_IMAGE_PATH);
        //是否裁剪至最小非透明区域 （非必填）
        Boolean crop = true;
        //填充背景色 （非必填）
        String bgColor = "EE8262";
        try {
            String resultData = apiMattingRequest.requestMattingReturnsBase64(url, file, crop, bgColor);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) { // 请求成功正确返回数据
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out put image path");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("object matting error:" + e.getMessage());
        }
    }

    /**
     * 物体抠图（通过图片地址返回base64字符串）
     */
    public void
    objectByImageUrl() {
        String url = REQUEST_URL + "/mattingByUrl";
        //抠图类型 1-人像抠图
        Integer mattingType = 2;
        //是否裁剪 （非必填）
        Boolean crop = true;
        //填充背景色 （非必填）
        String bgColor = "CD5555";
        //图片网络地址 (必填)
        String imageUrl = IMAGE_URL;
        try {
            String resultData = apiMattingRequest.requestMattingByImageUrl(url, crop, bgColor, imageUrl, mattingType);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) { // 请求成功正确返回数据
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out image path");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("object matting error:" + e.getMessage());
        }
    }

    /**
     * 通用抠图（返回二进制文件流）
     */
    public void universalReturnsBinary() {
        String url = REQUEST_URL + "/matting?mattingType=6";
        File file = new File(INPUT_IMAGE_PATH);
        //是否裁剪至最小非透明区域 （非必填）
        Boolean crop = false;
        //填充背景色 （非必填）
        String bgColor = "EE6363";
        try {
            byte[] bytes = apiMattingRequest.requestMattingReturnsBinary(url, file, crop, bgColor);
            if (bytes != null) {
                FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out put image path");
                outputStream.write(bytes);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("universal matting error:" + e.getMessage());
        }
    }

    /**
     * 通用抠图（返回base64字符串）
     */
    public void universalReturnsBase64() {
        String url = REQUEST_URL + "/matting2?mattingType=6";
        File file = new File(INPUT_IMAGE_PATH);
        //是否裁剪至最小非透明区域 （非必填）
        Boolean crop = null;
        //填充背景色 （非必填）
        String bgColor = "EE6363";
        try {
            String resultData = apiMattingRequest.requestMattingReturnsBase64(url, file, crop, bgColor);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) { // 请求成功正确返回数据
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out put image path");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("universal matting error:" + e.getMessage());
        }
    }

    /**
     * 通用抠图（通过图片URL返回Base64字符串）
     */
    public void universalByImageUrl() {
        String url = REQUEST_URL + "/mattingByUrl";
        //抠图类型 1-人像抠图
        Integer mattingType = 6;
        //是否裁剪 （非必填）
        Boolean crop = null;
        //填充背景色 （非必填）
        String bgColor = "EE6363";
        //图片网络地址 (必填)
        String imageUrl = IMAGE_URL;
        try {
            String resultData = apiMattingRequest.requestMattingByImageUrl(url, crop, bgColor, imageUrl, mattingType);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) { // 请求成功正确返回数据
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("universal matting error:" + e.getMessage());
        }
    }

    /**
     * 头像抠图（返回二进制文件流）
     */
    public void avatarReturnsBinary() {
        String url = REQUEST_URL + "/matting?mattingType=3";
        File file = new File(INPUT_IMAGE_PATH);
        //是否裁剪至最小非透明区域 （非必填）
        Boolean crop = null;
        try {
            byte[] bytes = apiMattingRequest.requestMattingReturnsBinary(url, file, crop, null);
            if (bytes != null) {
                FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out put image path");
                outputStream.write(bytes);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("object matting error:" + e.getMessage());
        }
    }

    /**
     * 头像抠图（返回base64字符串）
     */
    public void avatarReturnsBase64() {
        String url = REQUEST_URL + "/matting2?mattingType=3";
        File file = new File(INPUT_IMAGE_PATH);
        //是否裁剪至最小非透明区域 （非必填）
        Boolean crop = null;
        try {
            String resultData = apiMattingRequest.requestMattingReturnsBase64(url, file, crop, null);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) { // 请求成功正确返回数据
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("avatar matting error:" + e.getMessage());
        }
    }

    /**
     * 头像抠图（通过图片URL返回Base64字符串）
     */
    public void avatarByImageUrl() {
        String url = REQUEST_URL + "/mattingByUrl";
        //抠图类型 1-人像抠图
        Integer mattingType = 3;
        //是否裁剪 （非必填）
        Boolean crop = null;
        //图片网络地址 (必填)
        String imageUrl = IMAGE_URL;
        try {
            String resultData = apiMattingRequest.requestMattingByImageUrl(url, crop, null, imageUrl, mattingType);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) { // 请求成功正确返回数据
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("avatar matting error:" + e.getMessage());
        }
    }

    /**
     * 证件照
     */
    public void idPhoto() {
        String url = REQUEST_URL + "/idphoto/printLayout";
        Map<String, Object> params = new HashMap<>();
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(INPUT_IMAGE_PATH));
            String base64 = Base64.getEncoder().encodeToString(bytes);
            //头像文件图片的Base64
            params.put("base64", base64);
            //证件背景色
            params.put("bgColor", "FFFFFF");
            //证件照渐变背景色（非必填）
            //params.put("bgColor2", "");
            //证件照打印dpi，一般为300
            params.put("dpi", 300);
            //证件照物理高度
            params.put("mmHeight", 35);
            //证件照物理宽度
            params.put("mmWidth", 25);
            //排版背景色
            params.put("printBgColor", "FFFFFF");
            //打印排版尺寸，单位为毫米
            params.put("printMmHeight", 210);
            //打印排版尺寸，单位为毫米
            params.put("printMmWidth", 150);
            //换装参数,填此参数额外扣除一点点数
            //params.put("dress", "");
            String resultData = apiMattingRequest.requestMattingIdPhoto(url, params);
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    //输出TXT格式
                    PrintWriter printWriter = new PrintWriter(new FileWriter(OUT_PUT_PATH + ""));
                    printWriter.println(data.toString());
                    printWriter.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("id photo error:" + e.getMessage());
        }
    }

    /**
     * 一键美化（返回二进制文件流）
     */
    public void beautifyReturnsBinary() {
        String url = REQUEST_URL + "/matting?mattingType=4";
        File file = new File(INPUT_IMAGE_PATH);
        try {
            byte[] bytes = apiMattingRequest.requestMattingReturnsBinary(url, file);
            if (bytes != null) {
                FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "");
                outputStream.write(bytes);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("beautify matting error:" + e.getMessage());
        }
    }

    /**
     * 一键美化（返回Base64字符串）
     */
    public void beautifyReturnsBase64() {
        String url = REQUEST_URL + "/matting2?mattingType=4";
        File file = new File(INPUT_IMAGE_PATH);
        String resultData = apiMattingRequest.requestMattingReturnsBase64(url, file);
        try {
            if (!isEmpty(resultData)) {
                JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out put image path");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("beautify matting error:" + e.getMessage());
        }
    }

    /**
     * 图像修复
     */
    public void imageFix() {
        String url = REQUEST_URL + "/imageFix";
        Map<String, Object> params = new HashMap<>();
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(INPUT_IMAGE_PATH));
            String base64 = Base64.getEncoder().encodeToString(bytes);
            //图片文件base64字符串
            params.put("base64", base64);
            //矩形区域，支持多个数组
            List<Map<String, Integer>> rectangles = new ArrayList<>();
            Map<String, Integer> rectangle = new HashMap<>();
            rectangle.put("height", 100);
            rectangle.put("width", 100);
            rectangle.put("x", 150);
            rectangle.put("y", 280);
            params.put("rectangles", rectangles);
            //mask图片文件转为base64字符串, 同时支持单通道，三通道，四通道黑白图片，修复区域为纯白色，其它区域为黑色。如果此字段有值，则矩形区域参数无效
            //params.put("maskBase64", "maskBase64");
            String result = apiMattingRequest.requestMattingImageFix(url, params);
            if (!isEmpty(result)) {
                JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    //输出TXT格式以查看接口返回结果
                    PrintWriter printWriter = new PrintWriter(new FileWriter(OUT_PUT_PATH + ""));
                    printWriter.println(data.toString());
                    printWriter.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("image fix error:" + e.getMessage());
        }
    }

    /**
     * 动漫化（返回图片二进制流）
     */
    public void animeReturnsBinary() {
        String url = REQUEST_URL + "/matting?mattingType=11";
        File file = new File(INPUT_IMAGE_PATH);
        try {
            byte[] bytes = apiMattingRequest.requestMattingReturnsBinary(url, file);
            if (bytes != null) {
                FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out put image path");
                outputStream.write(bytes);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("anime matting error:" + e.getMessage());
        }
    }

    /**
     * 动漫化（返回base64字符串）
     */
    public void animeReturnsBase64() {
        String url = REQUEST_URL + "/matting2?mattingType=11";
        File file = new File(INPUT_IMAGE_PATH);
        try {
            String result = apiMattingRequest.requestMattingReturnsBase64(url, file);
            if (!isEmpty(result)) {
                JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "");
                    outputStream.write(bytes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("anime matting error:" + e.getMessage());
        }
    }

    /**
     * 动漫化（通过图片地址返回base64字符串）
     */
    public void animeByImageUrl() {
        String url = REQUEST_URL + "/mattingByUrl";
        //抠图类型
        Integer mattingType = 11;
        //图片地址
        String imageUrl = IMAGE_URL;
        try {
            String result = apiMattingRequest.requestMattingByImageUrl(url, null, null, imageUrl, mattingType);
            if (!isEmpty(result)) {
                JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String imageBase64 = data.get("imageBase64").getAsString();
                    byte[] bytes = Base64.getDecoder().decode(imageBase64);
                    FileOutputStream outputStream = new FileOutputStream(OUT_PUT_PATH + "out put image path");
                    outputStream.write(bytes);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("anime matting error:" + e.getMessage());
        }
    }

    /**
     * 风格化
     */
    public void styleTransfer() {
        String url = REQUEST_URL + "/styleTransferBase64";
        Map<String, Object> params = new HashMap<>();
        try {
            //待转化的图片Base64
            byte[] bytes = Files.readAllBytes(Paths.get(INPUT_IMAGE_PATH));
            String contentBase64 = Base64.getEncoder().encodeToString(bytes);
            params.put("contentBase64", contentBase64);
            //风格图片文件的Base64,此处可以选择自己想要的风格化图片
            byte[] bytes1 = Files.readAllBytes(Paths.get("INPUT_IMAGE_PATH"));
            String styleBase64 = Base64.getEncoder().encodeToString(bytes1);
            params.put("styleBase64", styleBase64);
            String result = apiMattingRequest.requestMattingStyleTransfer(url, params);
            if (!isEmpty(result)) {
                JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
                if (jsonObject.get("code").getAsInt() == 0) {
                    //输出TXT格式
                    PrintWriter printWriter = new PrintWriter(new FileWriter(OUT_PUT_PATH + "xxx.txt"));
                    printWriter.println(jsonObject.get("data").getAsString());
                    printWriter.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("style transfer error:" + e.getMessage());
        }
    }

    private boolean isEmpty(String str) {
        return (str == null || str.equals(""));
    }

    public static void main(String[] args) throws IOException {
        ApiMattingRequestExample example = new ApiMattingRequestExample();
        //example.portraitReturnsBinary();
        //example.portraitReturnsBase64();
        //example.portraitByImageUrl();
        //example.idPhoto();
        //example.objectReturnsBinary();
        //example.objectReturnsBase64();
        //example.objectByImageUrl();
        //example.universalReturnsBinary();
        //example.universalReturnsBase64();
        //example.universalByImageUrl();
        //example.avatarReturnsBinary();
        //example.avatarReturnsBase64();
        //example.avatarByImageUrl();
        //example.beautifyReturnsBinary();
        //example.beautifyReturnsBase64();
        //example.imageFix();
        //example.animeReturnsBinary();
        //example.animeReturnsBase64();
        //example.animeByImageUrl();
        //example.styleTransfer();
    }
}