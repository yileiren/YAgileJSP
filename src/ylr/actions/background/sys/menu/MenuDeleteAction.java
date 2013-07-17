package ylr.actions.background.sys.menu;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.menu.MenuDataBase;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 删除菜单action。
 * 
 * @author 董帅 创建时间：2013-07-16 13:13:58
 *
 */
public class MenuDeleteAction extends ActionSupport
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
	 * 选中的菜单分组。
	 */
	private String chkGroup = "";
	
	/**
	 * 选中的菜单。
	 */
	private String chkItem = "";
	
	/**
	 * 删除分组或菜单。
	 */
	private String type = "group";
	
	public String execute()
	{
		try
		{
			if(this.type.equals("group"))
			{
				//删除分组
				String[] sids = this.chkGroup.split(",");
				if(sids.length > 0)
				{
					int retTopMenuId = this.topMenuId; //返回用的顶级菜单id，如果删除菜单里包括则返回-1。
					int[] ids = new int[sids.length];
					for(int i = 0;i < sids.length;i++)
					{
						ids[i] = Integer.valueOf(sids[i].trim());
						if(this.topMenuId == ids[i])
						{
							retTopMenuId = -1;
						}
					}
					
					MenuDataBase db = MenuDataBase.createMenuDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
					if(null != db)
					{
						if(!db.deleteMenus(ids))
						{
							Exception e = new Exception("删除数据出错！" + db.getLastErrorMessage());
							throw e;
						}
						this.topMenuId = retTopMenuId;
					}
					else
					{
						Exception e = new Exception("创建数据库访问对象失败！");
						throw e;
					}
				}
				else
				{
					Exception e = new Exception("未指定要删除的菜单！");
					throw e;
				}
			}
			else
			{
				//删除菜单。
				String[] sids = this.chkItem.split(",");
				if(sids.length > 0)
				{
					int[] ids = new int[sids.length];
					for(int i = 0;i < sids.length;i++)
					{
						ids[i] = Integer.valueOf(sids[i].trim());
					}
					
					MenuDataBase db = MenuDataBase.createMenuDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
					if(null != db)
					{
						if(!db.deleteMenus(ids))
						{
							Exception e = new Exception("删除数据出错！" + db.getLastErrorMessage());
							throw e;
						}
						System.out.println(this.topMenuId);
					}
					else
					{
						Exception e = new Exception("创建数据库访问对象失败！");
						throw e;
					}
				}
				else
				{
					Exception e = new Exception("未指定要删除的菜单！");
					throw e;
				}
			}
		}
		catch(Exception ex)
		{
			this.returnMessage = ex.getMessage();
		}
		
		return SUCCESS;
	}

	public String getChkGroup()
	{
		return chkGroup;
	}

	public void setChkGroup(String chkGroup)
	{
		this.chkGroup = chkGroup;
	}

	public int getTopMenuId()
	{
		return topMenuId;
	}

	public void setTopMenuId(int topMenuId)
	{
		this.topMenuId = topMenuId;
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

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getChkItem()
	{
		return chkItem;
	}

	public void setChkItem(String chkItem)
	{
		this.chkItem = chkItem;
	}
}
