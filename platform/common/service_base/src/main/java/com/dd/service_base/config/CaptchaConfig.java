package com.dd.service_base.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置类
 */
@Configuration
public class CaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha(){
        //验证码生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        //配置
        Properties properties = new Properties();
        //时候有边框
        properties.setProperty("kaptcha.border","no");
        //设置边框颜色
        properties.setProperty("kaptcha.border.color","105,179,98");
        //验证码
        properties.setProperty("kaptcha.session.key","code");
        //验证码文本字符颜色，默认黑
        properties.setProperty("kaptcha.textproducer.font.color","blue");
        //设置字体样式
        properties.setProperty("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
        //设置字体大小默认40
        properties.setProperty("kaptcha.textproducer.font.size","30");
        //验证码文本字符内容范围 默认abced2345678gfynmnpwx
//        properties.setProperty("kaptcha.textproducer.char.string","");
        //字符长度，默认5
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //字符间距，默认2
        properties.setProperty("kaptcha,textproducer.char.space","4");
        //验证码图片宽度，默认208
        properties.setProperty("kaptcha.image.width","100");
        //验证码图片高度，默认40
        properties.setProperty("kaptcha.image.height","40");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
