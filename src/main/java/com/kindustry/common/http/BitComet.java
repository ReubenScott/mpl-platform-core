package com.kindustry.common.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class BitComet {

  /**
   * HTTP 代理 下载远程文件并保存到本地
   * 
   * @param remoteFilePath
   *          远程文件路径
   * @param localFilePath
   *          本地文件路径
   */
  public static void downloadByHttpProxy(String url, String localFilePath) {
    HttpURLConnection httpUrl = null;
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;

    try {
      Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8118));
      httpUrl = (HttpURLConnection) new URL(url).openConnection(proxy);
      httpUrl.setConnectTimeout(5000);
      httpUrl.connect();
//      ReadableByteChannel rbc = Channels.newChannel(conn.getInputStream());
//      FileOutputStream fos = new FileOutputStream(localFilePath);
//      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//      fos.close();

      bis = new BufferedInputStream(httpUrl.getInputStream());
      bos = new BufferedOutputStream(new FileOutputStream(localFilePath));
      int len = 2048;
      byte[] b = new byte[len];
      while ((len = bis.read(b)) != -1) {
        bos.write(b, 0, len);
      }
      bos.flush();
      bis.close();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bis != null) {
          bis.close();
        }
        if (bos != null) {
          bos.close();
        }
        if (httpUrl != null) {
          httpUrl.disconnect();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * 下载远程文件并保存到本地
   * 
   * @param remoteFilePath
   *          远程文件路径
   * @param localFilePath
   *          本地文件路径
   */
  public static void downloadFile(String url, String localFilePath) {
    URL urlfile = null;
    HttpURLConnection httpUrl = null;
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;

    File f = new File(localFilePath);
    try {
      urlfile = new URL(url);
      httpUrl = (HttpURLConnection) urlfile.openConnection();
      httpUrl.connect();
      bis = new BufferedInputStream(httpUrl.getInputStream());
      bos = new BufferedOutputStream(new FileOutputStream(f));
      int len = 2048;
      byte[] b = new byte[len];
      while ((len = bis.read(b)) != -1) {
        bos.write(b, 0, len);
      }
      bos.flush();
      bis.close();
      httpUrl.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        bis.close();
        bos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * sock
   * 
   * @param url
   * @param localFilePath
   */
  public static void downloadBySocketProxy(String url, String localFilePath) {
    try {
      // SocketAddress addr = new InetSocketAddress("122.72.88.85", 1080);
      // SocketAddress addr = new InetSocketAddress("127.0.0.1", 1080);
      SocketAddress addr = new InetSocketAddress("127.0.0.1", 9050);
      Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
      Socket socket = new Socket(proxy);
      InetSocketAddress dest = new InetSocketAddress("r2---sn-npoe7n76.googlevideo.com", 80);
      socket.connect(dest);

      URLConnection conn = new URL(url).openConnection(proxy);
      conn.setConnectTimeout(5000);
      conn.connect();

      final ReadableByteChannel rbc = Channels.newChannel(conn.getInputStream());
      final FileOutputStream fos = new FileOutputStream(localFilePath);
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
      fos.close();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {

    }

  }

  public static void downloadBySocketProxy1(String url, String localFilePath) {
    try {
      // SocketAddress addr = new InetSocketAddress("122.72.88.85", 1080);
      // SocketAddress addr = new InetSocketAddress("127.0.0.1", 1080);
      SocketAddress addr = new InetSocketAddress("127.0.0.1", 9050);
      Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
      final HttpURLConnection httpUrlConnetion = (HttpURLConnection) new URL(url).openConnection(proxy);
      httpUrlConnetion.setDoOutput(true);
      httpUrlConnetion.setDoInput(true);
      httpUrlConnetion.setRequestProperty("Content-type", "text/xml");
      httpUrlConnetion.setRequestProperty("Accept", "text/xml, application/xml");
      httpUrlConnetion.setRequestMethod("POST");
      httpUrlConnetion.connect();

      final ReadableByteChannel rbc = Channels.newChannel(httpUrlConnetion.getInputStream());
      final FileOutputStream fos = new FileOutputStream(localFilePath);
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
      fos.close();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {

    }

  }

}