package org.lhq.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @program: wangdefa_graduation_project
 * @description: 被分享的文件
 * @author: Wang defa
 * @create: 2020-12-10 10:01
 */

@Data
@Entity
public class ShareFile {
    @Id
    @GeneratedValue
    private Long id;
    private String shareId;
    private Long fileId;
    private Boolean fileOrDir;
}
