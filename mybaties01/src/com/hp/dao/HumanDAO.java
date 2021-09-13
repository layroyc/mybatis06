package com.hp.dao;

import com.hp.bean.Human;
import com.hp.bean.HumanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HumanDAO {
    //所有的dao  单表：
    // 增--增加一条数据
    // 删--根据id删除，条件删除，（动态SQL）
    // 改：根基主键，条件修改（动态SQL）
    // 查-- 按主键id 查对象，，查总条数（动态），

    long countByExample(HumanExample example);// 用Example类查总条数, 动态的SQL查询总条数
    //当Example类为null的时候  sql语句的 等于如下
    //select count(*) from human
    //当example 类不为 null的时候，SQL语句等于如下
    //select count(*) from human  where gender = 2  ||参数如何传递呢


    int deleteByExample(HumanExample example);//按条件的删除

    int deleteByPrimaryKey(Integer id);//按主键id删除

    int insert(Human record);//当 human对象 所有属性都在就可以用他，就是一个普通的增加

    int insertSelective(Human record);//尽量用他！！！ 动态的添加   //优先用他报错了，就用上面的

    List<Human> selectByExample(HumanExample example);//动态查询

    Human selectByPrimaryKey(Integer id);//按主键id查询

    //传一个对象，传一个example
    int updateByExampleSelective(@Param("record") Human record, @Param("example") HumanExample example);//动态的批量修改  用它！！！

    int updateByExample(@Param("record") Human record, @Param("example") HumanExample example);//动态的批量修改  不用！！

    int updateByPrimaryKeySelective(Human record);//按主键id修改一条数据  一个对象

    int updateByPrimaryKey(Human record);//千万不能用！！！
}