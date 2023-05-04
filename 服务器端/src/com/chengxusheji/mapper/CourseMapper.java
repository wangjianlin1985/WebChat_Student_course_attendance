package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Course;

public interface CourseMapper {
	/*添加课程信息*/
	public void addCourse(Course course) throws Exception;

	/*按照查询条件分页查询课程记录*/
	public ArrayList<Course> queryCourse(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有课程记录*/
	public ArrayList<Course> queryCourseList(@Param("where") String where) throws Exception;

	/*按照查询条件的课程记录数*/
	public int queryCourseCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条课程记录*/
	public Course getCourse(int courseId) throws Exception;

	/*更新课程记录*/
	public void updateCourse(Course course) throws Exception;

	/*删除课程记录*/
	public void deleteCourse(int courseId) throws Exception;

}
