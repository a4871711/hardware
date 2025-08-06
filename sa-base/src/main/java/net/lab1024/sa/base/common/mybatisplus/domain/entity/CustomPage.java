package net.lab1024.sa.base.common.mybatisplus.domain.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 自定义分页对象，支持统计功能
 *
 * @Author 赵嘉伟
 * @Date 22:03 2025/6/8
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomPage<T> extends Page<T> {

    /**
     * 统计的列的结果
     */
    private Map<String, Object> statisticsResultMap;

    public CustomPage(long current, long size) {
        super(current, size, 0);
    }

}
