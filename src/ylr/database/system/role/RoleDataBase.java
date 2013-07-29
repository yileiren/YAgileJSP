package ylr.database.system.role;

import java.util.ArrayList;
import java.util.List;

import ylr.YDB.YDataBase;
import ylr.YDB.YDataBaseConfig;
import ylr.YDB.YDataBaseType;
import ylr.YDB.YDataTable;
import ylr.YDB.YSqlParameters;

/**
 * 角色数据库访问。
 * 
 * @author 董帅 创建时间：2013-07-29 14:45:07
 *
 */
public class RoleDataBase
{
	/**
	 * 角色数据库操作对象。
	 */
	private YDataBase roleDataBase = null;
	
	/**
	 * 最后一次错误信息。
	 */
	private String lastErrorMessage = "";
	
	/**
	 * 获取最后一次错误信息。
	 * 
	 * @return 错误信息。
	 */
	public String getLastErrorMessage()
	{
		return this.lastErrorMessage;
	}

	public YDataBase getRoleDataBase()
	{
		return roleDataBase;
	}

	public void setRoleDataBase(YDataBase roleDataBase)
	{
		this.roleDataBase = roleDataBase;
	}
	
	/**
	 * 创建数据库操作对象。
	 * 
	 * @param configFile 数据库配置文件路径。
	 * @param nodeName 数据库配置节点名称。
	 * @return 数据库操作对象。
	 * @throws Exception 未处理异常。
	 */
	static public RoleDataBase createRoleDataBase(String configFile,String nodeName) throws Exception
	{
		RoleDataBase db = new RoleDataBase();
		
		try
		{
			db.setRoleDataBase(YDataBaseConfig.getDataBase(configFile, nodeName));
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
		return db;
	}
	
	/**
	 * 创建角色。
	 * @param role 角色信息。
	 * @return 成功返回角色id，否则返回-1。
	 */
	public int createRole(RoleInfo role)
	{
		int retValue = -1;
		
		try
		{
			if(this.roleDataBase.connectDataBase())
			{
				retValue = this.createRole(role,this.roleDataBase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.roleDataBase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.roleDataBase.disconnectDataBase();
		}
		
		return retValue;
	}
	
	/**
	 * 创建角色。
	 * @param role 角色信息。
	 * @param db 使用的数据库连接。
	 * @return 成功返回角色id，否则返回-1。
	 */
	public int createRole(RoleInfo role,YDataBase db)
	{
		int retValue = -1;
		
		try
		{
			if (role == null)
            {
				Exception e = new Exception("不能插入空角色！");
				throw e;
            }
            else if (null == role.getName() || role.getName().length() > 20)
            {
                Exception e = new Exception("角色名称长度应该在1～20！");
				throw e;
            }
            else
            {
	        	//构建SQL语句
				String sql = "";
				//参数
				YSqlParameters ps = new YSqlParameters();
				ps.addParameter(1, role.getName());
				ps.addParameter(2, role.getExplain());
				
				if(YDataBaseType.MSSQL == db.getDatabaseType())
				{
					sql = "INSERT INTO AUT_ROLE (NAME,EXPLAIN) VALUES (?,?)";
				}
				else
				{
					Exception e = new Exception("不支持的数据库类型！");
					throw e;
				}
				
				//执行语句。
				int rowCount = db.executeSqlWithOutData(sql, ps);
				if(rowCount > 0)
				{
					//获取id
					YDataTable table = db.executeSqlReturnData("SELECT @@IDENTITY AS ID");
					
					if(null != table && table.rowCount() > 0)
					{
						if(null != table.getData(0,"ID"))
						{
							double d = (Double)table.getData(0,"ID");
							retValue = (int) d;
						}
					}
					else
					{
						Exception e = new Exception(db.getLastErrorMessage());
						throw e;
					}
				}
				else
				{
					Exception e = new Exception(db.getLastErrorMessage());
					throw e;
				}
            }
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		
		return retValue;
	}
	
	/**
	 * 获取所有角色。
	 * @return 成功然会角色列表，否则返回null。
	 */
	public List<RoleInfo> getRoles()
	{
		List<RoleInfo> roles = null;
		
		try
		{
			if(this.roleDataBase.connectDataBase())
			{
				roles = this.getRoles(this.roleDataBase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.roleDataBase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.roleDataBase.disconnectDataBase();
		}
		
		return roles;
	}
	
	/**
	 * 获取所有角色。
	 * @param db 使用的数据库连接。
	 * @return 成功然会角色列表，否则返回null。
	 */
	public List<RoleInfo> getRoles(YDataBase db)
	{
		List<RoleInfo> roles = null;
		
		try
        {
        	//构建SQL语句
			String sql = "";
			if(YDataBaseType.MSSQL == db.getDatabaseType())
			{
				sql = "SELECT * FROM AUT_ROLE";
			}
			else
			{
				Exception e = new Exception("不支持的数据库类型！");
				throw e;
			}
			
			//执行语句。
			YDataTable table = db.executeSqlReturnData(sql);
			
			if(null != table)
			{
				roles = new ArrayList<RoleInfo>();
				for(int i = 0;i < table.rowCount();i++)
				{
					RoleInfo role = new RoleInfo();
					
					if(null != table.getData(i,"ID"))
					{
						role.setId((Integer)table.getData(i,"ID"));
					}
					
					if(null != table.getData(i,"NAME"))
					{
						role.setName((String)table.getData(i,"NAME"));
					}
					
					if(null != table.getData(i,"EXPLAIN"))
					{
						role.setExplain((String)table.getData(i,"EXPLAIN"));
					}
					
					roles.add(role);
				}
			}
			else
			{
				Exception e = new Exception(db.getLastErrorMessage());
				throw e;
			}
        }
        catch(Exception ex)
        {
        	this.lastErrorMessage = ex.getMessage();
        }
		
		return roles;
	}
}
