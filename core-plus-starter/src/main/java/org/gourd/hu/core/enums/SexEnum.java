package org.gourd.hu.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 性别枚举类
 *
 * @author gourd.hu
 * @create 2018-07-04 15:41
 **/
@AllArgsConstructor
public enum SexEnum implements IEnum<String> {
    M("M","男"),
    F("F","女"),
    X("X","未知");

    @Getter
    @Setter
    @EnumValue
    private String value;

    @Getter
    @Setter
    @JsonValue
    private String label;

    /**
     * 获取枚举labels
     * @return
     */
    public static String[] getLabels(){
        String[] labels = new String[2];
        SexEnum[] values = SexEnum.values();
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
    public static SexEnum getByLabel(String label){
        SexEnum[] values = SexEnum.values();
        for(int i=0;i< values.length;i++){
            if(values[i].getLabel().equals(label)){
                return values[i];
            }
        }
        return null;
    }
}