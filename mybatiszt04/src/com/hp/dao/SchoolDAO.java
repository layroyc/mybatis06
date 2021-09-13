package com.hp.dao;

import com.hp.entity.School;
import com.hp.entity.SchoolExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SchoolDAO {
    long countByExample(SchoolExample example);

    int deleteByExample(SchoolExample example);

    int deleteByPrimaryKey(Integer scid);

    int insert(School record);

    int insertSelective(School record);

    List<School> selectByExample(SchoolExample example);

    School selectByPrimaryKey(Integer scid);

    int updateByExampleSelective(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByExample(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
    //学校--班级--学生 的  1 对多  对多的 查询
    List<School> selectStudentById(Integer sid);
    //写出 学校--班级--学生 的   多条查询
    List<Map> selectAllDongTai(Map map);

}