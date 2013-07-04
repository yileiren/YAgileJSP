package ylr.database.system.organization;

/**
 * 用户信息类。
 * 
 * @author 董帅 创建时间：2013-07-04 10:53:35
 *
 */
public class UserInfo
{
	/**
	 * 用户id。
	 */
	private int id;
	
	/**
	 * 用户姓名。
	 */
	private String name;
	
	/**
	 * 用户登录名。
	 */
	private String logName;
	
	/**
	 * 用户登陆密码。
	 */
	private String logPassword;
	
	/**
	 * 用户组织机构。
	 */
	private int organizationId;
	
	/**
	 * 用户是否删除。
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

	public String getLogName()
	{
		return logName;
	}

	public void setLogName(String logName)
	{
		this.logName = logName;
	}

	public String getLogPassword()
	{
		return logPassword;
	}

	public void setLogPassword(String logPassword)
	{
		this.logPassword = logPassword;
	}

	public int getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(int organizationId)
	{
		this.organizationId = organizationId;
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
