package dev.itboot.mb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import dev.itboot.mb.model.Teacher;

@Mapper
public interface TeacherMapper {
	Long count();

	// RowBoundsを使用しない方法で実装
	List<Teacher> selectAll(@Param("pageable") Pageable pageable);

	// 各SQLはTeacherMapper.xmlに記述
	List<Teacher> selectAll();

	Teacher selectByPrimaryKey(Long id);

	int insert(Teacher record);

	int updateByPrimaryKey(Teacher record);

	int deleteByPrimaryKey(Long id);
}
