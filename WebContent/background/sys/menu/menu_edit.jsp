<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>编辑菜单</title>
	
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
	
	<script type="text/javascript">
		//表单验证方法
	    function checkForms()
	    {
	        if ($("#menuName").validatebox("isValid"))
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }
		
		/*!
		 * \brief
		 * 保存菜单。
		 */
		function saveMenu()
		{
			if(checkForms())
			{
				$("#menuEditForm").submit();
			}
		}
		
		$(document).ready(function ()
        {
            var returnMessage = "<s:property value="returnMessage" />";
            
            if("" != returnMessage)
            {
            	window.parent.$.messager.alert("提示",returnMessage,"info");
            }
        });
	</script>
</head>
<body class="easyui-layout">
	<div id="center" data-options="region:'center'" style="padding:3px;background-color:#EEF5FD">
	<form id="menuEditForm" method="post" action="<%=basePath%>/background/sys/menu/menuSave.action">
		<input type="hidden" id="parentId" name="parentId" value="<s:property value="parentId" />" />
		<input type="hidden" id="menuId" name="menuId" value="<s:property value="menuId" />" />
		<table class="editTable" style="width:100%;">
            <tr><th style="width:120px">名称：</td><td><input type="input" id="menuName" name="menuName"  class="easyui-validatebox" data-options="required:true" maxlength="20" style="width:300px" /></td></tr>
            <tr style="<s:if test="parentId == -1">display: none;</s:if>"><th style="width:120px">页面URL：</td><td><input type="input" id="menuURL" name="menuURL" maxlength="200" style="width:300px" /></td></tr>
            <tr><th style="width:120px">菜单图标：</td><td><input type="input" id="menuIcon" name="menuIcon" data-options="required:true" maxlength="20" style="width:300px" /></td></tr>
            <tr style="<s:if test="parentId == -1">display: none;</s:if>"><th style="width:120px">桌面图标：</td><td><input type="input" id="menuDesktopIcon" name="menuDesktopIcon" maxlength="20" style="width:300px" /></td></tr>
            <tr><th style="width:120px">序号：</td><td><input type="text" id="menuOrder" name="menuOrder" class="easyui-numberbox" min="0" max="50000" precision="0" value="0" runat="server" style="width:300px" /></td></tr>
        </table>
    </form>
	</div>
	<div data-options="region:'south',border:true" style="height:35px;background:#D9E5FD;padding:3px;text-align:right;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="javascript:saveMenu();" >保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:closeParentPopupsWindow('#popups')">取消</a>
	</div>
</body>
</html>