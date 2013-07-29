package ylr.database.system.role;

import java.util.ArrayList;
import java.util.List;

import ylr.database.system.menu.MenuInfo;

/**
 * 角色菜单。
 * @author 董帅 创建时间：2013-07-29 16:45:01
 *
 */
public class RoleMenuInfo extends MenuInfo
{
	/**
	 * 是否选中。
	 */
	private boolean choused = false;
	
	/**
	 * 子菜单。
	 */
	private List<RoleMenuInfo> menus = new ArrayList<RoleMenuInfo>();

	public boolean isChoused()
	{
		return choused;
	}

	public void setChoused(boolean choused)
	{
		this.choused = choused;
	}

	public List<RoleMenuInfo> getMenus()
	{
		return menus;
	}

	public void setMenus(List<RoleMenuInfo> menus)
	{
		this.menus = menus;
	}

}
