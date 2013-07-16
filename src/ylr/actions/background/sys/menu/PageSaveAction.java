package ylr.actions.background.sys.menu;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.menu.MenuDataBase;
import ylr.database.system.menu.PageInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 关联页面信息保存。
 * 
 * @author 董帅 创建时间：2013-07-16 17:05:55
 *
 */
public class PageSaveAction extends ActionSupport
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
	private PageInfo page = new PageInfo();

	public String execute()
	{
		String retValue = ERROR;
		try
		{
			if (page == null)
	        {
				Exception e = new Exception("不能插入空页面！");
				throw e;
	        }
	        else if (null == page.getPath() || page.getPath().length() > 500)
	        {
	            Exception e = new Exception("页面路径长度应该在1～500！");
				throw e;
	        }
	        else if (page.getDetail().length() > 200)
	        {
	        	Exception e = new Exception("页面说明不合法，长度1～200！");
				throw e;
	        }
	        else
	        {
	        	this.page.setMenuId(this.menuId);
	        	MenuDataBase db = MenuDataBase.createMenuDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
	        	
	        	if(null != db)
	        	{
		        	if(-1 == this.pageId)
		        	{
		        		//新增
		        		int id = db.createPage(this.page);
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
		        		this.page.setId(this.pageId);
		        		this.page.setMenuId(this.menuId);
		        		if(db.changePage(this.page))
		        		{
		        			this.message = "";
		        			retValue = SUCCESS;
		        		}
		        		else
		        		{
		        			Exception e = new Exception("修改数据出错！" + db.getLastErrorMessage());
		    				throw e;
		        		}
		        	}
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
