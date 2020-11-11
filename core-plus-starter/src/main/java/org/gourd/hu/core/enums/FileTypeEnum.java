package org.gourd.hu.core.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 附件类型
 *
 * @author gourd.hu
 * @create 2018-07-04 15:41
 **/
@JSONType(serializeEnumAsJavaBean = true)
@AllArgsConstructor
public enum FileTypeEnum implements IEnum<String> {
    PPT("PPT","ppt"),
    FILE("FILE","文本"),
    WORD("WORD","word"),
    EXCEL("EXCEL","excel"),
    PDF("PDF","pdf"),
    VIDEO("VIDEO","视频"),
    TXT("TXT","文本"),
    PNG("PNG","图片"),
    BPM("BPM","bpm"),
    JPG("JPG","图片"),
    GIF("GIF","gif");


    @Getter
    @Setter
    private String value;

    @Getter
    @Setter
    private String label;


    /**
     * 获取枚举labels
     * @return
     */
    public static String[] getLabels(){
        String[] labels = new String[2];
        FileTypeEnum[] values = FileTypeEnum.values();
        for(int i=0;i< values.length;i++){
            labels[i] = values[i].getLabel();
        }
        return labels;
    }

    /**
     * 根据label获取枚举
     * @param label
     * @return
     */
    public static FileTypeEnum getByLabel(String label){
        FileTypeEnum[] values = FileTypeEnum.values();
        for(int i=0;i< values.length;i++){
            if(values[i].getLabel().equals(label)){
                return values[i];
            }
        }
        return null;
    }
}
