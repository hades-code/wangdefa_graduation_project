package org.lhq.gp.product.entity;

/**
 * @program: wangdefa_graduation_project
 * @description: User
 * @author: Wang defa
 * @create: 2020-08-19 16:39
 */


public class User {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
