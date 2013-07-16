package ylr.actions.background.sys.menu;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.menu.MenuDataBase;
import ylr.database.system.menu.PageInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 页面信息查询。
 * 
 * @author 董帅 创建时间：2013-07-16 16:46:02
 *
 */
public class PageQueryAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 所属菜单id。
	 */
	private int menuId = -1;
	
	/**
	 * 消息。
	 */
	private String message = "";
	
	/**
	 * 页面id，-1表示新增。
	 */
	private int pageId = -1;
	
	/**
	 * 获取的页面信息。
	 */
	private PageInfo page = null;

	public String execute()
	{
		try
		{
			MenuDataBase db = MenuDataBase.createMenuDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			if(this.pageId != -1)
			{
				page = db.getPage(this.pageId);
				if(null == page)
				{
					Exception e = new Exception("获取数据出错！" + db.getLastErrorMessage());
					throw e;
				}
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

	public int getPageId()
	{
		return pageId;
	}

	public void setPageId(int pageId)
	{
		this.pageId = pageId;
	}

	public PageInfo getPage()
	{
		return page;
	}

	public void setPage(PageInfo page)
	{
		this.page = page;
	}
}
