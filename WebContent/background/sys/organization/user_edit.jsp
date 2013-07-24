<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>编辑用户信息</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" ontent="no-cache" />  
    <meta http-equiv="expires" content="0" />  
    <%
   	String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
	%>
    <link href="<%=basePath%>/js/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>/js/jquery-easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>/css/table.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="<%=basePath%>/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/YWindows.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/md5.js"></script>
	
	<script type="text/javascript">
		//表单验证方法
	    function checkForms()
	    {
	    	if (!$("#userName").validatebox("isValid"))
            {
                return false;
            }

            if (!$("#userLogName").validatebox("isValid"))
            {
                return false;
            }

            //判断密码是否相同
            if ($("#logPassword").val() != $("#logPassword2").val())
            {
                window.parent.$.messager.alert("提示","密码与确认密码不一致，请重新输入！","info");
                return false;
            }

            //只有新增时加密空密码，否则不处理
            if ($("#logPassword").val() != "" || ($("#userId").val() == "" && $("#logPassword").val() == ""))
            {
                $("#logPassword").val(hex_md5($("#logPassword").val()));
                $("#logPassword2").val(hex_md5($("#logPassword2").val()));
            }

            if (!$("#userOrder").validatebox("isValid"))
            {
                return false;
            }

            return true;
	    }
		
		/*!
		 * \brief
		 * 保存菜单。
		 */
		function savePage()
		{
			if(checkForms())
			{
				$("#userEditForm").submit();
			}
		}
		
		$(document).ready(function ()
        {
            var message = "<s:property value="message" />";
            
            if("" != message)
            {
            	window.parent.$.messager.alert("提示",message,"info");
            }
        });
	</script>
</head>
<body class="easyui-layout">
	<div id="center" data-options="region:'center'" style="padding:3px;background-color:#EEF5FD">
	<form id="userEditForm" method="post" action="<%=basePath%>/background/sys/organization/userSave.action">
		<input type="hidden" id="userId" name="userId" value="<s:if test="user == null">-1</s:if><s:else><s:property value="userId" /></s:else>" />
		<input type="hidden" id="parentId" name="parentId" value="<s:property value="parentId" />" />
		<table class="editTable" style="width:100%;">
            <tr><th style="width:120px">姓名：</th><td><input type="input" id="userName" name="user.name" value="<s:property value="user.name" />" class="easyui-validatebox" data-options="required:true" maxlength="20" style="width:300px" /></td></tr>
            <tr><th style="width:120px">登陆名：</th><td><input type="input" id="userLogName" name="user.logName" value="<s:property value="user.logName" />" <s:if test="user != null">readonly="readonly"</s:if> class="easyui-validatebox" data-options="required:true" maxlength="20" style="width:300px" /></td></tr>
            <tr><th style="width:120px">登陆密码：</th><td><input type="password" id="logPassword" name="user.logPassword" maxlength="20" style="width:300px" /></td></tr>
            <tr><th style="width:120px">确认密码：</th><td><input type="password" id="logPassword2" name="logPassword2" maxlength="20" style="width:300px" /></td></tr>
            <tr><th style="width:120px">序号：</th><td><input type="text" id="userOrder" name="user.order" class="easyui-numberspinner" data-options="min:0,max:50000,precision:0" value="<s:if test="user==nulll">0</s:if><s:else><s:property value="user.order" /></s:else>" style="width:100px;text-align:right;" /></td></tr>
        </table>
    </form>
	</div>
	<div data-options="region:'south',border:true" style="height:35px;background:#D9E5FD;padding:3px;text-align:right;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="javascript:savePage();" >保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:closeParentPopupsWindow('#popups')">取消</a>
	</div>
</body>
</html>