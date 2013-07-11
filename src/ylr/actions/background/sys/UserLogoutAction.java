package ylr.actions.background.sys;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户退出登陆action。
 * 
 * @author 董帅 创建时间：2013-07-11 14:06:02
 *
 */
public class UserLogoutAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	public String execute()
	{
		//移除用户信息。
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().removeAttribute(SystemConfig.sessionUserInfoName);
		return LOGIN;
		
	}

}
