package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.CourseService;
import com.chengxusheji.po.Course;
import com.chengxusheji.service.CourseTypeService;
import com.chengxusheji.po.CourseType;

//Course管理控制层
@Controller
@RequestMapping("/Course")
public class CourseController extends BaseController {

    /*业务层对象*/
    @Resource CourseService courseService;

    @Resource CourseTypeService courseTypeService;
	@InitBinder("courseTypeObj")
	public void initBindercourseTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("courseTypeObj.");
	}
	@InitBinder("course")
	public void initBinderCourse(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("course.");
	}
	/*跳转到添加Course视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Course());
		/*查询所有的CourseType信息*/
		List<CourseType> courseTypeList = courseTypeService.queryAllCourseType();
		request.setAttribute("courseTypeList", courseTypeList);
		return "Course_add";
	}

	/*客户端ajax方式提交添加课程信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Course course, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			course.setCoursePhoto(this.handlePhotoUpload(request, "coursePhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        courseService.addCourse(course);
        message = "课程添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询课程信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("courseTypeObj") CourseType courseTypeObj,String courseName,String courseLocation,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Course course:courseList) {
			JSONObject jsonCourse = course.getJsonObject();
			jsonArray.put(jsonCourse);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询课程信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Course> courseList = courseService.queryAllCourse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Course course:courseList) {
			JSONObject jsonCourse = new JSONObject();
			jsonCourse.accumulate("courseId", course.getCourseId());
			jsonCourse.accumulate("courseName", course.getCourseName());
			jsonArray.put(jsonCourse);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询课程信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("courseTypeObj") CourseType courseTypeObj,String courseName,String courseLocation,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (courseName == null) courseName = "";
		if (courseLocation == null) courseLocation = "";
		if (addTime == null) addTime = "";
		List<Course> courseList = courseService.queryCourse(courseTypeObj, courseName, courseLocation, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    courseService.queryTotalPageAndRecordNumber(courseTypeObj, courseName, courseLocation, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = courseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = courseService.getRecordNumber();
	    request.setAttribute("courseList",  courseList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("courseTypeObj", courseTypeObj);
	    request.setAttribute("courseName", courseName);
	    request.setAttribute("courseLocation", courseLocation);
	    request.setAttribute("addTime", addTime);
	    List<CourseType> courseTypeList = courseTypeService.queryAllCourseType();
	    request.setAttribute("courseTypeList", courseTypeList);
		return "Course/course_frontquery_result"; 
	}

     /*前台查询Course信息*/
	@RequestMapping(value="/{courseId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer courseId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键courseId获取Course对象*/
        Course course = courseService.getCourse(courseId);

        List<CourseType> courseTypeList = courseTypeService.queryAllCourseType();
        request.setAttribute("courseTypeList", courseTypeList);
        request.setAttribute("course",  course);
        return "Course/course_frontshow";
	}

	/*ajax方式显示课程修改jsp视图页*/
	@RequestMapping(value="/{courseId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer courseId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键courseId获取Course对象*/
        Course course = courseService.getCourse(courseId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonCourse = course.getJsonObject();
		out.println(jsonCourse.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新课程信息*/
	@RequestMapping(value = "/{courseId}/update", method = RequestMethod.POST)
	public void update(@Validated Course course, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String coursePhotoFileName = this.handlePhotoUpload(request, "coursePhotoFile");
		if(!coursePhotoFileName.equals("upload/NoImage.jpg"))course.setCoursePhoto(coursePhotoFileName); 


		try {
			courseService.updateCourse(course);
			message = "课程更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "课程更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除课程信息*/
	@RequestMapping(value="/{courseId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer courseId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  courseService.deleteCourse(courseId);
	            request.setAttribute("message", "课程删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "课程删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条课程记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String courseIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = courseService.deleteCourses(courseIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出课程信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("courseTypeObj") CourseType courseTypeObj,String courseName,String courseLocation,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(courseName == null) courseName = "";
        if(courseLocation == null) courseLocation = "";
        if(addTime == null) addTime = "";
        List<Course> courseList = courseService.queryCourse(courseTypeObj,courseName,courseLocation,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Course信息记录"; 
        String[] headers = { "课程id","课程类型","课程名称","课程照片","总课时","上课教室","课程学分","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<courseList.size();i++) {
        	Course course = courseList.get(i); 
        	dataset.add(new String[]{course.getCourseId() + "",course.getCourseTypeObj().getCourseTypeName(),course.getCourseName(),course.getCoursePhoto(),course.getPersonNum() + "",course.getCourseLocation(),course.getPrice() + "",course.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Course.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
