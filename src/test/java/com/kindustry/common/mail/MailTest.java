package com.kindustry.common.mail;


import org.junit.Test;

import com.kindustry.common.mail.MailBean;
import com.kindustry.common.mail.MailService;



public class MailTest {

  @Test
  public void testSendMail() {
    MailBean mail = new MailBean();
    mail.setTitle("测试标题");
    // bean.setTargetMail("265583513@qq.com");
    mail.setTargetMail("chenjun12@beyondsoft.com");
    mail.setContent("测试内容");
    mail.setAttachPath("E:/wsdl.html");
    
    // bean.setTargetName("收件人");
    MailService.sendMail(mail);
  }
  
}
