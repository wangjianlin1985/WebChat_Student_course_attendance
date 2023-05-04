$(function () {
	$("#timeInterval_intervalName").validatebox({
		required : true, 
		missingMessage : '请输入时段名称',
	});

	$("#timeInterval_product").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入商品数量',
		invalidMessage : '商品数量输入不对',
	});

	//单击添加按钮
	$("#timeIntervalAddButton").click(function () {
		//验证表单 
		if(!$("#timeIntervalAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#timeIntervalAddForm").form({
			    url:"TimeInterval/add",
			    onSubmit: function(){
					if($("#timeIntervalAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#timeIntervalAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#timeIntervalAddForm").submit();
		}
	});

	//单击清空按钮
	$("#timeIntervalClearButton").click(function () { 
		$("#timeIntervalAddForm").form("clear"); 
	});
});
