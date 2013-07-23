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
				Exception e = new Exception(db.getLastErrorMessage());
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
				Exception e = new Exception(db.getLastErrorMessage());
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
	 * 获取指定的菜单信息。
	 * 
	 * @param id 菜单id。
	 * 
	 * @return 成功返回菜单信息，否则返回null。
	 */
	public MenuInfo getMenu(int id)
	{
		MenuInfo menu = null;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				menu = this.getMenu(id,this.menuDatabase);
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
		
		return menu;
	}
	
	/**
	 * 获取指定的菜单信息。
	 * 
	 * @param id 菜单id。
	 * @param db 使用的数据库连接。
	 * 
	 * @return 成功返回菜单信息，否则返回null。
	 */
	public MenuInfo getMenu(int id,YDataBase db)
	{
		MenuInfo menu = null;
		
		try
        {
        	//构建SQL语句
			String sql = "";
			if(YDataBaseType.MSSQL == db.getDatabaseType())
			{
				sql = "SELECT * FROM SYS_MENUS WHERE ID = ?";
			}
			else
			{
				Exception e = new Exception("不支持的数据库类型！");
				throw e;
			}
			
			
			//参数
			YSqlParameters ps = new YSqlParameters();
			ps.addParameter(1, id);
			
			//执行语句。
			YDataTable table = db.executeSqlReturnData(sql, ps);
			
			if(null != table)
			{
				if(table.rowCount() > 0)
				{
					menu = new MenuInfo();
					
					//构建菜单。
					if(null != table.getData(0,"ID"))
					{
						menu.setId((Integer)table.getData(0,"ID"));
					}
					
					if(null != table.getData(0,"NAME"))
					{
						menu.setName((String)table.getData(0,"NAME"));
					}
					
					if(null != table.getData(0,"URL"))
					{
						menu.setURL((String)table.getData(0,"URL"));
					}
					
					if(null != table.getData(0,"PARENTID"))
					{
						menu.setParentId((Integer)table.getData(0,"PARENTID"));
					}
					
					if(null != table.getData(0,"ICON"))
					{
						menu.setIcon((String)table.getData(0,"ICON"));
					}
					
					if(null != table.getData(0,"DESKTOPICON"))
					{
						menu.setDesktopIcon((String)table.getData(0,"DESKTOPICON"));
					}
					
					if(null != table.getData(0,"ORDER"))
					{
						menu.setOrder((Integer)table.getData(0,"ORDER"));
					}
				}
				else
				{
					Exception e = new Exception("未找到指定数据！");
					throw e;
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
		
		return menu;
	}
	
	/**
	 * 创建菜单。
	 * 
	 * @param menu 菜单信息。
	 * @return 成功返回创建的菜单id，失败返回-1。
	 */
	public int createMenu(MenuInfo menu)
	{
		int retValue = -1;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				retValue = this.createMenu(menu,this.menuDatabase);
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
		
		return retValue;
	}
	
	/**
	 * 创建菜单。
	 * 
	 * @param menu 菜单信息。
	 * @param db 使用的数据库连接。
	 * @return 成功返回创建的菜单id，失败返回-1。
	 */
	public int createMenu(MenuInfo menu,YDataBase db)
	{
		int retValue = -1;
		
		try
        {
			if (menu == null)
            {
				Exception e = new Exception("不能插入空菜单！");
				throw e;
            }
            else if (null == menu.getName() || menu.getName().length() > 20)
            {
                Exception e = new Exception("菜单名称不合法，长度1～20！");
				throw e;
            }
            else if (menu.getURL().length() > 200)
            {
            	Exception e = new Exception("菜单URL不合法，长度1～200！");
				throw e;
            }
            else if (menu.getIcon().length() > 20)
            {
            	Exception e = new Exception("菜单图标不合法！");
				throw e;
            }
            else if (menu.getDesktopIcon().length() > 100)
            {
            	Exception e = new Exception("菜单桌面图标不合法！");
				throw e;
            }
            else
            {
	        	//构建SQL语句
				String sql = "";
				//参数
				YSqlParameters ps = new YSqlParameters();
				
				if(menu.getParentId() == -1)
				{
					//顶级菜单
					if(YDataBaseType.MSSQL == db.getDatabaseType())
					{
						sql = "INSERT INTO SYS_MENUS (NAME,ICON,[ORDER]) VALUES (?,?,?)";
						ps.addParameter(1, menu.getName());
						ps.addParameter(2, menu.getIcon());
						ps.addParameter(3, menu.getOrder());
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
						sql = "INSERT INTO SYS_MENUS (NAME,URL,PARENTID,ICON,DESKTOPICON,[ORDER]) VALUES (?,?,?,?,?,?)";
						ps.addParameter(1, menu.getName());
						ps.addParameter(2, menu.getURL());
						ps.addParameter(3, menu.getParentId());
						ps.addParameter(4, menu.getIcon());
						ps.addParameter(5, menu.getDesktopIcon());
						ps.addParameter(6, menu.getOrder());
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
	 * 修改菜单。
	 * 
	 * @param menu 要修改的菜单。
	 * 
	 * @return 成功返回true，否则返回false。
	 */
	public boolean changeMenu(MenuInfo menu)
	{
		boolean retValue = false;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				retValue = this.changeMenu(menu,this.menuDatabase);
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
		
		return retValue;
	}
	
	/**
	 * 修改菜单。
	 * 
	 * @param menu 要修改的菜单。
	 * @param db 使用的数据库连接。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean changeMenu(MenuInfo menu,YDataBase db)
	{
		boolean retValue = false;
		
		try
        {
			if (menu == null)
            {
				Exception e = new Exception("不能插入空菜单！");
				throw e;
            }
			else if(menu.getId() < 0)
			{
				Exception e = new Exception("菜单id不合法，不能小于0！");
				throw e;
			}
            else if (null == menu.getName() || menu.getName().length() > 20)
            {
                Exception e = new Exception("菜单名称不合法，长度1～20！");
				throw e;
            }
            else if (menu.getURL().length() > 200)
            {
            	Exception e = new Exception("菜单URL不合法，长度1～200！");
				throw e;
            }
            else if (menu.getIcon().length() > 20)
            {
            	Exception e = new Exception("菜单图标不合法！");
				throw e;
            }
            else if (menu.getDesktopIcon().length() > 100)
            {
            	Exception e = new Exception("菜单桌面图标不合法！");
				throw e;
            }
            else
            {
	        	//构建SQL语句
				String sql = "";
				//参数
				YSqlParameters ps = new YSqlParameters();
				
				if(menu.getParentId() == -1)
				{
					//顶级菜单
					if(YDataBaseType.MSSQL == db.getDatabaseType())
					{
						sql = "UPDATE SYS_MENUS SET NAME = ?,ICON = ?,[ORDER] = ? WHERE ID = ?";
						ps.addParameter(1, menu.getName());
						ps.addParameter(2, menu.getIcon());
						ps.addParameter(3, menu.getOrder());
						ps.addParameter(4, menu.getId());
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
						sql = "UPDATE SYS_MENUS SET NAME = ?,URL = ?,ICON = ?,DESKTOPICON = ?,[ORDER] = ? WHERE ID = ?";
						ps.addParameter(1, menu.getName());
						ps.addParameter(2, menu.getURL());
						ps.addParameter(3, menu.getIcon());
						ps.addParameter(4, menu.getDesktopIcon());
						ps.addParameter(5, menu.getOrder());
						ps.addParameter(6, menu.getId());
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
					retValue = true;
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
	 * 删除菜单。
	 * 
	 * @param ids 要删除的菜单id。
	 * 
	 * @return 成功返回true，否则返回false。
	 */
	public boolean deleteMenus(int[] ids)
	{
		boolean retValue = false;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				if(this.menuDatabase.beginTransaction())
				{
					retValue = this.deleteMenus(ids,this.menuDatabase);
				}
				else
				{
					Exception e = new Exception("开启事务失败！" + this.menuDatabase.getLastErrorMessage());
					throw e;
				}
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
			//处理事务
			if(retValue)
			{
				this.menuDatabase.commitTransaction();
			}
			else
			{
				this.menuDatabase.rollbackTransaction();
			}
			
			this.menuDatabase.disconnectDataBase();
		}
		
		return retValue;
	}
	
	/**
	 * 删除菜单。
	 * 
	 * @param ids 要删除的菜单id。
	 * @param db 使用的数据库连接。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean deleteMenus(int[] ids,YDataBase db)
	{
		boolean retValue = false;
		
		try
        {
			if (null == ids || ids.length == 0)
            {
				Exception e = new Exception("未指定要删除的菜单！");
				throw e;
            }
            else
            {
            	int i = 0;
            	for(;i < ids.length;i++)
            	{
            		if(!this.deleteMenu(ids[i], db))
            		{
            			Exception e = new Exception(db.getLastErrorMessage());
        				throw e;
            		}
            	}
            	
            	if(i == ids.length)
            	{
            		retValue = true;
            	}
            	else
            	{
            		Exception e = new Exception("删除菜单出错！");
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
	 * 删除指定的菜单。
	 * 
	 * @param id 菜单id。
	 * @param db 使用的数据库连接。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean deleteMenu(int id,YDataBase db)
	{
		boolean retValue = false;
		
		try
        {
			if (id < 0)
            {
				Exception e = new Exception("未指定要删除的菜单id！");
				throw e;
            }
            else
            {
            	//获取子菜单。
            	List<MenuInfo> cMenus = this.getMenusByParentId(id, db);
            	if(null != cMenus)
            	{
            		for(int i = 0;i < cMenus.size();i++)
            		{
            			//删除子菜单。
            			if(!this.deleteMenu(cMenus.get(i).getId(), db))
            			{
            				Exception e = new Exception(db.getLastErrorMessage());
                    		throw e;
            			}
            		}
            		
            		//删除关联页面
            		if(this.deletePagesByMenuId(id, db))
            		{
            			//删除菜单。
	    				String sql = "";
	    				YSqlParameters ps = new YSqlParameters();
	    				ps.addParameter(1, id);
	    				
	    				if(YDataBaseType.MSSQL == db.getDatabaseType())
	    				{
	    					sql = "DELETE FROM SYS_MENUS WHERE ID = ?";
	    				}
	    				else
	    				{
	    					Exception e = new Exception("不支持的数据库类型！");
	    					throw e;
	    				}
	    				
	    				//执行语句。
	    				int rowCount = db.executeSqlWithOutData(sql,ps);
	    				if(rowCount >= 0)
	    				{
	    					retValue = true;
	    				}
	    				else
	    				{
	    					Exception e = new Exception(db.getLastErrorMessage());
	    					throw e;
	    				}
            		}
            		else
            		{
            			Exception e = new Exception("删除关联页面失败！" + db.getLastErrorMessage());
    					throw e;
            		}
            	}
            	else
            	{
            		Exception e = new Exception("获取子菜单出错！" + db.getLastErrorMessage());
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
	 * 创建关联页面。
	 * 
	 * @param page 页面信息。
	 * 
	 * @return 成功返回页面id，否则返回-1.
	 */
	public int createPage(PageInfo page)
	{
		int retValue = -1;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				retValue = this.createPage(page,this.menuDatabase);
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
		
		return retValue;
	}
	
	/**
	 * 创建关联页面。
	 * 
	 * @param page 页面信息。
	 * @param db 使用的数据库连接。
	 * @return 成功返回页面id，否则返回-1.
	 */
	public int createPage(PageInfo page,YDataBase db)
	{
		int retValue = -1;
		
		try
        {
			if (page == null)
            {
				Exception e = new Exception("不能插入空页面！");
				throw e;
            }
            else if (null == page.getPath() || page.getPath().length() > 500)
            {
                Exception e = new Exception("页面路径长度应该在1～500！");
				throw e;
            }
            else if (page.getDetail().length() > 200)
            {
            	Exception e = new Exception("页面说明不合法，长度1～200！");
				throw e;
            }
            else
            {
	        	//构建SQL语句
				String sql = "";
				//参数
				YSqlParameters ps = new YSqlParameters();
				
				//顶级菜单
				if(YDataBaseType.MSSQL == db.getDatabaseType())
				{
					sql = "INSERT INTO SYS_MENU_PAGE (DETAIL,PATH,MENUID) VALUES (?,?,?)";
					ps.addParameter(1, page.getDetail());
					ps.addParameter(2, page.getPath());
					ps.addParameter(3, page.getMenuId());
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
	 * 获取指定菜单的关联页面。
	 * 
	 * @param menuId 菜单id。
	 *  
	 * @return 成功返回页面列表，否则返回null。
	 */
	public List<PageInfo> getPagesByMenuId(int menuId)
	{
		List<PageInfo> pages = null;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				pages = this.getPagesByMenuId(menuId,this.menuDatabase);
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
		
		return pages;
	}
	
	/**
	 * 获取指定菜单的关联页面。
	 * 
	 * @param menuId 菜单id。 
	 * @param db 使用的数据库连接。
	 * @return 成功返回页面列表，否则返回null。
	 */
	public List<PageInfo> getPagesByMenuId(int menuId,YDataBase db)
	{
		List<PageInfo> pages = null;
		
		try
        {
        	//构建SQL语句
			String sql = "";
			if(YDataBaseType.MSSQL == db.getDatabaseType())
			{
				sql = "SELECT * FROM SYS_MENU_PAGE WHERE MENUID = ?";
			}
			else
			{
				Exception e = new Exception("不支持的数据库类型！");
				throw e;
			}
			
			//参数
			YSqlParameters ps = new YSqlParameters();
			ps.addParameter(1, menuId);
			
			//执行SQL语句
			YDataTable table = db.executeSqlReturnData(sql, ps);
			
			if(null != table)
			{
				pages = new ArrayList<PageInfo>();
				
				//构建菜单。
				for(int i = 0;i < table.rowCount();i++)
				{
					//顶级菜单
					PageInfo page = new PageInfo();
					if(null != table.getData(i,"ID"))
					{
						page.setId((Integer)table.getData(i,"ID"));
					}
					
					if(null != table.getData(i,"PATH"))
					{
						page.setPath((String)table.getData(i,"PATH"));
					}
					
					if(null != table.getData(i,"DETAIL"))
					{
						page.setDetail((String)table.getData(i,"DETAIL"));
					}
					
					if(null != table.getData(i,"MENUID"))
					{
						page.setMenuId((Integer)table.getData(i,"MENUID"));
					}
						
					pages.add(page);
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
		
		return pages;
	}
	
	/**
	 * 获取关联页面信息。
	 * 
	 * @param id 页面id。
	 * @return 成功返回页面信息，否则返回null。
	 */
	public PageInfo getPage(int id)
	{
		PageInfo page = null;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				page = this.getPage(id,this.menuDatabase);
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
		
		return page;
	}
	
	/**
	 * 获取关联页面信息。
	 * 
	 * @param id 页面id。
	 * @param db 使用的数据库连接。
	 * @return 成功返回页面信息，否则返回null。
	 */
	public PageInfo getPage(int id,YDataBase db)
	{
		PageInfo page = null;
		
		try
        {
        	//构建SQL语句
			String sql = "";
			if(YDataBaseType.MSSQL == db.getDatabaseType())
			{
				sql = "SELECT * FROM SYS_MENU_PAGE WHERE id = ?";
			}
			else
			{
				Exception e = new Exception("不支持的数据库类型！");
				throw e;
			}
			
			
			//参数
			YSqlParameters ps = new YSqlParameters();
			ps.addParameter(1, id);
			
			//执行语句。
			YDataTable table = db.executeSqlReturnData(sql, ps);
			
			if(null != table)
			{
				if(table.rowCount() > 0)
				{
					page = new PageInfo();
					
					if(null != table.getData(0,"ID"))
					{
						page.setId((Integer)table.getData(0,"ID"));
					}
					
					if(null != table.getData(0,"PATH"))
					{
						page.setPath((String)table.getData(0,"PATH"));
					}
					
					if(null != table.getData(0,"DETAIL"))
					{
						page.setDetail((String)table.getData(0,"DETAIL"));
					}
					
					if(null != table.getData(0,"MENUID"))
					{
						page.setMenuId((Integer)table.getData(0,"MENUID"));
					}
				}
				else
				{
					Exception e = new Exception("未找到指定数据！");
					throw e;
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
		
		return page;
	}
	
	/**
	 * 修改页面信息。
	 * 
	 * @param page 页面信息。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean changePage(PageInfo page)
	{
		boolean retValue = false;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				retValue = this.changePage(page,this.menuDatabase);
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
		
		return retValue;
	}
	
	/**
	 * 修改页面信息。
	 * 
	 * @param page 页面信息。
	 * @param db 使用的数据库连接。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean changePage(PageInfo page,YDataBase db)
	{
		boolean retValue = false;
		
		try
        {
			if (page == null)
            {
				Exception e = new Exception("不能插入空页面！");
				throw e;
            }
			else if(page.getId() < 0)
			{
				Exception e = new Exception("id不合法，不能小于0！");
				throw e;
			}
			else if (null == page.getPath() || page.getPath().length() > 500)
            {
                Exception e = new Exception("页面路径长度应该在1～500！");
				throw e;
            }
            else if (page.getDetail().length() > 200)
            {
            	Exception e = new Exception("页面说明不合法，长度1～200！");
				throw e;
            }
            else
            {
	        	//构建SQL语句
				String sql = "";
				//参数
				YSqlParameters ps = new YSqlParameters();
				ps.addParameter(1, page.getDetail());
				ps.addParameter(2, page.getPath());
				ps.addParameter(3, page.getId());
				
				if(YDataBaseType.MSSQL == db.getDatabaseType())
				{
					sql = "UPDATE SYS_MENU_PAGE SET DETAIL = ?,PATH = ? WHERE ID = ?";
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
					retValue = true;
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
	 * 删除页面。
	 * 
	 * @param ids 页面id数组。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean deletePages(int[] ids)
	{
		boolean retValue = false;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				retValue = this.deletePages(ids,this.menuDatabase);
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
		
		return retValue;
	}
	
	/**
	 * 删除页面。
	 * 
	 * @param ids 页面id数组。
	 * @param db 使用的数据库连接。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean deletePages(int[] ids,YDataBase db)
	{
		boolean retValue = false;
		
		try
        {
			if (ids == null || ids.length == 0)
            {
				Exception e = new Exception("未指定要删除的页面！");
				throw e;
            }
            else
            {
	        	//构建SQL语句
				String sql = "";
				
				if(YDataBaseType.MSSQL == db.getDatabaseType())
				{
					sql = "DELETE FROM SYS_MENU_PAGE WHERE ID IN (" + String.valueOf(ids[0]);
					for(int i = 1;i < ids.length;i++)
					{
						sql += "," + String.valueOf(ids[i]);
					}
					sql += ")";
				}
				else
				{
					Exception e = new Exception("不支持的数据库类型！");
					throw e;
				}
				
				//执行语句。
				int rowCount = db.executeSqlWithOutData(sql);
				if(rowCount >= 0)
				{
					retValue = true;
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
	 * 删除页面。
	 * 
	 * @param id 菜单id。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean deletePagesByMenuId(int id)
	{
		boolean retValue = false;
		
		try
		{
			if(this.menuDatabase.connectDataBase())
			{
				retValue = this.deletePagesByMenuId(id,this.menuDatabase);
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
		
		return retValue;
	}
	
	/**
	 * 删除页面。
	 * 
	 * @param id 菜单id。
	 * @param db 使用的数据库连接。
	 * @return 成功返回true，否则返回false。
	 */
	public boolean deletePagesByMenuId(int id,YDataBase db)
	{
		boolean retValue = false;
		
		try
        {
			if (id < 0)
            {
				Exception e = new Exception("未指定要删除的页面！");
				throw e;
            }
            else
            {
	        	//构建SQL语句
				String sql = "";
				YSqlParameters ps = new YSqlParameters();
				ps.addParameter(1, id);
				
				if(YDataBaseType.MSSQL == db.getDatabaseType())
				{
					sql = "DELETE FROM SYS_MENU_PAGE WHERE MENUID = ?";
				}
				else
				{
					Exception e = new Exception("不支持的数据库类型！");
					throw e;
				}
				
				//执行语句。
				int rowCount = db.executeSqlWithOutData(sql,ps);
				if(rowCount >= 0)
				{
					retValue = true;
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
}
