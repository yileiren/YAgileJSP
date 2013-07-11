package ylr.Interceptor.background;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.organization.UserInfo;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class MainInterceptor extends AbstractInterceptor
{
	private static final long serialVersionUID = 1L;
	
	public String intercept(ActionInvocation actionInvocation) throws Exception
	{
		String retValue = ""; //返回值。
		
		//获取用户访问路径。
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri = request.getRequestURI();
		
		//不是后台管理功能不拦截。
		if(0 == uri.substring(request.getContextPath().length()).indexOf("/background"))
		{
			//判断用户是否登陆
			UserInfo user = (UserInfo) request.getSession().getAttribute(SystemConfig.sessionUserInfoName);
			
			if(null == user)
			{
				request.setAttribute("interceptorErrorMessage", "用户未登陆或登陆超时，请重新登陆！");
				retValue = Action.LOGIN;
			}
		}
		
		if("" == retValue)
		{
			retValue = actionInvocation.invoke();
		}
		
		return retValue;
	}
}
