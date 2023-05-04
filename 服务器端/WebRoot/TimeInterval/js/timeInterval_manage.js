var timeInterval_manage_tool = null; 
$(function () { 
	initTimeIntervalManageTool(); //建立TimeInterval管理对象
	timeInterval_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#timeInterval_manage").datagrid({
		url : 'TimeInterval/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "intervalId",
		sortOrder : "desc",
		toolbar : "#timeInterval_manage_tool",
		columns : [[
			{
				field : "intervalId",
				title : "时段id",
				width : 70,
			},
			{
				field : "intervalName",
				title : "时段名称",
				width : 140,
			},
			{
				field : "product",
				title : "商品数量",
				width : 70,
			},
		]],
	});

	$("#timeIntervalEditDiv").dialog({
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
				if ($("#timeIntervalEditForm").form("validate")) {
					//验证表单 
					if(!$("#timeIntervalEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#timeIntervalEditForm").form({
						    url:"TimeInterval/" + $("#timeInterval_intervalId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#timeIntervalEditForm").form("validate"))  {
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
			                        $("#timeIntervalEditDiv").dialog("close");
			                        timeInterval_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#timeIntervalEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#timeIntervalEditDiv").dialog("close");
				$("#timeIntervalEditForm").form("reset"); 
			},
		}],
	});
});

function initTimeIntervalManageTool() {
	timeInterval_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#timeInterval_manage").datagrid("reload");
		},
		redo : function () {
			$("#timeInterval_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#timeInterval_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#timeIntervalQueryForm").form({
			    url:"TimeInterval/OutToExcel",
			});
			//提交表单
			$("#timeIntervalQueryForm").submit();
		},
		remove : function () {
			var rows = $("#timeInterval_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var intervalIds = [];
						for (var i = 0; i < rows.length; i ++) {
							intervalIds.push(rows[i].intervalId);
						}
						$.ajax({
							type : "POST",
							url : "TimeInterval/deletes",
							data : {
								intervalIds : intervalIds.join(","),
							},
							beforeSend : function () {
								$("#timeInterval_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#timeInterval_manage").datagrid("loaded");
									$("#timeInterval_manage").datagrid("load");
									$("#timeInterval_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#timeInterval_manage").datagrid("loaded");
									$("#timeInterval_manage").datagrid("load");
									$("#timeInterval_manage").datagrid("unselectAll");
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
			var rows = $("#timeInterval_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "TimeInterval/" + rows[0].intervalId +  "/update",
					type : "get",
					data : {
						//intervalId : rows[0].intervalId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (timeInterval, response, status) {
						$.messager.progress("close");
						if (timeInterval) { 
							$("#timeIntervalEditDiv").dialog("open");
							$("#timeInterval_intervalId_edit").val(timeInterval.intervalId);
							$("#timeInterval_intervalId_edit").validatebox({
								required : true,
								missingMessage : "请输入时段id",
								editable: false
							});
							$("#timeInterval_intervalName_edit").val(timeInterval.intervalName);
							$("#timeInterval_intervalName_edit").validatebox({
								required : true,
								missingMessage : "请输入时段名称",
							});
							$("#timeInterval_product_edit").val(timeInterval.product);
							$("#timeInterval_product_edit").validatebox({
								required : true,
								validType : "number",
								missingMessage : "请输入商品数量",
								invalidMessage : "商品数量输入不对",
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
