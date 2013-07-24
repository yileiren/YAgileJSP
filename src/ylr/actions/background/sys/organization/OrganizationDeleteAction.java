package ylr.actions.background.sys.organization;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.organization.OrganizationDataBase;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 删除组织机构。
 * 
 * @author 董帅 创建时间：2013-07-24 15:04:39
 *
 */
public class OrganizationDeleteAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	/**
	 * 当前页面的上级机构id。
	 */
	private int parentId = -1;
	
	private String chkOrg = "";
	
	private String message = "";
	
	public String execute()
	{
		try
		{
			String[] sids = this.chkOrg.split(",");
			if(sids.length > 0)
			{
				int[] ids = new int[sids.length];
				for(int i = 0;i < sids.length;i++)
				{
					ids[i] = Integer.valueOf(sids[i].trim());
				}
				
				OrganizationDataBase db = OrganizationDataBase.createOrganizationDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
				if(null != db)
				{
					if(!db.deleteOrganizations(ids))
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
				Exception e = new Exception("未指定要删除的组织机构！");
				throw e;
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

	public String getChkOrg()
	{
		return chkOrg;
	}

	public void setChkOrg(String chkOrg)
	{
		this.chkOrg = chkOrg;
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
