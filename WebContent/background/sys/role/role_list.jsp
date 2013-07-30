<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色列表</title>
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
         * 新增角色。
         * 作者：董帅 创建时间：2013-07-29 14:32:20
         */
        function addRole()
        {
            window.parent.popupsWindow("#popups", "新增角色", 700, 180, "<%=basePath%>/background/sys/role/roleQuery.action", "icon-add", true, true);
        }
		
        /*!
         * \brief
         * 修改角色信息。
         * 作者：董帅 创建时间：2013年07月29日15:42:01
         */
        function editRole()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkRole']").length != 1)
            {
            	window.parent.$.messager.alert("提示","请选中要修改的角色，一次只能选择一个！","info");
                return;
            }

            //打开编辑页面
            window.parent.popupsWindow("#popups", "修改角色信息", 700, 180, "<%=basePath%>/background/sys/role/roleQuery.action?roleId=" + $("input:checked[type='checkbox'][name='chkRole']").eq(0).val(), "icon-edit", true, true);
        }

        /*!
         * \brief
         * 删除选中的组织机构。
         * 作者：董帅 创建时间：2013-07-24 15:15:10
         */
        function deleteOrgs()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkOrg']").length > 0)
            {
            	window.parent.$.messager.confirm("提示", "确认要删除选中的组织机构？删除机构将连同子机构和用户一并删除！", function(r){
    				if (r)
    				{
    					$("#orgForm").attr("action","<%=basePath%>/background/sys/organization/organizationDelete.action");
    					$("#orgForm").submit();
    				}
    			});
            }
            else
            {
            	window.parent.$.messager.alert("提示","请选中要删除的组织机构！","info");
            }
        }
        
        /*!
         * \brief
         * 权限访问菜单设置。
         * 作者：董帅 创建时间：2013-07-30 10:31:51
         */
        function chouseMenu()
        {
            //判断选中
            if ($("input:checked[type='checkbox'][name='chkRole']").length != 1)
            {
            	window.parent.$.messager.alert("提示","请选中要设置的角色，一次只能选择一个！","info");
                return;
            }

            //打开编辑页面
            window.parent.popupsWindow("#popups", "选择菜单", 350, 500, "<%=basePath%>/background/sys/role/chouseMenuList.action?roleId=" + $("input:checked[type='checkbox'][name='chkRole']").eq(0).val(), "icon-menu", true, true);
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
	<div data-options="region:'north', border:'true'" style="height:28px;background-color:#EEF5FD;text-align:right;">
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-menu',plain:'true'" onclick="javascript:chouseMenu();">访问菜单</a>
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'" onclick="javascript:addRole();">新增</a>
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick="editRole();">修改</a>
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:'true'" onclick="javascript:return deletePages();">删除</a>
    </div>
	<div id="center" data-options="region:'center'" style="padding:3px;background-color:#EEF5FD">
	<form id="rolesForm" method="post">
		<table class="listTable" style="width:100%">
	        <tr class="tableHead"">
	            <th style="width:30px">选择</th>
	            <th style="width:200px">名称</th>
	            <th>说明</th>
	        </tr>
	        <s:iterator value="roles" id="role">
            <tr class="tableBody1">
                <td style="text-align:center;"><input type="checkbox" value="<s:property value="#role.id"/>" name="chkRole" /></td>
                <td style="text-align:center"><s:property value="#role.name"/></td>
                <td><s:property value="#role.explain"/></td>
            </tr>
            </s:iterator>
        </table>
    </form>
	</div>
</body>
</html>