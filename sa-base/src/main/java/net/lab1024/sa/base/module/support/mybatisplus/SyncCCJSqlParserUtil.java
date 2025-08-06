package net.lab1024.sa.base.module.support.mybatisplus;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.StringProvider;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;

import java.util.function.Consumer;


public class SyncCCJSqlParserUtil {
  public final static int ALLOWED_NESTING_DEPTH = 10;

  private SyncCCJSqlParserUtil() {
  }

  public static Statement parse(String sql) throws JSQLParserException {
    return parse(sql, null);
  }

  /**
   * Parses an sql statement while allowing via consumer to configure the used parser before.
   *
   * For instance to activate SQLServer bracket quotation on could use:
   *
   * {@code
   * CCJSqlParserUtil.parse("select * from [mytable]", parser -> parser.withSquareBracketQuotation(true));
   * }
   *
   * @param sql
   * @param consumer
   * @return
   * @throws JSQLParserException
   */
  public static Statement parse(String sql, Consumer<CCJSqlParser> consumer) throws JSQLParserException {
    Statement statement = null;

    // first, try to parse fast and simple
    try {
      CCJSqlParser parser = newParser(sql).withAllowComplexParsing(false);
      if (consumer != null) {
        consumer.accept(parser);
      }
      statement = parseStatement(parser);
    } catch (JSQLParserException ex) {
      if (getNestingDepth(sql)<=ALLOWED_NESTING_DEPTH) {
        CCJSqlParser parser = newParser(sql).withAllowComplexParsing(true);
        if (consumer != null) {
          consumer.accept(parser);
        }
        statement = parseStatement(parser);
      }
    }
    return statement;
  }

  public static CCJSqlParser newParser(String sql) {
    return new CCJSqlParser(new StringProvider(sql));
  }

  /**
   * @param parser
   * @return the statement parsed
   * @throws JSQLParserException
   */
  public static Statement parseStatement(CCJSqlParser parser) throws JSQLParserException {
    try {
      return parser.Statement();
    } catch (Exception ex) {
      throw new JSQLParserException(ex);
    }
  }

  /**
   * Parse a statement list.
   *
   * @return the statements parsed
   */
  public static Statements parseStatements(String sqls) throws JSQLParserException {
    return parseStatements(sqls, null);
  }

  /**
   * Parse a statement list.
   *
   * @return the statements parsed
   */
  public static Statements parseStatements(String sqls, Consumer<CCJSqlParser> consumer) throws JSQLParserException {
    Statements statements = null;

    // first, try to parse fast and simple
    try {
      CCJSqlParser parser = newParser(sqls).withAllowComplexParsing(false);
      if (consumer != null) {
        consumer.accept(parser);
      }
      statements = parseStatements(parser);
    } catch (JSQLParserException ex) {
      // when fast simple parsing fails, try complex parsing but only if it has a chance to succeed
      if (getNestingDepth(sqls)<=ALLOWED_NESTING_DEPTH) {
        CCJSqlParser parser = newParser(sqls).withAllowComplexParsing(true);
        if (consumer != null) {
          consumer.accept(parser);
        }
        statements = parseStatements(parser);
      }
    }
    return statements;
  }

  /**
   * @param parser
   * @return the statements parsed
   * @throws JSQLParserException
   */
  public static Statements parseStatements(CCJSqlParser parser) throws JSQLParserException {
    try {
      return parser.Statements();
    } catch (Exception ex) {
      throw new JSQLParserException(ex);
    }
  }

  public static int getNestingDepth(String sql) {
    int maxlevel=0;
    int level=0;

    char[] chars = sql.toCharArray();
    for (char c:chars) {
      switch(c) {
        case '(':
          level++;
          break;
        case ')':
          if (maxlevel<level) {
            maxlevel = level;
          }
          level--;
          break;
        default:
          // Codazy/PMD insists in a Default statement
      }
    }
    return maxlevel;
  }
}
