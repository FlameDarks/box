import com.github.pagehelper.PageInfo;
import com.zx.bean.NoteBook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class test {
//    @Autowired
//    WebApplicationContext context;
//    MockMvc mockMvc;
//
//    @Before
//    public void test1(){
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//    @Test
//    public void test2() throws Exception{
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/notebook").param("pn","1")).andReturn();
//        MockHttpServletRequest request = result.getRequest();
//        PageInfo pageInfo = (PageInfo)request.getAttribute("notebook_pageInfo");
//        System.out.println("当前页码："+pageInfo.getPageNum());
//        System.out.println("总页码："+pageInfo.getPages());
//        System.out.println("总记录数："+pageInfo.getTotal());
//        System.out.println("连续显示的页码：");
//        int[] num = pageInfo.getNavigatepageNums();
//        System.out.println(num);
//        for (int i:num){
//            System.out.print(" "+i);
//        }
//        System.out.println();
//        List<NoteBook> noteBooks = pageInfo.getList();
//        for(NoteBook noteBook:noteBooks){
//            System.out.println("ID:"+noteBook.getNotebookId()+" name:"+noteBook.getNotebookTime());
//        }
//    }

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
//    由表逆向建类
//    public static void main(String[] args) throws Exception {
//        List<String> warnings = new ArrayList<String>();
//        boolean overwrite = true;
//        File configFile = new File("mbg.xml");
//        ConfigurationParser cp = new ConfigurationParser(warnings);
//        Configuration config = cp.parseConfiguration(configFile);
//        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//        myBatisGenerator.generate(null);
//    }

}
