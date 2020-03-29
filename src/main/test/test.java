import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class test {
//    @Test
//    public void tests() throws IOException {
//        //1、根据全局配置文件创建出一个SqlSessionFactory
//        //SqlSessionFactory：是SqlSession工厂，负责创建SqlSession对象；
//        //SqlSession:sql会话（代表和数据库的一次会话）;
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//
//
//        //4、调用之前的方法；
//        User user;
//        SqlSession openSession = sqlSessionFactory.openSession();
//        try {
//            //2、获取和数据库的一次会话；getConnection()；
//            //3、使用SqlSession操作数据库，获取到dao接口的实现
//            UserDao userDao = openSession.getMapper(UserDao.class);
//            user = userDao.getuserId(1);
//        } finally{
//            openSession.close();
//        }
//        System.out.println(user);
//    }

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

}
