package ylr.actions.background.sys.organization;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import ylr.YCrypto.MD5Encrypt;
import ylr.actions.background.sys.SystemConfig;
import ylr.database.system.organization.UserDataBase;
import ylr.database.system.organization.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户信息存储action。
 * 
 * @author 董帅 创建时间：2013-07-23 14:34:09
 *
 */
public class UserSaveAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前页面的上级机构id。
	 */
	private int parentId = -1;
	
	private int userId = -1;
	
	private UserInfo user = null;
	
	private String message = "";
	
	private String centerIframeURL = "";
	
	public String execute()
	{
		String retValue = ERROR;
		
		try
		{
			if(null == this.user)
			{
				Exception e = new Exception("用户信息为null！");
				throw e;
			}
			else if(null == this.user.getName() || this.user.getName().equals(""))
			{
				Exception e = new Exception("未设置用户姓名！");
				throw e;
			}
			else if(this.user.getName().length() > 20)
			{
				Exception e = new Exception("用户姓名字符个数超过20！");
				throw e;
			}
			else if(null == this.user.getLogName() || this.user.getLogName().equals(""))
			{
				Exception e = new Exception("未设置用户登陆名！");
				throw e;
			}
			else if(this.user.getLogName().length() > 20)
			{
				Exception e = new Exception("用户登陆名字符个数超过20！");
				throw e;
			}
			else
			{
				this.user.setOrganizationId(this.parentId);
				this.user.setLogPassword(MD5Encrypt.getMD5(this.user.getLogPassword()));
				
				UserDataBase db = UserDataBase.createUserDataBase(SystemConfig.databaseConfigFileName, SystemConfig.databaseConfigNodeName);
				
				if(db != null)
				{
					if(this.userId == -1)
					{
						//新增
		            	int rId = db.createUser(this.user);
		            	if(rId > 0)
		            	{
		            		retValue = SUCCESS;
		            		
		            		//设置跳转地址
							HttpServletRequest request = ServletActionContext.getRequest();
							this.setCenterIframeURL(request.getScheme() 
									+ "://" + request.getServerName() 
									+ ":" + request.getServerPort() 
									+request.getContextPath() 
									+ "/background/sys/organization/organizationList.action?parentId=" + String.valueOf(this.parentId));
		            	}
		            	else
		            	{
		            		Exception e = new Exception("创建用户失败！" + db.getLastErrorMessage());
		    				throw e;
		            	}
					}
				}
				else
				{
					Exception e = new Exception("创建数据库访问对象出错！");
					throw e;
				}
			}
		}
		catch (Exception ex)
		{
			this.message = ex.getMessage();
		}
		
		return retValue;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public UserInfo getUser()
	{
		return user;
	}

	public void setUser(UserInfo user)
	{
		this.user = user;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getCenterIframeURL()
	{
		return centerIframeURL;
	}

	public void setCenterIframeURL(String centerIframeURL)
	{
		this.centerIframeURL = centerIframeURL;
	}
}
