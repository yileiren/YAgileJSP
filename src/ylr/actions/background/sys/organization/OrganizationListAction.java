package ylr.actions.background.sys.organization;

import java.util.List;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.organization.OrganizationDataBase;
import ylr.database.system.organization.OrganizationInfo;

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
	
	public String execute()
	{
		try
		{
			OrganizationDataBase db = OrganizationDataBase.createOrganizationDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			if(null != db)
			{
				System.out.println(this.parentId);
				this.orgs = db.getOrganizationsByParentId(this.parentId);
				System.out.println(this.orgs);
				
				if(null == this.orgs)
				{
					Exception e = new Exception("获取组织机构列表出错！" + db.getLastErrorMessage());
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

}
