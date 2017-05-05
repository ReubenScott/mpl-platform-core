package com.soak.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;

public class StringUtil {

  /**
   * 判断是否为空 是否有实际内容，空格不算
   * 
   * @param source
   * @return
   */
  public static boolean isEmpty(String source) {
    if (null == source || source.trim().replaceAll("[\\s\\u00A0]+$", "").equals("")) {
      // trim方法只能处理掉ASCII32空格，我们需要用 160空格
      return true;
    } else {
      char[] strChar = source.toCharArray();
      for (char c : strChar) {
        if (!Character.isWhitespace(c)) { // 判断一个字符是否是空白字符
          return false;
        }
      }
      return true;
    }
  }

  public static String filterStr(String str) {
    StringBuffer strs = new StringBuffer();
    if (str != null) {
      for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        if (ch == '\r') {
          strs.append("<br>");
        } else if (ch == ' ') {
          strs.append("&nbsp;");
        } else if (ch == '<') {
          strs.append("&lt;");
        } else if (ch == '>') {
          strs.append("&gt;");
        } else {
          strs.append(ch);
        }
      }
    }
    return strs.toString();
  }

  public static String encodeStr(String str) {
    if (str != null) {
      try {
        str = new String(str.getBytes("ISO-8859-1"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return str;
  }

  public static String arrayToString(String[] array) {
    StringBuffer sb = new StringBuffer();
    int length = array.length;
    for (int i = 0; i < length; i++) {
      if (i == length - 1) {
        sb.append(array[i]);
      } else {
        sb.append(array[i] + ",");
      }
    }
    return sb.toString();
  }

  public static String arrayToString(List<String> array) {
    StringBuffer sb = new StringBuffer();
    int length = array.size();
    for (int i = 0; i < length; i++) {
      if (i == length - 1) {
        sb.append(array.get(i));
      } else {
        sb.append(array.get(i) + ",");
      }
    }
    return sb.toString();
  }

  /**
   * 获取文件 可以根据正则表达式查找
   * 
   * @param dir
   *          String 文件夹名称
   * @param regex
   *          String 查找文件名，可带*.?进行模糊查询
   * @return File[] 找到的文件
   */
  public static String[] fuzzyLookup(String[] strArray, String regex) {
    // 开始的文件夹
    regex = regex.replace('.', '#');
    regex = regex.replaceAll("#", "\\\\.");
    regex = regex.replace('*', '#');
    regex = regex.replaceAll("#", ".*");
    regex = regex.replace('?', '#');
    regex = regex.replaceAll("#", ".?");
    regex = "^" + regex + "$";

    System.out.println(regex);
    Pattern p = Pattern.compile(regex);
    // Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
    List<String> result = new ArrayList<String>();

    for (String str : strArray) {
      Matcher fMatcher = p.matcher(str);
      if (fMatcher.matches()) {
        result.add(str);
      }
    }

    return (String[]) result.toArray(new String[result.size()]);
  }

  /**
   * 使用java.util.zip.*工具对字符串进行压缩
   * 
   * @param str
   *          压缩前的文本
   * @return 压缩后的文本字节数组
   */
  public static final byte[] compress(String str) throws IOException {
    byte[] compressed = null;
    ByteArrayOutputStream out = null;
    ZipOutputStream zout = null;

    try {
      out = new ByteArrayOutputStream();
      zout = new ZipOutputStream(out);
      zout.putNextEntry(new ZipEntry("0"));
      zout.write(str.getBytes());
      zout.closeEntry();
      compressed = out.toByteArray();
    } finally {
      if (zout != null) {
        try {
          zout.close();
        } catch (IOException e) {
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
        }
      }
    }
    return compressed;
  }

  /**
   * 压缩字符串
   * 
   * @param str
   *          压缩前的文本
   * @return 压缩后的文本
   */
  public static final String zip(String str) throws IOException {
    byte[] compressed = compress(str);
    compressed = Base64.encodeBase64(compressed);
    return new String(compressed);
  }

  /**
   * 使用java.util.zip.*工具对压缩后的 byte[]进行解压缩
   * 
   * @param compressed
   *          压缩后的 byte[] 数据
   * @return 解压后的字符串
   */
  public static final String decompress(byte[] compressed) throws IOException {
    ByteArrayOutputStream out = null;
    ByteArrayInputStream in = null;
    ZipInputStream zin = null;
    String decompressed = null;
    try {
      out = new ByteArrayOutputStream();
      in = new ByteArrayInputStream(compressed);
      zin = new ZipInputStream(in);
      zin.getNextEntry();
      byte[] buffer = new byte[1024];
      int offset = -1;
      while ((offset = zin.read(buffer)) != -1) {
        out.write(buffer, 0, offset);
      }
      decompressed = out.toString();
    } finally {
      if (zin != null) {
        try {
          zin.close();
        } catch (IOException e) {
        }
      }
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
        }
      }
    }

    return decompressed;
  }

  /**
   * 解压缩字符串
   * 
   * @param strCompressed
   *          压缩后的文本
   * @return 解压缩后的文本
   */
  public static final String unZip(String strCompressed) throws IOException {
    byte[] compressed = strCompressed.getBytes();
    compressed = Base64.decodeBase64(compressed);
    return decompress(compressed);
  }

  // 判断一个字符是否是中文
  public static boolean isChinese(char c) {
    return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
  }

  // 判断一个字符串是否含有中文
  public static boolean isChinese(String str) {
    if (str == null)
      return false;
    for (char c : str.toCharArray()) {
      if (isChinese(c))
        return true;// 有一个中文字符就返回
    }
    return false;
  }

  // ---------------------------------------------------------------------
  // General convenience methods for working with Strings
  // ---------------------------------------------------------------------

  // 判断str是否为空值
  public static boolean hasLength(CharSequence str) {
    return (str != null && str.length() > 0);
  }

  /**
   *检测CharSequence是否有空白字符
   */
  public static boolean containsWhitespace(CharSequence str) {
    // 如果长度为0，则返回false
    if (null == str) {
      return false;
    }
    // 循环遍历str
    for (int i = 0; i < str.length(); i++) {
      // 如果在0到strLen之间有空白符，则返回true
      if (Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  /**
   *判断给定的字符串str是否含有空白符
   */
  public static boolean containsWhitespace(String str) {
    return containsWhitespace((CharSequence) str);
  }

  /**
   * 去掉str开头和结尾的空白符
   */
  public static String trimWhitespace(String str) {
    // 如果没有长度，则放回str
    if (!hasLength(str)) {
      return str;
    }

    StringBuilder sb = new StringBuilder(str);
    // 如果sb.charAt(0)是空白符的话，删除该空白符
    while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
      sb.deleteCharAt(0);
    }
    // 如果末尾是空白符的话，也删除该空白符
    while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
      sb.deleteCharAt(sb.length() - 1);
    }
    // 返回去掉开头结尾空白符之后的字符串
    return sb.toString();
  }

  /**
   *删除给定的字符串中所有的空白符
   */
  public static String trimAllWhitespace(String str) {
    // 如果str没有长度，返回str
    if (!hasLength(str)) {
      return str;
    }
    StringBuilder sb = new StringBuilder(str);
    int index = 0;
    // 循环遍历sb
    while (sb.length() > index) {
      // 如果当前位置index为空白符，则删除之
      if (Character.isWhitespace(sb.charAt(index))) {
        sb.deleteCharAt(index);
      } else {
        index++;
      }
    }
    // 返回去掉空白符之后的字符串
    return sb.toString();
  }

  /**
   *删除掉str的开头的空白符，如果有的话
   */
  public static String trimLeadingWhitespace(String str) {
    // 如果str的长度为0，返回str
    if (!hasLength(str)) {
      return str;
    }
    StringBuilder sb = new StringBuilder(str);
    // 如果开头有字符串，则删除之
    while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
      sb.deleteCharAt(0);
    }
    // 返回删除开头空白符的字符串
    return sb.toString();
  }

  /**
   * 删除str结尾的空白符，如果结尾是空白符的话
   */
  public static String trimTrailingWhitespace(String str) {
    // 如果str的长度为0，返回str
    if (!hasLength(str)) {
      return str;
    }
    StringBuilder sb = new StringBuilder(str);
    // 如结尾头有字符串，则删除之
    while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
      sb.deleteCharAt(sb.length() - 1);
    }
    // 返回删除开头空白符的字符串
    return sb.toString();
  }

  /**
   *删除str中开头是字符是给定字符的那个字符
   */
  public static String trimLeadingCharacter(String str, char leadingCharacter) {
    // 如果str的长度为0，返回str
    if (!hasLength(str)) {
      return str;
    }
    StringBuilder sb = new StringBuilder(str);
    // 判断sb的开头是否==leadingCharacter,若是就删除，否则什么也不做
    while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
      sb.deleteCharAt(0);
    }
    return sb.toString();
  }

  /**
   *删除结尾等于trailingCharacter的那个字符
   */
  public static String trimTrailingCharacter(String str, char trailingCharacter) {
    // 如果str的长度为0，返回str
    if (!hasLength(str)) {
      return str;
    }
    StringBuilder sb = new StringBuilder(str);
    // 判断sb的开头是否==leadingCharacter,若是就删除，否则什么也不做
    while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  /**
   *检测str的前缀是否是prefix，大小写不敏感
   */
  public static boolean startsWithIgnoreCase(String str, String prefix) {
    if (str == null || prefix == null) {
      return false;
    }
    // 如果是则返回true
    if (str.startsWith(prefix)) {
      return true;
    }
    // 如果str小于前缀，则返回false
    if (str.length() < prefix.length()) {
      return false;
    }
    // 设定大小写不明感
    // 把str的前面长度等于prefix的字符变小写
    String lcStr = str.substring(0, prefix.length()).toLowerCase();
    // 把prefix变小写
    String lcPrefix = prefix.toLowerCase();
    // 判断
    return lcStr.equals(lcPrefix);
  }

  /**
   *检测str的后缀是否是prefix，大小写不敏感
   */
  public static boolean endsWithIgnoreCase(String str, String suffix) {
    if (str == null || suffix == null) {
      return false;
    }
    // 如果后缀是suffix，返回true
    if (str.endsWith(suffix)) {
      return true;
    }
    if (str.length() < suffix.length()) {
      return false;
    }
    // 设定大小写不敏感
    String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
    String lcSuffix = suffix.toLowerCase();
    return lcStr.equals(lcSuffix);
  }

  /**
   * 判断给定的str中是否有在位置index处存在子序列subString
   */
  public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
    for (int j = 0; j < substring.length(); j++) {
      int i = index + j;
      // 如果i>=str.length说明str字符串自index到最后的长度小于subString
      // str.charAt(i) != substring.charAt(j),如果当前j位置字符和str中i位置字符不相等
      if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
        return false;
      }
    }
    return true;
  }

  /**
   *检测str中出现sub子字符串的个数.
   */
  public static int countOccurrencesOf(String str, String sub) {
    // 边界处理
    if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
      return 0;
    }
    // 计数器
    int count = 0;
    // 记录当前位置
    int pos = 0;
    int idx;
    // indexOf(String str,int fromIndex)str - 要搜索的子字符串。
    // fromIndex - 开始搜索的索引位置
    // 如果含有此sub，则计数器+1
    while ((idx = str.indexOf(sub, pos)) != -1) {
      ++count;
      // 下一个开始比较的位置
      pos = idx + sub.length();
    }
    // 返回sub出现的个数
    return count;
  }

  /**
   * 用newPattern来替换inString中的oldPattern
   */
  public static String replace(String inString, String oldPattern, String newPattern) {
    // 边界处理
    if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
      return inString;
    }

    StringBuilder sb = new StringBuilder();
    int pos = 0;
    // 返回oldPattern在inString的位置索引
    int index = inString.indexOf(oldPattern);
    // 记录oldPattern的长度
    int patLen = oldPattern.length();
    while (index >= 0) {
      // 保存index之前的inString子串
      sb.append(inString.substring(pos, index));
      // 拼接新的字符（串）
      sb.append(newPattern);
      pos = index + patLen;
      // 检测pos之后是否还有oldPattern,如果有继续替换
      index = inString.indexOf(oldPattern, pos);
    }
    // 拼接pos之后的字符串
    sb.append(inString.substring(pos));
    // remember to append any characters to the right of a match
    return sb.toString();
  }

  /**
   *删除inString中符合pattern要求的字符（串） 实现方法是：把inString中符合pattern的字符（串）替换成“”从而实现删除
   */
  public static String delete(String inString, String pattern) {
    return replace(inString, pattern, "");
  }

  /**
   * 到此可以发先StringBuilder的强大作用 删除inString中在charsToDelete中存在的字符 例如 inString =
   * "abddfkjfd"; charsToDelete = "cdjf"; 则处理后的inString = "abk"
   */
  public static String deleteAny(String inString, String charsToDelete) {
    // 边界处理
    if (!hasLength(inString) || !hasLength(charsToDelete)) {
      return inString;
    }
    // 字符构造器
    StringBuilder sb = new StringBuilder();
    // 循环遍历inString,判断每个字符是否在charsToDelete中
    for (int i = 0; i < inString.length(); i++) {
      // 获取当前位置i的字符c
      char c = inString.charAt(i);
      // 如果charsToDelete中不包含c，则拼接到sb中
      if (charsToDelete.indexOf(c) == -1) {
        sb.append(c);
      }
    }
    // 返回处理过的字符串
    return sb.toString();
  }

  // ---------------------------------------------------------------------
  // Convenience methods for working with formatted Strings
  // ---------------------------------------------------------------------

  /**
   * 用单引号把非空的str括起来，例如str == "hello" 那么返回的将是‘hello’
   */
  public static String quote(String str) {
    return (str != null ? "'" + str + "'" : null);
  }

  /**
   * 如果给定的对象是String类型，则调用quote方法处理，否则什么都不做原样返回
   */
  public static Object quoteIfString(Object obj) {
    return (obj instanceof String ? quote((String) obj) : obj);
  }

  /**
   * Unqualify a string qualified by a '.' dot character. For example,
   * "this.name.is.qualified", returns "qualified".
   * 
   * @param qualifiedName
   *          the qualified name
   */
  public static String unqualify(String qualifiedName) {
    return unqualify(qualifiedName, '.');
  }

  /**
   * 获取给定的字符串中，最后一个满足分隔符separator之后字符串， 例如 qualifiedName =
   * "this:name:is:qualified" separator = ':' 那么处理过后的字符串就是 qualified
   */
  public static String unqualify(String qualifiedName, char separator) {
    return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
  }

  /**
   *设置首字母为大写
   */
  public static String capitalize(String str) {
    return changeFirstCharacterCase(str, true);
  }

  /**
   *设置str首字母为小写
   */
  public static String uncapitalize(String str) {
    return changeFirstCharacterCase(str, false);
  }

  private static String changeFirstCharacterCase(String str, boolean capitalize) {
    if (str == null || str.length() == 0) {
      return str;
    }
    StringBuilder sb = new StringBuilder(str.length());
    if (capitalize) {// 如果首字母要求大写的话
      sb.append(Character.toUpperCase(str.charAt(0)));
    } else { // 否则首字母设置为小写
      sb.append(Character.toLowerCase(str.charAt(0)));
    }
    // 拼接首字母剩下的字符串
    sb.append(str.substring(1));
    return sb.toString();
  }


  // 检测是否是有效路径locale的语法是locale -O 64 -a | -m | -c -k Name ...
  private static void validateLocalePart(String localePart) {
    for (int i = 0; i < localePart.length(); i++) {
      char ch = localePart.charAt(i);
      // 检测当前字符
      if (ch != '_' && ch != ' ' && !Character.isLetterOrDigit(ch)) {
        throw new IllegalArgumentException("Locale part \"" + localePart + "\" contains invalid characters");
      }
    }
  }

  // ---------------------------------------------------------------------
  // Convenience methods for working with String arrays
  // ---------------------------------------------------------------------

  /**
   * 把集合转化为数组
   */
  public static String[] toStringArray(Collection<String> collection) {
    // 边界处理
    if (collection == null) {
      return null;
    }
    // toArray(T[] a)把list里面的元素放入a中，并返回a
    return collection.toArray(new String[collection.size()]);
  }

  /**
   *把Enumeration类型转化为数组
   */
  public static String[] toStringArray(Enumeration<String> enumeration) {
    if (enumeration == null) {
      return null;
    }
    // 先转换为list
    List<String> list = Collections.list(enumeration);
    // toArray(T[] a)把list里面的元素放入a中，并返回a
    return list.toArray(new String[list.size()]);
  }

  /**
   *把一个字符串分按照delimiter分割成两个子字符串，组成数组返回
   */
  public static String[] split(String toSplit, String delimiter) {
    // 边界处理。个人认为该边界处理的有问题，如果toSplit不为空而delimiter为空的话，返回的最好是原来的字符串组成的
    // 长度为一的数组 new String[]{toSplit}，可该做法直接返回了空值
    if (!hasLength(toSplit) || !hasLength(delimiter)) {
      return null;
    }
    // 获得delimiter的位置
    int offset = toSplit.indexOf(delimiter);
    if (offset < 0) {// 此时不符合要求
      return null;
    }
    // 获得在delimiter之前的子字符串
    String beforeDelimiter = toSplit.substring(0, offset);
    // 获得在delimiter之后的子字符串
    String afterDelimiter = toSplit.substring(offset + delimiter.length());
    // 组成数组返回
    return new String[] { beforeDelimiter, afterDelimiter };
  }

  /**
   * Tokenize the given String into a String array via a StringTokenizer. Trims
   * tokens and omits empty tokens.
   * <p>
   * The given delimiters string is supposed to consist of any number of
   * delimiter characters. Each of those characters can be used to separate
   * tokens. A delimiter is always a single character; for multi-character
   * delimiters, consider using <code>delimitedListToStringArray</code>
   * 
   * @param str
   *          the String to tokenize
   * @param delimiters
   *          the delimiter characters, assembled as String (each of those
   *          characters is individually considered as delimiter).
   * @return an array of the tokens
   * @see java.util.StringTokenizer
   * @see java.lang.String#trim()
   * @see #delimitedListToStringArray
   */
  public static String[] tokenizeToStringArray(String str, String delimiters) {
    return tokenizeToStringArray(str, delimiters, true, true);
  }

  /**
   * Tokenize the given String into a String array via a StringTokenizer.
   * <p>
   * The given delimiters string is supposed to consist of any number of
   * delimiter characters. Each of those characters can be used to separate
   * tokens. A delimiter is always a single character; for multi-character
   * delimiters, consider using <code>delimitedListToStringArray</code>
   * 
   * @param str
   *          the String to tokenize
   * @param delimiters
   *          the delimiter characters, assembled as String (each of those
   *          characters is individually considered as delimiter)
   * @param trimTokens
   *          trim the tokens via String's <code>trim</code>
   * @param ignoreEmptyTokens
   *          omit empty tokens from the result array (only applies to tokens
   *          that are empty after trimming; StringTokenizer will not consider
   *          subsequent delimiters as token in the first place).
   * @return an array of the tokens (<code>null</code> if the input String was
   *         <code>null</code>)
   * @see java.util.StringTokenizer
   * @see java.lang.String#trim()
   * @see #delimitedListToStringArray
   */
  public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

    if (str == null) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(str, delimiters);
    List<String> tokens = new ArrayList<String>();
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (trimTokens) {
        token = token.trim();
      }
      if (!ignoreEmptyTokens || token.length() > 0) {
        tokens.add(token);
      }
    }
    return toStringArray(tokens);
  }

}
