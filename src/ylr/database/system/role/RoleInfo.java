package ylr.database.system.role;

/**
 * 角色信息。
 * @author 董帅 创建时间：2013-07-29 14:40:05
 *
 */
public class RoleInfo
{
	private int id = -1;
	
	private String name = "";
	
	private String explain = "";

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

	public String getExplain()
	{
		return explain;
	}

	public void setExplain(String explain)
	{
		this.explain = explain;
	}
}
