package com.o2o.massage.core.utils;

/**
 * Created by zhuifengbuaa on 16/11/23.
 */

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片与BASE64编码互转工具类
 *
 * @author tongyao1@xiaomi.com
 */
public class ImageBase64Utils {

    private static final String BASE64_PNG_PREFIX = "data:image/png;base64,";
    private static final String BASE64_JPG_PREFIX = "data:image/jpg;base64,";
    private static final String BASE64_JPEG_PREFIX = "data:image/jpeg;base64,";
//    public static void main(String[] args) {
//        // 测试从Base64编码转换为图片文件
////        String strImg = "xxxxx"; \\图片BASE64字符串
////        GenerateImage(strImg, "D:\\wangyc.jpg");
//        // 测试从图片文件转换为Base64编码
//        //System.out.println(GetImageStr("d:\\0.jpg"));
//
////        GenerateImage(GetImageStr("d:\\0.jpg"), "D:\\000.jpg");
//        String s = GetImageStr("/Users/zhuifengbuaa/mockUser/不吃青椒.jpg");
//        System.out.println("s is : " + s);
//        GenerateImage(s, "/Users/zhuifengbuaa/mockUser/不吃青椒New.jpg");
//    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgFilePath 图片路径
     * @return String
     */
    public static String GetImageStr(String imgFilePath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param imgStr      Base64字符串
     * @param imgFilePath 生成图片保存路径
     * @return boolean
     */
    public static boolean GenerateImage(String imgStr, String imgFilePath) {
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param imgStr 图片字符串
     * @return byte[]
     */
    public static byte[] getStrToBytes(String imgStr) {
        if (imgStr == null) // 图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            String base64Pic = "data:image/jpeg;base64,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";
            int type = -1;
            if (base64Pic.indexOf(BASE64_PNG_PREFIX) != -1) {
                type = 0;
                base64Pic = base64Pic.replace(BASE64_PNG_PREFIX, "");
            }

            if (base64Pic.indexOf(BASE64_JPG_PREFIX) != -1) {
                type = 1;
                base64Pic = base64Pic.replace(BASE64_JPG_PREFIX, "");
            }

            if (base64Pic.indexOf(BASE64_JPEG_PREFIX) != -1) {
                type = 2;
                base64Pic = base64Pic.replace(BASE64_JPEG_PREFIX, "");
            }

            byte[] data = Base64.decodeBase64(base64Pic);
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage originalImgage = ImageIO.read(bis);
            BufferedImage SubImgage = originalImgage.getSubimage(0, 0, 100, 100);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(SubImgage, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            File outputfile = new File("/tmp/corp.jpg");
            OutputStream outputStream = null;
            outputStream =
                    new FileOutputStream(outputfile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            is.close();
            os.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
