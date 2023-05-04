<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/courseType.css" />
<div id="courseType_editDiv">
	<form id="courseTypeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">课程类型id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="courseType_courseTypeId_edit" name="courseType.courseTypeId" value="<%=request.getParameter("courseTypeId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">课程类型名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="courseType_courseTypeName_edit" name="courseType.courseTypeName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">课程类型说明:</span>
			<span class="inputControl">
				<textarea id="courseType_courseTypeDesc_edit" name="courseType.courseTypeDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="courseTypeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/CourseType/js/courseType_modify.js"></script> 
