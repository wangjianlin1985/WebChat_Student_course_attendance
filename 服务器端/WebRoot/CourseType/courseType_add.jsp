<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/courseType.css" />
<div id="courseTypeAddDiv">
	<form id="courseTypeAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">课程类型名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="courseType_courseTypeName" name="courseType.courseTypeName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">课程类型说明:</span>
			<span class="inputControl">
				<textarea id="courseType_courseTypeDesc" name="courseType.courseTypeDesc" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="courseTypeAddButton" class="easyui-linkbutton">添加</a>
			<a id="courseTypeClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/CourseType/js/courseType_add.js"></script> 
