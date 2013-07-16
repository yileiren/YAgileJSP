package ylr.actions.background.sys.menu;

import java.util.List;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.menu.MenuDataBase;
import ylr.database.system.menu.PageInfo;

import com.opensymphony.xwork2.ActionSupport;

public class PageListAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private int menuId = -1;
	
	private String message = "";
	
	private List<PageInfo> pages = null;
	
	public String execute()
	{
		try
		{
			MenuDataBase db = MenuDataBase.createMenuDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			
			if(null != db)
			{
				pages = db.getPagesByMenuId(menuId);
				
				if(null == pages)
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

	public int getMenuId()
	{
		return menuId;
	}

	public void setMenuId(int menuId)
	{
		this.menuId = menuId;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public List<PageInfo> getPages()
	{
		return pages;
	}

	public void setPages(List<PageInfo> pages)
	{
		this.pages = pages;
	}
}
