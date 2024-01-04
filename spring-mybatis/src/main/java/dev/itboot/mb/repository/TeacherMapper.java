package dev.itboot.mb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dev.itboot.mb.model.Teacher;

@Mapper
public interface TeacherMapper {

	// 各SQLはTeacherMapper.xmlに記述したため削除
	List<Teacher> selectAll();

	Teacher selectByPrimaryKey(Long id);

	int insert(Teacher record);

	int updateByPrimaryKey(Teacher record);

	int deleteByPrimaryKey(Long id);
}
