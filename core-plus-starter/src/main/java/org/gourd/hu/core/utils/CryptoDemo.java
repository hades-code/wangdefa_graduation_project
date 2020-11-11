package org.gourd.hu.core.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 密码加解密工具
 * @author gourd.hu
 */
public class CryptoDemo {

    public static void main(String[] args) throws Exception {
        // jasypt
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("GOURD-HXN-1314");
        // 加密
        String password = textEncryptor.encrypt("gourd123");
        System.out.println("^0^===password:"+ password);
        // 解密
        String originPwd = textEncryptor.decrypt("4M6RKeFuZ7OngpmunjkMm/a+W8eCJrsF");
        System.out.println("^0^===originPwd:"+ originPwd);
    }
}