import com.zx.bean.NoteBook;
import com.zx.bean.User;
import com.zx.bean.UserExample;
import com.zx.dao.ChatMapper;
import com.zx.dao.NoteBookMapper;
import com.zx.dao.UserMapper;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class MapperTest {
    public static void main(String[] args) {
        Object result = new SimpleHash("MD5","Az123456","842356712",1024);
        System.out.println(result);
    }

//    @Autowired
//    UserMapper userMapper;
//
//    @Autowired
//    NoteBookMapper noteBookMapper;
//
//    @Autowired
//    SqlSessionTemplate sqlSessionTemplate;
//
//    private static final String slat = "123456";
//    private static final String slats = "abcdefg";
//    @Test
//    public void CRUD(){
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("../webapp/WEB-INF/applicationContext.xml");
//        ChatMapper chat = ioc.getBean(ChatMapper.class);
//        System.out.println(userMapper);
//        userMapper.insertSelective(new User(null,"中","zxc"));
//        UserMapper userMapper = sqlSessionTemplate.getMapper(UserMapper.class);
//        List<User> users = userMapper.selectByExample(null);
//        User user = new User();
//        String base = slat.substring(0,1)+slat+slat.substring(slat.length()-1,slat.length());
//        System.out.println(base);
//        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
//        System.out.println(md5);
//        userMapper.insertSelective(new User(null,"123456",md5));
//        System.out.println("id:"+user.getUserName()+" name:"+user.getUserPassword());
//        for (int i = 0;i<users.size();i++){
//            String uid = UUID.randomUUID().toString().substring(0,5)+i;
//            userMapper.insertSelective(new User(null,uid,uid+i));
//            System.out.println("id:"+user.getUserId()+" name:"+user.getUserName());
//        }
//
//        密码加密
//        String bases = slat.substring(0,1)+slat+slat.substring(slat.length()-1,slat.length());
//        System.out.println(bases);
//        String md5s = DigestUtils.md5DigestAsHex(base.getBytes());
//        System.out.println(md5s);
//
//    }
}
