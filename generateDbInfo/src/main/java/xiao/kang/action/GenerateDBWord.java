package xiao.kang.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import xiao.kang.model.Table;
import xiao.kang.model.TableColumn;

/**
 * 
 * @author xgg
 * @date 2017年6月1日
 */
public class GenerateDBWord {
	private static BaseDao baseDao;

	public GenerateDBWord() {
		baseDao = new BaseDao();
	}

	/**
	 * 查询数据库所有的表
	 * 
	 * @return
	 */
	public List<String> queryTableAll() {
		List<String> list = new ArrayList<String>();
		String sql = "show tables";
		baseDao.executeQuery(sql, null);
		try {
			while (baseDao.rs.next()) {
				list.add(baseDao.rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取数据库表信息
	 * 
	 * @param list
	 * @return
	 */
	public List<Table> queryTableInfo() {
		List<String> list = queryTableAll();
		List<Table> tables = new ArrayList<Table>();
		for (int i = 0; i < list.size(); i++) {
			Table table = new Table();
			List<TableColumn> TableColumns = new ArrayList<TableColumn>();
			String sql = "select COLUMN_NAME,column_comment,COLUMN_TYPE from INFORMATION_SCHEMA.Columns where table_name='"
					+ list.get(i) + "' and table_schema='dtomnew'";
			baseDao.executeQuery(sql, null);
			try {
				while (baseDao.rs.next()) {
					TableColumn tableColumn = new TableColumn();
					tableColumn.setColumnName(baseDao.rs.getString(1));
					tableColumn.setColumnComment(baseDao.rs.getString(2));
					tableColumn.setColumnType(baseDao.rs.getString(3));
					TableColumns.add(tableColumn);
				}

				String sql2 = "SHOW CREATE TABLE  " + list.get(i);
				baseDao.executeQuery(sql2, null);
				String createTable = "";
				while (baseDao.rs.next()) {
					createTable = baseDao.rs.getString(2);
				}
				if (createTable.indexOf("COMMENT='") > 0) {
					table.setTableName(
							createTable.substring(createTable.indexOf("COMMENT='") + 9, createTable.length() - 1));
				} else {
					table.setTableName("");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			table.setTableNameCode(list.get(i));
			table.setTableColumns(TableColumns);
			tables.add(table);
		}
		return tables;

	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("--------------------Mysql提取所有表基本信息小工具（大大怪v1）--------------------");
		System.out.println("1、输入你的连接符：");
		String url = input.nextLine();
		System.out.println("2:、输入你的数据库用户名：");
		String name = input.nextLine();
		System.out.println("3、输入你的用户对应的密码：");
		String pwd = input.nextLine();
		System.out.println("4、输入你的生成路径：");
		String address = input.nextLine();
		GenerateDBWord dbWord = new GenerateDBWord();
		baseDao.URL = url;
		baseDao.USERNAME = name;
		baseDao.PWD = pwd;
		List<Table> queryTableInfo = dbWord.queryTableInfo();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < queryTableInfo.size(); i++) {
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\t" + queryTableInfo.get(i).getTableNameCode()
					+ "\t" + queryTableInfo.get(i).getTableName() + "\t<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
			List<TableColumn> tableColumns = queryTableInfo.get(i).getTableColumns();
			buffer.append("字段名\t字段类型\t字段注释\n");
			for (int j = 0; j < tableColumns.size(); j++) {
				buffer.append(tableColumns.get(j).getColumnName() + "\t" + tableColumns.get(j).getColumnType() + "\t"
						+ tableColumns.get(j).getColumnComment() + "\n");
			}
			buffer.append(
					"\n--------------------------------------------------------------------------------------------------------------------------------\n");

		}
		System.out.println("生成中请稍等.......");
		dbWord.contentToTxt(address, buffer.toString());
		System.out.println("生成成功~请查看！");
	}

	public void contentToTxt(String filePath, String content) {
		String str = new String(); // 原有txt内容
		String s1 = new String();// 内容更新
		try {
			File f = new File(filePath);
			if (f.exists()) {
			} else {
				f.createNewFile();// 不存在则创建
			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			System.out.println(s1);
			input.close();
			s1 += content;

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
