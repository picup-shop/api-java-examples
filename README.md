# 皮卡智能抠图API接口项目示例代码

#### 使用示例
Clone下此项目，在ApiMattingExample.java下更改APIKEY，图片的输入路径和输出路径即可 注:(*示例代码仅供参考，如需使用请结合项目实际情况做出更改*)
```java
   public class ApiMattingExample {
   public static String APIKEY = "账号获取到的APIKEY"; 
    
   static {
      INPUT_IMAGE_PATH = "图片所在的路径";
      //OUT_PUT_PATH可以为有后缀名全路径，也可以为固定路径 + 加上图片名称和后缀方便测试每一个方法
      OUT_PUT_PATH = "设置你需要输出图片到指定的路径";
   }
   
   public static void main (String args[]) {
        //调用你所需要的接口方法,执行成功后到设置的输出路径查看图片修改的结果
        new ApiMattingExample().universalReturnsBinary();
     }
  }
```

##### 注：更多API接口的详细信息请参考官网的API文档(示例项目的代码会根据官网的API文档同步更新)
[皮卡智能抠图](http://www.picup.shop/apidoc-image-matting.html)

---
#### 关于我们
皮卡智能（英文名：PicUP.AI）是杭州王道起兮科技有限公司旗下产品

皮卡智能利用人工智能和计算机视觉的力量，提供各种各样的产品，使您的生活更容易，工作更富有成效。无论是人像裁剪、风格转换、绘画、图像增强、逆向图像搜索利基或通用图像分类、检测或语义分割任务，我们都能满足您的需求。让我们一起让人类变得更聪明！

#### 如有其他需求可以通过以下方式联系我们
- 邮箱
pikachu@picup.ai
- 微信
roymind
- 电话
4001180827
