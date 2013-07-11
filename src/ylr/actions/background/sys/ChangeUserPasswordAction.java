package ylr.actions.background.sys;

import org.apache.struts2.ServletActionContext;

import ylr.YCrypto.MD5Encrypt;
import ylr.database.system.organization.UserDataBase;
import ylr.database.system.organization.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 修改用户密码action。
 * 
 * @author 董帅 创建时间：2013-07-11 15:16:42
 *
 */
public class ChangeUserPasswordAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误信息。
	 */
	private String errorMessage = "";
	
	private String pswOldPsw = "";
	
	private String pswNewPsw1 = "";
	
	public String execute()
	{
		try
		{
			//获取用户信息
			UserInfo user = (UserInfo) ServletActionContext.getRequest().getSession().getAttribute(SystemConfig.sessionUserInfoName);
			if(null == user)
			{
				Exception e = new Exception("获取用户信息失败！");
				throw e;
			}
			
			
			//创建数据库连接
			UserDataBase db = UserDataBase.createUserDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
			if(null != db)
			{
				if(db.changePassword(user.getId(), MD5Encrypt.getMD5(this.getPswOldPsw()), MD5Encrypt.getMD5(this.getPswNewPsw1())))
				{
					Exception e = new Exception("修改密码成功！");
					throw e;
				}
				else
				{
					Exception e = new Exception("修改密码失败！" + db.getLastErrorMessage());
					throw e;
				}
			}
			else
			{
				Exception e = new Exception("创建数据库访问失败！");
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.errorMessage = ex.getMessage();
		}
		
		return SUCCESS;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public String getPswOldPsw()
	{
		return pswOldPsw;
	}

	public void setPswOldPsw(String pswOldPsw)
	{
		this.pswOldPsw = pswOldPsw;
	}

	public String getPswNewPsw1()
	{
		return pswNewPsw1;
	}

	public void setPswNewPsw1(String pswNewPsw1)
	{
		this.pswNewPsw1 = pswNewPsw1;
	}

}
