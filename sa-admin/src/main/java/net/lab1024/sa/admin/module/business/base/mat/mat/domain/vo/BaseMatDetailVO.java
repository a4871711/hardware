package net.lab1024.sa.admin.module.business.base.mat.mat.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.entity.BaseMatEntity;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.vo.BaseMatCustVO;
import net.lab1024.sa.base.common.json.serializer.FileKeyVoSerializer;

import java.util.List;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 15:08 2025/4/8
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseMatDetailVO extends BaseMatEntity {

    private String matTypeName;

    private String matTypeCode;


    @Schema(description = "来源属性")
    private String sourceProperty;

    @Schema(description = "图片")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String matImage;

    private String colorName;


    private List<BaseMatCustVO> baseMatCustList;
}
