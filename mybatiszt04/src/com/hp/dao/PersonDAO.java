package com.hp.dao;

import com.hp.entity.Person;
import com.hp.entity.PersonExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonDAO {
    long countByExample(PersonExample example);

    int deleteByExample(PersonExample example);

    int deleteByPrimaryKey(Integer pid);

    int insert(Person record);

    int insertSelective(Person record);

    List<Person> selectByExample(PersonExample example);

    Person selectByPrimaryKey(Integer pid);

    int updateByExampleSelective(@Param("record") Person record, @Param("example") PersonExample example);

    int updateByExample(@Param("record") Person record, @Param("example") PersonExample example);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);

    //根据人查找对应的身份信息
    Person selectById(Integer pId);
}