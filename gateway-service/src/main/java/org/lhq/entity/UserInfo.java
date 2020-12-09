package org.lhq.entity;

import java.util.Set;

/**
 * @program: wangdefa_graduation_project
 * @description: UserInfo
 * @author: Wang defa
 * @create: 2020-08-24 23:39
 */


public class UserInfo {
    /**
     * 用户的唯一标识
     */
    private String account;

    /**
     * accessToken的密钥，用于对accessToken进行加密和解密
     * 建议为每个用户配置不同的密钥（比如使用用户的password）
     */
    private String secret;

    /**
     * 用户权限集合，含义类似于Shiro中的perms
     */
    private Set<String> permissions;
}
