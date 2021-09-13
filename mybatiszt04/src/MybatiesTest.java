import com.hp.entity.Person;
import com.hp.entity.School;
import com.hp.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatiesTest {
    private SqlSession sqlSession;

    @Before //@Test注解之前，执行的方法，提取重复的方法
    public void before() throws Exception {
        //加载并读取xml
        String path = "SqlMapConfig.xml";
        InputStream is = Resources.getResourceAsStream(path);
        //sql  连接的工厂类
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = sqlSessionFactory.openSession();
        System.out.println("sqlSession = " + sqlSession);

    }
    //写出 学校--班级--学生 的  1 对多  对多的 查询
    @Test
    public void test01(){
        School o = sqlSession.selectOne("com.hp.dao.SchoolDAO.selectStudentById", 1);
        System.out.println("o = " + o);
        sqlSession.close();
    }
    //写出 学校--班级--学生 的   多条查询
    @Test
    public void test02(){
        Map map = new HashMap();
        map.put("scName","三门峡职业技术学院");
        List<Map> mapList = sqlSession.selectList("com.hp.dao.SchoolDAO.selectAllDongTai", map);
        for (Map map1 : mapList) {
            System.out.println("map1 = " + map1);
        }
        sqlSession.close();
    }

    //一对一   写出 人和 身份证的 1 对 1  查询
    @Test
    public void test03() {
        Person person = sqlSession.selectOne("com.hp.dao.PersonDAO.selectById", 2);
        System.out.println("person = " + person);
        sqlSession.close();
    }
    @Test
    public void test04(){
        List<Student> students = sqlSession.selectList("com.hp.dao.StudentDAO.selectBanweiByParam","班长");
        for (Student student : students) {
            System.out.println("student = " + student);
        }
        sqlSession.close();
    }
}