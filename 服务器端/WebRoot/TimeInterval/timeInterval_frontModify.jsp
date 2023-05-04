<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.TimeInterval" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    TimeInterval timeInterval = (TimeInterval)request.getAttribute("timeInterval");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改时段信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">时段信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="timeIntervalEditForm" id="timeIntervalEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="timeInterval_intervalId_edit" class="col-md-3 text-right">时段id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="timeInterval_intervalId_edit" name="timeInterval.intervalId" class="form-control" placeholder="请输入时段id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="timeInterval_intervalName_edit" class="col-md-3 text-right">时段名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="timeInterval_intervalName_edit" name="timeInterval.intervalName" class="form-control" placeholder="请输入时段名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="timeInterval_product_edit" class="col-md-3 text-right">商品数量:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="timeInterval_product_edit" name="timeInterval.product" class="form-control" placeholder="请输入商品数量">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxTimeIntervalModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#timeIntervalEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改时段界面并初始化数据*/
function timeIntervalEdit(intervalId) {
	$.ajax({
		url :  basePath + "TimeInterval/" + intervalId + "/update",
		type : "get",
		dataType: "json",
		success : function (timeInterval, response, status) {
			if (timeInterval) {
				$("#timeInterval_intervalId_edit").val(timeInterval.intervalId);
				$("#timeInterval_intervalName_edit").val(timeInterval.intervalName);
				$("#timeInterval_product_edit").val(timeInterval.product);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交时段信息表单给服务器端修改*/
function ajaxTimeIntervalModify() {
	$.ajax({
		url :  basePath + "TimeInterval/" + $("#timeInterval_intervalId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#timeIntervalEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                location.href= basePath + "TimeInterval/frontlist";
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse > a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();
    timeIntervalEdit("<%=request.getParameter("intervalId")%>");
 })
 </script> 
</body>
</html>

