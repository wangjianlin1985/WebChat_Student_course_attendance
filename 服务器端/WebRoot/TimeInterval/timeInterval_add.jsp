<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timeInterval.css" />
<div id="timeIntervalAddDiv">
	<form id="timeIntervalAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">时段名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="timeInterval_intervalName" name="timeInterval.intervalName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">商品数量:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="timeInterval_product" name="timeInterval.product" style="width:80px" />

			</span>

		</div>
		<div class="operation">
			<a id="timeIntervalAddButton" class="easyui-linkbutton">添加</a>
			<a id="timeIntervalClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/TimeInterval/js/timeInterval_add.js"></script> 
