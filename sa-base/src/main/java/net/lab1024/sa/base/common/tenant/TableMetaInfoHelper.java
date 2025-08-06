package net.lab1024.sa.base.common.tenant;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.google.common.collect.Maps;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

/**
 * 租户元数据助手
 *
 * @author Debenson
 * @since 2021-04-23 22:01
 */
public class TableMetaInfoHelper {
  private static final Map<String, ExTableInfo> TABLE_META_INFO = Maps.newHashMap();
  private static final Map<String, String> COLUMN_DEFAULT_VALUE_MAP = Maps.newHashMap();

  public static void register(@NonNull TableInfo tableInfo) {
    TABLE_META_INFO.put(tableInfo.getTableName().toLowerCase(), new ExTableInfo(tableInfo));
  }

  /**
   * 是否支持租户
   *
   * @param tableName
   * @return
   */
  public static boolean isTenantEntity(@NonNull String tableName) {
    tableName = tableName.toLowerCase().replaceAll("`", "");
    return Optional.ofNullable(TABLE_META_INFO.get(tableName)).map(ExTableInfo::isTenantEntity).orElse(false);
  }

  /**
   * 注册默认值
   *
   * @param tableName    表名
   * @param column       字段名称
   * @param defaultvalue 默认值
   */
  public static void registerDefaultValue(@NonNull String tableName, @NonNull String column, String defaultvalue) {
    COLUMN_DEFAULT_VALUE_MAP.put(buildDefaultValueKey(tableName, column), defaultvalue);
  }

  /**
   * 取表字段的默认值
   *
   * @param tableName 表名
   * @param column    字段名称
   * @return 默认值
   */
  public static String getDefaultValue(String tableName, String column) {
    return COLUMN_DEFAULT_VALUE_MAP.get(buildDefaultValueKey(tableName, column));
  }

  private static String buildDefaultValueKey(String tableName, String column) {
    return (tableName + "@" + column).toLowerCase();
  }

}
