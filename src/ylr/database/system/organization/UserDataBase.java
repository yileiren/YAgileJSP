package ylr.database.system.organization;

import java.sql.ResultSet;
import java.sql.SQLException;

import ylr.YDB.YDataBase;
import ylr.YDB.YDataBaseConfig;
import ylr.YDB.YDataBaseType;
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
	 * @param db 使用的数据库连接。
	 * @return 成功返回用户信息，否则返回null。
	 */
	public UserInfo login(String logName,String logPassword,YDataBase db)
	{
		UserInfo user = null;
		ResultSet rs = null;
		
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
			rs = db.executeSqlReturnData(sql, ps);
			if(null != rs)
			{
				if(rs.next())
				{
					user = new UserInfo();
					user.setId(rs.getInt("ID"));
					
					user.setName(rs.getString("NAME"));
					if(rs.wasNull())
					{
						user.setName("");
					}
					
					user.setLogName(rs.getString("LOGNAME"));
					if(rs.wasNull())
					{
						user.setLogName("");
					}
					
					user.setLogPassword("LOGPASSWORD");
					if(rs.wasNull())
					{
						user.setLogPassword("");
					}
					
					user.setOrganizationId(rs.getInt("ORGANIZATIONID"));
					if(rs.wasNull())
					{
						user.setOrganizationId(-1);
					}
					
					String isDelete = rs.getString("ISDELETE");
					if(!rs.wasNull())
					{
						if(isDelete.equals("N"))
						{
							user.setDelete(false);
						}
						else
						{
							user.setDelete(true);
						}
					}
					
					user.setOrder(rs.getInt("ORDER"));
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
		finally
		{
			if(null != rs)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return user;
	}
}
