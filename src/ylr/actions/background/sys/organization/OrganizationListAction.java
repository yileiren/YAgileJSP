package ylr.actions.background.sys.organization;

import java.util.List;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.organization.OrganizationDataBase;
import ylr.database.system.organization.OrganizationInfo;
import ylr.database.system.organization.UserDataBase;
import ylr.database.system.organization.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 组织机构列表action。
 * 
 * @author 董帅 创建时间：2013-07-19 10:14:24
 *
 */
public class OrganizationListAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回消息。
	 */
	private String message = "";
	
	/**
	 * 父机构id。
	 */
	private int parentId = -1;
	
	/**
	 * 获取的组织机构列表。
	 */
	private List<OrganizationInfo> orgs = null;
	
	/**
	 * 用户列表。
	 */
	private List<UserInfo> users = null;
	
	/**
	 * 上级机构
	 */
	private OrganizationInfo parentOrg = null;
	
	public String execute()
	{
		try
		{
			OrganizationDataBase db = OrganizationDataBase.createOrganizationDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			if(null != db)
			{
				//获取机构列表。
				this.orgs = db.getOrganizationsByParentId(this.parentId);
				
				if(null == this.orgs)
				{
					Exception e = new Exception("获取组织机构列表出错！" + db.getLastErrorMessage());
					throw e;
				}
				
				//获取上级机构信息。
				if(this.parentId == -1)
				{
					this.parentOrg = new OrganizationInfo();
					this.parentOrg.setId(-1);
					this.parentOrg.setName("顶级机构");
				}
				else
				{
					this.parentOrg = db.getOrganization(this.parentId);
					if(this.parentOrg == null)
					{
						Exception e = new Exception("获取上级机构出错！" + db.getLastErrorMessage());
						throw e;
					}
				}
				
				//获取用户列表
				UserDataBase udb = UserDataBase.createUserDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
				if(udb != null)
				{
					this.users = udb.getUsersByOrganizationId(this.parentId);
					if(this.users == null)
					{
						Exception e = new Exception("获取用户列表出错！" + udb.getLastErrorMessage());
						throw e;
					}
				}
				else
				{
					Exception e = new Exception("创建用户数据库访问对象失败！");
					throw e;
				}
			}
			else
			{
				Exception e = new Exception("创建组织机构数据库访问对象失败！");
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.message = ex.getMessage();
		}
		
		return NONE;
	}
	

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public List<OrganizationInfo> getOrgs()
	{
		return orgs;
	}

	public void setOrgs(List<OrganizationInfo> orgs)
	{
		this.orgs = orgs;
	}


	public OrganizationInfo getParentOrg()
	{
		return parentOrg;
	}


	public void setParentOrg(OrganizationInfo parentOrg)
	{
		this.parentOrg = parentOrg;
	}


	public List<UserInfo> getUsers()
	{
		return users;
	}


	public void setUsers(List<UserInfo> users)
	{
		this.users = users;
	}

}
