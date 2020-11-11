package org.gourd.hu.core.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.gourd.hu.core.base.dto.BaseFindDTO;
import org.gourd.hu.core.enums.SortEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 *
 * @author gourd.hu
 */
public class PageUtil {

    /**
     * 构建Page
     * @return
     */
    public static <T extends BaseFindDTO> Page buildPage (T baseFindDTO){
        Page page = new Page<>(baseFindDTO.getPageNo(),baseFindDTO.getPageSize());
        if (baseFindDTO.getOrderColumn() != null) {
            List<OrderItem> orderItems = new ArrayList<>(2);
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(baseFindDTO.getOrderColumn());
            if (baseFindDTO.getOrderType() != null && SortEnum.DESC.equals(baseFindDTO.getOrderType())) {
                orderItem.setAsc(false);
            }
            orderItems.add(orderItem);
            page.setOrders(orderItems);
        }
        return page;
    }

}
