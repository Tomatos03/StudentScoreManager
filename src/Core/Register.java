package Core;

import Util.DatabaseManager;

import javax.swing.*;
import java.sql.*;

public class Register extends JPanel {

	private boolean checkInput(int type, String username, String password, String confirmPassword) {
		if (password.isEmpty() || username.isEmpty() || confirmPassword.isEmpty()) {
			JOptionPane.showMessageDialog(null, "存在未填写的信息", "提示", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// 密码一致性
		if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(null, "两次的密码输入不一致", "提示", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		int passwordLength = password.length();
		// 密码长度合理性检测
		if (passwordLength > 20 || passwordLength < 8) {
			JOptionPane.showMessageDialog(null, "密码长度必须大于等于8小于等于20", "提示", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// 用户名长度合理性检测
		int nameLength = username.length();
		if (nameLength > 20 || nameLength < 8) {
			JOptionPane.showMessageDialog(null, "用户名长度必须大于等于8小于等于20", "提示", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Connection connection = DatabaseManager.getConnection();
		try {
			// 检查表是否存在
			String sql = "CREATE TABLE `account` (" +
					"`type` TINYINT NOT NULL," +
					"`username` VARCHAR(20) NOT NULL PRIMARY KEY," +
					"`password` VARCHAR(20) NOT NULL" +
					");";
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, "account",  new String[] {"TABLE"});
			if (!resultSet.next()) {
				connection.createStatement().execute(sql);
			}

			// 用户唯一性检测
			sql = "select * from account where username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				JOptionPane.showMessageDialog(null, "该用户已存在", "提示", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (SQLException e) {
			System.out.println("发生异常...");
			System.out.println(e.getMessage());
		}
		return true;
	}

	public boolean registerUser(int type,String username, String password, String confirmPassword) {
		if (!checkInput(type, username, password, confirmPassword)){
			return false;
		}
		Connection connection = DatabaseManager.getConnection();
		try{
			String sql = "INSERT INTO `account` VALUES (?,?,?);";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, type);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, password);
			if (preparedStatement.executeUpdate() == 0) {
				System.out.println("注册失败");
				return false;
			}
			JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}catch (SQLException e) {
			System.out.println("发生异常...");
			System.out.println(e.getMessage());
		}
		return false;
	}
}
