package com.kindustry.common.http;

import org.junit.Test;

import com.kindustry.common.http.BitComet;

public class BitCometTest {

  @Test
  public void testDownload() {
    String url = "https://r6---sn-q4flrn7d.googlevideo.com/videoplayback?ratebypass=yes&pcm2=yes&gir=yes&dur=203.128&key=yt6&expire=1498573108&pl=24&ipbits=0&signature=8C0EF39573DD7A57D3284A6CD52BC178C1083AE1.67DC0913E4343E11659FEE74B80DBC61E088EC0A&itag=18&clen=18307120&requiressl=yes&mime=video/mp4&mm=31&mn=sn-q4flrn7d&ms=au&mt=1498551401&mv=m&source=youtube&ip=119.81.31.43&sparams=clen,dur,ei,gir,id,initcwndbps,ip,ipbits,itag,lmt,mime,mm,mn,ms,mv,pcm2,pl,ratebypass,requiressl,source,expire&ei=1BRSWezFEMLfWr3nrOAC&id=o-ABsWIG9qhZ9E_lLL_nXpgCROJjAjvr6sd4_Y7M-YlVcn&initcwndbps=333750&lmt=1482727373682270&title=%E7%95%B6%E4%BD%A0%E6%B2%92%E9%8C%A2%E7%9A%84%E6%99%82%E5%80%99%EF%BC%8C%E5%AF%AB%E5%BE%97%E5%A4%AA%E5%A5%BD%E4%BA%86%EF%BC%81";
    String localFilePath = "D:/20170509/aab.mp4";
    BitComet.downloadByHttpProxy(url, localFilePath);

  }

}