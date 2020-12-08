package org.lhq.gp.product.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
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
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    private String password;
    private Role role;
    private String status;
    private Double usedStorageSize;
    private LocalDateTime createTime;



}
