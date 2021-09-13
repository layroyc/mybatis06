package com.hp.dao;

import com.hp.entity.Student;
import com.hp.entity.StudentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDAO {
    long countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
    //学生   学生班委    班委名称   他们 多对多的查询 根据名称查角色
    List<Student> selectBanweiByParam(String BanName);
}