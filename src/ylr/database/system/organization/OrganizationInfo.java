package ylr.database.system.organization;

import java.util.Date;

/**
 * 组织机构信息。
 * 
 * @author 董帅 创建时间：2013-07-04 11:05:17
 *
 */
public class OrganizationInfo
{
	/**
	 * 组织机构id。
	 */
	private int id;
	
	/**
	 * 组织机构名称。
	 */
	private String name;
	
	/**
	 * 上级机构。
	 */
	private OrganizationInfo praentOrganization;
	
	/**
	 * 创建时间。
	 */
	private Date createTime;
	
	/**
	 * 是否删除。
	 */
	private boolean isDelete;
	
	/**
	 * 排序序号。
	 */
	private int order;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public OrganizationInfo getPraentOrganization()
	{
		return praentOrganization;
	}

	public void setPraentOrganization(OrganizationInfo praentOrganization)
	{
		this.praentOrganization = praentOrganization;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public boolean isDelete()
	{
		return isDelete;
	}

	public void setDelete(boolean isDelete)
	{
		this.isDelete = isDelete;
	}

	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order)
	{
		this.order = order;
	}
}
