package com.kindustry.common.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtility {

  private static final Logger log = LoggerFactory.getLogger(HttpUtility.class);

  // 自定义请求头
  private static Map<String, String> requestHeaders = new HashMap<String, String>();

  // 设置通用的请求属性
  static {
    requestHeaders.put("Accept", "*/*");
    requestHeaders.put("connection", "Keep-Alive");
    requestHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
    // requestHeaders.put("Accept-Charset", "utf-8");
    // requestHeaders.put("contentType", "utf-8");
  }

  // 链接连接超时时间（单位：毫秒）
  private static int timeoutMillisecond = 5000;

  /**
   * 证书信任管理器（用于https请求） X509TrustManager
   */
  private static class SSLTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[] {};
    }
  }

  static class LoginAuthenticator extends Authenticator {
    /** User name **/
    private String m_sUser;
    /** Password **/
    private String m_sPsw;

    public LoginAuthenticator(String sUser, String sPsw) {
      m_sUser = sUser;
      m_sPsw = sPsw;
    }

    public PasswordAuthentication getPasswordAuthentication() {
      return (new PasswordAuthentication(m_sUser, m_sPsw.toCharArray()));
    }
  }

  /**
   * 
   * @param addr
   * @param port
   * @return
   * @author kindustry
   */
  public static Proxy getSocksProxy(String addr, int port) {
    return new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(addr, port));
  }

  // addr = "10.0.0.1"
  // port = 8080
  public static Proxy getHttpProxy(String addr, int port) {
    return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(addr, port));
  }

  /**
   * 
   */
  public static HttpsURLConnection getConnection(String uri) {
    return getConnection(uri, null);
  }

  public static HttpsURLConnection getConnection(String uri, Proxy proxy) {
    // 请求方法 GET POST PUT DELETE 等
    String requestMethod = "GET";
    HttpsURLConnection connection = null;
    int code;
    try {
      connection = getConnection(requestMethod, uri, proxy);
      code = connection.getResponseCode();
      switch (code) {
        case HttpURLConnection.HTTP_BAD_METHOD:
          requestMethod = "POST";
          connection.disconnect();
          connection = getConnection(requestMethod, uri, null);
          break;
      }
    } catch (ConnectException e) {
      e.printStackTrace();
      // log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
    } catch (SocketTimeoutException e) {
      e.printStackTrace();
      // log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
    } catch (IOException e) {
      e.printStackTrace();
      // log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
    } catch (Exception e) {
      e.printStackTrace();
      // log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
    }

    return connection;
  }

  public static HttpsURLConnection getConnection(String requestMethod, String uri, Proxy proxy) throws Exception {
    // 模拟http请求获取ts片段文件
    HttpsURLConnection httpsConn = null;
    try {

      // 创建SSLContext对象，并使用我们指定的信任管理器初始化
      TrustManager[] tm = {new SSLTrustManager()};
      // SSLContext sslContext = SSLContext.getInstance("SSL");
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, tm, new java.security.SecureRandom());
      // 从上述SSLContext对象中得到SSLSocketFactory对象
      SSLSocketFactory ssf = sslContext.getSocketFactory();

      // 用户验证
      // Authenticator.setDefault(new LoginAuthenticator(args[0], args[1]));

      URL url = new URL(uri);
      // 设置代理
      httpsConn = (HttpsURLConnection)(proxy == null ? url.openConnection() : url.openConnection(proxy));
      httpsConn.setSSLSocketFactory(ssf);

      httpsConn.setConnectTimeout(timeoutMillisecond);
      // 请求方法 GET POST PUT DELETE 等
      httpsConn.setRequestMethod(requestMethod);
      httpsConn.setUseCaches(false);
      httpsConn.setReadTimeout(timeoutMillisecond);

      httpsConn.setHostnameVerifier((hostname, session) -> true);

      // 设置通用的请求属性
      for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
        httpsConn.addRequestProperty(entry.getKey(), entry.getValue());
      }

      // 发送POST请求必须设置如下两行
      httpsConn.setDoOutput(true);
      httpsConn.setDoInput(true);

    } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
      throw e;
    }
    return httpsConn;
  }

  /**
   * 向指定 URL 发送请求 请求参数为json形式数据
   *
   * @param requestMethod
   *          GET POST PUT 等
   * @param url
   *          发送请求的 URL
   * @param paramJson
   *          请求参数，请求参数应该是 json格式数据。
   * @return 所代表远程资源的响应结果
   */
  public static String sendWithJsonBody(String requestMethod, String url, String paramJson) {
    PrintWriter out = null;
    BufferedReader in = null;
    StringBuilder result = new StringBuilder();
    try {
      //
      // String urlNameString = url + "?" + param;
      log.info("sendPost - {}", url + "?" + paramJson);
      URL realUrl = new URL(url);
      URLConnection urlConn = realUrl.openConnection();
      // http的连接类
      HttpURLConnection conn = (HttpURLConnection)urlConn;
      conn.setRequestMethod(requestMethod);
      // 设置不用缓存
      conn.setUseCaches(false);
      conn.setRequestProperty("Accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      // conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      // conn.setRequestProperty("Accept-Charset", "utf-8");

      // 设置文件字符集:
      conn.setRequestProperty("Charset", "UTF-8");
      // 转换为字节数组
      byte[] data = (paramJson).getBytes();
      // 设置文件长度
      conn.setRequestProperty("Content-Length", String.valueOf(data.length));
      // 设置文件类型:
      conn.setRequestProperty("Content-Type", "application/json");
      // 设置请求内容类型
      // conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
      conn.setDoOutput(true);
      conn.setDoInput(true);

      // 开始连接请求
      // conn.connect();

      out = new PrintWriter(conn.getOutputStream());
      out.print(paramJson);
      out.flush();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
      log.info("recv - {}", result);
    } catch (ConnectException e) {
      log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",paramJson=" + paramJson, e);
    } catch (SocketTimeoutException e) {
      log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",paramJson=" + paramJson, e);
    } catch (IOException e) {
      log.error("调用HttpUtils.sendPost IOException, url=" + url + ",paramJson=" + paramJson, e);
    } catch (Exception e) {
      log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",paramJson=" + paramJson, e);
    } finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
        log.error("调用in.close Exception, url=" + url + ",paramJson=" + paramJson, ex);
      }
    }
    return result.toString();
  }

  public static String HttpsProxy(String url, String param, String proxy, int port) {
    HttpsURLConnection httpsConn = null;
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    BufferedReader reader = null;
    try {
      URL urlClient = new URL(url);
      System.out.println("请求的URL========：" + urlClient);

      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(httpsConn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
      // 断开连接
      httpsConn.disconnect();
      System.out.println("====result====" + result);
      System.out.println("返回结果：" + httpsConn.getResponseMessage());

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
      }
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (out != null) {
        out.close();
      }
    }

    return result;
  }

  public static String HttpProxy(String url, String param, String proxy, int port) {
    HttpURLConnection httpConn = null;
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    BufferedReader reader = null;
    try {
      URL urlClient = new URL(url);
      System.out.println("请求的URL========：" + urlClient);
      // 创建代理
      Proxy proxy1 = new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
      // 设置代理
      httpConn = (HttpURLConnection)urlClient.openConnection(proxy1);
      // 设置通用的请求属性
      httpConn.setRequestProperty("accept", "*/*");
      httpConn.setRequestProperty("connection", "Keep-Alive");
      httpConn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      // 发送POST请求必须设置如下两行
      httpConn.setDoOutput(true);
      httpConn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(httpConn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
      // 断开连接
      httpConn.disconnect();
      System.out.println("====result====" + result);
      System.out.println("返回结果：" + httpConn.getResponseMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
      }
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (out != null) {
        out.close();
      }
    }

    return result;
  }

  public static void main(String[] args) {
    HttpsProxy("https://www.baidu.com//", "", "127.0.0.1", 81);
    HttpProxy("http://www.aseoe.com/", "", "127.0.0.1", 81);
  }

  private static byte[] getBytesFromStream(InputStream is) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] kb = new byte[1024];
    int len;
    while ((len = is.read(kb)) != -1) {
      baos.write(kb, 0, len);
    }
    byte[] bytes = baos.toByteArray();
    baos.close();
    is.close();
    return bytes;
  }

  private static void setBytesToStream(OutputStream os, byte[] bytes) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    byte[] kb = new byte[1024];
    int len;
    while ((len = bais.read(kb)) != -1) {
      os.write(kb, 0, len);
    }
    os.flush();
    os.close();
    bais.close();
  }

}