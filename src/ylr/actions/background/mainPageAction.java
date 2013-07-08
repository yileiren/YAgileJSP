package ylr.actions.background;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.menu.MenuDataBase;
import ylr.database.system.menu.MenuInfo;
import ylr.database.system.organization.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

public class mainPageAction extends ActionSupport
{

	private static final long serialVersionUID = 1L;
	
	private String errorMessage = "";
	
	/**
	 * 主界面菜单。
	 */
	private List<MenuInfo> mainPageMenus = new ArrayList<MenuInfo>();
	
	public String execute()
	{
		if(this.buildMainPage())
			return SUCCESS;
		else
			return ERROR;
	}

	/**
	 * 构建后台主界面。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean buildMainPage()
	{
		boolean retValue = false;
		
		try
		{
			//获取用户信息。
			HttpServletRequest request = ServletActionContext.getRequest();
			UserInfo user = (UserInfo) request.getSession().getAttribute("UserInfo");
			
			if(null != user)
			{
				retValue = true;
				MenuDataBase db = MenuDataBase.createMenuDataBase(ServletActionContext.getServletContext().getRealPath("/") + SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
				List<MenuInfo> menus = db.getMainPageMunus(user.getId());
				if(null != menus)
				{
					this.mainPageMenus = menus;
				}
				else
				{
					Exception e = new Exception("获取菜单数据出错！" + db.getLastErrorMessage());
					throw e;
				}
			}
			else
			{
				Exception e = new Exception("获取用户信息失败，用户未登陆或登陆超时！");
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.errorMessage = ex.getMessage();
		}
		
		return retValue;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public List<MenuInfo> getMainPageMenus()
	{
		return mainPageMenus;
	}

	public void setMainPageMenus(List<MenuInfo> mainPageMenus)
	{
		this.mainPageMenus = mainPageMenus;
	}
}
