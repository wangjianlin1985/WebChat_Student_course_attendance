package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.CourseType;
import com.chengxusheji.po.Course;

import com.chengxusheji.mapper.CourseMapper;
@Service
public class CourseService {

	@Resource CourseMapper courseMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加课程记录*/
    public void addCourse(Course course) throws Exception {
    	courseMapper.addCourse(course);
    }

    /*按照查询条件分页查询课程记录*/
    public ArrayList<Course> queryCourse(CourseType courseTypeObj,String courseName,String courseLocation,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != courseTypeObj && courseTypeObj.getCourseTypeId()!= null && courseTypeObj.getCourseTypeId()!= 0)  where += " and t_course.courseTypeObj=" + courseTypeObj.getCourseTypeId();
    	if(!courseName.equals("")) where = where + " and t_course.courseName like '%" + courseName + "%'";
    	if(!courseLocation.equals("")) where = where + " and t_course.courseLocation like '%" + courseLocation + "%'";
    	if(!addTime.equals("")) where = where + " and t_course.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return courseMapper.queryCourse(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Course> queryCourse(CourseType courseTypeObj,String courseName,String courseLocation,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != courseTypeObj && courseTypeObj.getCourseTypeId()!= null && courseTypeObj.getCourseTypeId()!= 0)  where += " and t_course.courseTypeObj=" + courseTypeObj.getCourseTypeId();
    	if(!courseName.equals("")) where = where + " and t_course.courseName like '%" + courseName + "%'";
    	if(!courseLocation.equals("")) where = where + " and t_course.courseLocation like '%" + courseLocation + "%'";
    	if(!addTime.equals("")) where = where + " and t_course.addTime like '%" + addTime + "%'";
    	return courseMapper.queryCourseList(where);
    }

    /*查询所有课程记录*/
    public ArrayList<Course> queryAllCourse()  throws Exception {
        return courseMapper.queryCourseList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(CourseType courseTypeObj,String courseName,String courseLocation,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != courseTypeObj && courseTypeObj.getCourseTypeId()!= null && courseTypeObj.getCourseTypeId()!= 0)  where += " and t_course.courseTypeObj=" + courseTypeObj.getCourseTypeId();
    	if(!courseName.equals("")) where = where + " and t_course.courseName like '%" + courseName + "%'";
    	if(!courseLocation.equals("")) where = where + " and t_course.courseLocation like '%" + courseLocation + "%'";
    	if(!addTime.equals("")) where = where + " and t_course.addTime like '%" + addTime + "%'";
        recordNumber = courseMapper.queryCourseCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取课程记录*/
    public Course getCourse(int courseId) throws Exception  {
        Course course = courseMapper.getCourse(courseId);
        return course;
    }

    /*更新课程记录*/
    public void updateCourse(Course course) throws Exception {
        courseMapper.updateCourse(course);
    }

    /*删除一条课程记录*/
    public void deleteCourse (int courseId) throws Exception {
        courseMapper.deleteCourse(courseId);
    }

    /*删除多条课程信息*/
    public int deleteCourses (String courseIds) throws Exception {
    	String _courseIds[] = courseIds.split(",");
    	for(String _courseId: _courseIds) {
    		courseMapper.deleteCourse(Integer.parseInt(_courseId));
    	}
    	return _courseIds.length;
    }
}
