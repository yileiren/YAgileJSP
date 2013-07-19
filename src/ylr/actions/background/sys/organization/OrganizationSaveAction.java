package ylr.actions.background.sys.organization;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.organization.OrganizationDataBase;
import ylr.database.system.organization.OrganizationInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 保存组织机构信息。
 * 
 * @author 董帅 创建时间：2013-07-19 14:28:13
 *
 */
public class OrganizationSaveAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 消息提示内容。
	 */
	private String message = "";
	
	/**
	 * 要查询的组织机构id，-1表示新增。
	 */
	private int orgId = -1;
	
	/**
	 * 当前页面的上级机构id。
	 */
	private int parentId = -1;
	
	/**
	 * 查询出来的组织机构信息。
	 */
	private OrganizationInfo org = null;
	
	/**
	 * 主界面跳转地址。
	 */
	private String centerIframeURL = "";
	
	public String execute()
	{
		String retValue = ERROR;
		
		try
		{
			if (this.org == null)
            {
				Exception e = new Exception("不能插入空机构！");
				throw e;
            }
            else if (null == this.org.getName() || this.org.getName().length() > 50)
            {
                Exception e = new Exception("菜单名称不合法，长度1～50！");
				throw e;
            }
            else
            {
            	this.org.setParentId(this.parentId);
            	OrganizationDataBase db = OrganizationDataBase.createOrganizationDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
				if(null != db)
				{
					
	            	if(this.orgId == -1)
	            	{
	            		//新增
		            	int rId = db.createOrganization(this.org);
		            	if(rId > 0)
		            	{
		            		retValue = SUCCESS;
		            		
		            		//设置跳转地址
							HttpServletRequest request = ServletActionContext.getRequest();
							this.centerIframeURL = request.getScheme() 
									+ "://" + request.getServerName() 
									+ ":" + request.getServerPort() 
									+request.getContextPath() 
									+ "/background/sys/organization/organizationList.action?parentId=" + String.valueOf(this.parentId);
		            	}
		            	else
		            	{
		            		Exception e = new Exception("创建组织机构失败！" + db.getLastErrorMessage());
		    				throw e;
		            	}
	            	}
	            	else
	            	{
	            		//修改
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

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getOrgId()
	{
		return orgId;
	}

	public void setOrgId(int orgId)
	{
		this.orgId = orgId;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public OrganizationInfo getOrg()
	{
		return org;
	}

	public void setOrg(OrganizationInfo org)
	{
		this.org = org;
	}

	public String getCenterIframeURL()
	{
		return centerIframeURL;
	}

	public void setCenterIframeURL(String centerIframeURL)
	{
		this.centerIframeURL = centerIframeURL;
	}

}
