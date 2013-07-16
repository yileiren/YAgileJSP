package ylr.actions.background.sys.menu;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 删除菜单action。
 * 
 * @author 董帅 创建时间：2013-07-16 13:13:58
 *
 */
public class MenuDeleteAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 父菜单id。
	 */
	private int topMenuId = -1;
	
	private String topMenuName = "";
	
	private String topMenuIcon = "";
	
	/**
	 * 返回消息。
	 */
	private String returnMessage = "";
	
	/**
	 * 选中的菜单分组。
	 */
	private String chkGroup = "";
	
	/**
	 * 删除分组或菜单。
	 */
	private String type = "group";
	
	public String execute()
	{
		System.out.println(this.chkGroup);
		return SUCCESS;
	}

	public String getChkGroup()
	{
		return chkGroup;
	}

	public void setChkGroup(String chkGroup)
	{
		this.chkGroup = chkGroup;
	}

	public int getTopMenuId()
	{
		return topMenuId;
	}

	public void setTopMenuId(int topMenuId)
	{
		this.topMenuId = topMenuId;
	}

	public String getTopMenuName()
	{
		return topMenuName;
	}

	public void setTopMenuName(String topMenuName)
	{
		this.topMenuName = topMenuName;
	}

	public String getTopMenuIcon()
	{
		return topMenuIcon;
	}

	public void setTopMenuIcon(String topMenuIcon)
	{
		this.topMenuIcon = topMenuIcon;
	}

	public String getReturnMessage()
	{
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage)
	{
		this.returnMessage = returnMessage;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
