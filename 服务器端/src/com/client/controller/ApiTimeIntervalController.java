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
import com.chengxusheji.po.TimeInterval;
import com.chengxusheji.service.TimeIntervalService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/timeInterval") 
public class ApiTimeIntervalController {
	@Resource TimeIntervalService timeIntervalService;
	@Resource AuthService authService;

	@InitBinder("timeInterval")
	public void initBinderTimeInterval(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("timeInterval.");
	}

	/*客户端ajax方式添加时段信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated TimeInterval timeInterval, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        timeIntervalService.addTimeInterval(timeInterval); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新时段信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated TimeInterval timeInterval, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        timeIntervalService.updateTimeInterval(timeInterval);  //更新记录到数据库
        return JsonResultBuilder.ok(timeIntervalService.getTimeInterval(timeInterval.getIntervalId()));
	}

	/*ajax方式显示获取时段详细信息*/
	@RequestMapping(value="/get/{intervalId}",method=RequestMethod.POST)
	public JsonResult getTimeInterval(@PathVariable int intervalId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键intervalId获取TimeInterval对象*/
        TimeInterval timeInterval = timeIntervalService.getTimeInterval(intervalId); 
        return JsonResultBuilder.ok(timeInterval);
	}

	/*ajax方式删除时段记录*/
	@RequestMapping(value="/delete/{intervalId}",method=RequestMethod.POST)
	public JsonResult deleteTimeInterval(@PathVariable int intervalId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			timeIntervalService.deleteTimeInterval(intervalId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询时段信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)timeIntervalService.setRows(rows);
		List<TimeInterval> timeIntervalList = timeIntervalService.queryTimeInterval(page);
	    /*计算总的页数和总的记录数*/
	    timeIntervalService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = timeIntervalService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = timeIntervalService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", timeIntervalList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有时段
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<TimeInterval> timeIntervalList = timeIntervalService.queryAllTimeInterval(); 
		return JsonResultBuilder.ok(timeIntervalList);
	}
}

