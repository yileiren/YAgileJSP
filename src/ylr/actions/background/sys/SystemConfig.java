package ylr.actions.background.sys;

import org.apache.struts2.ServletActionContext;

/**
 * 系统配置。
 * @author 董帅 创建时间：2013-07-04 15:55:54
 *
 */
public class SystemConfig
{
	/**
	 * 数据库配置文件名称。
	 */
	public final static String databaseConfigFileName = ServletActionContext.getServletContext().getRealPath("/") + "WEB-INF/DataBaseConfig.xml";
	
	/**
	 * 数据库配置文件节点名称。
	 */
	public final static String databaseConfigNodeName = "SQLServer";
	
	/**
	 * session中的用户信息名称。
	 */
	public final static String sessionUserInfoName = "UserInfo";
}
