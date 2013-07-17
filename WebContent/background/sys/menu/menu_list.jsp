<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>系统菜单</title>
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
		/*!
		 * \brief
		 * 显示菜单。
		 *
		 * \param 选择的顶级菜单id。
		 */
		function showMenus(topId)
		{
			$("#topMenuForm").attr("action","<%=basePath%>/background/sys/menu/menuList.action");
			$("#topMenuId").val(topId);
			$("#topMenuForm").submit();
		}
		
		/*!
         * \brief
         * 新增分组。
         * 作者：董帅 创建时间：2013-07-15 14:27:10
         */
        function addGroup()
        {
            window.parent.popupsWindow("#popups", "新增菜单分组", 700, 230, "<%=basePath%>/background/sys/menu/menuQuery.action", "icon-add", true, true);
        }
		
        /*!
         * \brief
         * 修改分组。
         * 作者：董帅 创建时间：2013-07-16 13:18:05
         */
        function editGroup()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkGroup']").length != 1)
            {
            	window.parent.$.messager.alert("提示","请选中要编辑的分组，一次只能选择一个！","info");
                return;
            }

            //打开编辑页面
            window.parent.popupsWindow("#popups", "修改菜单分组", 700, 230, "<%=basePath%>/background/sys/menu/menuQuery.action?menuId=" + $("input:checked[type='checkbox'][name='chkGroup']").eq(0).val(), "icon-edit", true, true);
        }

        /*!
         * \brief
         * 删除选中的分组。
         * 作者：董帅 创建时间：2013-07-16 13:18:00
         */
        function deleteGroups()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkGroup']").length > 0)
            {
            	window.parent.$.messager.confirm("提示", "确认要删除选中的分组？删除分组将连同子菜单一并删除，并且无法恢复！", function(r){
    				if (r)
    				{
    					$("#topMenuForm").attr("action","<%=basePath%>/background/sys/menu/menuDelete.action?type=group");
    					$("#topMenuForm").submit();
    				}
    			});
            }
            else
            {
            	window.parent.$.messager.alert("提示","请选中要删除的分组！","info");
            }
        }

        /*!
         * \brief
         * 新增菜单。
         * 作者：董帅 创建时间：2013-07-16 13:18:11
         */
        function addMenu()
        {
            window.parent.popupsWindow("#popups", "新增菜单", 700, 230, "<%=basePath%>/background/sys/menu/menuQuery.action?parentId=<s:property value="topMenuId"/>", "icon-add", true, true);
        }

        /*!
         * \brief
         * 编辑菜单。
         * 作者：董帅 创建时间：2013-07-16 13:18:15
         */
        function editMenu()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkItem']").length != 1)
            {
            	window.parent.$.messager.alert("提示","请选中要编辑的菜单，一次只能选择一个！","info");
                return;
            }
            
            //打开编辑页面
            window.parent.popupsWindow("#popups", "修改菜单", 700, 230, "<%=basePath%>/background/sys/menu/menuQuery.action?menuId=" + $("input:checked[type='checkbox'][name='chkItem']").eq(0).val() + "&parentId=<s:property value="topMenuId"/>", "icon-edit", true, true);
        }

        /*!
         * \brief
         * 删除菜单。
         * 作者：董帅 创建时间：2013-07-17 22:25:48
         */
        function deleteItem()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkItem']").length > 0)
            {
                window.parent.$.messager.confirm("提示", "确认要删除选中的菜单？", function(r){
     				if (r)
     				{
     					$("#itemMenuForm").attr("action","<%=basePath%>/background/sys/menu/menuDelete.action?type=item&topMenuId=<s:property value="topMenuId"/>");
     					$("#itemMenuForm").submit();
     				}
     			});
            }
            else
            {
                window.parent.$.messager.alert("提示","请选中要删除的菜单！","info");
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
	<div data-options="region:'west',split:false,border:false" style="width:250px;background-color:#EEF5FD">
	<div class="easyui-panel" data-options="title:'菜单分组',fit:true,tools:'#groutsButtons'" style="overflow-x:hidden;background-color:#FFFFFF">
		<form id="topMenuForm" method="post">
		<input type="hidden" id="topMenuId" name="topMenuId" value="<s:property value="topMenuId"/>" />
		<table class="listTable" style="width:100%;">
		<s:iterator value="topMenus" id="menu">
			<tr  class="tableBody1">
			<td style="width:30px;text-align:center;"><input type="checkbox" name="chkGroup" value="<s:property value="#menu.id"/>"></td>
			<td>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'<s:property value="#menu.icon"/>',plain:true" style="width:200px" onclick="javascript:showMenus(<s:property value="#menu.id"/>)"><s:property value="#menu.name"/></a>
			</td>
			</tr>
		</s:iterator>
		</table>
		</form>
	</div>
	</div>
	<div id="center" data-options="region:'center',title:'<s:property value="topMenuName"/>',iconCls:'<s:property value="topMenuIcon"/>',tools:'#menusButtons'" style="padding:3px;background-color:#EEF5FD">
	<form id="itemMenuForm" method="post">
		<table class="listTable" style="width:100%;">
			<tr class="tableHead">
			<th style="width:30px;">选择</th>
			<th>名称</th>
			<th style="width:100px;">图标</th>
			<th style="width:100px;">桌面图标</th>
			<th style="width:30px;">序号</th>
			<th style="width:120px;">关联页面</th>
			</tr>
		<s:iterator value="childMenus" id="menu">
			<tr class="tableBody1">
			<td style="text-align:center;"><input type="checkbox" name="chkItem" value="<s:property value="#menu.id"/>" /></td>
			<td><s:property value="#menu.name"/></td>
			<td style="text-align:center;"><s:property value="#menu.icon"/></td>
			<td style="text-align:center;"><s:property value="#menu.desktopIcon"/></td>
			<td style="text-align:center;"><s:property value="#menu.order"/></td>
			<td style="text-align:center">
                <a id="butSetPage" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-supplies'" 
                	onclick="javascript:window.parent.popupsWindow('#popups', '关联页面', 640, 480, '<%=basePath%>/background/sys/menu/menuPageList.action?menuId=<s:property value="#menu.id"/>', 'icon-supplies', true, true);">关联页面</a>
            </td>
			</tr>
		</s:iterator>
		</table>
	</form>
	</div>
	<div id="groutsButtons">
		<a href="#" class="icon-add" onclick="javascript:addGroup();"></a>
		<a href="#" class="icon-edit" onclick="javascript:editGroup();"></a>
		<a href="#" class="icon-cancel" onclick="javascript:deleteGroups();"></a>
	</div>
    <div id="menusButtons">
		<a href="#" class="icon-add" onclick="javascript:addMenu();"></a>
		<a href="#" class="icon-edit" onclick="javascript:editMenu();"></a>
		<a href="#" class="icon-cancel" onclick="javascript:deleteItem();"></a>
	</div>
</body>
</html>