import com.zx.bean.NoteBook;
import com.zx.bean.User;
import com.zx.dao.ChatMapper;
import com.zx.dao.NoteBookMapper;
import com.zx.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class MapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    NoteBookMapper noteBookMapper;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static final String slat = "123456";
    private static final String slats = "abcdefg";
    @Test
    public void CRUD(){
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("../webapp/WEB-INF/applicationContext.xml");
//        ChatMapper chat = ioc.getBean(ChatMapper.class);
//        System.out.println(userMapper);
//        userMapper.insertSelective(new User(null,"中","zxc"));
//        noteBookMapper.insertSelective(new NoteBook(null,5,"123","123"));
        UserMapper userMapper = sqlSessionTemplate.getMapper(UserMapper.class);
        for (int i = 0;i<5;i++){
            String uid = UUID.randomUUID().toString().substring(0,5)+i;
            userMapper.insertSelective(new User(null,uid,uid+i));
            System.out.println(uid);
        }

//        密码加密
//        String base = slat.substring(0,1)+slat+slat.substring(slat.length()-1,slat.length());
//        System.out.println(base);
//        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
//        System.out.println(md5);

    }
}
