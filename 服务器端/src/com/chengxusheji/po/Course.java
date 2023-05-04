package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Course {
    /*课程id*/
    private Integer courseId;
    public Integer getCourseId(){
        return courseId;
    }
    public void setCourseId(Integer courseId){
        this.courseId = courseId;
    }

    /*课程类型*/
    private CourseType courseTypeObj;
    public CourseType getCourseTypeObj() {
        return courseTypeObj;
    }
    public void setCourseTypeObj(CourseType courseTypeObj) {
        this.courseTypeObj = courseTypeObj;
    }

    /*课程名称*/
    @NotEmpty(message="课程名称不能为空")
    private String courseName;
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /*课程照片*/
    private String coursePhoto;
    public String getCoursePhoto() {
        return coursePhoto;
    }
    public void setCoursePhoto(String coursePhoto) {
        this.coursePhoto = coursePhoto;
    }

    private String coursePhotoUrl;
    public void setCoursePhotoUrl(String coursePhotoUrl) {
		this.coursePhotoUrl = coursePhotoUrl;
	}
	public String getCoursePhotoUrl() {
		return  SessionConsts.BASE_URL + coursePhoto;
	}
    /*总课时*/
    @NotNull(message="必须输入总课时")
    private Integer personNum;
    public Integer getPersonNum() {
        return personNum;
    }
    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    /*上课教室*/
    @NotEmpty(message="上课教室不能为空")
    private String courseLocation;
    public String getCourseLocation() {
        return courseLocation;
    }
    public void setCourseLocation(String courseLocation) {
        this.courseLocation = courseLocation;
    }

    /*课程学分*/
    @NotNull(message="必须输入课程学分")
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    /*课程介绍*/
    @NotEmpty(message="课程介绍不能为空")
    private String courseDesc;
    public String getCourseDesc() {
        return courseDesc;
    }
    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCourse=new JSONObject(); 
		jsonCourse.accumulate("courseId", this.getCourseId());
		jsonCourse.accumulate("courseTypeObj", this.getCourseTypeObj().getCourseTypeName());
		jsonCourse.accumulate("courseTypeObjPri", this.getCourseTypeObj().getCourseTypeId());
		jsonCourse.accumulate("courseName", this.getCourseName());
		jsonCourse.accumulate("coursePhoto", this.getCoursePhoto());
		jsonCourse.accumulate("personNum", this.getPersonNum());
		jsonCourse.accumulate("courseLocation", this.getCourseLocation());
		jsonCourse.accumulate("price", this.getPrice());
		jsonCourse.accumulate("courseDesc", this.getCourseDesc());
		jsonCourse.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonCourse;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}