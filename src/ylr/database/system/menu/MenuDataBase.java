package ylr.database.system.menu;

import java.util.ArrayList;
import java.util.List;

import ylr.YDB.YDataBase;
import ylr.YDB.YDataBaseConfig;
import ylr.YDB.YDataBaseType;
import ylr.YDB.YDataTable;
import ylr.YDB.YSqlParameters;

/**
 * 菜单数据库操作类。
 * 
 * @author 董帅 创建时间：2013-07-08 14:43:16
 *
 */
public class MenuDataBase
{
	/**
	 * 用户数据库。
	 */
	private YDataBase menuDatabase = null;
	
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
	
	/**
	 * 创建数据库操作对象。
	 * 
	 * @param configFile 数据库配置文件路径。
	 * @param nodeName 数据库配置节点名称。
	 * @return 数据库操作对象。
	 * @throws Exception 未处理异常。
	 */
	static public MenuDataBase createMenuDataBase(String configFile,String nodeName) throws Exception
	{
		MenuDataBase db = new MenuDataBase();
		
		try
		{
			db.setMenuDatabase(YDataBaseConfig.getDataBase(configFile, nodeName));
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
		return db;
	}

	public YDataBase getMenuDatabase()
	{
		return menuDatabase;
	}

	public void setMenuDatabase(YDataBase menuDatabase)
	{
		this.menuDatabase = menuDatabase;
	}
	
	/**
	 * 获取主界面菜单。
	 * 
	 * @param userId 用户id。
	 * @return 菜单，失败返回null。
	 */
	public List<MenuInfo> getMainPageMunus(int userId)
    {
		List<MenuInfo> menus = null;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				menus = this.getMainPageMunus(userId,this.menuDatabase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.menuDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.menuDatabase.disconnectDataBase();
		}
		
		return menus;
    }
	
	/**
	 * 获取主界面菜单。
	 * public List<MenuInfo> getMainPageMunus(int userId)
    {
        List<MenuInfo> menus = null;
	 * @param userId 用户id。
	 * @param db 使用的数据库连接。
	 * @return 成功返回菜单，失败返回null。
	 */
	public List<MenuInfo> getMainPageMunus(int userId,YDataBase db)
    {
        List<MenuInfo> menus = null;
        
        try
        {
        	//构建SQL语句
			String sql = "";
			if(YDataBaseType.MSSQL == db.getDatabaseType())
			{
				if(userId == 1)
				{
					sql = "SELECT * FROM SYS_MENUS ORDER BY PARENTID ASC,[ORDER] ASC";
				}
				else
				{
					sql = "SELECT DISTINCT SYS_MENUS.* "
                    		+ "FROM SYS_MENUS,AUT_USER_ROLE,AUT_ROLE_MENU"
                    		+ "WHERE AUT_USER_ROLE.USERID = ?" 
                            + "AND AUT_USER_ROLE.ROLEID = AUT_ROLE_MENU.ROLEID"
                            + "AND AUT_ROLE_MENU.MENUID = SYS_MENUS.ID"
                            + "ORDER BY PARENTID ASC,[ORDER] ASC";
				}
			}
			else
			{
				Exception e = new Exception("不支持的数据库类型！");
				throw e;
			}
			
			
			
			//执行语句
			YDataTable table = null;
			
			if(userId == 1)
			{
				table = db.executeSqlReturnData(sql);
			}
			else
			{
				//参数
				YSqlParameters ps = new YSqlParameters();
				ps.addParameter(1, userId);
				table = db.executeSqlReturnData(sql, ps);
			}
			
			if(null != table)
			{
				menus = new ArrayList<MenuInfo>();
				
				//构建菜单。
				for(int i = 0;i < table.rowCount();i++)
				{
					if(null == table.getData(i,"PARENTID"))
					{
						//顶级菜单
						MenuInfo pMenu = new MenuInfo();
						if(null != table.getData(i,"ID"))
						{
							pMenu.setId((Integer)table.getData(i,"ID"));
						}
						
						if(null != table.getData(i,"NAME"))
						{
							pMenu.setName((String)table.getData(i,"NAME"));
						}
						
						if(null != table.getData(i,"URL"))
						{
							pMenu.setURL((String)table.getData(i,"URL"));
						}
						
						if(null != table.getData(i,"PARENTID"))
						{
							pMenu.setParentId((Integer)table.getData(i,"PARENTID"));
						}
						
						if(null != table.getData(i,"ICON"))
						{
							pMenu.setIcon((String)table.getData(i,"ICON"));
						}
						
						if(null != table.getData(i,"DESKTOPICON"))
						{
							pMenu.setDesktopIcon((String)table.getData(i,"DESKTOPICON"));
						}
						
						if(null != table.getData(i,"ORDER"))
						{
							pMenu.setOrder((Integer)table.getData(i,"ORDER"));
						}
						
						for(int j = 0;j < table.rowCount();j++)
						{
							
							if(null != table.getData(j,"PARENTID") && pMenu.getId() == (Integer)table.getData(j,"PARENTID"))
							{
								//子菜单
								MenuInfo cMenu = new MenuInfo();
								if(null != table.getData(j,"ID"))
								{
									cMenu.setId((Integer)table.getData(j,"ID"));
								}
								
								if(null != table.getData(j,"NAME"))
								{
									cMenu.setName((String)table.getData(j,"NAME"));
								}
								
								if(null != table.getData(j,"URL"))
								{
									cMenu.setURL((String)table.getData(j,"URL"));
								}
								
								if(null != table.getData(j,"PARENTID"))
								{
									cMenu.setParentId((Integer)table.getData(j,"PARENTID"));
								}
								
								if(null != table.getData(j,"ICON"))
								{
									cMenu.setIcon((String)table.getData(j,"ICON"));
								}
								
								if(null != table.getData(j,"DESKTOPICON"))
								{
									cMenu.setDesktopIcon((String)table.getData(j,"DESKTOPICON"));
								}
								
								if(null != table.getData(j,"ORDER"))
								{
									cMenu.setOrder((Integer)table.getData(j,"ORDER"));
								}
								
								pMenu.getChildMenus().add(cMenu);
							}
						}
						
						menus.add(pMenu);
					}
				}
			}
			else
			{
				Exception e = new Exception(this.menuDatabase.getLastErrorMessage());
				throw e;
			}
        }
        catch(Exception ex)
        {
        	this.lastErrorMessage = ex.getMessage();
        }
        
        return menus;
    }
	
	/**
	 * 获取指定父id的菜单。
	 * 
	 * @param pId 父菜单id，顶级菜单为-1.
	 * 
	 * @return 菜单列表，出错返回null。
	 */
	public List<MenuInfo> getMenusByParentId(int pId)
	{
		List<MenuInfo> menus = null;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				menus = this.getMenusByParentId(pId,this.menuDatabase);
			}
			else
			{
				Exception e = new Exception("数据库连接失败！" + this.menuDatabase.getLastErrorMessage());
				throw e;
			}
		}
		catch(Exception ex)
		{
			this.lastErrorMessage = ex.getMessage();
		}
		finally
		{
			this.menuDatabase.disconnectDataBase();
		}
		
		return menus;
	}
	
	/**
	 * 获取指定父id的菜单。
	 * 
	 * @param pId 父菜单id，顶级菜单为-1.
	 * @param db 数据库连接。
	 * 
	 * @return 菜单列表，出错返回null。
	 */
	public List<MenuInfo> getMenusByParentId(int pId,YDataBase db)
	{
		List<MenuInfo> menus = null;
		
		try
        {
        	//构建SQL语句
			String sql = "";
			if(YDataBaseType.MSSQL == db.getDatabaseType())
			{
				if(pId == -1)
				{
					sql = "SELECT * FROM SYS_MENUS WHERE PARENTID IS NULL ORDER BY [ORDER] ASC";
				}
				else
				{
					sql = "SELECT * FROM SYS_MENUS WHERE PARENTID = ? ORDER BY [ORDER] ASC";
				}
			}
			else
			{
				Exception e = new Exception("不支持的数据库类型！");
				throw e;
			}
			
			
			
			//执行语句
			YDataTable table = null;
			
			if(pId == -1)
			{
				table = db.executeSqlReturnData(sql);
			}
			else
			{
				//参数
				YSqlParameters ps = new YSqlParameters();
				ps.addParameter(1, pId);
				table = db.executeSqlReturnData(sql, ps);
			}
			
			if(null != table)
			{
				menus = new ArrayList<MenuInfo>();
				
				//构建菜单。
				for(int i = 0;i < table.rowCount();i++)
				{
					MenuInfo pMenu = new MenuInfo();
					if(null != table.getData(i,"ID"))
					{
						pMenu.setId((Integer)table.getData(i,"ID"));
					}
					
					if(null != table.getData(i,"NAME"))
					{
						pMenu.setName((String)table.getData(i,"NAME"));
					}
					
					if(null != table.getData(i,"URL"))
					{
						pMenu.setURL((String)table.getData(i,"URL"));
					}
					
					if(null != table.getData(i,"PARENTID"))
					{
						pMenu.setParentId((Integer)table.getData(i,"PARENTID"));
					}
					
					if(null != table.getData(i,"ICON"))
					{
						pMenu.setIcon((String)table.getData(i,"ICON"));
					}
					
					if(null != table.getData(i,"DESKTOPICON"))
					{
						pMenu.setDesktopIcon((String)table.getData(i,"DESKTOPICON"));
					}
					
					if(null != table.getData(i,"ORDER"))
					{
						pMenu.setOrder((Integer)table.getData(i,"ORDER"));
					}
					
					menus.add(pMenu);
				}
			}
			else
			{
				Exception e = new Exception(this.menuDatabase.getLastErrorMessage());
				throw e;
			}
        }
        catch(Exception ex)
        {
        	this.lastErrorMessage = ex.getMessage();
        }
		
		return menus;
	}
}
