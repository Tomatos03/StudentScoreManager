package Core;

import UI.ManagerUI;
import Util.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class Manager extends JPanel {


	public void userList(DefaultTableModel tableModel) {
		String[] columnNames = getColumnNames("account");
		String sql = "SELECT * FROM  `account` ";
		String[][] rowData = getRowData(sql, null);
		tableModel.setDataVector(rowData, columnNames);
	}

	public void queryScore(DefaultTableModel tableModel, String username) {
		String[] columnNames = getColumnNames("score");
		String sql = "SELECT * FROM  score " + 
						 "WHERE 账号 = ?;";
		String[][] rowData = getRowData(sql, username);
		tableModel.setDataVector(rowData, columnNames);
	}

	private String[][] getRowData(String sql, String username) {
		try {
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			if (username != null) {
				preparedStatement.setString(1, username);
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			int columnCount = resultSet.getMetaData().getColumnCount();
			ArrayList<ArrayList<String>> list = new ArrayList<>();
			while (resultSet.next()) {
				ArrayList<String> temp = new ArrayList<>();
				for (int i = 0; i < columnCount; i++) {
					temp.add(resultSet.getString(i + 1));
				}
				list.add(new ArrayList<>(temp));
			}
			String[][] result = new String[list.size()][];
			int i = 0;
			for (ArrayList<String> strings : list) {
				result[i++] = strings.toArray(new String[0]);
			}
			return result;
		} catch (Exception e){
			System.out.println("Manager -> getRowData()发生错误");
			System.out.println(e.getMessage());
		}
		return null;
	}

	private String[] getColumnNames(String table_name) {
		try {
			Connection connection = DatabaseManager.getConnection();
			ResultSet resultSet = connection.getMetaData().getColumns(null, null, table_name, null);
			ArrayList<String> list = new ArrayList<>();
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				list.add(columnName);
			}
			return list.toArray(new String[0]);
		} catch (Exception e) {
			System.out.println("Manager -> getColumnNames()发生错误");
			System.out.println(e.getMessage());
		}
		return null;
	}


	public void exportScore() {
	}

	public void importScore() {
	}
}
