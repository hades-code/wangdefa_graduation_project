package org.lhq.gp.product.entity;
/**
 * @author Wallace
 */
public enum  Role {
    /**
     * 超级管理员
     */
    God(0,"草鸡管理员"),
    /**
     * 普通管理员
     */
    Admin(1,"普通管理员"),
    /**
     * 普通用户
     */
    User(10,"用户");
    /**
     * 角色代码
     */
    private Integer roleCode;
    /**
     * 角色名字
     */
    private String roleName;

     Role(Integer roleCode,String roleName){
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
