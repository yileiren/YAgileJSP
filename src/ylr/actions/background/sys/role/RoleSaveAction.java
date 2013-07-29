package ylr.actions.background.sys.role;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.role.RoleDataBase;
import ylr.database.system.role.RoleInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 保存角色。
 * @author 董帅 创建时间：2013-07-29 15:00:30
 *
 */
public class RoleSaveAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private int roleId = -1;
	
	private RoleInfo role = null;
	
	private String message = "";
	
	private String centerIframeURL = "";
	
	public String execute()
	{
		String retValue = ERROR;
		try
		{
			if (this.role == null)
            {
				Exception e = new Exception("不能插入空角色！");
				throw e;
            }
            else if (null == this.role.getName() || this.role.getName().length() > 20)
            {
                Exception e = new Exception("角色名称长度应该在1～20！");
				throw e;
            }
	        else
	        {
	        	RoleDataBase db = RoleDataBase.createRoleDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
	        	
	        	if(null != db)
	        	{
		        	if(-1 == this.roleId)
		        	{
		        		//新增
		        		int id = db.createRole(this.role);
		        		if(id > 0)
		        		{
		        			this.message = "";
		        			retValue = SUCCESS;
		        		}
		        		else
		        		{
		        			Exception e = new Exception("新增数据出错！" + db.getLastErrorMessage());
		    				throw e;
		        		}
		        	}
		        	else
		        	{
		        		//修改
//		        		this.page.setId(this.pageId);
//		        		this.page.setMenuId(this.menuId);
//		        		if(db.changePage(this.page))
//		        		{
//		        			this.message = "";
//		        			retValue = SUCCESS;
//		        		}
//		        		else
//		        		{
//		        			Exception e = new Exception("修改数据出错！" + db.getLastErrorMessage());
//		    				throw e;
//		        		}
		        	}
		        	
		        	//设置跳转地址
					HttpServletRequest request = ServletActionContext.getRequest();
					this.setCenterIframeURL(request.getScheme() 
							+ "://" + request.getServerName() 
							+ ":" + request.getServerPort() 
							+request.getContextPath() 
							+ "/background/sys/role/roleList.action");
	        	}
				else
				{
					Exception e = new Exception("创建数据库访问对象失败！");
					throw e;
				}
	        }
		}
		catch(Exception ex)
		{
			this.message = ex.getMessage();
		}
		
		return retValue;
	}

	public int getRoleId()
	{
		return roleId;
	}

	public void setRoleId(int roleId)
	{
		this.roleId = roleId;
	}

	public RoleInfo getRole()
	{
		return role;
	}

	public void setRole(RoleInfo role)
	{
		this.role = role;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getCenterIframeURL()
	{
		return centerIframeURL;
	}

	public void setCenterIframeURL(String centerIframeURL)
	{
		this.centerIframeURL = centerIframeURL;
	}
}
