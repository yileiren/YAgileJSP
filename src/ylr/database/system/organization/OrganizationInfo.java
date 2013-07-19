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
	private int id = -1;
	
	/**
	 * 组织机构名称。
	 */
	private String name = "";
	
	/**
	 * 上级机构。
	 */
	private int parentId = -1;
	
	/**
	 * 创建时间。
	 */
	private Date createTime;
	
	/**
	 * 是否删除。
	 */
	private boolean isDelete = false;
	
	/**
	 * 排序序号。
	 */
	private int order = 0;

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

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}
}
