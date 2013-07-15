package ylr.actions.background.sys.menu;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.menu.MenuDataBase;
import ylr.database.system.menu.MenuInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 菜单存储action。
 * 
 * @author 董帅 创建时间：2013-07-15 15:46:35
 *
 */
public class MenuSaveAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 父菜单id，-1表示顶级菜单。
	 */
	private int parentId = -1;
	
	/**
	 * 菜单id，-1表示未菜单，编辑新增数据。
	 */
	private int menuId = -1;
	
	/**
	 * 菜单名称。
	 */
	private String menuName = "";
	
	/**
	 * 菜单url地址。
	 */
	private String menuURL = "";
	
	/**
	 * 菜单图标。
	 */
	private String menuIcon = "";
	
	/**
	 * 桌面图标。
	 */
	private String menuDesktopIcon = "";
	
	/**
	 * 菜单序号。
	 */
	private int menuOrder = 0;
	
	/**
	 * 返回消息。
	 */
	private String returnMessage = "";

	public String execute()
	{
		String retValue = ERROR;
		try
		{
			MenuInfo menu = new MenuInfo();
			if(this.menuName != null || this.menuName.length() < 0 || this.menuName.length() > 20)
			{
				menu.setName(this.menuName);
			}
			else
			{
				Exception e = new Exception("菜单名称不合法，长度必须在1～20!");
				throw e;
			}
			
			if(this.menuURL.length() <= 200)
			{
				menu.setURL(this.menuURL);
			}
			else
			{
				Exception e = new Exception("菜单URL超长，不能大于200！");
				throw e;
			}
			
			if(this.menuIcon.length() <= 20)
			{
				menu.setIcon(this.menuIcon);
			}
			else
			{
				Exception e = new Exception("菜单图标超长，不能大于20！");
				throw e;
			}
			
			if(this.menuDesktopIcon.length() <= 20)
			{
				menu.setDesktopIcon(this.menuDesktopIcon);
			}
			else
			{
				Exception e = new Exception("桌面图标超长，不能大于20！");
				throw e;
			}
			
			if(this.menuOrder >= 0 && this.menuOrder <= 50000)
			{
				menu.setOrder(this.menuOrder);
			}
			else
			{
				Exception e = new Exception("序号必须大于等于0,小于等于50000！");
				throw e;
			}
			
			menu.setId(this.menuId);
			menu.setParentId(this.parentId);
			
			//保存数据
			MenuDataBase db = MenuDataBase.createMenuDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			
			if(this.menuId == -1)
			{
				//新增
				int retId = db.createMenu(menu);
				if(retId >= 0)
				{
					//retValue = SUCCESS;
					
					this.returnMessage = "新增数据成功！";
				}
				else
				{
					Exception e = new Exception("新增数据出错！" + db.getLastErrorMessage());
					throw e;
				}
			}
		}
		catch(Exception ex)
		{
			this.setReturnMessage(ex.getMessage());
		}
		return retValue;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public int getMenuId()
	{
		return menuId;
	}

	public void setMenuId(int menuId)
	{
		this.menuId = menuId;
	}

	public String getMenuName()
	{
		return menuName;
	}

	public void setMenuName(String menuName)
	{
		this.menuName = menuName;
	}

	public String getMenuURL()
	{
		return menuURL;
	}

	public void setMenuURL(String menuURL)
	{
		this.menuURL = menuURL;
	}

	public String getMenuIcon()
	{
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon)
	{
		this.menuIcon = menuIcon;
	}

	public String getMenuDesktopIcon()
	{
		return menuDesktopIcon;
	}

	public void setMenuDesktopIcon(String menuDesktopIcon)
	{
		this.menuDesktopIcon = menuDesktopIcon;
	}

	public int getMenuOrder()
	{
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder)
	{
		this.menuOrder = menuOrder;
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
