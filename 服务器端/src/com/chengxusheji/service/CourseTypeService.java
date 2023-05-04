package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.CourseType;

import com.chengxusheji.mapper.CourseTypeMapper;
@Service
public class CourseTypeService {

	@Resource CourseTypeMapper courseTypeMapper;
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

    /*添加课程类型记录*/
    public void addCourseType(CourseType courseType) throws Exception {
    	courseTypeMapper.addCourseType(courseType);
    }

    /*按照查询条件分页查询课程类型记录*/
    public ArrayList<CourseType> queryCourseType(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return courseTypeMapper.queryCourseType(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<CourseType> queryCourseType() throws Exception  { 
     	String where = "where 1=1";
    	return courseTypeMapper.queryCourseTypeList(where);
    }

    /*查询所有课程类型记录*/
    public ArrayList<CourseType> queryAllCourseType()  throws Exception {
        return courseTypeMapper.queryCourseTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = courseTypeMapper.queryCourseTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取课程类型记录*/
    public CourseType getCourseType(int courseTypeId) throws Exception  {
        CourseType courseType = courseTypeMapper.getCourseType(courseTypeId);
        return courseType;
    }

    /*更新课程类型记录*/
    public void updateCourseType(CourseType courseType) throws Exception {
        courseTypeMapper.updateCourseType(courseType);
    }

    /*删除一条课程类型记录*/
    public void deleteCourseType (int courseTypeId) throws Exception {
        courseTypeMapper.deleteCourseType(courseTypeId);
    }

    /*删除多条课程类型信息*/
    public int deleteCourseTypes (String courseTypeIds) throws Exception {
    	String _courseTypeIds[] = courseTypeIds.split(",");
    	for(String _courseTypeId: _courseTypeIds) {
    		courseTypeMapper.deleteCourseType(Integer.parseInt(_courseTypeId));
    	}
    	return _courseTypeIds.length;
    }
}
