package com.client.controller;

import java.text.SimpleDateFormat;
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
import com.chengxusheji.po.OrderInfo;
import com.chengxusheji.po.PayWay;
import com.chengxusheji.po.Course;
import com.chengxusheji.po.TimeInterval;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.service.OrderInfoService;
import com.chengxusheji.service.CourseService;
import com.chengxusheji.service.TimeIntervalService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/orderInfo") 
public class ApiOrderInfoController {
	@Resource OrderInfoService orderInfoService;
	@Resource AuthService authService;
	@Resource TimeIntervalService timeIntervalService;
	@Resource CourseService courseService;

	@InitBinder("payWayObj")
	public void initBinderpayWayObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("payWayObj.");
	}
	@InitBinder("courseObj")
	public void initBindercourseObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("courseObj.");
	}
	@InitBinder("intervalObj")
	public void initBinderintervalObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("intervalObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("orderInfo")
	public void initBinderOrderInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("orderInfo.");
	}

	/*客户端ajax方式添加考勤记录信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated OrderInfo orderInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        orderInfoService.addOrderInfo(orderInfo); //添加到数据库
        return JsonResultBuilder.ok();
	}

	
	/*客户端ajax方式添加考勤记录信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public JsonResult userAdd(@Validated OrderInfo orderInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
		
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(userName);
		orderInfo.setUserObj(userObj);
		
		if(orderInfoService.queryOrderInfo(null, orderInfo.getCourseObj(), orderInfo.getOrderDate(), orderInfo.getIntervalObj(), null, "", "", "", "", "").size() > 0)
			return JsonResultBuilder.error(ReturnCode.ORDER_CONFLICT_ERROR);
		
		//计算预定总金额
		int courseId = orderInfo.getCourseObj().getCourseId();
		int intervalId = orderInfo.getIntervalObj().getIntervalId();
		float price = courseService.getCourse(courseId).getPrice(); //获取到单价
		float productNum = timeIntervalService.getTimeInterval(intervalId).getProduct();
		float totalMoney = price * productNum;
		orderInfo.setTotalMoney(totalMoney);
		
		orderInfo.setOrderStateObj("待处理");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		orderInfo.setOrderTime(sdf.format(new java.util.Date()));  //生成签到时间
		 
		
        orderInfoService.addOrderInfo(orderInfo); //添加到数据库
        return JsonResultBuilder.ok();
	}
	
	
	/*客户端ajax更新考勤记录信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated OrderInfo orderInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        orderInfoService.updateOrderInfo(orderInfo);  //更新记录到数据库
        return JsonResultBuilder.ok(orderInfoService.getOrderInfo(orderInfo.getOrderNo()));
	}

	/*ajax方式显示获取考勤记录详细信息*/
	@RequestMapping(value="/get/{orderNo}",method=RequestMethod.POST)
	public JsonResult getOrderInfo(@PathVariable int orderNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键orderNo获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderNo); 
        return JsonResultBuilder.ok(orderInfo);
	}

	/*ajax方式删除考勤记录记录*/
	@RequestMapping(value="/delete/{orderNo}",method=RequestMethod.POST)
	public JsonResult deleteOrderInfo(@PathVariable int orderNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			orderInfoService.deleteOrderInfo(orderNo);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询考勤记录信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("userObj") UserInfo userObj,@ModelAttribute("courseObj") Course courseObj,String orderDate,@ModelAttribute("intervalObj") TimeInterval intervalObj,@ModelAttribute("payWayObj") PayWay payWayObj,String payAccount,String orderStateObj,String orderTime,String receiveName,String telephone,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		
		if (page==null || page == 0) page = 1;
		if (orderDate == null) orderDate = "";
		if (payAccount == null) payAccount = "";
		if (orderStateObj == null) orderStateObj = "";
		if (orderTime == null) orderTime = "";
		if (receiveName == null) receiveName = "";
		if (telephone == null) telephone = "";
		if(rows != 0)orderInfoService.setRows(rows);
		userObj = new UserInfo();
		userObj.setUser_name(userName);
		List<OrderInfo> orderInfoList = orderInfoService.queryOrderInfo(userObj, courseObj, orderDate, intervalObj, payWayObj, payAccount, orderStateObj, orderTime, receiveName, telephone, page);
	    /*计算总的页数和总的记录数*/
	    orderInfoService.queryTotalPageAndRecordNumber(userObj, courseObj, orderDate, intervalObj, payWayObj, payAccount, orderStateObj, orderTime, receiveName, telephone);
	    /*获取到总的页码数目*/
	    int totalPage = orderInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = orderInfoService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", orderInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有考勤记录
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<OrderInfo> orderInfoList = orderInfoService.queryAllOrderInfo(); 
		return JsonResultBuilder.ok(orderInfoList);
	}
}

