package net.lab1024.sa.base.module.support.serialnumber.service;

import net.lab1024.sa.base.common.domain.RequestUser;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.base.module.support.serialnumber.domain.CompanySerialNumberUpdateForm;
import net.lab1024.sa.base.module.support.serialnumber.domain.vo.SerialNumberEntityVO;

import java.util.List;

/**
 * 单据序列号
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-25 21:46:07
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
public interface SerialNumberService {

    /**
     * 生成
     *
     * @param serialNumberIdEnum
     * @return
     */
    String generate(SerialNumberIdEnum serialNumberIdEnum, Long company);


    /**
     * 生成n个
     *
     * @param serialNumberIdEnum
     * @param count
     * @return
     */
    List<String> generate(SerialNumberIdEnum serialNumberIdEnum, int count, Long company);

    ResponseDTO<String> update(CompanySerialNumberUpdateForm companySerialNumberUpdateForm);

    ResponseDTO<SerialNumberEntityVO> getBySerialNumberId(Integer serialNumberId, RequestUser requestUser);
}
