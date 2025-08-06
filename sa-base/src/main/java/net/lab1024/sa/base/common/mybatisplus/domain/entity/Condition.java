package net.lab1024.sa.base.common.mybatisplus.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @className: Condition
 * @author: D7608
 * @date: 2025/5/8 15:05
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Condition extends StatisticalColumn {

    @Data
    public static class Criterion {
        private String field;
        private String operation;
        private Object value;
        private String sortBy;
    }

    private List<Criterion> conditionsFieldList;

    private Boolean alreadySelectedSoFilter;
}
