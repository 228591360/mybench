package com.wb.bench.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;


public class QrGenerateMain {

    public static void main(String[] args) throws Exception{

//        FileOutputStream outputStream = new FileOutputStream("D:\\newpic.jpg");

       String imgPath = "/Users/wangbiao/logs/132131653446661.jpg";///Users/wangbiao/logs
        createImage("https://www.baidu.com/",350,350,imgPath);
    }

    public static void createImage(String text, int width, int height, String imgPath){
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height,hints);
             width = bitMatrix.getWidth();
             height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            // 插入图片
            //insertImage(image, imgPath, true);
            ImageIO.write(image,"jpg",new File("/Users/wangbiao/logs/newPic2.jpg"));

//            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertImage(BufferedImage source,String imgPath,Boolean needCompress) throws Exception{
        int QRCODE_SIZE = 350;
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(""+imgPath+"   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            width = 50;
            height = 50;
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
}
