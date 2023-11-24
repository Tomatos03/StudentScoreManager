package Core;

import Util.DatabaseManager;

import javax.swing.*;
import java.sql.*;

public class Login extends JPanel {
	public boolean LoginSystem(String username, String password) {
			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(null, "用户名或密码不能为空", "提示", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			Connection connection = DatabaseManager.getConnection();
			try {
				// 检查表是否存在
				DatabaseMetaData metaData = connection.getMetaData();
				ResultSet resultSet = metaData.getTables(null, null, "account",  new String[] {"TABLE"});
				if (!resultSet.next()) {
					JOptionPane.showMessageDialog(null, "登录失败", "提示", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				String sql = "select * from account where username = ? and password = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					return true;
				} else {
					JOptionPane.showMessageDialog(null, "登录失败", "提示", JOptionPane.ERROR_MESSAGE);
				}
			}catch (SQLException e) {
				System.out.println("发生异常...");
				System.out.println(e.getMessage());
			}
			return false;
		}
}
