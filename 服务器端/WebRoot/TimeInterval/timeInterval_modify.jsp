<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timeInterval.css" />
<div id="timeInterval_editDiv">
	<form id="timeIntervalEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">时段id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="timeInterval_intervalId_edit" name="timeInterval.intervalId" value="<%=request.getParameter("intervalId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">时段名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="timeInterval_intervalName_edit" name="timeInterval.intervalName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">商品数量:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="timeInterval_product_edit" name="timeInterval.product" style="width:80px" />

			</span>

		</div>
		<div class="operation">
			<a id="timeIntervalModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/TimeInterval/js/timeInterval_modify.js"></script> 
