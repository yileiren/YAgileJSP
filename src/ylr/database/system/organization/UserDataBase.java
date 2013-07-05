package ylr.database.system.organization;

import ylr.YDB.YDataBase;
import ylr.YDB.YDataBaseConfig;
import ylr.YDB.YDataBaseType;
import ylr.YDB.YDataTable;
import ylr.YDB.YSqlParameters;

/**
 * 用户数据库操作类。。
 * @author 董帅 创建时间：2013-07-03 14:14:38
 *
 */
public class UserDataBase
{
	/**
	 * 用户数据库。
	 */
	private YDataBase userDatabase;
	
	/**
	 * 最后一次错误信息。
	 */
	private String lastErrorMessage = "";

	public YDataBase getUserDatabase()
	{
		return userDatabase;
	}

	public void setUserDatabase(YDataBase userDatabase)
	{
		this.userDatabase = userDatabase;
	}
	
	/**
	 * 获取最后一次错误信息。
	 * 
	 * @return 错误信息。
	 */
	public String getLastErrorMessage()
	{
		return this.lastErrorMessage;
	}
	
	/**
	 * 创建用户数据库操作对象。
	 * 
	 * @param configFile 数据库配置文件路径。
	 * @param nodeName 数据库配置节点名称。
	 * @return 用户数据库操作对象。
	 * @throws Exception 未处理异常。
	 */
	static public UserDataBase createUserDataBase(String configFile,String nodeName) throws Exception
	{
		UserDataBase db = new UserDataBase();
		
		try
		{
			db.setUserDatabase(YDataBaseConfig.getDataBase(configFile, nodeName));
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
		return db;
	}
	
	/**
	 * 用户登陆。
	 * 
	 * @param userName 用户名。
	 * @param userPassword 密码。
	 * @return 成功返回用户信息，否则返回null。
	 */
	public UserInfo login(String logName,String logPassword)
	{
		UserInfo user = null;
		
		try
		{
			if(this.userDatabase.connectDataBase())
			{
				user = this.login(logName, logPassword, this.userDatabase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.userDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.userDatabase.disconnectDataBase();
		}
		
		return user;
	}
	
	/**
	 * 用户登陆。
	 * 
	 * @param userName 用户名。
	 * @param userPassword 密码。
	 * @param db 使用的数据库连接。
	 * @return 成功返回用户信息，否则返回null。
	 */
	public UserInfo login(String logName,String logPassword,YDataBase db)
	{
		UserInfo user = null;
		
		try
		{
			//构建SQL语句
			String sql = "";
			if(YDataBaseType.MSSQL == db.getDatabaseType())
			{
				sql = "SELECT TOP(1) * FROM ORG_USER WHERE ISDELETE = 'N' AND LOGNAME = ? AND LOGPASSWORD = ?";
			}
			else
			{
				Exception e = new Exception("不支持的数据库类型！");
				throw e;
			}
			
			//参数
			YSqlParameters ps = new YSqlParameters();
			ps.addParameter(1, logName);
			ps.addParameter(2, logPassword);
			
			//执行语句
			YDataTable table = db.executeSqlReturnData(sql, ps);
			if(null != table)
			{
				if(table.rowCount() > 0)
				{
					user = new UserInfo();
					if(null != table.getData(0,"ID"))
					{
						user.setId((Integer)table.getData(0,"ID"));
					}
					
					if(null != table.getData(0,"NAME"))
					{
						user.setName((String)table.getData(0,"NAME"));
					}
					
					if(null != table.getData(0,"LOGNAME"))
					{
						user.setLogName((String)table.getData(0,"LOGNAME"));
					}
					
					if(null != table.getData(0, "LOGPASSWORD"))
					{
						user.setLogPassword((String)table.getData(0, "LOGPASSWORD"));
					}
					
					if(null != table.getData(0, "ORGANIZATIONID"))
					{
						user.setOrganizationId((Integer)table.getData(0, "ORGANIZATIONID"));
					}
					
					if(null != table.getData(0, "ISDELETE"))
					{
						String isDelete = (String)table.getData(0, "ISDELETE");
						if(isDelete.equals("N"))
						{
							user.setDelete(false);
						}
						else
						{
							user.setDelete(true);
						}
					}
					
					if(null != table.getData(0, "ORDER"))
					{
						user.setOrder((Integer)table.getData(0, "ORDER"));
					}
				}
				else
				{
					Exception e = new Exception("用户名或密码错误！");
					throw e;
				}
			}
			else
			{
				Exception e = new Exception(this.userDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		
		return user;
	}
}
