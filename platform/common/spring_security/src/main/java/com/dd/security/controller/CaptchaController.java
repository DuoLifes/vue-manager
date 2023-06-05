package com.dd.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "验证码,以image/jpeg格式固定响应")
    @GetMapping(value = "/captcha",produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response)  {

        /**定义response输出类型为image/jpeg类型**/
        response.setDateHeader("Expires",0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers(use addHeader)
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma","no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        /**生成验证码 begin**/
        String code = defaultKaptcha.createText();
        System.out.println("验证码："+code);
        //将验证码文本内容放入session
//        request.getSession().setAttribute("captcha",code);
        //将验证码文本内容放入redis,
        //获取用户ip作为键值
        String remoteAddr = request.getRemoteAddr();
        //获取redis对象
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //设置验证码到redis，时限60秒，key为ip地址+"captcha"
        valueOperations.set(remoteAddr+"captcha",code,60, TimeUnit.SECONDS);
        //根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(code);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出.jpg格式图片
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
