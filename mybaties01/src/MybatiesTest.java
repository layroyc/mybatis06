import com.hp.bean.Human;
import com.hp.bean.HumanExample;
import com.hp.bean.Orders;
import com.hp.bean.Person;
import com.hp.bean.dto.PersonDTO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        System.out.println("sqlSession = " + sqlSession);//sqlSession = org.apache.ibatis.session.defaults.DefaultSqlSession@5ce81285


    }

    //全查
    @Test
    public void test01(){
        //mybaties
        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.selectAll");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //单查    带参数的
    @Test
    public void test02(){
        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.selectPersonBySex", 2);
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //查询总条数     这个主要学习的是 返回数据类型，和上面的数据类型不一样
    @Test
    public void test03(){
        long o = sqlSession.selectOne("com.hp.dao.PersonDao.selectCount");
        System.out.println("o = " + o);
        sqlSession.close();
    }

    //查询女生总条数有多少个
    @Test
    public void test04(){
        Person person = new Person();
        person.setScore(100);
        person.setGender(2);
        long o = sqlSession.selectOne("com.hp.dao.PersonDao.selectCountByParam01", person);
        System.out.println("o = " + o);
        sqlSession.close();
    }

    //带参数的查询   第二种方式  map传参--多见于 多表联查
    //查询性别是2，且生日 小于 2020-10-14 的人有哪些
    @Test
    public void test05() throws ParseException {
        String date = "2020-10-14";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sf.parse(date);
        Map map = new HashMap();
            map.put("gender",2);    //key 要和值保持一致
            map.put("birthday",birthday);
        List<Person> lists = sqlSession.selectList("com.hp.dao.PersonDao.selectCountByParam02", map);
        for (Person person : lists) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

   //子查询
    @Test
    public void test06(){
        List<Person> lists = sqlSession.selectList("com.hp.dao.PersonDao.selectPersonByZi");
        for (Person list : lists) {
            System.out.println("list = " + list);
        }
        sqlSession.close();
    }

    //分组查询
    @Test
    public void test07(){
        List<PersonDTO> personDTOList = sqlSession.selectList("com.hp.dao.PersonDao.selectAvgScore");
        for (PersonDTO personDTO : personDTOList) {
            System.out.println("personDTO = " + personDTO);
        }
        sqlSession.close();
    }

    //带参数的分组查询
    @Test
    public void test08(){
        List<PersonDTO> personDTOList = sqlSession.selectList("com.hp.dao.PersonDao.selectAvgScoreParam",200);
        for (PersonDTO personDTO : personDTOList) {
            System.out.println("personDTO = " + personDTO);
        }
        sqlSession.close();
    }



    //  第二种方式  map分组查询   上面的不灵活
    @Test
    public void test09(){
        List<Map> maps = sqlSession.selectList("com.hp.dao.PersonDao.selectAvgScore02");
        for (Map map : maps) {
            System.out.println("map = " + map);
        }
        sqlSession.close();
    }
    //带参数的分组查询
    @Test
    public void test10(){
        List<Map> maps = sqlSession.selectList("com.hp.dao.PersonDao.selectAvgScoreParam02", 200);
        for (Map map : maps) {
            System.out.println("map = " + map);
        }
        sqlSession.close();
    }


    //模糊查询
    //第一种方式 拼接型     不建议这样写
    @Test
    public void test11(){
        Map map = new HashMap();
            map.put("name","孙");

        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.selectPersonByLike",map);
        //There is no getter for property named 'name'   因为 $是拼接的，没有getter这个概念
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //查询名字中所有带孙的
    @Test
    public void test12(){
        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.selectPersonByLike02","孙");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //查询名字中所有带孙的    第三种方式 常用
    @Test
    public void test13(){
        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.selectPersonByLike03","孙");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //以上是单表的所有查询！！！

    //玩  增加  insert into.....
    @Test
    public void test14(){
        Person person = new Person();
            person.setName("王源");
            person.setGender(1);
            person.setBirthday(new Date());
            person.setAddress("重庆");
            person.setScore(888);
        int insert = sqlSession.insert("com.hp.dao.PersonDao.insertPerson",person);
        System.out.println("insert = " + insert);
        sqlSession.commit();
        sqlSession.close();
    }


    //删除
    @Test
    public void test15(){
        int i = sqlSession.delete("com.hp.dao.PersonDao.deletePersonById",16);
        System.out.println("i = " + i);
        sqlSession.commit();
        sqlSession.close();
    }

    //动态SQL   重点：难点
    //动态SQL  其实就是  让 达到 1条xml中的语句 可以实现N多种查询
    //那么  要实现多种查询 就有一个硬性的条件（你的参数要多） 参数要多 》》》 1.放弃单个 属性（int  string），改用实体类  2.参数改成map
    //今天所学的推翻昨天所学的 ， 那么需要 总结昨天所学的

    //第一类： 特征   1） 返回值-----》正常表的结果集，对应的是 person 实体类
    //              2）都是 select * from person 开头的
    //1.1 select * from person      if  如果  where 后面没有参数  那么就是 全查
    //1.2 select * from person where gender = 2     if  如果  where 后面有参数 gender  那么就是 单查 gender
    //1.3 select * from person where gender = #{gender} and birthday >= #{birthday}
    //1.4 select * from person where name like "%"#{name}"%"
    //1-4 可以 合N为1   只需要把 where 后面的参数做个 if 判断

    //第二类： 特征   1）返回值-----》 一个数，单行单列  非person实体类，是一个数据类型
    //              2)都是 select count(*) from person 开头的
    //2.1 select count(*) from person
    //2.2 select count(*) from person where sex=2 and score>100

    //综上所述！！！  以上sql  可以进行 动态 判断 形成一个sql!!!  这就叫动态sql

    //动态查询
    @Test
    public void test16(){
        Person person = new Person();
               //如果不传参数 传null 就是全查
               //person.setId(17);  //select * from person p WHERE p.id = ?
        person.setScore(200);
        person.setGender(2);//select * from person p WHERE p.gender = ? and p.score > ?
        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.dongTaiSelect",person);
        for (Person person1 : personList) {
            System.out.println("person1 = " + person1);
        }
        sqlSession.close();
    }

    //动态修改  其实就是 有选择性的修改多个字段， 比如说 可以修改 分数 日期 等
    @Test
    public void test17() throws ParseException {
        Person person = new Person();
               person.setId(17);
               person.setName("iroy");
               person.setBirthday(new Date());
        int i = sqlSession.update("com.hp.dao.PersonDao.dongTaiUpdate",person);
        System.out.println("i = " + i);
        sqlSession.commit();
        sqlSession.close();

    }

    //批量删除  delete xxx in (1,2,3,4)
    //构造一个  ids
    @Test
    public void test18(){
        List<Integer> idsList = new ArrayList<>();
        idsList.add(1);
        idsList.add(2);
        idsList.add(3);
        Map map = new HashMap();
        map.put("ids",idsList);
        int delete = sqlSession.delete("com.hp.dao.PersonDao.piliangDel",map);
        System.out.println("delete = " + delete);
        sqlSession.commit();
        sqlSession.close();
    }

    //以上代码不需要手写，因为垃圾
    //xml  不需要你写！！！  Dao 不需要你写
    //但是  需要能看懂

    //没有写一行代码，但是动态的查询总条数已经做完了！

    @Test
    public void test19(){
        HumanExample example = new HumanExample();//创建一个 例子类
        HumanExample.Criteria criteria = example.createCriteria();//用例子类 实现查询的规则或者标准
        //criteria.andGenderEqualTo(2);   //select count(*) from human WHERE ( gender = ? )
        //criteria.andAddressEqualTo("西京");//Preparing: select count(*) from human WHERE ( gender = ? and address = ? )
        //模糊查询性别为2带西京的
        //criteria.andGenderEqualTo(2);
        //criteria.andAddressLike("%"+"西京"+"%");
        //练习：家住北京的或者 分数是555的
        //select * from human where address="北京" or score=555
        //没有or  但是有in
       /* criteria.andAddressEqualTo("北京");
        HumanExample.Criteria criteria2 = example.createCriteria();//用例子类 实现查询的规则或者标准
        criteria2.andScoreEqualTo(555.0);
        example.or(criteria2);*/

       //or 不要criteria类
       //example.or().andAddressEqualTo("北京");// Preparing: select count(*) from human WHERE ( address = ? ) or( score = ? )
       //example.or().andScoreEqualTo(555.0);

       //练习：id是1，4，5的人有哪些
        /*方法一
        example.or().andIdEqualTo(1);
        example.or().andIdEqualTo(4);
        example.or().andIdEqualTo(5);*/
        //example.or().andIdBetween(2,5);   //方法二
        //等于 select * from human where id in(1,4,5) //方法三
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(4);
        ids.add(5);
        criteria.andIdIn(ids);*/


        //当example的criteria不用 赋值的时候，则是：Preparing: select count(*) from human
        long o = sqlSession.selectOne("com.hp.dao.HumanDAO.countByExample", example);
        System.out.println("o = " + o);
        sqlSession.close();
    }

    //单表的所有
//查询：
//select * from human; === 全查
//select * from human where gender = 2;
//select * from human where gender = 1;
//select * from human where id = 1;
//select * from human where score > 100;
//select * from human where score > 100 and gender = 1;
//select * from human where score > 100 and gender = 1 and address like "%西京%";
//以上所有的SQL 语句，需要写成一个    List<Human> selectByExample(HumanExample example);
//动态的where
    @Test
    public void test20(){
        //select * from human; === 全查   不带参数
        HumanExample example = new HumanExample();
        List<Human> humanList = sqlSession.selectList("com.hp.dao.HumanDAO.selectByExample",example);
        for (Human human : humanList) {
            System.out.println("human = " + human);
        }
        sqlSession.close();


    }
    @Test
    public void test20_01(){
        //select * from human where gender = 2;   带gender 的参数
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andGenderEqualTo(2);
        List<Human> humanList = sqlSession.selectList("com.hp.dao.HumanDAO.selectByExample",example);
        for (Human human : humanList) {
            System.out.println("human = " + human);
        }
        sqlSession.close();


    }
    @Test
    public void test20_02(){
        //select * from human where gender = 1;   带gender 的参数
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andGenderEqualTo(1);
        List<Human> humanList = sqlSession.selectList("com.hp.dao.HumanDAO.selectByExample",example);
        for (Human human : humanList) {
            System.out.println("human = " + human);
        }
        sqlSession.close();


    }
    @Test
    public void test20_03(){
        //select * from human where id = 1;  带id 的参数
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(1);
        List<Human> humanList = sqlSession.selectList("com.hp.dao.HumanDAO.selectByExample",example);
        for (Human human : humanList) {
            System.out.println("human = " + human);
        }
        sqlSession.close();


    }
    @Test
    public void test20_04(){
        //select * from human where score > 100;  带 score 的参数   大于andScoreGreaterThan
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andScoreGreaterThan(100.0);
        List<Human> humanList = sqlSession.selectList("com.hp.dao.HumanDAO.selectByExample",example);
        for (Human human : humanList) {
            System.out.println("human = " + human);
        }
        sqlSession.close();


    }
    @Test
    public void test20_05(){
        //select * from human where score > 100 and gender = 1;
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andScoreGreaterThan(100.0);
        criteria.andGenderEqualTo(1);
        List<Human> humanList = sqlSession.selectList("com.hp.dao.HumanDAO.selectByExample",example);
        for (Human human : humanList) {
            System.out.println("human = " + human);
        }
        sqlSession.close();


    }
    @Test
    public void test20_06(){
        //select * from human where score > 100 and gender = 1 and address like "%西京%";
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andScoreGreaterThan(100.0);
        criteria.andGenderEqualTo(1);
        criteria.andAddressLike("%"+"西京"+"%");
        List<Human> humanList = sqlSession.selectList("com.hp.dao.HumanDAO.selectByExample",example);
        for (Human human : humanList) {
            System.out.println("human = " + human);
        }
        sqlSession.close();


    }


    //工具的增加
    @Test
    public void test21(){
        Human human = new Human();
        human.setGender(2);
        human.setName("王源");
        human.setAddress("重庆");
        int insert = sqlSession.insert("com.hp.dao.HumanDAO.insertSelective", human);
        //insert into human ( `name`, gender, address ) values ( ?, ?, ? )
        //int insert = sqlSession.insert("com.hp.dao.HumanDAO.insert", human);
        //insert into human (id, `name`, gender, birthday, address, score ) values (?, ?, ?, ?, ?, ? )
        //Parameters: null, 王源(String), 2(Integer), null, 重庆(String), null  //满参数
        //当数据库中有的字段是默认值时 或者 不能为null 的时候，该增加的方法就会出错，所以要用 长的 insertSelective
        System.out.println("insert = " + insert);
        sqlSession.commit();
        sqlSession.close();
    }

    //工具的删除
    //按主键id删除
    @Test
    public void test22(){
        int delete = sqlSession.delete("com.hp.dao.HumanDAO.deleteByPrimaryKey", 7);
        System.out.println("delete = " + delete);//delete from human where id = ?
        sqlSession.commit();
        sqlSession.close();
    }
    //按条件删除：
    //1.删除所有女生    2.删除分数小于20的  3.删除名字中 带有 云字的的人  4.删除女生并且分数小于20的
    @Test
    public void test23(){
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        //criteria.andGenderEqualTo(2);
        //criteria.andScoreLessThan(20.0);
        criteria.andNameLike("%"+"云"+"%");//模糊删除

        int delete = sqlSession.delete("com.hp.dao.HumanDAO.deleteByExample",example);
        System.out.println("delete = " + delete);//delete from human WHERE ( gender = ? and score < ? )
        sqlSession.commit();
        sqlSession.close();
    }

    //按主键id 修改 1个对象
    // 把 悟空 改为八戒
    @Test
    public void test24(){
        Human human = new Human();
        human.setId(4);
        human.setName("孙悟空");
        int update = sqlSession.update("com.hp.dao.HumanDAO.updateByPrimaryKeySelective", human);
        //update human SET `name` = ? where id = ?

        //int update = sqlSession.update("com.hp.dao.HumanDAO.updateByPrimaryKey", human);
        //update human set `name` = ?, gender = ?, birthday = ?, address = ?, score = ? where id = ?
        //Parameters: 孙悟空(String), null, null, null, null, 4(Integer)
        //修改千万不能用短的，使用updateByPrimaryKey， 会造成 原有字段变成null
        System.out.println("update = " + update);
        sqlSession.commit();
        sqlSession.close();
    }

    //批量 的动态修改----当分数超过100的改为100
    //测试不了
    @Test
    public void test25(){
        Human human = new Human();
        human.setScore(100.0);
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andScoreGreaterThan(100.0);

       // sqlSession.update("com.hp.dao.HumanDAO.updateByExampleSelective", human);
       //测试不了
    }

    //按主键id查询
    @Test
    public void test26(){
        Human o = sqlSession.selectOne("com.hp.dao.HumanDAO.selectByPrimaryKey", 4);
        System.out.println("o = " + o);//select id, `name`, gender, birthday, address, score from human where id = ?
        sqlSession.close();
    }

    //动态的查询 com.hp.dao.HumanDAO.selectByExample
    //1.查询分数大于100的人
    //2.查询分数大于100的人且 生日 大于 2020-11-04 的人
    //3.查询分数大于100的人且 生日 大于 2020-11-04 的人 并且是女生的人
    @Test
    public void test27() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2020-11-04");
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andScoreGreaterThan(100.0);
        //select id, `name`, gender, birthday, address, score from human WHERE ( score > ? )
        criteria.andBirthdayGreaterThan(date);
        //select id, `name`, gender, birthday, address, score from human WHERE ( score > ? and birthday > ? )
        criteria.andGenderEqualTo(2);
        //select id, `name`, gender, birthday, address, score from human WHERE ( score > ? and birthday > ? and gender = ? )
        List<Human> humanList = sqlSession.selectList("com.hp.dao.HumanDAO.selectByExample", example);
        for (Human human : humanList) {
            System.out.println("human = " + human);
        }
        sqlSession.close();
    }


    //一对多
    @Test
    public void test28(){
        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.selectOrdersByPersonId",1);
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //动态查询
    @Test
    public void test29(){
        Map codeMap = new HashMap();
        codeMap.put("id",1);
        //codeMap.put("name","孙香香1");

        List<Map> maps = sqlSession.selectList("com.hp.dao.PersonDao.dongTaiOrdersByPerson",codeMap);
        for (Map map : maps) {
            System.out.println("map = " + map);
        }
        sqlSession.close();
    }

    //1对多对多
    @Test
    public void test30(){
        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.selectDetailByPersonId",3);
        System.out.println("personList = " + personList.size());
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //三表联查  平面的   这个关系 没有1对 对多 概念了，只有关联关系！  平面的结构
    @Test
    public void test31(){
        Map map = new HashMap();
        map.put("id",3);
        List<Map> mapList = sqlSession.selectList("com.hp.dao.PersonDao.selectDetailByParam",map);
        System.out.println("mapList.size() = " + mapList.size());
        for (Map map1 : mapList) {
            System.out.println("map1 = " + map1);
        }
        sqlSession.close();
    }

    //多对1  反向，注意： 实体类中 多方写 1方的实体类

    @Test
    public void test32(){
        Orders o = sqlSession.selectOne("com.hp.dao.OrdersDAO.selectPersonByOrdersId", 3);
        System.out.println("o = " + o);
        sqlSession.close();

    }

    // 1 vs 1  可以看做是简单的  多对一    譬如 人和身份证  不再写了
    //多对多-----可以看作是 带中间表的 1 对多 他们是由两个一对多组成
    @Test
    public void test34(){
        List<Person> personList = sqlSession.selectList("com.hp.dao.PersonDao.selectRoleByParam","iroy");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //1.单表的curd  ----动态的SQL  都可以自动生成
    //2.多表的查询   ----1对多（树状）  多对1（1条数据）  万能查（N多表 可以查多条数据）  可以吗？？？   可以
}
