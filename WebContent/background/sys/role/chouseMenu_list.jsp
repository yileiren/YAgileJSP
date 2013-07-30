<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>选择菜单</title>
	
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
</head>
<body class="easyui-layout">
	<div id="center" data-options="region:'center'" style="padding:3px;background-color:#EEF5FD">
	<form id="menusForm" method="post" action="<%=basePath%>/background/sys/role/roleSave.action">
		<ul id="tt1" class="easyui-tree" >
		<s:iterator value="menus" id="menu">
		<li><span><input type="checkbox" name="chkItem" <s:if test="#menu.choused">checked="checked"</s:if> value="<s:property value="#menu.id"/>"/><s:property value="#menu.name"/></span>
             <ul >
		<s:iterator value="#menu.menus" id="cmenu">
			<li><span><input type="checkbox" name="chkItem" <s:if test="#cmenu.choused">checked="checked"</s:if> value="<s:property value="#cmenu.id"/>"/><s:property value="#cmenu.name"/></span></li>
		</s:iterator>
			</ul>
		</li>
		</s:iterator>
            
        </ul>
    </form>
	</div>
	<div data-options="region:'south',border:true" style="height:35px;background:#D9E5FD;padding:3px;text-align:right;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="javascript:savePage();" >保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:closeParentPopupsWindow('#popups')">取消</a>
	</div>
</body>
</html>