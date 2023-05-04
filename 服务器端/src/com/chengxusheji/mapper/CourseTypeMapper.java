package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.CourseType;

public interface CourseTypeMapper {
	/*添加课程类型信息*/
	public void addCourseType(CourseType courseType) throws Exception;

	/*按照查询条件分页查询课程类型记录*/
	public ArrayList<CourseType> queryCourseType(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有课程类型记录*/
	public ArrayList<CourseType> queryCourseTypeList(@Param("where") String where) throws Exception;

	/*按照查询条件的课程类型记录数*/
	public int queryCourseTypeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条课程类型记录*/
	public CourseType getCourseType(int courseTypeId) throws Exception;

	/*更新课程类型记录*/
	public void updateCourseType(CourseType courseType) throws Exception;

	/*删除课程类型记录*/
	public void deleteCourseType(int courseTypeId) throws Exception;

}
