package ylr.actions.background.sys.menu;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.menu.MenuDataBase;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 关联页面删除action。
 * 
 * @author 董帅 创建时间：2013-07-16 23:57:13
 *
 */
public class PageDeleteAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	private int menuId = -1;
	
	private String chkPage = "";
	
	private String message = "";
	
	public String execute()
	{
		try
		{
			String[] sids = this.chkPage.split(",");
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
					if(!db.deletePages(ids))
					{
						Exception e = new Exception("删除数据出错！" + db.getLastErrorMessage());
						throw e;
					}
				}
				else
				{
					Exception e = new Exception("创建数据库访问对象失败！");
					throw e;
				}
			}
			else
			{
				Exception e = new Exception("未指定要删除的页面！");
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.setMessage(ex.getMessage());
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

	public String getChkPage()
	{
		return chkPage;
	}

	public void setChkPage(String chkPage)
	{
		this.chkPage = chkPage;
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
