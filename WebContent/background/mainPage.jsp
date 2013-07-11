<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>异类人敏捷开发平台</title>
	
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

    <script type="text/javascript" src="<%=basePath%>/js/md5.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

    <script language="javascript" type="text/javascript">
    <!--
	  	//退出系统处理方法。
	    function logOut()
	    {
	    	$.messager.confirm("退出", "确定要退出系统？", function(r){
				if (r){
					$("#logOutForm").submit();
				}
			});
	    }
	
	    //修改密码
	    function changePassword()
	    {
	        popupsWindow("#popups", "修改密码", 300, 165, "sys/changePassword.aspx", "icon-key", true, true);
	    }
	
	
	    /*!
	     * \brief
	     * 菜单按钮点击事件处理方法。用来显示点击的菜单页面。
	     * 作者：董帅 
	     *
	     * \param name 菜单名称，显示在中间区域的title中。
	     * \param icon 菜单图标，显示在中间区域的icon中。
	     * \param url 菜单对应的url。
	     */
	    function menuButtonOnClick(name, icon, url)
	    {
	        //设置panel标题和图标。
	        $('#center').panel(
	            {
	                title:name,
	                iconCls:icon
	            }
	        );
	
	        //显示页面
	        $("#centerIframe").attr("src", url);
	    }
    
        $(document).ready(function ()
        {
            var errorMessage = "<s:property value="errorMessage" />";
            if("" != errorMessage)
            {
            	$.messager.alert("提示",errorMessage,"info");
            }
        });
    //-->
    </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:50px;background-image:url('<%=basePath%>/background/images/mainPage/title_01.gif')">
		<span style="position:absolute;left:10px">
            <img src="<%=basePath%>/background/images/mainPage/title_02.gif" alt="异类人敏捷开发平台" />
        </span>
        <span style="position:absolute;right:10px;bottom:0px">
            <a id="butChangePassword" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-key" onclick="javascript:changePassword();">修改密码</a>
            <a id="logOut" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-out" onclick="javascript:logOut();" >退出系统</a>
            <form id="logOutForm" method="post" action="<%=basePath%>/background/sys/logout.action">
            </form>
        </span>
	</div>
	<div data-options="region:'west',split:true,title:'菜单',iconCls:'icon-menu'" style="width:250px;padding:3px;background-color:#EEF5FD">
	<div id="menu" class="easyui-accordion" fit="true" border="true" style="background-color:#EEF5FD">
		<s:iterator value="mainPageMenus" id="menu">
		<div data-options="title:'<s:property value="#menu.name"/>',iconCls:'<s:property value="#menu.icon"/>'" style="overflow:auto;padding:3px;overflow-x:hidden">
		<s:iterator value="#menu.childMenus" id="cmenu">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'<s:property value="#cmenu.icon"/>',plain:true" style="width:100%" onclick="javascript:menuButtonOnClick('<s:property value="#cmenu.name"/>','<s:property value="#cmenu.icon"/>','<s:property value="#cmenu.URL"/>');"><s:property value="#cmenu.name"/></a>
		</s:iterator>
		</div>
		</s:iterator>
	</div>
	</div>
	<div data-options="region:'south',border:true" style="height:25px;background:#D9E5FD;padding:5px;">
		<span style="position:absolute;right:0px;width:400px">
	        <span style="font-weight:bold">登陆用户：</span>
	        <span><s:property value="#session.UserInfo.logName"/></span>
	        &nbsp;&nbsp;&nbsp;
	        <span style="font-weight:bold">登录名：</span>
	        <span><s:property value="#session.UserInfo.name"/></span>
	    </span>
	</div>
	<div id="center" data-options="region:'center',title:'首页',iconCls:'icon-home'" style="padding:3px;background-color:#EEF5FD">
		<iframe id="centerIframe" frameborder="0" style="width:100%;height:100%;background-color:#EEF5FD">
		</iframe>
	</div>
	<!--弹出窗口-->
    <div id="popups" class="easyui-dialog" closed="true" style="padding:0px;background-color:#EEF5FD;overflow-x:hidden;overflow-y:hidden">
        <div style="width:100%;height:100%">
	        <iframe id="popupsIframe" frameborder="0" style="width:100%;height:100%;background-color:#EEF5FD">
	        </iframe>
	    </div>
    </div>
</body>
</html>