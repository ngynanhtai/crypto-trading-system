package com.aquariux.crypto.utils;

import com.aquariux.crypto.enums.SortDirectionEnum;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    public static Sort parseSort(String sortParam) {
        if (sortParam == null || sortParam.isEmpty()) {
            return Sort.unsorted();
        }

        String[] parts = sortParam.split(",");
        List<Sort.Order> orders = new ArrayList<>();

        for (String part : parts) {
            String[] elements = part.split(";");
            String field = elements[0];
            String direction = elements.length > 1 ? elements[1] : SortDirectionEnum.ASC.name();

            if (SortDirectionEnum.ASC.name().equalsIgnoreCase(direction)) {
                orders.add(Sort.Order.asc(field));
            } else {
                orders.add(Sort.Order.desc(field));
            }
        }

        return Sort.by(orders);
    }
}
