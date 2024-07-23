package miniProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnManager {
	private static Instance instance = null;
	private static Connection con = null;
	private static Scanner sc = null;

	// 접근 불가능한 기본생성자
	private ConnManager() {
		// 접근 불가능한 코드
	};

	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/** Connection 객체를 반환합니다. */
	public static Connection getConnection() {
		if (con == null) {
			try {
				String user = "LMS";
				String pwd = "1111";
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				con = DriverManager.getConnection(url, user, pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return con;
	}

	/** Scanner 객체를 반환합니다. */
	public static Scanner getScanner() {
		if (sc == null) {
			sc = new Scanner(System.in);
		}

		return sc;
	}

	public static Instance getInstance() {
		if (instance == null) {
			instance = new Instance();
		}
		return instance;
	}


	/** Connection 객체를 닫아줍니다. */
	public static void CloseConnection() {
		try {
			sc.close();
			if (con != null) {
				if (!con.isClosed()) {
					con.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}