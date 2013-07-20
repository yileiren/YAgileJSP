package ylr.actions.background.sys.organization;

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
	
	public String execute()
	{
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
}
