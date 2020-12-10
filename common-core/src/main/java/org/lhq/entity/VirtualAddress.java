package org.lhq.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @program: wangdefa_graduation_project
 * @description: 虚拟地址
 * @author: Wang defa
 * @create: 2020-12-08 20:30
 */

@Entity
@Data
public class VirtualAddress {
    @Id
    @GeneratedValue
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long shareId;
    private Boolean dirOrFile;
    private Long dirOrFileId;
    private String name;
    private String fileMd5;
    private Double fileSize;
    private Date createTime;
    private Date modifyTime;
}
