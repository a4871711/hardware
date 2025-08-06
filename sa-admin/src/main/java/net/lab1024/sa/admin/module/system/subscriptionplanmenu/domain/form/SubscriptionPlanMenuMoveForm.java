package net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 19:36 2025/3/25
 * @Version 1.0
 **/
@Data
public class SubscriptionPlanMenuMoveForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Long> menuIdList;

    private Integer subscriptionPlanMenu;
}
