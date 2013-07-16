<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>关联页面</title>
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
	<input type="hidden" id="menuId" name="menuId" value="<s:property value="menuId"/>" />
    <div data-options="region:'north', border:'true'" style="height:28px;background-color:#EEF5FD;text-align:right;">
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'" onclick="javascript:window.location.href='<%=basePath%>/background/sys/menu/menuPageQuery.action?menuId=<s:property value="menuId" />';">新增</a>
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick="javascript:editPage();">修改</a>
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:'true'" onclick="javascript:return deletePages();">删除</a>
    </div>
            
	<div id="center" data-options="region:'center'" style="padding:3px;background-color:#EEF5FD">
		<table class="listTable" style="width:100%">
	        <tr class="tableHead"">
	            <th style="width:30px">选择</th>
	            <th style="width:300px">路径</th>
	            <th>说明</th>
	        </tr>
	        <s:iterator value="pages" id="page">
            <tr class="tableBody1">
                <td style="text-align:center;"><input type="checkbox" value="<s:property value="#page.id"/>" name="chkPage" /></td>
                <td><s:property value="#page.path"/></td>
                <td><s:property value="#page.detail"/></td>
            </tr>
            </s:iterator>
        </table>
	</div>
</body>
</html>