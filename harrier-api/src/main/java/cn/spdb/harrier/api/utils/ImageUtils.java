
package cn.spdb.harrier.api.utils;



import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;

import java.awt.*;

import java.awt.image.BufferedImage;

import java.io.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * image utils
 */
public class ImageUtils {
	private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
	
    /**

     * 生成base64位图片验证码

     *

     * @return

     * @throws IOException

     */

    public static String createCaptchImage(String code) {

        int width = 160;

        int height = 60;

        int lines = 10;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = img.getGraphics();

        // 设置背景色

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, width, height);

        // 设置字体

        g.setFont(new Font("cmr10", Font.BOLD, (int) (height / 1.2)));

        // 随机数字

        Random r = new Random(System.currentTimeMillis());

        char[] codeChar = code.toCharArray();
        for (int i=0;i<code.length();i++) {

        	char a = codeChar[i];
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));

            g.setColor(c);

            g.drawString("" + a, 5 + i * width / 4, (int) (height / 1.3) + r.nextInt(height / 8));
        }

        // 干扰线

        for (int i = 0; i < lines; i++) {

            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));

            g.setColor(c);

            g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));

        }

        g.dispose();// 类似于流中的close()带动flush()---把数据刷到img对象当中

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        try {

            ImageIO.write(img, "jpg", outputStream);

            byte[] base64Img = Base64Utils.encode(outputStream.toByteArray());

            return "data:image/jpeg;base64," + new String(base64Img).replaceAll("\n", "");

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }

    }

}
