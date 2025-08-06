package net.lab1024.sa.base.common.tenant;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;
import org.springframework.util.ClassUtils;

/**
 * 扩展的表元数据
 *
 * @author Debenson
 * @since 2021-04-23 22:02
 */
@Data
public class ExTableInfo {

  /**
   * 支持租户
   */
  private final boolean tenantEntity;


  /**
   * 元数据
   */
  private final TableInfo tableInfo;

  public ExTableInfo(TableInfo tableInfo) {
    this.tableInfo = tableInfo;
    this.tenantEntity = ClassUtils.isAssignable(CompanyBaseEntity.class, tableInfo.getEntityType());
  }

}
