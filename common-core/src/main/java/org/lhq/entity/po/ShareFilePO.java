package org.lhq.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: admin-service
 * @description: 分享文件中间表
 * @author: Wang defa
 * @create: 2021-01-07 10:41
 */

@Data
@Accessors(chain = true)
public class ShareFilePO {
    private Long id;
    private Long fileId;
    private Boolean fileOrDir;
    private String shareLink;
}
