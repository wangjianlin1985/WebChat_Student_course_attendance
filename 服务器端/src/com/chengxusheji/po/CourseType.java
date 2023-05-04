package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CourseType {
    /*课程类型id*/
    private Integer courseTypeId;
    public Integer getCourseTypeId(){
        return courseTypeId;
    }
    public void setCourseTypeId(Integer courseTypeId){
        this.courseTypeId = courseTypeId;
    }

    /*课程类型名称*/
    @NotEmpty(message="课程类型名称不能为空")
    private String courseTypeName;
    public String getCourseTypeName() {
        return courseTypeName;
    }
    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    /*课程类型说明*/
    @NotEmpty(message="课程类型说明不能为空")
    private String courseTypeDesc;
    public String getCourseTypeDesc() {
        return courseTypeDesc;
    }
    public void setCourseTypeDesc(String courseTypeDesc) {
        this.courseTypeDesc = courseTypeDesc;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCourseType=new JSONObject(); 
		jsonCourseType.accumulate("courseTypeId", this.getCourseTypeId());
		jsonCourseType.accumulate("courseTypeName", this.getCourseTypeName());
		jsonCourseType.accumulate("courseTypeDesc", this.getCourseTypeDesc());
		return jsonCourseType;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}