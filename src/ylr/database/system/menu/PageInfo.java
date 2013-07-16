package ylr.database.system.menu;

/**
 * 关联页面实体类。
 * 
 * @author 董帅 创建时间：2013-07-16 15:59:30
 *
 */
public class PageInfo
{
	/**
	 * 页面id。
	 */
	private int id = -1;
	
	/**
	 * 路径。
	 */
	private String path = "";
	
	/**
	 * 说明。
	 */
	private String detail = "";
	
	/**
	 * 菜单id。
	 */
	private int menuId = -1;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getDetail()
	{
		return detail;
	}

	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	public int getMenuId()
	{
		return menuId;
	}

	public void setMenuId(int menuId)
	{
		this.menuId = menuId;
	}
}
