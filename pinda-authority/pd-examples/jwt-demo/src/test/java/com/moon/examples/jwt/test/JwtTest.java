package com.moon.examples.jwt.test;

import cn.hutool.core.io.FileUtil;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于 jjwt 实现的 jwt 基础的测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 14:09
 * @description
 */
public class JwtTest {

    // 通过jjwt生成和解析jwt令牌，不使用签名算法
    @Test
    public void test1() {
        // 用于封装 jwt 的 header 部分
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "none"); // 不使用签名算法
        header.put("typ", "JWT"); // 指定令牌的类型，如果是jwt令牌统一都写为JWT

        // 用于封装 jwt 的 body 部分
        Map<String, Object> body = new HashMap<>();
        body.put("userId", "100");
        body.put("account", "admin");
        body.put("role", "admin");

        // 使用 jjwt 提供的 API 生成 jwt 令牌
        String jwt = Jwts.builder()
                .setHeader(header)
                .setClaims(body)
                .setId("101")
                .compact();
        System.out.println(jwt);

        // 使用 jjwt 提供的 API 解析 jwt 令牌
        Jwt result = Jwts.parser().parse(jwt);
        Header header1 = result.getHeader();
        Object body1 = result.getBody();
        System.out.println(header1);
        System.out.println(body1);
    }

    // 通过jjwt生成和解析jwt令牌，使用HS256签名算法
    @Test
    public void test2() {
        // 定义签名密钥
        String key = "MooNkirA";

        // 用于封装 jwt 的 header 部分
        Map<String, Object> header = new HashMap<>();
        header.put("alg", SignatureAlgorithm.HS256.getValue()); // 使用HS256签名算法
        header.put("typ", "JWT"); // 指定令牌的类型，如果是jwt令牌统一都写为JWT

        // 用于封装 jwt 的 body 部分
        Map<String, Object> body = new HashMap<>();
        body.put("userId", "100");
        body.put("account", "admin");
        body.put("role", "admin");

        // 使用 jjwt 提供的 API 生成 jwt 令牌
        String jwt = Jwts.builder()
                .setHeader(header)
                .setClaims(body)
                .setId("101")
                .signWith(SignatureAlgorithm.HS256, key) // 使用加密密钥
                .compact();
        System.out.println(jwt);

        // 使用 jjwt 提供的 API 解析 jwt 令牌
        Jwt result = Jwts.parser()
                .setSigningKey(key) // 设置解密密钥
                .parse(jwt);
        Header header1 = result.getHeader();
        Object body1 = result.getBody();
        System.out.println(header1);
        System.out.println(body1);
    }

    // 生成一对公钥和私钥，用于RS256算法
    @Test
    public void test3() throws Exception {
        //自定义随机密码（可以任意修改）
        String password = "MoonZero";
        // 保存的地址（可以任意修改）
        String path = "D:\\code\\pinda-authority-project\\pinda-authority\\pd-examples\\jwt-demo\\src\\main\\resources\\";

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        // 生成公钥
        FileUtil.writeBytes(publicKeyBytes, path + "pub.key");
        // 生成私钥
        FileUtil.writeBytes(privateKeyBytes, path + "pri.key");
    }

    // 通过jjwt生成和解析jwt令牌，使用RS256签名算法
    @Test
    public void test4() throws Exception {
        // 用于封装 jwt 的 header 部分
        Map<String, Object> header = new HashMap<>();
        header.put("alg", SignatureAlgorithm.RS256.getValue()); // 使用RS256签名算法
        header.put("typ", "JWT"); // 指定令牌的类型，如果是jwt令牌统一都写为JWT

        // 用于封装 jwt 的 body 部分
        Map<String, Object> body = new HashMap<>();
        body.put("userId", "100");
        body.put("account", "admin");
        body.put("role", "admin");

        // 使用 jjwt 提供的 API 生成 jwt 令牌
        String jwt = Jwts.builder()
                .setHeader(header)
                .setClaims(body)
                .setId("101")
                .signWith(SignatureAlgorithm.RS256, getPriKey()) // 生成jwt令牌时需要使用私钥
                .compact();
        System.out.println(jwt);

        // 使用 jjwt 提供的 API 解析 jwt 令牌
        Jwt result = Jwts.parser()
                .setSigningKey(getPubKey()) // 解密jwt令牌时需要使用公钥
                .parse(jwt);
        Header header1 = result.getHeader();
        Object body1 = result.getBody();
        System.out.println(header1);
        System.out.println(body1);
    }

    // 获取私钥
    public PrivateKey getPriKey() throws Exception {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("pri.key");
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    // 获取公钥
    public PublicKey getPubKey() throws Exception {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("pub.key");
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

}
