package org.lhq.entity.bo;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
@Getter
@Setter
@Accessors(chain = true)
public class TreeModel {
    private Collection<TreeModel> children;
    private Long id;
    private Long pid;
    private String name;
    private String type;
    private boolean root;


    public TreeModel(Long id, Object name, Long pid, Object root) {
        this(id, name, pid);
        this.setRoot(root);
    }


    public TreeModel(Long id, Object name, Long pid) {
        if (id != null) {
            this.id = id;
        }
        if (name != null) {
            this.name = name.toString();
        }
        if (pid != null) {
            this.pid = pid;
        }
    }

    public void setRoot(Object rootFlag) {
        if (null != rootFlag && StrUtil.isNotBlank(rootFlag.toString())) {
            if (null == this.pid) {
                this.root = false;
            } else {
                this.root = rootFlag.equals(this.pid);
            }
        } else {
            this.root = null == this.pid;
        }
    }
}
