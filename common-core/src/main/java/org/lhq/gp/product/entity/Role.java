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
    User(10,"用户"),
	/**
	 *氪金用户
	 */
    VipUser(11,"Vip用户"),
	/**
	 * 更有钱的氪金用户
	 */
    SuperVipUser(12,"超级Vip用户");

    /**
     * 角色代码
     */

    private Integer roleCode;
    /**
     * 角色名字
     */
    private String roleName;
	/**
	 * 角色描述
	 */
	private String description;
	/**
	 * 角色所拥有的空间
	 */
	private Integer storageSize;
	/**
	 *
	 */
	private Integer downloadSpeed;

     Role(Integer roleCode,String roleName){
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
