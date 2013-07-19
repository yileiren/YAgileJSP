package ylr.actions.background.sys.organization;

import ylr.database.system.organization.OrganizationInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 组织机构查询action。
 * 
 * @author 董帅 创建时间：2013-07-19 14:00:57
 *
 */
public class OrganizationQueryAction extends ActionSupport
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
	
	public String execute()
	{
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

	public int getOrgId()
	{
		return orgId;
	}

	public void setOrgId(int orgId)
	{
		this.orgId = orgId;
	}

	public OrganizationInfo getOrg()
	{
		return org;
	}

	public void setOrg(OrganizationInfo org)
	{
		this.org = org;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}
}
