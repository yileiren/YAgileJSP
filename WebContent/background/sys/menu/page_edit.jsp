<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>编辑页面</title>
	
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
	        if ($("#pagePath").validatebox("isValid"))
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
		function savePage()
		{
			if(checkForms())
			{
				$("#pageEditForm").submit();
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
	<div style="background-color:#FDFC01;padding:0px;margin:0px;width:100%;height:38px;top:0px;left:0px;right:0px;">
        <div style="float:left;width:32px;height:37px;"><img src="<%=basePath%>/background/images/warning.png" alt="" style="width:32px;height:32px;margin-top:5px" /></div>
        <div style="float:left;height:37px;margin-left:5px"><p>action地址使用相对网站根目录的完整路径，例如“/background/sys/login.action”，程序只对action进行拦截。</p></div>
    </div>
	<form id="pageEditForm" method="post" action="<%=basePath%>/background/sys/menu/menuPageSave.action">
		<input type="hidden" id="pageId" name="pageId" value="<s:property value="pageId" />" />
		<input type="hidden" id="menuId" name="menuId" value="<s:property value="menuId" />" />
		<table class="editTable" style="width:100%;">
            <tr><th style="width:120px">路径：</td><td><input type="input" id="pagePath" name="page.path" value="<s:property value="page.path" />" class="easyui-validatebox" data-options="required:true" maxlength="500" style="width:300px" /></td></tr>
            <tr><th style="width:120px">说明：</td><td><input type="input" id="pageDetail" name="page.detail" value="<s:property value="page.detail" />" maxlength="200" style="width:300px" /></td></tr>
        </table>
    </form>
	</div>
	<div data-options="region:'south',border:true" style="height:35px;background:#D9E5FD;padding:3px;text-align:right;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="javascript:savePage();" >保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:window.location.href='<%=basePath%>/background/sys/menu/menuPageList.action?menuId=<s:property value="menuId"/>'">取消</a>
	</div>
</body>
</html>