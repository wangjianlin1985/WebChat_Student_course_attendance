package com.client.controller;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.chengxusheji.po.Course;
import com.chengxusheji.po.CourseType;
import com.chengxusheji.service.CourseService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/course") 
public class ApiCourseController {
	@Resource CourseService courseService;
	@Resource AuthService authService;

	@InitBinder("courseTypeObj")
	public void initBindercourseTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("courseTypeObj.");
	}
	@InitBinder("course")
	public void initBinderCourse(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("course.");
	}

	/*客户端ajax方式添加课程信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated Course course, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        courseService.addCourse(course); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新课程信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated Course course, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        courseService.updateCourse(course);  //更新记录到数据库
        return JsonResultBuilder.ok(courseService.getCourse(course.getCourseId()));
	}

	/*ajax方式显示获取课程详细信息*/
	@RequestMapping(value="/get/{courseId}",method=RequestMethod.POST)
	public JsonResult getCourse(@PathVariable int courseId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键courseId获取Course对象*/
        Course course = courseService.getCourse(courseId); 
        return JsonResultBuilder.ok(course);
	}

	/*ajax方式删除课程记录*/
	@RequestMapping(value="/delete/{courseId}",method=RequestMethod.POST)
	public JsonResult deleteCourse(@PathVariable int courseId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			courseService.deleteCourse(courseId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询课程信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("courseTypeObj") CourseType courseTypeObj,String courseName,String courseLocation,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (courseName == null) courseName = "";
		if (courseLocation == null) courseLocation = "";
		if (addTime == null) addTime = "";
		if(rows != 0)courseService.setRows(rows);
		List<Course> courseList = courseService.queryCourse(courseTypeObj, courseName, courseLocation, addTime, page);
	    /*计算总的页数和总的记录数*/
	    courseService.queryTotalPageAndRecordNumber(courseTypeObj, courseName, courseLocation, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = courseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = courseService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", courseList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有课程
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<Course> courseList = courseService.queryAllCourse(); 
		return JsonResultBuilder.ok(courseList);
	}
}

