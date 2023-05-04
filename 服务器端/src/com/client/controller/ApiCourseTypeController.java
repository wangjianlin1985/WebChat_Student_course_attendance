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
import com.chengxusheji.po.CourseType;
import com.chengxusheji.service.CourseTypeService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/courseType") 
public class ApiCourseTypeController {
	@Resource CourseTypeService courseTypeService;
	@Resource AuthService authService;

	@InitBinder("courseType")
	public void initBinderCourseType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("courseType.");
	}

	/*客户端ajax方式添加课程类型信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated CourseType courseType, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        courseTypeService.addCourseType(courseType); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新课程类型信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated CourseType courseType, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        courseTypeService.updateCourseType(courseType);  //更新记录到数据库
        return JsonResultBuilder.ok(courseTypeService.getCourseType(courseType.getCourseTypeId()));
	}

	/*ajax方式显示获取课程类型详细信息*/
	@RequestMapping(value="/get/{courseTypeId}",method=RequestMethod.POST)
	public JsonResult getCourseType(@PathVariable int courseTypeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键courseTypeId获取CourseType对象*/
        CourseType courseType = courseTypeService.getCourseType(courseTypeId); 
        return JsonResultBuilder.ok(courseType);
	}

	/*ajax方式删除课程类型记录*/
	@RequestMapping(value="/delete/{courseTypeId}",method=RequestMethod.POST)
	public JsonResult deleteCourseType(@PathVariable int courseTypeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			courseTypeService.deleteCourseType(courseTypeId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询课程类型信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)courseTypeService.setRows(rows);
		List<CourseType> courseTypeList = courseTypeService.queryCourseType(page);
	    /*计算总的页数和总的记录数*/
	    courseTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = courseTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = courseTypeService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", courseTypeList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有课程类型
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<CourseType> courseTypeList = courseTypeService.queryAllCourseType(); 
		return JsonResultBuilder.ok(courseTypeList);
	}
}

