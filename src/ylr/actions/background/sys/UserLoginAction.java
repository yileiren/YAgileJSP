package ylr.actions.background.sys;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户登陆Action。
 * 
 * @author 董帅 创建时间：2013-07-03 22:57:48
 *
 */
public class UserLoginAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String txtUserName;
	
	private String passUserPassword;
	
	public String execute() throws Exception
    {
		System.out.println(this.txtUserName);
		System.out.println(this.passUserPassword);
        return SUCCESS;
    }

	public String getTxtUserName()
	{
		return txtUserName;
	}

	public void setTxtUserName(String txtUserName)
	{
		this.txtUserName = txtUserName;
	}

	public String getPassUserPassword()
	{
		return passUserPassword;
	}

	public void setPassUserPassword(String passUserPassword)
	{
		this.passUserPassword = passUserPassword;
	}

}
