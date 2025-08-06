package net.lab1024.sa.base.module.support.serialnumber.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import net.lab1024.sa.base.common.domain.RequestUser;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.exception.BusinessException;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartEnumUtil;
import net.lab1024.sa.base.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.base.module.support.serialnumber.constant.SerialNumberRuleTypeEnum;
import net.lab1024.sa.base.module.support.serialnumber.dao.CompanySerialNumberDao;
import net.lab1024.sa.base.module.support.serialnumber.dao.SerialNumberDao;
import net.lab1024.sa.base.module.support.serialnumber.dao.SerialNumberRecordDao;
import net.lab1024.sa.base.module.support.serialnumber.domain.*;
import net.lab1024.sa.base.module.support.serialnumber.domain.vo.SerialNumberEntityVO;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单据序列号 基类
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-25 21:46:07
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
public abstract class SerialNumberBaseService implements SerialNumberService {

    @Resource
    protected SerialNumberRecordDao serialNumberRecordDao;

    @Resource
    protected SerialNumberDao serialNumberDao;

    public static final String SEPARATOR = "-";
    @Resource
    private CompanySerialNumberDao companySerialNumberDao;
    private ConcurrentHashMap<String, SerialNumberInfoBO> serialNumberMap = new ConcurrentHashMap<>();

    public abstract List<String> generateSerialNumberList(SerialNumberInfoBO serialNumber, int count);

    @PostConstruct
    void init() {
        // 查询所有的单据序列号
        List<SerialNumberEntity> serialNumberEntityList = serialNumberDao.selectList(null);
        if (serialNumberEntityList == null) {
            return;
        }
        List<Integer> serList = serialNumberEntityList.stream().map(SerialNumberEntity::getSerialNumberId).toList();

        List<CompanySerialNumberEntity> companySerialNumberEntityList = companySerialNumberDao.findAllBySerialNumberId(serList);
        for (SerialNumberEntity serialNumberEntity : serialNumberEntityList) {
            // 根据该单号生成器id获取公司自定义的单号生成器

            for (CompanySerialNumberEntity companySerialNumberEntity : companySerialNumberEntityList) {
                SerialNumberEntity copy = SmartBeanUtil.copy(companySerialNumberEntity, SerialNumberEntity.class);
                copy.setMenuName(serialNumberEntity.getMenuName());
                copy.setBusinessName(serialNumberEntity.getBusinessName());
                insertDefaultSerialNumberMap(copy, companySerialNumberEntity.getCompanyId());
            }
            initLastGenerateData(companySerialNumberEntityList);
        }

        //初始化数据

    }

    private void insertDefaultSerialNumberMap(SerialNumberEntity serialNumberEntity, Long companyId) {
        SerialNumberRuleTypeEnum ruleTypeEnum = SmartEnumUtil.getEnumByName(serialNumberEntity.getRuleType().toUpperCase(), SerialNumberRuleTypeEnum.class);
        if (ruleTypeEnum == null) {
            throw new ExceptionInInitializerError("cannot find rule type , id : " + serialNumberEntity.getSerialNumberId());
        }

        String format = serialNumberEntity.getFormat();
        int startIndex = format.indexOf("[n");
        int endIndex = format.indexOf("n]");
        if (startIndex == -1 || endIndex == -1 || endIndex <= startIndex) {
            throw new ExceptionInInitializerError("[nnn] 配置错误，请仔细查看 id : " + serialNumberEntity.getSerialNumberId());
        }

        String numberFormat = format.substring(startIndex + 1, endIndex + 1);

        if (serialNumberEntity.getStepRandomRange() < 1) {
            throw new ExceptionInInitializerError("random step range must greater than 1 " + serialNumberEntity.getSerialNumberId());
        }

        SerialNumberInfoBO serialNumberInfoBO = SerialNumberInfoBO.builder()
                .serialNumberId(serialNumberEntity.getSerialNumberId())
                .serialNumberRuleTypeEnum(ruleTypeEnum)
                .initNumber(serialNumberEntity.getInitNumber())
                .format(serialNumberEntity.getFormat())
                .stepRandomRange(serialNumberEntity.getStepRandomRange())
                .haveDayFlag(format.contains(SerialNumberRuleTypeEnum.DAY.getValue()))
                .haveMonthFlag(format.contains(SerialNumberRuleTypeEnum.MONTH.getValue()))
                .haveYearFlag(format.contains(SerialNumberRuleTypeEnum.YEAR.getValue()))
                .numberCount(endIndex - startIndex)
                .numberFormat("\\[" + numberFormat + "\\]")
                .companyId(companyId)
                .build();

        String mapKey = serialNumberEntity.getSerialNumberId() + SEPARATOR + companyId;
        this.serialNumberMap.put(mapKey, serialNumberInfoBO);
    }

    /**
     * 初始化上次生成的数据
     *
     * @param serialNumberEntityList
     */
    public abstract void initLastGenerateData(List<CompanySerialNumberEntity> serialNumberEntityList);

    @Override
    public String generate(SerialNumberIdEnum serialNumberIdEnum, Long company) {
        List<String> generateList = this.generate(serialNumberIdEnum, 1, company);
        if (generateList == null || generateList.isEmpty()) {
            throw new BusinessException("cannot generate : " + serialNumberIdEnum.toString());
        }
        return generateList.get(0);
    }

    @Override
    public List<String> generate(SerialNumberIdEnum serialNumberIdEnum, int count, Long company) {
        String mapKey = serialNumberIdEnum.getSerialNumberId() + SEPARATOR + company;
        SerialNumberInfoBO serialNumberInfoBO = serialNumberMap.get(mapKey);
        if (serialNumberInfoBO == null) {
            SerialNumberEntity serialNumberEntity = serialNumberDao.selectById(serialNumberIdEnum.getSerialNumberId());
            if (serialNumberEntity == null) {
                throw new BusinessException("cannot found SerialNumberId : " + serialNumberIdEnum.toString());
            }
            CompanySerialNumberEntity copy = SmartBeanUtil.copy(serialNumberEntity, CompanySerialNumberEntity.class);
            copy.setCompanyId(company);
            companySerialNumberDao.insert(copy);
            insertDefaultSerialNumberMap(serialNumberEntity, company);
            serialNumberInfoBO = serialNumberMap.get(mapKey);
        }
        return this.generateSerialNumberList(serialNumberInfoBO, count);
    }

    @Override
    public ResponseDTO<String> update(CompanySerialNumberUpdateForm companySerialNumberUpdateForm) {
        CompanySerialNumberEntity companySerialNumberEntity = SmartBeanUtil.copy(companySerialNumberUpdateForm, CompanySerialNumberEntity.class);
        Long companySerialNumberId = companySerialNumberEntity.getCompanySerialNumberId();
        if (companySerialNumberId == null) {
            //插入
            companySerialNumberDao.insert(companySerialNumberEntity);

        } else {
            //更新
            companySerialNumberDao.updateById(companySerialNumberEntity);
        }
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO<SerialNumberEntityVO> getBySerialNumberId(Integer serialNumberId, RequestUser requestUser) {
        // 查询自定义单号生成器
        SerialNumberEntity serialNumberEntity = serialNumberDao.selectById(serialNumberId);
        if (serialNumberEntity == null) {
            throw new BusinessException("未找到 SerialNumberId: " + serialNumberId);
        }

        // 查询公司关联的单号生成器
        CompanySerialNumberEntity companySerialNumberEntity = companySerialNumberDao.selectOne(
                new LambdaQueryWrapper<CompanySerialNumberEntity>()
                        .eq(CompanySerialNumberEntity::getSerialNumberId, serialNumberId)
                        .eq(CompanySerialNumberEntity::getCompanyId, requestUser.getCompanyId())
        );

        // 如果存在公司自定义数据，合并属性
        if (companySerialNumberEntity != null) {
            SmartBeanUtil.copyProperties(companySerialNumberEntity, serialNumberEntity);
        }

        // 转换为 VO 并设置额外属性
        SerialNumberEntityVO resultVO = SmartBeanUtil.copy(serialNumberEntity, SerialNumberEntityVO.class);
        if (companySerialNumberEntity != null) {
            resultVO.setCompanySerialNumberId(companySerialNumberEntity.getCompanySerialNumberId());
        }

        return ResponseDTO.ok(resultVO);
    }

    /**
     * 循环生成 number 集合
     *
     * @param lastGenerate
     * @param serialNumberInfo
     * @param count
     * @return
     */
    protected SerialNumberGenerateResultBO loopNumberList(SerialNumberLastGenerateBO lastGenerate, SerialNumberInfoBO serialNumberInfo, int count) {
        Long lastNumber = lastGenerate.getLastNumber();
        boolean isReset = false;
        if (isResetInitNumber(lastGenerate, serialNumberInfo)) {
            lastNumber = serialNumberInfo.getInitNumber();
            isReset = true;
        }

        ArrayList<Long> numberList = Lists.newArrayListWithCapacity(count);
        for (int i = 0; i < count; i++) {
            Integer stepRandomRange = serialNumberInfo.getStepRandomRange();
            if (stepRandomRange > 1) {
                lastNumber = lastNumber + RandomUtils.nextInt(1, stepRandomRange + 1);
            } else {
                lastNumber = lastNumber + 1;
            }

            numberList.add(lastNumber);
        }

        return SerialNumberGenerateResultBO
                .builder()
                .serialNumberId(serialNumberInfo.getSerialNumberId())
                .lastNumber(lastNumber)
                .lastTime(LocalDateTime.now())
                .numberList(numberList)
                .isReset(isReset)
                .build();
    }

    protected void saveRecord(SerialNumberGenerateResultBO resultBO, Long companyId) {
        Long effectRows = serialNumberRecordDao.updateRecord(resultBO.getSerialNumberId(),
                resultBO.getLastTime().toLocalDate(),
                resultBO.getLastNumber(),
                resultBO.getNumberList().size(),
                companyId
        );

        // 需要插入
        if (effectRows == null || effectRows == 0) {
            SerialNumberRecordEntity recordEntity = SerialNumberRecordEntity.builder()
                    .serialNumberId(resultBO.getSerialNumberId())
                    .recordDate(LocalDate.now())
                    .lastTime(resultBO.getLastTime())
                    .lastNumber(resultBO.getLastNumber())
                    .count((long) resultBO.getNumberList().size())
                    .companyId(companyId)
                    .build();
            serialNumberRecordDao.insert(recordEntity);
        }

    }

    /**
     * 若不在规则周期内，重制初始值
     *
     * @return
     */
    private boolean isResetInitNumber(SerialNumberLastGenerateBO lastGenerate, SerialNumberInfoBO serialNumberInfo) {
        LocalDateTime lastTime = lastGenerate.getLastTime();
        if (lastTime == null) {
            return true;
        }

        SerialNumberRuleTypeEnum serialNumberRuleTypeEnum = serialNumberInfo.getSerialNumberRuleTypeEnum();
        int lastTimeYear = lastTime.getYear();
        int lastTimeMonth = lastTime.getMonthValue();
        int lastTimeDay = lastTime.getDayOfYear();

        LocalDateTime now = LocalDateTime.now();

        switch (serialNumberRuleTypeEnum) {
            case YEAR:
                return lastTimeYear != now.getYear();
            case MONTH:
                return lastTimeYear != now.getYear() || lastTimeMonth != now.getMonthValue();
            case DAY:
                return lastTimeYear != now.getYear() || lastTimeDay != now.getDayOfYear();
            default:
                return false;
        }
    }

    /**
     * 替换特殊rule，即替换[yyyy][mm][dd][nnn]等规则
     */
    protected List<String> formatNumberList(SerialNumberGenerateResultBO generateResult, SerialNumberInfoBO serialNumberInfo) {

        /**
         * 第一步：替换年、月、日
         */
        LocalDate lastTime = generateResult.getLastTime().toLocalDate();
        String year = String.valueOf(lastTime.getYear());
        String month = lastTime.getMonthValue() > 9 ? String.valueOf(lastTime.getMonthValue()) : "0" + lastTime.getMonthValue();
        String day = lastTime.getDayOfMonth() > 9 ? String.valueOf(lastTime.getDayOfMonth()) : "0" + lastTime.getDayOfMonth();

        // 把年月日替换
        String format = serialNumberInfo.getFormat();

        if (serialNumberInfo.getHaveYearFlag()) {
            format = format.replaceAll(SerialNumberRuleTypeEnum.YEAR.getRegex(), year);
        }
        if (serialNumberInfo.getHaveMonthFlag()) {
            format = format.replaceAll(SerialNumberRuleTypeEnum.MONTH.getRegex(), month);
        }
        if (serialNumberInfo.getHaveDayFlag()) {
            format = format.replaceAll(SerialNumberRuleTypeEnum.DAY.getRegex(), day);
        }


        /**
         * 第二步：替换数字
         */

        List<String> numberList = Lists.newArrayListWithCapacity(generateResult.getNumberList().size());
        for (Long number : generateResult.getNumberList()) {
            StringBuilder numberStringBuilder = new StringBuilder();
            int currentNumberCount = String.valueOf(number).length();
            //数量不够，前面补0
            if (serialNumberInfo.getNumberCount() > currentNumberCount) {
                int remain = serialNumberInfo.getNumberCount() - currentNumberCount;
                for (int i = 0; i < remain; i++) {
                    numberStringBuilder.append(0);
                }
            }
            numberStringBuilder.append(number);
            //最终替换
            String finalNumber = format.replaceAll(serialNumberInfo.getNumberFormat(), numberStringBuilder.toString());
            numberList.add(finalNumber);
        }
        return numberList;
    }


}
