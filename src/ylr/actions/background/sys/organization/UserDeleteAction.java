package ylr.actions.background.sys.organization;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.organization.UserDataBase;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 删除用户。
 * @author 董帅 创建时间：2013-07-24 14:06:57
 *
 */
public class UserDeleteAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	/**
	 * 当前页面的上级机构id。
	 */
	private int parentId = -1;
	
	private String chkUser = "";
	
	private String message = "";
	
	public String execute()
	{
		try
		{
			System.out.println(this.chkUser);
			String[] sids = this.chkUser.split(",");
			if(sids.length > 0)
			{
				int[] ids = new int[sids.length];
				for(int i = 0;i < sids.length;i++)
				{
					ids[i] = Integer.valueOf(sids[i].trim());
				}
				
				UserDataBase db = UserDataBase.createUserDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
				if(null != db)
				{
					if(!db.deleteUsers(ids))
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

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public String getChkUser()
	{
		return chkUser;
	}

	public void setChkUser(String chkUser)
	{
		this.chkUser = chkUser;
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
