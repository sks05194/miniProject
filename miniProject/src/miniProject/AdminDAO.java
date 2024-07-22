package miniProject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDAO {
	// 관리자 역할변경
	public void changeArole(AdminVO adminVO) {
		String id = null;
		int arole = 0;

		System.out.println("변경할 아이디 입력 >>");
		id = ConnManager.getScanner().next();

		System.out.println("변경할 역할 입력(1. SUPER 2.SUB)");
		if (ConnManager.getScanner().hasNextInt()) {
			arole = ConnManager.getScanner().nextInt();
		} else {
			System.out.println("숫자만 입력해주세요.");
			ConnManager.getScanner().next();
		}

//			System.out.println(adminVO.getArole().toString());

		if (adminVO.getArole().equals("SUPER")) {
			changeArole(id, arole);
		} else {
			System.out.println("변경 실패");
			System.out.println("상태 변경은 SUPER 관리자만 변경하실 수 있습니다.");
		}

	}

	// 관리자 역할변경 쿼리관련
	private void changeArole(String id, int arole) {
		String sql = "update admin set arole = ? where aid = ?";
		PreparedStatement ps = null;
//		System.out.println("SQL문 실행");

		try {
			ps = ConnManager.getConnection().prepareStatement(sql);
			ps.setString(1, arole == 1 ? "SUPER" : "SUB");
//			System.out.println("aps의 값은 : " + aps);
//			System.out.println("id의 값id);
			ps.setString(2, id);
			int updateCount = ps.executeUpdate();

			if (updateCount > 0) {
				System.out.println("변경 성공");
			} else {
				System.out.println("변경 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				ConnManager.CloseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 관리자 상태변경
	public void changeAps(AdminVO adminVO) {
		String id = null;
		int aps = 0;

		System.out.println("상태 변경 메뉴");
		System.out.println("변경할 아이디 입력 >>");
		id = ConnManager.getScanner().next();

		System.out.println("변경할 상태 입력(0. 사용불가 또는 대기 1. 사용가능)");
		if (ConnManager.getScanner().hasNextInt()) {
			aps = ConnManager.getScanner().nextInt();

		} else {
			System.out.println("숫자만 입력할 수 있습니다.");
			ConnManager.getScanner().next();
		}

		if (adminVO.getArole().equals("SUPER")) {
			changeAps(id, aps);
		} else {
			System.out.println("변경 실패");
			System.out.println("상태 변경은 SUPER 관리자만 변경하실 수 있습니다.");
		}

	}

	// 관리자 상태변경 쿼리 관련
	private void changeAps(String id, int aps) {
		String sql = "update admin set aps = ? where aid = ?";
		PreparedStatement ps = null;

		try {
			ps = ConnManager.getConnection().prepareStatement(sql);
			ps.setString(1, aps == 1 ? "Y" : "N");
			ps.setString(2, id);
			int updateCount = ps.executeUpdate();

			if (updateCount > 0) {
				System.out.println("상태 변경 성공");
			} else {
				System.out.println("상태 변경 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				ConnManager.CloseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 목록 출력
	public void showList() {
		ConnManager.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "Select * from student";

		try {

			pst = ConnManager.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			System.out.println("\t 학번 \t학생이름 \t 학생아이디 \t 학생 입학일자 \t 가입여부");

			while (rs.next()) {
				System.out.println("\t" + rs.getInt("sno") + "\t" + rs.getString("snm") + "\t" + rs.getString("sid")
						+ "\t" + rs.getDate("sdate") + "\t" + rs.getString("slms"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnManager.CloseConnection();

		}

	}

	// 등록 메소드
	public void regirster() {
		StudentVO st = getStudent();

		PreparedStatement pst = null;

		try {
			String sql = "Insert into student (sno, sdate, snm) values (?, ?, ?)";

			pst = ConnManager.getConnection().prepareStatement(sql);

			pst.setInt(1, st.getSno());
			pst.setString(2, st.getSdate());
			pst.setString(3, st.getSnm());

			int count = pst.executeUpdate();

			if (count > 0) {
				System.out.println("학생 등록 성공");
			} else {
				System.out.println("학생 등록 실패 ");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ConnManager.CloseConnection();
		}

	}

	// 등록 메소드 학생 전체 목록 출력
	public static StudentVO getStudent() {
		System.out.print("학번을 입력해주세요 예 : 202431012");
		int sno = ConnManager.getScanner().nextInt();

		System.out.print("이름을 입력해주세요:");
		String snm = ConnManager.getScanner().next();

		System.out.print("입학일자(에: 20240302)");
		String sdate = ConnManager.getScanner().next();

		StudentVO st = new StudentVO(sno, snm, null, null, sdate, false);
		return st;

	}

	// 로그인
	public AdminVO login(String aid, String apwd) {
		AdminVO avo = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			ConnManager.getConnection();
			st = ConnManager.getConnection().createStatement();
			String sql = "select * from admin where aid = '" + aid + "' AND apwd = '" + apwd + "'";
			rs = st.executeQuery(sql);

			if (rs.next())
				avo = new AdminVO(
						rs.getString("aid"),
						rs.getString("apwd"),
						rs.getString("anm"),
						rs.getString("arole"),
						rs.getString("aps").equals("O"));

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				rs.close();

				st.close();
			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		}

		return avo;
	}

	// 관리자 가입 메소드
	public int insertAmMember(String aid, String apwd, String anm) {
		int cnt = 0;

		PreparedStatement ps = null;
		String sql = "insert into admin (aid, apwd, anm) values (?, ? ,?)";
		try {
			ps = ConnManager.getConnection().prepareStatement(sql);
			ps.setString(1, aid);
			ps.setString(2, apwd);
			ps.setString(3, anm);

			if (cnt > 0) {

				cnt = ps.executeUpdate();
				System.out.println("등록 완료");
			} else
				System.out.println("등록 실패");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return cnt;
	}

	// 관리자 목록 조회
	public void AshowList() {
		ConnManager.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "Select * from admin";

		try {

			pst = ConnManager.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			System.out.println("//관리자 등록 데이터");

			while (rs.next()) {
				System.out.println(rs.getString("aid") + "\t" + rs.getString("apwd") + "\t" + rs.getString("anm") + "\t"
						+ rs.getString("arole") + "\t" + rs.getString("aps"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnManager.CloseConnection();

		}

	}
}