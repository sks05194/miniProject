package miniProject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {
	/** 로그인 관련 메세지 출력 및 관련 메소드 호출 */
	public StudentVO loginStudent() {
		System.out.println("학생 로그인 메뉴");

		System.out.print("아이디 입력");
		String sid = ConnManager.getScanner().next();

		// TODO 아이디 제약조건. 추후 보충하거나 메소드로 뺄 것.
		if (sid == null || sid.length() == 0) {
			System.out.println("아이디 입력 오류");
			return null;
		}

		System.out.print("비번 입력>>");
		String spw = ConnManager.getScanner().next();

		// TODO 비밀번호 제약조건. 추후 보충하거나 메소드로 뺄 것.
		if (spw == null || spw.length() == 0) {
			System.out.println("비밀번호 입력 오류");
			return null;
		}

		try {
			return loginStudent(sid, spw);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/** 로그인 기능 */
	private StudentVO loginStudent(String sid, String spw) throws SQLException {
		String sql = "SELECT * FROM student WHERE sid = ? AND spw = ?";

		PreparedStatement ps = ConnManager.getConnection().prepareStatement(sql);
		ps.setString(1, sid);
		ps.setString(2, spw);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			int sno = rs.getInt("sno");
			String snm = rs.getString("snm");
			String sdate = rs.getString("sdate");
			// XXX sdate에서 오류난다면 Date형 컬럼을 받아오는 과정에서 생긴 오류일 확률이 높습니다.
			// 오류가 발생한다면 쿼리문에서 컬럼을 받아오는 줄을 변경하거나 자료형을 변경해주세요.
			boolean slms = rs.getString("slms") == "Y";

			rs.close();
			ps.close();
			return new StudentVO(sno, snm, sid, spw, sdate, slms);
		} else
			return null;
	}

	/**
	 * 학생 시험
	 * 
	 * @throws main 함수 실행 중 이 메소드에서 오류 발생 시 catch문에서 시험 중단이라고 알려줄거에요.
	 */
	public void test(StudentVO student) throws SQLException {
		System.out.println("시험응시 시작");
		System.out.println("2024년 1학기 JAVA 초급 시험시작"); // TODO 이후 변경 예정

		// 이미 응시한 시험은 다시 보실 수 없습니다. 나가세요.
		if (!checkTestPoint(student)) {
			System.out.println("응시한 시험입니다.");
			return;
		}

		// TODO 일시적으로 2024년 1학기로 설정하였습니다.
		// 추후 년도나 학기는 변경해주세요.
		ArrayList<TestVO> testList = testList(2024, 1); // 2024년도 1학기 시험 문제 리스트
		if(testList.size() == 0) {
			System.out.println("해당 년도의 학기에 해당하는 문제가 존재하지 않습니다.");
			return;
		}
		int[] correctArr = new int[testList.size()]; // 문제에 따른 답을 담는 배열

		// 문제 풀기
		// XXX 이 부분 메소드로 만들지 고민해볼것.
		for (int testNo = 0; testNo < testList.size(); testNo++) {
			// 문제 출력
			int no = testList.get(testNo).getTn();
			String question = testList.get(testNo).getTq();

			System.out.println(no + "번. " + question);

			// 문제 번호에 따라 점수 correctArr에 반영
			System.out.print("답안 입력(번호로 입력해주세요)>> ");
			correctArr[testNo] = ConnManager.getScanner().nextInt();
		}

		// 문제 번호 변경하고 싶은 경우
		while (true) {
			System.out.println("선택 : 1.시험종료  2.답안수정");
			int choice = ConnManager.getScanner().nextInt();

			if (choice == 1)
				break;
			else if (choice != 2) {
				System.out.println("허용되지 않은 접근입니다. 시험을 종료합니다.");
				break;
			}

			System.out.print("수정할 문제 번호 입력>>");
			choice = ConnManager.getScanner().nextInt();

			// 수정할 문제 찾고 번호 고치기
			for (int testNo = 0; testNo < testList.size(); testNo++) {
				if (choice != testList.get(testNo).getTn())
					continue;

				// 문제 출력
				int no = testList.get(testNo).getTn();
				String question = testList.get(testNo).getTq();

				System.out.println(no + "번. " + question);

				// 문제 번호에 따라 점수 correctArr에 반영
				System.out.print("답안 입력(번호로 입력해주세요)>> ");
				correctArr[testNo] = ConnManager.getScanner().nextInt();
				break;
			}
		}

		// 시험 점수 테이블에 반영하는 메소드 호출 예정
		for (int testNo = 0; testNo < testList.size(); testNo++) {
			reflecting(student.getSno(), testList.get(testNo), correctArr[testNo]);
		}
	}

	/** 시험을 치뤘는지 확인하는 메소드 */
	private boolean checkTestPoint(StudentVO student) throws SQLException {
		String sql = "Select * FROM answer WHERE sno = ? AND ty = ? AND ts = ?";

		PreparedStatement ps = ConnManager.getConnection().prepareStatement(sql);
		ps.setString(1, student.getSid());
		ps.setInt(2, 2024); // 추후 변경 가능성 높음

		ResultSet rs = ps.executeQuery();

		// TODO close 위로 올리기
		if (rs.next()) {
			rs.close();
			return true;
		}
		
		rs.close();
		return false;
	}

	/**
	 * 년도, 학기별 시험 문제 리스트를 반환하는 메소드
	 * 
	 * @param ty 시험년도
	 * @param ts 시험학기
	 */
	private ArrayList<TestVO> testList(int ty, int ts) throws SQLException {
		ArrayList<TestVO> list = new ArrayList<>();

		String sql = "SELECT * FROM test WHERE ty = ? AND ts = ?";

		PreparedStatement ps = ConnManager.getConnection().prepareStatement(sql);
		ps.setInt(1, ty);
		ps.setInt(2, ts);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int tn = rs.getInt("tn");
			String tq = rs.getString("tq");
			int ta = rs.getInt("ta");

			list.add(new TestVO(ty, ts, tn, tq, ta));
		}

		rs.close();
		ps.close();
		return list;
	}

	/**
	 * 점수를 sql 쿼리문을 사용하여 반영합니다.
	 * 
	 * @param sno  학번
	 * @param test 본 시험 정보
	 * @param ans  정답여부
	 */
	private void reflecting(int sno, TestVO test, int ans) throws SQLException {
		String sql = "INSERT INTO answer VALUES ( ?, ?, ?, ?, ? )";

		PreparedStatement ps = ConnManager.getConnection().prepareStatement(sql);
		ps.setInt(1, sno);
		ps.setInt(2, test.getTy());
		ps.setInt(3, test.getTs());
		ps.setInt(4, test.getTn());
		ps.setString(5, test.getTa() == ans ? "O" : "X");

		int rowCount = ps.executeUpdate();

		ps.close();
		// 여기 아래는 확인 후 지워줘요.
		if (rowCount > 0)
			System.out.println("반영완료");
	}

	/**
	 * 학생의 총점을 출력합니다.
	 * 
	 * @param student: 학생 정보
	 */
	public void checkPoint(StudentVO student) {
		System.out.println("확인하고 싶은 년도와 학기를 입력하세요.");
		System.out.print("년도입력(예: 2024) >>");
		int year = ConnManager.getScanner().nextInt();

		System.out.print("학기 선택(숫자로입력): 1.1학기 2.2학기 >>");
		int semester = ConnManager.getScanner().nextInt();
		if (semester != 1 || semester != 2) {
			System.out.println("존재하지 않는 접근입니다.");
			return;
		}

		ArrayList<AnswerVO> answerList;
		try {
			answerList = answerList(student.getSno(), year, semester);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("점수를 불러오는데 실패했습니다.");
			return;
		}
		System.out.println(year + "년 " + semester + "학기 성적확인");

		System.out.println("---------------------------------");
		double sum = sumPoint(answerList);
		System.out.println("총점 : " + sum);

		for (AnswerVO answer : answerList)
			System.out.println(answer.getTn() + "번. " + (answer.isAns() ? "O" : "X"));

	}

	/** 학번, 년도, 학기에 해당하는 점수 리스트를 가져옵니다. */
	private ArrayList<AnswerVO> answerList(int sno, int ty, int ts) throws SQLException {
		ArrayList<AnswerVO> list = new ArrayList<>();

		String sql = "SELECT * FROM answer WHERE ano = ? AND ty = ? AND ts = ?";

		PreparedStatement ps = ConnManager.getConnection().prepareStatement(sql);
		ps.setInt(1, sno);
		ps.setInt(2, ty);
		ps.setInt(3, ts);

		ResultSet rs = ps.executeQuery();

		while (rs.next())
			list.add(new AnswerVO(
					rs.getInt("sno"),
					rs.getInt("ty"),
					rs.getInt("ts"),
					rs.getInt("ts"),
					rs.getString("ans").equals("O")));

		return list;
	}

	/** 총점 계산기 */
	private double sumPoint(ArrayList<AnswerVO> list) {
		double sum = 0.0f;

		for (AnswerVO answer : list)
			if (answer.isAns())
				sum++;

		return sum * 100 / list.size();
	}

	

	// 회원가입 기능
	public void joinStudent() {
		int sno = 0;
		while (true) {
			try {
//				System.out.println("DB 연결 성공");
				System.out.println("회원 가입 메뉴");
				System.out.println("학번 입력 >>");
				if (ConnManager.getScanner().hasNextInt()) {
					sno = ConnManager.getScanner().nextInt();
				} else {
					System.out.println("숫자만 입력할 수 있습니다.");
					ConnManager.getScanner().next();
					// XXX 아마 이렇게 되신다면 오류가 발생할 수 있습니다.
				}
//				System.out.println(sno);

				System.out.println("아이디 입력 >>");
				String sid = ConnManager.getScanner().next();
				System.out.println(sid);

				System.out.println("비밀번호 입력 >>");
				String spw = ConnManager.getScanner().next();

				System.out.println("이름 입력 >>");
				String snm = ConnManager.getScanner().next();

				// XXX 줄 상쇄를 위해 작성. 필요 없으시면 삭제하셔도 괜찮습니다.
				ConnManager.getScanner().nextLine();
				
				joinStudent(sid, spw, sno, snm);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void joinStudent(String sid, String spw, int sno, String snm) {
		String sql = "update student set sid = ?, spw = ?, slms = 'Y' where sno = ? and snm = ? and slms = 'N'";
		PreparedStatement ps = null;
		try {
			ps = ConnManager.getConnection().prepareStatement(sql);
			ps.setString(1, sid);
			ps.setString(2, spw);
			ps.setInt(3, sno);
			ps.setString(4, snm);

			int rowCount = ps.executeUpdate();
			if (rowCount > 0) {
				System.out.println("회원가입 완료");
			} else
				System.out.println("회원 가입이 실패하였습니다.\n" + "사유: 없는 학번이거나 중복 아이디");
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
}