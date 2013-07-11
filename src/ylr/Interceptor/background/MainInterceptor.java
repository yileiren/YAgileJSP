package ylr.Interceptor.background;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class MainInterceptor extends AbstractInterceptor
{
	private static final long serialVersionUID = 1L;
	
	public String intercept(ActionInvocation actionInvocation) throws Exception
	{
		//获取用户访问路径。
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri = request.getRequestURI();
		
		System.out.println(uri.substring(request.getContextPath().length()));
		System.out.println(uri.substring(request.getContextPath().length()).indexOf("/background"));
		
		if(0 != uri.substring(request.getContextPath().length()).indexOf("/background"))
		{
			//不是后台管理功能不拦截。
			return actionInvocation.invoke();
		}
		else
		{
			request.setAttribute("errorMessage", "ddd");
			return Action.LOGIN;
		}
	}
}
