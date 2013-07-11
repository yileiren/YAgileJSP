<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>修改密码</title>
	
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

    <script type="text/javascript" src="<%=basePath%>/js/md5.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/YWindows.js"></script>
	
	<script type="text/javascript">
        //修改密码
        function changePsw()
        {
            var new1 = $("#pswNewPsw1").val();
            var new2 = $("#pswNewPsw2").val();
            if (new1 == new2)
            {
                var oldPsw = $("#pswOldPsw").val();
                $("#pswOldPsw").val(hex_md5(oldPsw));
                $("#pswNewPsw1").val(hex_md5(new1));
                $("#pswNewPsw2").val(hex_md5(new2));
                
                $("#changePasswordForm").submit();
            }
            else
            {
            	window.parent.$.messager.alert("提示","新密码与确认密码不一致！","info");
                return false;
            }
            
            return true;
        }
        
        $(document).ready(function ()
        {
            var errorMessage = "<s:property value="errorMessage" />";
            
            if("" != errorMessage)
            {
            	window.parent.$.messager.alert("提示",errorMessage,"info");
            }
        });
    </script>
</head>
<body class="easyui-layout" style="background-color:#EEF5FD">
	<div id="center" data-options="region:'center'" style="padding:3px;background-color:#EEF5FD">
	<form id="changePasswordForm" method="post" action="<%=basePath%>/background/sys/changePassword.action">
		<table class="editTable" style="width:100%;">
            <tr><th style="width:70px">原密码：</td><td><input type="password" id="pswOldPsw" name="pswOldPsw" /></td></tr>
            <tr><th style="width:70px">新密码：</td><td><input type="password" id="pswNewPsw1" name="pswNewPsw1" /></td></tr>
            <tr><th style="width:70px">确认密码：</td><td><input type="password" id="pswNewPsw2" name="pswNewPsw2" /></td></tr>
        </table>
    </form>
	</div>
	<div data-options="region:'south',border:true" style="height:35px;background:#D9E5FD;padding:3px;text-align:right;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="javascript:changePsw();" >保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:closeParentPopupsWindow('#popups')">取消</a>
	</div>
</body>
</html>