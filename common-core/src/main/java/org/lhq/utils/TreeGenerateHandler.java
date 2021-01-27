package org.lhq.utils;

import cn.hutool.core.collection.CollectionUtil;
import org.lhq.entity.vo.TreeModel;

import java.util.*;

public class TreeGenerateHandler {
    // 生成树结构数据
    public static List<TreeModel> treeModelToTree(List<TreeModel> treeModels) {
        return generateTree(treeModels);
    }
    private static List<TreeModel> generateTree( List<TreeModel> models){
        Map<Long, TreeModel> directoryMap = new LinkedHashMap<>();
        for (TreeModel model : models) {
            directoryMap.put(model.getId(),model);
        }
        List<TreeModel> resultTreeModels = new ArrayList<>();
        for (TreeModel treeModel : models) {
            if (treeModel.isRoot()){
                resultTreeModels.add(treeModel);
                continue;
            }
            TreeModel parentTreeModel = directoryMap.get(treeModel.getPid());
            if (parentTreeModel != null){
                Collection<TreeModel> children = CollectionUtil.isEmpty(parentTreeModel.getChildren())?new ArrayList<>():parentTreeModel.getChildren();
                children.add(treeModel);
                parentTreeModel.setChildren(children);
            }
        }
        return resultTreeModels;
    }
}
