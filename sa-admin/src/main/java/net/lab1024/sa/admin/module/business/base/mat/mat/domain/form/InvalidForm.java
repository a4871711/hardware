package net.lab1024.sa.admin.module.business.base.mat.mat.domain.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 作废和反作废
 * @Author 赵嘉伟
 * @Date 19:37 2025/4/24
 * @Version 1.0
 **/
@Data
public class InvalidForm implements Serializable {

    /**
     * true 作废
     * false 反作废
     */
    private Boolean invalid;


    private List<Long> idList;


}
