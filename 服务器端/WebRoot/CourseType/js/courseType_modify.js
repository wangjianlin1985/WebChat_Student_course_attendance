$(function () {
	$.ajax({
		url : "CourseType/" + $("#courseType_courseTypeId_edit").val() + "/update",
		type : "get",
		data : {
			//courseTypeId : $("#courseType_courseTypeId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (courseType, response, status) {
			$.messager.progress("close");
			if (courseType) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#courseTypeModifyButton").click(function(){ 
		if ($("#courseTypeEditForm").form("validate")) {
			$("#courseTypeEditForm").form({
			    url:"CourseType/" +  $("#courseType_courseTypeId_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#courseTypeEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
