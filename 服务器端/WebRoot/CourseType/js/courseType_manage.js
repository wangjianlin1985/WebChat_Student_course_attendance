var courseType_manage_tool = null; 
$(function () { 
	initCourseTypeManageTool(); //建立CourseType管理对象
	courseType_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#courseType_manage").datagrid({
		url : 'CourseType/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "courseTypeId",
		sortOrder : "desc",
		toolbar : "#courseType_manage_tool",
		columns : [[
			{
				field : "courseTypeId",
				title : "课程类型id",
				width : 70,
			},
			{
				field : "courseTypeName",
				title : "课程类型名称",
				width : 140,
			},
		]],
	});

	$("#courseTypeEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#courseTypeEditForm").form("validate")) {
					//验证表单 
					if(!$("#courseTypeEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#courseTypeEditForm").form({
						    url:"CourseType/" + $("#courseType_courseTypeId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#courseTypeEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#courseTypeEditDiv").dialog("close");
			                        courseType_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#courseTypeEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#courseTypeEditDiv").dialog("close");
				$("#courseTypeEditForm").form("reset"); 
			},
		}],
	});
});

function initCourseTypeManageTool() {
	courseType_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#courseType_manage").datagrid("reload");
		},
		redo : function () {
			$("#courseType_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#courseType_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#courseTypeQueryForm").form({
			    url:"CourseType/OutToExcel",
			});
			//提交表单
			$("#courseTypeQueryForm").submit();
		},
		remove : function () {
			var rows = $("#courseType_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var courseTypeIds = [];
						for (var i = 0; i < rows.length; i ++) {
							courseTypeIds.push(rows[i].courseTypeId);
						}
						$.ajax({
							type : "POST",
							url : "CourseType/deletes",
							data : {
								courseTypeIds : courseTypeIds.join(","),
							},
							beforeSend : function () {
								$("#courseType_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#courseType_manage").datagrid("loaded");
									$("#courseType_manage").datagrid("load");
									$("#courseType_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#courseType_manage").datagrid("loaded");
									$("#courseType_manage").datagrid("load");
									$("#courseType_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#courseType_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "CourseType/" + rows[0].courseTypeId +  "/update",
					type : "get",
					data : {
						//courseTypeId : rows[0].courseTypeId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (courseType, response, status) {
						$.messager.progress("close");
						if (courseType) { 
							$("#courseTypeEditDiv").dialog("open");
							$("#courseType_courseTypeId_edit").val(courseType.courseTypeId);
							$("#courseType_courseTypeId_edit").validatebox({
								required : true,
								missingMessage : "请输入课程类型id",
								editable: false
							});
							$("#courseType_courseTypeName_edit").val(courseType.courseTypeName);
							$("#courseType_courseTypeName_edit").validatebox({
								required : true,
								missingMessage : "请输入课程类型名称",
							});
							$("#courseType_courseTypeDesc_edit").val(courseType.courseTypeDesc);
							$("#courseType_courseTypeDesc_edit").validatebox({
								required : true,
								missingMessage : "请输入课程类型说明",
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
