package xiao.kang.model;

import java.util.List;

public class Table {
	private String tableName;
	private String tableNameCode;
	private List<TableColumn> TableColumns;

	public List<TableColumn> getTableColumns() {
		return TableColumns;
	}

	public void setTableColumns(List<TableColumn> tableColumns) {
		TableColumns = tableColumns;
	}

	public String getTableNameCode() {
		return tableNameCode;
	}

	public void setTableNameCode(String tableNameCode) {
		this.tableNameCode = tableNameCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
