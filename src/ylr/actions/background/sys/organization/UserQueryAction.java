package ylr.actions.background.sys.organization;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.organization.UserDataBase;
import ylr.database.system.organization.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户信息查询action。
 * @author 董帅 创建时间：2013-07-20 16:11:11
 *
 */
public class UserQueryAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	/**
	 * 当前页面的上级机构id。
	 */
	private int parentId = -1;
	
	private int userId = -1;
	
	private UserInfo user = null;
	
	private String message = "";
	
	public String execute()
	{
		try
		{
			if(this.userId > 0)
			{
				//修改用户获取用户信息
				UserDataBase udb = UserDataBase.createUserDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
				if(udb != null)
				{
					this.user = udb.getUser(this.userId);
					if(this.user == null)
					{
						Exception e = new Exception("获取用户信息出错！" + udb.getLastErrorMessage());
						throw e;
					}
				}
				else
				{
					Exception e = new Exception("创建用户数据库访问对象失败！");
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

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public UserInfo getUser()
	{
		return user;
	}

	public void setUser(UserInfo user)
	{
		this.user = user;
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
