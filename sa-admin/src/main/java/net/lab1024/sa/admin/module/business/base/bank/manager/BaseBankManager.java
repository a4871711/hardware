package net.lab1024.sa.admin.module.business.base.bank.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.business.base.bank.dao.BaseBankDao;
import net.lab1024.sa.admin.module.business.base.bank.domain.entity.BaseBankEntity;
import net.lab1024.sa.admin.module.business.base.bank.domain.vo.BaseBankVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行信息  Manager
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:28
 * @Copyright 赵嘉伟
 */
@Service
public class BaseBankManager extends ServiceImpl<BaseBankDao, BaseBankEntity> {

    public List<BaseBankVO> getBankListByCustId(Long custId) {
        return getBaseMapper().getBankListByCustId(custId);
    }

}
