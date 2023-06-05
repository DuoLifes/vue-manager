package com.dd.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME="sub";//荷载，用户名的key
    private static final String CLAIM_KEY_CREATED="created";//荷载，创建时间key
    /**
     * 项目中推荐使用此形式的密钥和过期时间，因为解耦合
     */
    @Value("${jwt.secret}")
    private String secret;//密钥
    @Value("${jwt.expiration}")
    private Long expiration;//过期时间

    /**
     * 不推荐使用这种，因为写死到了代码中
     */
    public static final long EXPIRE = 1000 * 60 * 60 * 24;                      //设置token过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";   //设置token密钥，我瞎写的，每个公司都有按自己规则生成的密钥

    //生成token字符串

    /**
     * 根据荷载生成token字符
     * @param id 密言1
     * @param nickname 密言2
     * @return
     */
    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()                                          //构建jwt字符串
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")                             //设置jwt头信息

                .setSubject("guli-user")                                           //分类，名字随便起的，不同的分类可以设置不同的过期
                .setIssuedAt(new Date())                                           //设置过期时间的计时起始值为当前时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))      //设置过期时间为当前时间+EXPIRE我们设定的过期时间

                .claim("id", id)                                                //token主体，这里放你需要的信息，我们实现登陆，就放用户登陆信息
                .claim("nickname", nickname)                                    //需要多少主体信息，就设置多少个claim属性

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)                    //签名哈希，根据指定规则和我们的密钥设定签名
                .compact();

        return JwtToken;
    }

    /**
     * 根据spring security 对象生成token
     * @param userDetails UserDetails对象，spring security会将用户信息保存在这个对象
     * @return
     */
    public String getJwtToken(UserDetails userDetails){

        //分组荷载，也就是主体信息，我们将用户名和有效时间封装到token中
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());

        String JwtToken = gennerateToken(claims);

        return JwtToken;
    }
    private String gennerateToken(Map<String,Object> claims){
        String JwtToken =Jwts.builder()                                          //构建jwt字符串
//                .setHeaderParam("typ", "JWT")
//                .setHeaderParam("alg", "HS256")                             //设置jwt头信息
//
//                .setSubject("dd-user")                                             //分类，名字随便起的，不同的分类可以设置不同的过期
                .setIssuedAt(new Date())                                           //设置过期时间的计时起始值为当前时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration))  //设置过期时间为当前时间+EXPIRE我们设定的过期时间
                .addClaims(claims)
//                .claim(claims)                          //token主体，这里放你需要的信息，我们实现登陆，就放用户登陆信息
//                .claim()                               //需要多少主体信息，就设置多少个claim属性
                .signWith(SignatureAlgorithm.HS512, secret)                    //签名哈希，根据指定规则和我们的密钥设定签名
                .compact();
        return JwtToken;
    }
    /**
     * 从token中获取登录用户名
     */
    public String getUsernameByToken(String token){
        String username;

        try {
            Claims claims = getClaimsFormToken(token); //获取主体内容
            //claims.get(CLAIM_KEY_USERNAME);
            username = claims.getSubject();//获取主体值，上面注释代码和这个都可以，获取的是CLAIM_KEY_USERNAME常量封装的key，对应的值
        }catch (Exception e){
            username = null;
            e.printStackTrace();
        }
        return username;

    }

    /**
     * 从token中获取荷载
     * @param token
     * @return
     */
    private Claims getClaimsFormToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();//解析token字符串，获取主体内容
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 从荷载中获取过期时间
     */
    private Date getExpiredDateFromToken(String token){
        Claims claimsFormToken = getClaimsFormToken(token);
        return claimsFormToken.getExpiration();
    }
    /**
     * 验证token是否失效,失效返回true，没失效返回false
     */
    private boolean isTokenExpired(String token){
        Date expiredDateFromToken = getExpiredDateFromToken(token);
//        boolean before = expiredDateFromToken.before(new Date());
        return expiredDateFromToken.before(new Date());//如果当前时间再过期时间之前，就是还没有到过期时间，返回true，否则返回false
    }

    /**
     * 判断token是否有效
     * @param jwtToken token字符串
     * @return
     */
    public boolean checkToken(String jwtToken,UserDetails userDetails) {
        if(StringUtils.isEmpty(jwtToken)) return false;//如果为空直接返回false表示失效
        String username;
        try {
             username = getUsernameByToken(jwtToken);//根据token获取用户名
        } catch (Exception e) {
            e.printStackTrace();
            username = null;
            return false;//获取不到用户名，直接失效
        }
        if(!username.equals(userDetails.getUsername())) return false;//如果token的用户名与当前UserDetails用户名不一致，直接失效
        if(isTokenExpired(jwtToken)) return false;//如果token失效，返回false
        return true;
    }
    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);//已经过期，表示可以被刷新
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token){
        Claims claimsFormToken = getClaimsFormToken(token);
        claimsFormToken.put(CLAIM_KEY_CREATED,new Date());
        return gennerateToken(claimsFormToken);
    }
}
