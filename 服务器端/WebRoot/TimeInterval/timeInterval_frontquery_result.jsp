<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.TimeInterval" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<TimeInterval> timeIntervalList = (List<TimeInterval>)request.getAttribute("timeIntervalList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>时段查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-12 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#timeIntervalListPanel" aria-controls="timeIntervalListPanel" role="tab" data-toggle="tab">时段列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>TimeInterval/timeInterval_frontAdd.jsp" style="display:none;">添加时段</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="timeIntervalListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>时段id</td><td>时段名称</td><td>商品数量</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<timeIntervalList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		TimeInterval timeInterval = timeIntervalList.get(i); //获取到时段对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=timeInterval.getIntervalId() %></td>
 											<td><%=timeInterval.getIntervalName() %></td>
 											<td><%=timeInterval.getProduct() %></td>
 											<td>
 												<a href="<%=basePath  %>TimeInterval/<%=timeInterval.getIntervalId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="timeIntervalEdit('<%=timeInterval.getIntervalId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="timeIntervalDelete('<%=timeInterval.getIntervalId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div style="display:none;">
		<div class="page-header">
    		<h1>时段查询</h1>
		</div>
		<form name="timeIntervalQueryForm" id="timeIntervalQueryForm" action="<%=basePath %>TimeInterval/frontlist" class="mar_t15" method="post">
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="timeIntervalEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;时段信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		</form> 
	    <style>#timeIntervalEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxTimeIntervalModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.timeIntervalQueryForm.currentPage.value = currentPage;
    document.timeIntervalQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.timeIntervalQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.timeIntervalQueryForm.currentPage.value = pageValue;
    documenttimeIntervalQueryForm.submit();
}

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
				$('#timeIntervalEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除时段信息*/
function timeIntervalDelete(intervalId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "TimeInterval/deletes",
			data : {
				intervalIds : intervalId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#timeIntervalQueryForm").submit();
					//location.href= basePath + "TimeInterval/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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

})
</script>
</body>
</html>

