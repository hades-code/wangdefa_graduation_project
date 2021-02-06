package org.lhq.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

/**
 * @program: wangdefa_graduation_project
 * @description: User
 * @author: Wang defa
 * @create: 2020-08-19 16:39
 */
@Data
@Entity
@Accessors(chain = true)
public class User {
	@Id
    private Long id;

	@ApiModelProperty("用户名")
    private String username;

	@ApiModelProperty("密码")
    private String password;

    @Email
    private String email;
    //private Role role;

    @ApiModelProperty("用户状态")
    private Boolean status;

    @ApiModelProperty("备注")
    private String remark;


    private Double usedStorageSize;



    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;



}
