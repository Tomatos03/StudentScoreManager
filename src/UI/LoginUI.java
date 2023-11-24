package UI;
import Core.Login;
import Util.DatabaseManager;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUI extends JFrame {
	private JTextField usernameField;
	private JPasswordField passwordField;
	Login login = new Login();
	public LoginUI() {
		// 设置窗口标题
		setTitle("登录界面");
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		login.setFocusable(true);

		// 创建用户名标签和文本框
		JLabel usernameLabel = new JLabel("用户名:");
		usernameField = new JTextField(20);
		usernameLabel.setBounds(120, 100, 100, 40);
		usernameField.setBounds(220, 100, 200, 40);


		// 创建密码标签和密码框
		JLabel passwordLabel = new JLabel("密码:");
		passwordField = new JPasswordField(20);
		passwordLabel.setBounds(120, 150, 100, 40);
		passwordField.setBounds(220, 150, 200, 40);

		// 创建登录按钮
		JButton loginButton = new JButton("登录");
		loginButton.addActionListener(loginAction);
		loginButton.setBounds(120, 200, 140, 40);

		JButton registerButton = new JButton("注册");
		registerButton.addActionListener(registerAction);
		registerButton.setBounds(270, 200, 140, 40);

		// 创建面板并添加组件
		add(login);
		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(registerButton);
		add(loginButton);

		setVisible(true);
	}

	ActionListener loginAction = e -> {
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		boolean isLogin = login.LoginSystem(username, password);
		if (isLogin) {
			dispose();
			Connection connection = DatabaseManager.getConnection();
			String sql = "SELECT `type` FROM `account` " +
					   		  "WHERE username = ?;";
			int type = 0;
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, username);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					type = resultSet.getInt("type");
				} else {
					System.out.println("LoginUI查询异常");
				}
			} catch (SQLException ex) {
				System.out.println("LoginUI类发生异常");
				System.out.println(ex.getMessage());
			}
			ManagerUI managerUI = new ManagerUI(type, username);
		}
	};

	ActionListener registerAction = e -> {
		RegisterUI registerUI = new RegisterUI();
	};
}
