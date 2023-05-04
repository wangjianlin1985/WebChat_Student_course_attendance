<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>时段添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<div class="row">
		<div class="col-md-12 wow fadeInUp" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li role="presentation" ><a href="<%=basePath %>TimeInterval/frontlist">时段列表</a></li>
			    	<li role="presentation" class="active"><a href="#timeIntervalAdd" aria-controls="timeIntervalAdd" role="tab" data-toggle="tab">添加时段</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="timeIntervalList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="timeIntervalAdd"> 
				      	<form class="form-horizontal" name="timeIntervalAddForm" id="timeIntervalAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="timeInterval_intervalName" class="col-md-2 text-right">时段名称:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="timeInterval_intervalName" name="timeInterval.intervalName" class="form-control" placeholder="请输入时段名称">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="timeInterval_product" class="col-md-2 text-right">商品数量:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="timeInterval_product" name="timeInterval.product" class="form-control" placeholder="请输入商品数量">
							 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxTimeIntervalAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#timeIntervalAddForm .form-group {margin:10px;}  </style>
					</div>
				</div>
			</div>
		</div>
	</div> 
</div>

<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加时段信息
	function ajaxTimeIntervalAdd() { 
		//提交之前先验证表单
		$("#timeIntervalAddForm").data('bootstrapValidator').validate();
		if(!$("#timeIntervalAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "TimeInterval/add",
			dataType : "json" , 
			data: new FormData($("#timeIntervalAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#timeIntervalAddForm").find("input").val("");
					$("#timeIntervalAddForm").find("textarea").val("");
				} else {
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
	//验证时段添加表单字段
	$('#timeIntervalAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"timeInterval.intervalName": {
				validators: {
					notEmpty: {
						message: "时段名称不能为空",
					}
				}
			},
			"timeInterval.product": {
				validators: {
					notEmpty: {
						message: "商品数量不能为空",
					},
					numeric: {
						message: "商品数量不正确"
					}
				}
			},
		}
	}); 
})
</script>
</body>
</html>
