package ylr.database.system.organization;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ylr.YDB.YDataBase;
import ylr.YDB.YDataBaseConfig;
import ylr.YDB.YDataBaseType;
import ylr.YDB.YDataTable;
import ylr.YDB.YSqlParameters;

/**
 * 组织机构数据库操作类。
 * 
 * @author 董帅 创建时间：2013-07-19 11:07:04
 *
 */
public class OrganizationDataBase
{
	/**
	 * 组织机构数据库。
	 */
	private YDataBase organizationDatabase;
	
	/**
	 * 最后一次错误信息。
	 */
	private String lastErrorMessage = "";

	public YDataBase getOrganizationDatabase()
	{
		return organizationDatabase;
	}

	public void setOrganizationDatabase(YDataBase organizationDatabase)
	{
		this.organizationDatabase = organizationDatabase;
	}

	public String getLastErrorMessage()
	{
		return lastErrorMessage;
	}
	
	/**
	 * 创建组织机构数据库操作对象。
	 * 
	 * @param configFile 数据库配置文件路径。
	 * @param nodeName 数据库配置节点名称。
	 * @return 用户数据库操作对象。
	 * @throws Exception 未处理异常。
	 */
	static public OrganizationDataBase createOrganizationDataBase(String configFile,String nodeName) throws Exception
	{
		OrganizationDataBase db = new OrganizationDataBase();
		
		try
		{
			db.setOrganizationDatabase(YDataBaseConfig.getDataBase(configFile, nodeName));
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
		return db;
	}
	
	/**
	 * 创建组织机构。
	 * 
	 * @param org 组织机构信息，父id为-1的是顶级机构。
	 * 
	 * @return 成功返回机构id，否则返回-1。
	 */
	public int createOrganization(OrganizationInfo org)
	{
		int retValue = -1;
		
		try
		{
			if(this.organizationDatabase.connectDataBase())
			{
				retValue = this.createOrganization(org,this.organizationDatabase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.organizationDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.organizationDatabase.disconnectDataBase();
		}
		
		return retValue;
	}
	
	/**
	 * 创建组织机构。
	 * 
	 * @param org 组织机构信息，父id为-1的是顶级机构。
	 * @param db 使用的数据库连接。
	 * @return 成功返回机构id，否则返回-1。
	 */
	public int createOrganization(OrganizationInfo org,YDataBase db)
	{
		int retValue = -1;
		
		try
        {
			if (org == null)
            {
				Exception e = new Exception("不能插入空机构！");
				throw e;
            }
            else if (null == org.getName() || org.getName().length() > 50)
            {
                Exception e = new Exception("菜单名称不合法，长度1～50！");
				throw e;
            }
            else
            {
	        	//构建SQL语句
				String sql = "";
				//参数
				YSqlParameters ps = new YSqlParameters();
				
				if(org.getParentId() == -1)
				{
					//顶级菜单
					if(YDataBaseType.MSSQL == db.getDatabaseType())
					{
						sql = "INSERT INTO org_organization (name,[ORDER]) VALUES (?,?)";
						ps.addParameter(1, org.getName());
						ps.addParameter(2, org.getOrder());
					}
					else
					{
						Exception e = new Exception("不支持的数据库类型！");
						throw e;
					}
				}
				else
				{
					//子菜单
					if(YDataBaseType.MSSQL == db.getDatabaseType())
					{
						sql = "INSERT INTO org_organization (name,parentId,[ORDER]) VALUES (?,?,?)";
						ps.addParameter(1, org.getName());
						ps.addParameter(2, org.getParentId());
						ps.addParameter(3, org.getOrder());
					}
					else
					{
						Exception e = new Exception("不支持的数据库类型！");
						throw e;
					}
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
						Exception e = new Exception(this.organizationDatabase.getLastErrorMessage());
						throw e;
					}
				}
				else
				{
					Exception e = new Exception(this.organizationDatabase.getLastErrorMessage());
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
	 * 获取指定父id的组织机构列表。
	 * 
	 * @param pId 父id，-1表示顶级机构。
	 * @return 成功返回机构列表，否则返回null。
	 */
	public List<OrganizationInfo> getOrganizationsByParentId(int pId)
	{
		List<OrganizationInfo> orgs = null;
		
		try
		{
			if(this.organizationDatabase.connectDataBase())
			{
				orgs = this.getOrganizationsByParentId(pId,this.organizationDatabase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.organizationDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.organizationDatabase.disconnectDataBase();
		}
		
		return orgs;
	}
	
	/**
	 * 获取指定父id的组织机构列表。
	 * 
	 * @param pId 父id，-1表示顶级机构。
	 * @param db 使用的数据库连接。
	 * @return 成功返回机构列表，否则返回null。
	 */
	public List<OrganizationInfo> getOrganizationsByParentId(int pId,YDataBase db)
	{
		List<OrganizationInfo> orgs = null;
		
		try
		{
			//构建SQL语句
			String sql = "";
			
			if(pId == -1)
			{
				//顶级机构
				if(YDataBaseType.MSSQL == db.getDatabaseType())
				{
					sql = "SELECT * FROM ORG_ORGANIZATION WHERE ISDELETE = 'N' AND PARENTID IS NULL ORDER BY [ORDER] ASC";
				}
				else
				{
					Exception e = new Exception("不支持的数据库类型！");
					throw e;
				}
			}
			else
			{
				//下级机构
				if(YDataBaseType.MSSQL == db.getDatabaseType())
				{
					sql = "SELECT * FROM ORG_ORGANIZATION WHERE ISDELETE = 'N' AND PARENTID = ? ORDER BY [ORDER] ASC";
				}
				else
				{
					Exception e = new Exception("不支持的数据库类型！");
					throw e;
				}
			}
			
			YDataTable table = null;
			if(pId == -1)
			{
				//顶级机构
				table = db.executeSqlReturnData(sql);
			}
			else
			{
				//参数
				YSqlParameters ps = new YSqlParameters();
				ps.addParameter(1, pId);
				
				table = db.executeSqlReturnData(sql,ps);
			}
			
			if(null != table)
			{
				orgs = new ArrayList<OrganizationInfo>();
				
				for(int i = 0;i < table.rowCount();i++)
				{
					OrganizationInfo o = new OrganizationInfo();
					
					if(null != table.getData(i,"ID"))
					{
						o.setId((Integer)table.getData(i,"ID"));
					}
					
					if(null != table.getData(i,"NAME"))
					{
						o.setName((String)table.getData(i,"NAME"));
					}
					
					if(null != table.getData(i,"PARENTID"))
					{
						o.setParentId((Integer)table.getData(i,"PARENTID"));
					}
					
					if(null != table.getData(i, "CREATETIME"))
					{
						o.setCreateTime((Date)table.getData(i, "CREATETIME"));
					}
					
					if(null != table.getData(i, "ISDELETE"))
					{
						String isDelete = (String)table.getData(i, "ISDELETE");
						if(isDelete.equals("N"))
						{
							o.setDelete(false);
						}
						else
						{
							o.setDelete(true);
						}
					}
					
					if(null != table.getData(i, "ORDER"))
					{
						o.setOrder((Integer)table.getData(i, "ORDER"));
					}
					orgs.add(o);
				}
			}
			else
			{
				Exception e = new Exception(this.organizationDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		
		return orgs;
	}
	
	/**
	 * 获取指定的组织机构信息。
	 * 
	 * @param id 组织机构id。
	 * @return 成功返回组织机构信息，否则返回null。
	 */
	public OrganizationInfo getOrganization(int id)
	{
		OrganizationInfo org = null;
		
		try
		{
			if(this.organizationDatabase.connectDataBase())
			{
				org = this.getOrganization(id,this.organizationDatabase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.organizationDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.organizationDatabase.disconnectDataBase();
		}
		
		return org;
	}
	
	/**
	 * 获取指定的组织机构信息。
	 * 
	 * @param id 组织机构id。
	 * @param db 使用的数据库连接。
	 * @return 成功返回组织机构信息，否则返回null。
	 */
	public OrganizationInfo getOrganization(int id,YDataBase db)
	{
		OrganizationInfo org = null;
		
		try
		{
			if(id < 0)
			{
				Exception e = new Exception("指定id有误！");
				throw e;
			}
			else
			{
				//构建SQL语句
				String sql = "";
				
				if(YDataBaseType.MSSQL == db.getDatabaseType())
				{
					sql = "SELECT * FROM ORG_ORGANIZATION WHERE ISDELETE = 'N' AND ID = ?";
				}
				else
				{
					Exception e = new Exception("不支持的数据库类型！");
					throw e;
				}
				
				YSqlParameters ps = new YSqlParameters();
				ps.addParameter(1, id);
				
				//执行
				YDataTable table = db.executeSqlReturnData(sql,ps);
				
				if(null != table && table.rowCount() > 0)
				{
					org = new OrganizationInfo();
					
					if(null != table.getData(0,"ID"))
					{
						org.setId((Integer)table.getData(0,"ID"));
					}
					
					if(null != table.getData(0,"NAME"))
					{
						org.setName((String)table.getData(0,"NAME"));
					}
					
					if(null != table.getData(0,"PARENTID"))
					{
						org.setParentId((Integer)table.getData(0,"PARENTID"));
					}
					
					if(null != table.getData(0, "CREATETIME"))
					{
						org.setCreateTime((Date)table.getData(0, "CREATETIME"));
					}
					
					if(null != table.getData(0, "ISDELETE"))
					{
						String isDelete = (String)table.getData(0, "ISDELETE");
						if(isDelete.equals("N"))
						{
							org.setDelete(false);
						}
						else
						{
							org.setDelete(true);
						}
					}
					
					if(null != table.getData(0, "ORDER"))
					{
						org.setOrder((Integer)table.getData(0, "ORDER"));
					}
				}
				else
				{
					Exception e = new Exception("获取数据失败！" + this.organizationDatabase.getLastErrorMessage());
					throw e;
				}
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		return org;
	}
	
	/**
	 * 修改组织机构信息。
	 * 
	 * @param org 要修改的组织机构。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean changeOrganization(OrganizationInfo org)
	{
		boolean retValue = false;
		
		try
		{
			if(this.organizationDatabase.connectDataBase())
			{
				retValue = this.changeOrganization(org,this.organizationDatabase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.organizationDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.organizationDatabase.disconnectDataBase();
		}
		
		return retValue;
	}
	
	/**
	 * 修改组织机构信息。
	 * 
	 * @param org 要修改的组织机构。
	 * @param db 使用的数据库连接。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean changeOrganization(OrganizationInfo org,YDataBase db)
	{
		boolean retValue = false;
		
		try
		{
			if(org == null)
			{
				Exception e = new Exception("组织机构为null！");
				throw e;
			}
			else
			{
				//构建SQL语句
				String sql = "";
				YSqlParameters ps = new YSqlParameters();
				
				if(org.getParentId() == -1)
				{
					if(YDataBaseType.MSSQL == db.getDatabaseType())
					{
						sql = "UPDATE ORG_ORGANIZATION SET NAME = ?,[ORDER] = ? WHERE ID = ?";
						ps.addParameter(1, org.getName());
						ps.addParameter(2, org.getOrder());
						ps.addParameter(3, org.getId());
					}
					else
					{
						Exception e = new Exception("不支持的数据库类型！");
						throw e;
					}
				}
				else
				{
					if(YDataBaseType.MSSQL == db.getDatabaseType())
					{
						sql = "UPDATE ORG_ORGANIZATION SET NAME = ?,PARENTID = ?,[ORDER] = ? WHERE ID = ?";
						ps.addParameter(1, org.getName());
						ps.addParameter(2, org.getParentId());
						ps.addParameter(3, org.getOrder());
						ps.addParameter(4, org.getId());
					}
					else
					{
						Exception e = new Exception("不支持的数据库类型！");
						throw e;
					}
				}
				
				//执行
				int rowCount = db.executeSqlWithOutData(sql,ps);
				
				if(rowCount == 1)
				{
					retValue = true;
				}
				else
				{
					Exception e = new Exception("更新数据失败！" + this.organizationDatabase.getLastErrorMessage());
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
}
