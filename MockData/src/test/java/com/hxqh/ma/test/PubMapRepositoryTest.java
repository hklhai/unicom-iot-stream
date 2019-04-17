package com.hxqh.ma.test;

import com.hxqh.ma.repository.PubMapRepository;
import org.junit.Test;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * Created by Ocean lin on 2017/7/3.
 */

//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
public class PubMapRepositoryTest {

    @Resource
    private PubMapRepository pubMapRepository;

    private String receiveMail = "linh@bjhxqh.com";

    private String serverName = "smtp.163.com";
    private String username = "hklhai@163.com";
    private String password = "wy85151918";

//    @Test
    public void testMail() throws Exception {


        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.host", "smtp.163.com");
        // 指定验证为true是否需要身份验证
        props.setProperty("mail.smtp.auth", "true");

        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                // 密码验证
//                return new PasswordAuthentication("邮箱账号不包括@126.com之类的后缀", "授权码");
                return new PasswordAuthentication("15030268371", "bqcenter123456");
            }
        };

        Session session = Session.getInstance(props, auth);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("15030268371@163.com")); // 设置发送者
        // 设置发送方式与接收者
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("linh@bjhxqh.com"));

        message.setSubject("数据采集异常");

        message.setContent("数据采集异常", "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }


//
//    @Test
//    public void testFindByPubnameStartingWithAndPubtype() {
//        List<PubMap> pubMapList = pubMapRepository.findByPubnameStartingWithAndPubtype("中国", "出版社-示范");
//        Assert.assertTrue(pubMapList.size() > 0);
//    }
//
//    @Test
//    public void testFindByPubnameEndingWithAndPubidLessThan() {
//        List<PubMap> pubMapList = pubMapRepository.findByPubnameEndingWithAndPubidLessThan("出版社", 20);
//        Assert.assertTrue(pubMapList.size() > 0);
//    }
//
//    @Test
//    public void testFindByPubnameInOrPubidIn() {
//
//        List<String> list = new ArrayList<>();
//        list.add("九州出版社");
//        list.add("中国科技出版传媒股份有限公司");
//
//        List<Integer> ids = new ArrayList<>();
//        ids.add(1);
//        ids.add(10);
//        ids.add(23);
//
//        List<PubMap> pubMapList = pubMapRepository.findByPubnameInOrPubidIn(list, ids);
//        Assert.assertTrue(pubMapList.size() > 0);
//    }
//
//    @Test
//    public void testGetMaxPubMap() {
//        PubMap maxPubMap = pubMapRepository.getMaxPubMap();
//        Assert.assertTrue(maxPubMap.getPubid().equals(20));
//    }
//
//    @Test
//    public void testQueryParam() {
//        List<PubMap> pubMapList = pubMapRepository.queryParam("九州出版社", 5);
//        Assert.assertTrue(pubMapList.size() > 0);
//        List<PubMap> pubMaps = pubMapRepository.queryParamter("九州出版社", 5);
//        Assert.assertTrue(pubMaps.size() > 0);
//    }
//
//
//    @Test
//    public void testQueryLike() {
//        List<PubMap> pubMapList = pubMapRepository.queryLike("出版社");
//        Assert.assertTrue(pubMapList.size() > 0);
//        List<PubMap> pubMaps = pubMapRepository.queryLikeParam("出版社");
//        Assert.assertTrue(pubMaps.size() > 0);
//    }
//
//
//    @Test
//    public void testGetCount() {
//        long count = pubMapRepository.getCount();
//        Assert.assertTrue(count>0);
//    }


}
