package ylr.database.system.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单信息。
 * @author 董帅 创建时间：2013-07-08 14:34:23
 *
 */
public class MenuInfo
{
	/**
	 * 菜单id。
	 */
	private int id = -1;
	
	/**
	 * 菜单名称。
	 */
	private String name = "";
	
	/**
	 * 菜单URL。
	 */
	private String URL = "";
	
	/**
	 * 父菜单id，顶级菜单id为-1。
	 */
	private int parentId = -1;
	
	/**
	 * 菜单图标。
	 */
	private String icon = "";
	
	/**
	 * 桌面图标。
	 */
	private String desktopIcon = "";
	
	/**
	 * 排序序号。
	 */
	private int order = 0;
	
	/**
	 * 子菜单。
	 */
	private List<MenuInfo> childMenus = new ArrayList<MenuInfo>();

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

	public String getURL()
	{
		return URL;
	}

	public void setURL(String uRL)
	{
		URL = uRL;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getDesktopIcon()
	{
		return desktopIcon;
	}

	public void setDesktopIcon(String desktopIcon)
	{
		this.desktopIcon = desktopIcon;
	}

	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order)
	{
		this.order = order;
	}

	public List<MenuInfo> getChildMenus()
	{
		return childMenus;
	}

	public void setChildMenus(List<MenuInfo> childMenus)
	{
		this.childMenus = childMenus;
	}
}
