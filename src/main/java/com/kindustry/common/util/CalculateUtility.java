package com.kindustry.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;

public final class CalculateUtility {

  /**
   * 公式计算
   * 
   * @param expression
   * @return
   */
  public static Number calculate(String expression) {
    /*
     * 初始化一个JexlContext对象，它代表一个执行JEXL表达式的上下文环境
     */
    JexlContext context = JexlHelper.createContext();
    Number number = null;
    Expression e;
    try {
      e = ExpressionFactory.createExpression(expression);
      // 对这个Expression对象求值，传入执行JEXL表达式的上下文环境对象
      number = (Number)e.evaluate(context);
      // 输出表达式求值结果
      System.out.println(e.getExpression() + " = " + number);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return number;
  }

  /**
   * 计算相加结果(按照a、b最大精度)
   * 
   * @param one
   *          数字1
   * @param two
   *          数字2
   * @return
   */
  public static BigDecimal add(BigDecimal one, BigDecimal two) {
    return one.add(two);
  }

  /**
   * 多数值 计算相加结果
   * 
   * @param objs
   * @return
   */
  public static BigDecimal add(BigDecimal... objs) {
    BigDecimal result = BigDecimal.ZERO;
    for (BigDecimal obj : objs) {
      result = result.add(obj);
    }
    return result;
  }

  /**
   * 两数字相加，结果取四舍五入精度
   * 
   * @param one
   *          数字1
   * @param two
   *          数字2
   * @param scale
   *          精度
   * @return
   */
  public static BigDecimal addForRoundHalfUp(BigDecimal one, BigDecimal two, int scale) {
    return setScaleForRoundHalfUp(add(one, two), scale);
  }

  /**
   * 计算相减结果(按照a、b最大精度)
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static BigDecimal subtract(BigDecimal one, BigDecimal two) {
    return one.subtract(two);
  }

  /**
   * 两数字相减，结果取四舍五入精度
   * 
   * @param one
   *          数字1
   * @param two
   *          数字2
   * @param scale
   *          精度
   * @return
   */
  public static BigDecimal subtractForRoundHalfUp(BigDecimal one, BigDecimal two, int scale) {
    return setScaleForRoundHalfUp(subtract(one, two), scale);
  }

  /**
   * 计算相乘结果(按照a、b最大精度)
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static BigDecimal multiply(BigDecimal one, BigDecimal two) {
    return one.multiply(two);
  }

  /**
   * 多数值 计算相乘结果
   * 
   * @param objs
   * @return
   */
  public static BigDecimal multiply(BigDecimal... objs) {
    BigDecimal result = BigDecimal.ONE;
    for (BigDecimal obj : objs) {
      result = result.multiply(obj);
    }
    return result;
  }

  /**
   * 计算相乘结果(按照a、b最大精度)
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static BigDecimal multiplyForRoundHalfUp(BigDecimal one, BigDecimal two, int scale) {
    return setScaleForRoundHalfUp(multiply(one, two), scale);
  }

  /**
   * 计算相除结果,默认精确32位（超过部分使用银行家算法舍入）
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static BigDecimal division(BigDecimal one, BigDecimal two) {
    BigDecimal result = division(one, two, 32);
    return result;
  }

  /**
   * 计算相除结果(按照指定精度参数)
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @param scale
   *          精度，超过部分使用银行家算法舍入
   * @return
   */
  public static BigDecimal division(BigDecimal one, BigDecimal two, int scale) {
    return one.divide(two, scale, RoundingMode.HALF_EVEN);
  }

  /**
   * 除法取整
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static int divisionFloor(BigDecimal one, BigDecimal two) {
    BigDecimal result = division(one, two);
    result.setScale(0, RoundingMode.DOWN);
    return result.intValue();
  }

  /**
   * 判断a是否大于b a > b 返回 TRUE a < b 返回 FALSE a = b 返回 FALSE
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static Boolean isLargeThan(BigDecimal one, BigDecimal two) {
    if (one == null || two == null) {
      return null;
    }
    if (one.compareTo(two) > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断a是否大于等于b<br>
   * a > b 返回 TRUE<br>
   * a = b 返回 TRUE<br>
   * a < b 返回 FALSE<br>
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static Boolean isLargeOrEqualThan(BigDecimal one, BigDecimal two) {
    if (one == null || two == null) {
      return null;
    }
    int result = one.compareTo(two);
    if (result > 0) {
      return true;
    } else if (result == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断a是否小于b a < b 返回 TRUE a > b 返回 FALSE a = b 返回 FALSE
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static Boolean isLessThan(BigDecimal one, BigDecimal two) {
    if (one == null || two == null) {
      return null;
    }
    if (one.compareTo(two) < 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断a是否小于等于b<br>
   * a < b 返回 TRUE <br>
   * a = b 返回 TRUE a > b 返回 FALSE <br>
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static Boolean isLessOrEqualThan(BigDecimal one, BigDecimal two) {
    if (one == null || two == null) {
      return null;
    }
    int result = one.compareTo(two);
    if (result < 0) {
      return true;
    } else if (result == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断a是否约等于b，精度取到小数点后三位 a = b 返回 TRUE a > b 返回 FALSE a < b 返回 FALSE
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @param scale
   *          精度，超过部分直接截取
   * @param errorRate
   *          允许误差范围
   * @return
   */
  public static Boolean isApproximatelyEquals(BigDecimal one, BigDecimal two, int scale, BigDecimal errorRate) {
    if (one == null || two == null) {
      return null;
    }
    BigDecimal min = CalculateUtility.add(one, errorRate);
    BigDecimal max = CalculateUtility.subtract(one, errorRate);
    min = min.setScale(scale, RoundingMode.DOWN);
    max = max.setScale(scale, RoundingMode.DOWN);
    two = two.setScale(scale, RoundingMode.DOWN);
    if ((min.compareTo(two) == 0) || (max.compareTo(two) == 0) || (min.compareTo(two) == 1 && max.compareTo(two) == -1)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 比较两个债权百分比是否相同(直接截取保留16位精度判断，内误差值为零)
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @param scale
   * @return
   */
  public static Boolean isApproximatelyEqualsForRate(BigDecimal one, BigDecimal two) {
    return isApproximatelyEquals(one, two, 16, BigDecimal.ZERO);
  }

  /**
   * 比较两个金额是否相同(直接截取保留6位精度判断，内误差值为零)
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static Boolean isApproximatelyEqualsForAmount(BigDecimal one, BigDecimal two) {
    return isApproximatelyEquals(one, two, 6, BigDecimal.ZERO);
  }

  /**
   * 比较两个值是否相等
   * 
   * @param one
   * @param two
   * @return
   */
  public static Boolean isEquals(BigDecimal one, BigDecimal two) {
    return one.compareTo(two) == 0;
  }

  /**
   * 返回最小的数值
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static BigDecimal min(BigDecimal one, BigDecimal two) {
    if (isLargeThan(one, two)) {
      return two;
    } else {
      return one;
    }
  }

  /**
   * 返回最大的数值
   * 
   * @param one
   *          比较数字1
   * @param two
   *          比较数字2
   * @return
   */
  public static BigDecimal max(BigDecimal one, BigDecimal two) {
    if (isLargeThan(one, two)) {
      return one;
    } else {
      return two;
    }
  }

  /**
   * 银行家取舍
   * 
   * @param input
   * @param scale
   * @return
   */
  public static BigDecimal setScale(BigDecimal input, int scale) {
    return input.setScale(scale, RoundingMode.HALF_EVEN);
  }

  /**
   * @methodName:setScale
   * @describe:不做四舍五入
   * @parm:
   * @author:liujun
   * @date:2016年4月20日上午10:07:03
   */
  public static BigDecimal setDownHScale(BigDecimal input, int scale) {
    return input.setScale(scale, RoundingMode.DOWN);
  }

  /**
   * 设置债权持有比率精度（直接截取保留16位精度），新交易系统设计的表持有比率最小保留小数点后16位
   * 
   * @param input
   *          待转换对象
   * @return
   */
  public static BigDecimal setScaleForBizRate(BigDecimal input) {
    return input.setScale(16, RoundingMode.DOWN);
  }

  /**
   * 设置金额精度（直接截取保留6位精度），用于保存新交易系统的表，新交易系统设计的表金额最小保留小数点后6位
   * 
   * @param input
   *          待转换对象
   * @return
   */
  public static BigDecimal setScaleForBizAmount(BigDecimal input) {
    return input.setScale(6, RoundingMode.DOWN);
  }

  /**
   * 根据设置的精度，四舍五入转换精度
   * 
   * @param input
   *          待转换对象
   * @param scale
   *          保留精度
   * @return
   */
  public static BigDecimal setScaleForRoundHalfUp(BigDecimal input, int scale) {
    return new BigDecimal(format(input, scale));
  }

  /**
   * 根据设置的精度，四舍五入转换精度,一般用于用户交互显示，一般建议金额保留小数后面2位
   * 
   * @param input
   *          待转换对象
   * @param scale
   *          精度
   * @return
   */
  public static String format(BigDecimal input, int scale) {
    return input.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
  }

  /**
   * 判断是否小于0
   * 
   * @param input
   * @return
   */
  public static Boolean isLessThanZero(BigDecimal input) {
    return isLessThan(input, BigDecimal.ZERO);
  }

  /**
   * 判断是否大于0
   * 
   * @param input
   * @return
   */
  public static Boolean isLargeThanZero(BigDecimal input) {
    return isLargeThan(input, BigDecimal.ZERO);
  }

  /**
   * @methodName:isNumeric
   * @describe:验证是否是数字
   * @author:liujun
   * @date:2016年4月27日上午11:30:17
   */
  public static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }

  /**
   * @methodName:compareTo
   * @describe:比较数字大小
   * @author:liujun null无法进行比较 1 大于 -1 小于 0 等于
   * @date:2016年5月23日上午11:08:11
   */
  public static Integer compareTo(String one, String two) {
    if (StringUtility.isEmpty(one) || StringUtility.isEmpty(two)) {
      return null;
    }
    BigDecimal oneb = BigDecimal.valueOf(Double.valueOf(one));
    BigDecimal twob = BigDecimal.valueOf(Double.valueOf(two));
    return oneb.compareTo(twob);
  }

  /**
   * @methodName:judgeNumber
   * @describe:返回整数小数
   * @author:liujun
   * @date:2016年5月23日下午4:15:59
   */
  public static boolean judgeisDecimals(String input) {
    Pattern integerPattern = Pattern.compile("^\\d+$|-\\d+$"); // 就是判断是否为整数
    Matcher integerNum = integerPattern.matcher(input);
    if (integerNum.matches()) {
      return true;
    }
    Pattern decimalPattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");// 判断是否为小数
    Matcher decimalNum = decimalPattern.matcher(input);
    if (decimalNum.matches()) {
      return false;
    }
    return false;
  }

}