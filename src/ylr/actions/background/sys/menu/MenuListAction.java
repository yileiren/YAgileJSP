package ylr.actions.background.sys.menu;

import java.util.ArrayList;
import java.util.List;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.menu.MenuDataBase;
import ylr.database.system.menu.MenuInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 菜单列表action。
 * 
 * @author 董帅 创建时间：2013-07-12 13:18:30
 *
 */
public class MenuListAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 父菜单id。
	 */
	private int topMenuId = -1;
	
	private String topMenuName = "";
	
	private String topMenuIcon = "";
	
	/**
	 * 返回消息。
	 */
	private String returnMessage = "";
	
	/**
	 * 顶级菜单。
	 */
	private List<MenuInfo> topMenus = new ArrayList<MenuInfo>();
	
	/**
	 * 子菜单。
	 */
	private List<MenuInfo> childMenus = new ArrayList<MenuInfo>();

	public String execute()
	{
		try
		{
			//获取菜单。
			MenuDataBase db = MenuDataBase.createMenuDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			List<MenuInfo> menus = db.getMenusByParentId(-1);
			if(null != menus)
			{
				this.topMenus = menus;
				
				//获取子菜单
				if(this.topMenus.size() > 0)
				{
					MenuInfo tm = this.topMenus.get(0);
					for(int i = 1;i < this.topMenus.size();i++)
					{
						if(this.topMenuId == this.topMenus.get(i).getId())
						{
							tm = this.topMenus.get(i);
							break;
						}
					}
					
					this.topMenuId = tm.getId();
					this.topMenuName = tm.getName();
					this.topMenuIcon = tm.getIcon();
					menus = db.getMenusByParentId(tm.getId());
					if(null != menus)
					{
						this.childMenus = menus;
					}
					else
					{
						Exception e = new Exception("获取子菜单失败！" + db.getLastErrorMessage());
						throw e;
					}
				}
			}
			else
			{
				Exception e = new Exception("获取菜单失败！" + db.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.setReturnMessage(ex.getMessage());
		}
		return SUCCESS;
	}
	
	public List<MenuInfo> getTopMenus()
	{
		return topMenus;
	}

	public void setTopMenus(List<MenuInfo> topMenus)
	{
		this.topMenus = topMenus;
	}

	public int getTopMenuId()
	{
		return topMenuId;
	}

	public void setTopMenuId(int topMenuId)
	{
		this.topMenuId = topMenuId;
	}

	public List<MenuInfo> getChildMenus()
	{
		return childMenus;
	}

	public void setChildMenus(List<MenuInfo> childMenus)
	{
		this.childMenus = childMenus;
	}

	public String getTopMenuName()
	{
		return topMenuName;
	}

	public void setTopMenuName(String topMenuName)
	{
		this.topMenuName = topMenuName;
	}

	public String getTopMenuIcon()
	{
		return topMenuIcon;
	}

	public void setTopMenuIcon(String topMenuIcon)
	{
		this.topMenuIcon = topMenuIcon;
	}

	public String getReturnMessage()
	{
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage)
	{
		this.returnMessage = returnMessage;
	}
}
