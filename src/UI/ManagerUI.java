package UI;

import Core.Manager;
import Util.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManagerUI extends JFrame{
	public static void main(String[] args) {
		ManagerUI managerUI = new ManagerUI(1,  "root1122");
	}
	Manager manager = new Manager();
	private DefaultTableModel tableModel;
	private String username;
	private final int type;
	public ManagerUI(int type, String username) {
		this.username = username;
		this.type = type;
		add(manager);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setTitle("学生成绩管理系统");
		setResizable(false);
		initTopMenu();
		initDataTable();
		setVisible(true);
	}

	public String getUsername() {
		return this.username;
	}

	private void initDataTable() {
		String sql = "CREATE TABLE score(" +
				"账号 VARCHAR(10)," +
				"姓名 VARCHAR(10)," +
				"班级 VARCHAR(10)," +
				"课程名称 VARCHAR(20)," +
				"课程分数 VARCHAR(20)," +
				"FOREIGN KEY(账号) REFERENCES `account`(username)" +
				");";
		Connection connection = DatabaseManager.getConnection();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, "score",  new String[]{"TABLE"});
			if (!resultSet.next()) {
				connection.createStatement().execute(sql);
			}
		} catch (SQLException e) {
			System.out.println("ManagerUI异常发生");
			System.out.println(e.getMessage());
		}

		// DefaultTableModel 主要提供操作表格方面的方法
		tableModel = new DefaultTableModel();
		manager.queryScore(tableModel, username);
		// JTable 主要提供表格的显示方面的方法
		JTable table = new JTable(tableModel);
		table.setEnabled(false);
		// 将表格加入滑动窗口中
		JScrollPane scrollPane = new JScrollPane(table);
		// 将滑动窗口添加到窗口中
		getContentPane().add(scrollPane);
	}

	private void initTopMenu() {
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		// 成绩管理
		JMenu scoreManagerMenu = new JMenu("成绩管理");

		JMenuItem queryScoreItem = scoreManagerMenu.add("查询成绩");
		queryScoreItem.addActionListener(QueryScoreAction);

		JMenuItem exportScoreItem = scoreManagerMenu.add("导出成绩");
		exportScoreItem.addActionListener(ExportScoreAction);

		JMenuItem importScoreItem = scoreManagerMenu.add("导入成绩");
		importScoreItem.addActionListener(importScoreAction);
		menu.add(scoreManagerMenu);

		// 账号管理
		JMenu userManagerMenu = new JMenu("用户管理");

		JMenuItem userListItem = userManagerMenu.add("用户列表");

		userListItem.addActionListener(UserListAction);

		JMenuItem exportUserItem = userManagerMenu.add("导出用户");
		exportUserItem.addActionListener(ExportScoreAction);

		JMenuItem importUserItem = userManagerMenu.add("导入用户");
		importUserItem.addActionListener(importScoreAction);
		menu.add(userManagerMenu);

		// 个人信息设置
		JMenu userInformationMenu = new JMenu("个人信息");

//		JMenuItem userListItem = userManagerMenu.add("用户列表");
//		userListItem.addActionListener(QueryScoreAction);
//
//		JMenuItem exportUserItem = userManagerMenu.add("导出用户");
//		exportUserItem.addActionListener(ExportScoreAction);
//
//		JMenuItem importUserItem = userManagerMenu.add("导入用户");
//		importUserItem.addActionListener(importScoreAction);
		menu.add(userInformationMenu);

	}

	ActionListener UserListAction = e -> {
		manager.userList(tableModel);
	};
	ActionListener QueryScoreAction = e -> {
		manager.queryScore(tableModel, username);
	};
	ActionListener ExportScoreAction = e -> {
		manager.exportScore();
	};
	ActionListener importScoreAction = e -> {
		manager.importScore();
	};
}
