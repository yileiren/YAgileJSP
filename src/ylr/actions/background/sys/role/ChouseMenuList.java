package ylr.actions.background.sys.role;

import java.util.List;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.role.RoleDataBase;
import ylr.database.system.role.RoleMenuInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 选择菜单列表。
 * @author 董帅 创建时间：2013-07-29 16:27:51
 *
 */
public class ChouseMenuList extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	private int roleId = -1;
	
	private String message = "";
	
	private List<RoleMenuInfo> menus = null;
	
	public String execute()
	{
		try
		{
			RoleDataBase db = RoleDataBase.createRoleDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			if(null != db)
			{
				this.menus = db.getChouseMenus(this.roleId);
				
				if(this.menus == null)
				{
					Exception e = new Exception("获取菜单失败！" + db.getLastErrorMessage());
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
			this.setMessage(ex.getMessage());
		}
		
		return NONE;
	}

	public int getRoleId()
	{
		return roleId;
	}

	public void setRoleId(int roleId)
	{
		this.roleId = roleId;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public List<RoleMenuInfo> getMenus()
	{
		return menus;
	}

	public void setMenus(List<RoleMenuInfo> menus)
	{
		this.menus = menus;
	}

}
