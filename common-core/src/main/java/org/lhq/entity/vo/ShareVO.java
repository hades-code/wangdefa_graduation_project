package org.lhq.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lhq.entity.Directory;
import org.lhq.entity.Share;
import org.lhq.entity.UserFile;

import java.util.HashSet;

/**
 * @program: admin-service
 * @description: 分享视图对线
 * @author: Wang defa
 * @create: 2021-01-07 10:15
 */

@Data
@Accessors(chain = true)
public class ShareVO {
    private Share share;
    private HashSet<UserFile> userFiles;
    private HashSet<Directory> directories;
}
