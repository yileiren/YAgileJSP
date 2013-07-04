package ylr.actions.background.sys;

import org.apache.catalina.connector.Request;
import org.apache.struts2.ServletActionContext;

import ylr.YCrypto.MD5Encrypt;
import ylr.database.system.organization.UserDataBase;
import ylr.database.system.organization.UserInfo;

import com.opensymphony.xwork2.ActionContext;
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
		this.login();
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

	
	/**
	 * 用户登陆。
	 * 
	 * @return 成功返回true，否则返回false。
	 */
	private boolean login()
	{
		boolean retValue = false;
		
		try
		{
			//创建数据库操作对象。
			UserDataBase db = UserDataBase.createUserDataBase(ServletActionContext.getServletContext().getRealPath("/") + SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			if(null != db)
			{
				UserInfo user = db.login(this.txtUserName, MD5Encrypt.getMD5(this.passUserPassword));
				if(user != null)
				{
					System.out.println(user.getName());
					retValue = true;
				}
				else
				{
					System.out.println(db.getLastErrorMessage());
					Exception e = new Exception("用户登陆失败！" + db.getLastErrorMessage());
					throw e;
				}
			}
			else
			{
				Exception e = new Exception("创建数据库操作对象失败！");
				throw e;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return retValue;
	}
}
