package ylr.actions.background.sys.role;

import java.util.List;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.role.RoleDataBase;
import ylr.database.system.role.RoleInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 权限列表action。
 * @author 董帅 创建时间：2013-07-29 14:20:00
 *
 */
public class RoleListAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private List<RoleInfo> roles = null;
	
	private String message = "";
	
	public String execute()
	{
		try
		{
			RoleDataBase db = RoleDataBase.createRoleDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			if(null != db)
			{
				this.roles = db.getRoles();
				
				if(null == roles)
				{
					Exception e = new Exception("获取数据失败！" + db.getLastErrorMessage());
					throw e;
				}
			}
			else
			{
				Exception e = new Exception("创建数据库访问对象失败！");
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.message = ex.getMessage();
		}
		
		return NONE;
	}

	public List<RoleInfo> getRoles()
	{
		return roles;
	}

	public void setRoles(List<RoleInfo> roles)
	{
		this.roles = roles;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
