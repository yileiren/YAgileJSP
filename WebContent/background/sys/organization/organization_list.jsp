<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>组织机构列表</title>
	
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
         * 新增机构。
         * 作者：董帅 创建时间：2013-07-19 14:17:53
         */
        function addOrg()
        {
            window.parent.popupsWindow("#popups", "新增组织机构", 700, 230, "<%=basePath%>/background/sys/organization/organizationQuery.action?parentId=<s:property value="parentId"/>", "icon-add", true, true);
        }
		
        /*!
         * \brief
         * 修改组织机构信息。
         * 作者：董帅 创建时间：2013-07-19 16:35:40
         */
        function editOrg()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkOrg']").length != 1)
            {
            	window.parent.$.messager.alert("提示","请选中要修改的组织机构，一次只能选择一个！","info");
                return;
            }

            //打开编辑页面
            window.parent.popupsWindow("#popups", "修改组织机构信息", 700, 230, "<%=basePath%>/background/sys/organization/organizationQuery.action?parentId=<s:property value="parentId"/>&orgId=" + $("input:checked[type='checkbox'][name='chkOrg']").eq(0).val(), "icon-edit", true, true);
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
         * 新增用户。
         * 作者：董帅 创建时间：2013-07-20 22:15:21
         */
        function addUser()
        {
            window.parent.popupsWindow("#popups", "新增用户", 700, 230, "<%=basePath%>/background/sys/organization/userQuery.action?parentId=<s:property value="parentId"/>", "icon-add", true, true);
        }

        /*!
         * \brief
         * 编辑用户。
         * 作者：董帅 创建时间：2013-07-24 10:30:30
         */
        function editUser()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkUser']").length != 1)
            {
            	window.parent.$.messager.alert("提示","请选中要修改的用户，一次只能选择一个！","info");
                return;
            }
            
            //打开编辑页面
            window.parent.popupsWindow("#popups", "修改用户", 700, 230, "<%=basePath%>/background/sys/organization/userQuery.action?parentId=<s:property value="parentId"/>&userId=" + $("input:checked[type='checkbox'][name='chkUser']").eq(0).val(), "icon-edit", true, true);
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
            var message = "<s:property value="message" />";
            if("" != message)
            {
            	window.parent.$.messager.alert("提示",message,"info");
            }
        });
	</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:false,border:false" style="width:250px;background-color:#EEF5FD">
	<div class="easyui-panel" data-options="title:'/<s:property value="parentOrg.name"/>',fit:true,tools:'#groutsButtons'" style="overflow-x:hidden;background-color:#FFFFFF">
		<form id="orgForm" method="post">
		<input type="hidden" id="parentId" name="parentId" value="<s:property value="parentId"/>" />
		<table class="listTable" style="width:100%;">
		<s:iterator value="orgs" id="org">
			<tr  class="tableBody1">
			<td style="width:30px;text-align:center;"><input type="checkbox" name="chkOrg" value="<s:property value="#org.id"/>"></td>
			<td>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-organization',plain:true" style="width:200px" onclick="javascript:location.href='<%=basePath%>/background/sys/organization/organizationList.action?parentId=<s:property value="#org.id"/>'"><s:property value="#org.name"/></a>
			</td>
			</tr>
		</s:iterator>
		</table>
		</form>
	</div>
	</div>
	<div id="center" data-options="region:'center',title:'“<s:property value="parentOrg.name"/>”的用户',iconCls:'<s:property value="topMenuIcon"/>',tools:'#menusButtons'" style="padding:3px;background-color:#EEF5FD">
	<form id="userForm" method="post">
		<table class="listTable" style="width:100%;">
			<tr class="tableHead">
			<th style="width:30px;">选择</th>
			<th style="width:100px;">姓名</th>
			<th style="width:100px;">登陆名</th>
			<th style="width:30px;">序号</th>
			<th></th>
			</tr>
		<s:iterator value="users" id="user">
			<tr class="tableBody1">
			<td style="text-align:center;"><input type="checkbox" name="chkUser" value="<s:property value="#user.id"/>" /></td>
			<td style="text-align:center;"><s:property value="#user.name"/></td>
			<td style="text-align:center;"><s:property value="#user.logName"/></td>
			<td style="text-align:center;"><s:property value="#user.order"/></td>
			<td></td>
			</tr>
		</s:iterator>
		</table>
	</form>
	</div>
	<div id="groutsButtons">
		<s:if test="parentId!=-1"><a href="#" class="icon-back" title="返回上级" onclick="javascript:location.href='<%=basePath%>/background/sys/organization/organizationList.action?parentId=<s:property value="parentOrg.parentId"/>'"></a></s:if>
		<a href="#" class="icon-add" title="新增组织机构" onclick="javascript:addOrg();"></a>
		<a href="#" class="icon-edit" title="修改组织机构" onclick="javascript:editOrg();"></a>
		<a href="#" class="icon-cancel" title="删除组织机构" onclick="javascript:deleteGroups();"></a>
	</div>
    <div id="menusButtons">
		<a href="#" class="icon-add" title="新增用户" onclick="javascript:addUser();"></a>
		<a href="#" class="icon-edit" title="修改用户" onclick="javascript:editUser();"></a>
		<a href="#" class="icon-cancel" title="删除用户" onclick="javascript:deleteItem();"></a>
	</div>
</body>
</html>