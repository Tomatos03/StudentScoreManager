package UI;

import Core.Register;
import Util.DatabaseManager;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterUI extends JDialog {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	Register register = new Register();
	public RegisterUI() {
		// 设置窗口标题
		// 设置窗口标题
		setTitle("注册窗口");
		setModal(true);
		setLocationRelativeTo(null);
		setLayout(null);
		setSize(600, 400);
		setResizable(false);
		register.setFocusable(true);

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

		// 创建确认密码标签和密码框
		JLabel confirmPasswordLabel = new JLabel("确认密码:");
		confirmPasswordField = new JPasswordField(20);
		confirmPasswordLabel.setBounds(120, 200, 100, 40);
		confirmPasswordField.setBounds(220, 200, 200, 40);

		// 创建注册按钮
		JButton registerButton = new JButton("注册");
		registerButton.addActionListener(confirmRegisterAction);
		registerButton.setBounds(120, 250, 140, 40);

		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(cancelRegisterAction);
		cancelButton.setBounds(270, 250, 140, 40);

		// 创建面板并添加组件
		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(confirmPasswordLabel);
		add(confirmPasswordField);
		add(registerButton);
		add(cancelButton);

		setVisible(true);
	}

	ActionListener confirmRegisterAction = e -> {
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(confirmPasswordField.getPassword());
		boolean isRegister = register.registerUser(DatabaseManager.STUDENT, username, password, confirmPassword);
		if (isRegister) {
			dispose();
		}
	};
	ActionListener cancelRegisterAction = e -> {
		dispose();
	};
}
