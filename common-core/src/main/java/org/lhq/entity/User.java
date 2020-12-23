package org.lhq.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
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
@ApiModel("用户实体")
public class User {
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    @Email
    private String email;
    private Role role;
    private String status;
    private Double usedStorageSize;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;



}
