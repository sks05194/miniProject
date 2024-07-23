package miniProject;

import java.sql.SQLException;
import java.util.InputMismatchException;

public class MiniProjectMain {
	public static void main(String[] args) {
		int menuNum = 0;
		AdminDAO adminDAO = new AdminDAO();
		StudentDAO studentDAO = new StudentDAO();

		while (true) {
			System.out.println("메뉴선택:  1.관리자  2.학생   3.시스템종료");
			try {
				menuNum = ConnManager.getScanner().nextInt();
			} catch (InputMismatchException e1) {
				System.out.println("숫자만 입력해주세요.");
				ConnManager.getScanner().nextLine();
				continue;
			}
			switch (menuNum) {

			case 1: // 관리자
				System.out.println("관리자 메뉴 시작");
				System.out.println("메뉴선택: 1. 관리자 로그인  2. 관리자 가입  3.홈으로");
				try {
					menuNum = ConnManager.getScanner().nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("숫자만 입력해주세요.");
					ConnManager.getScanner().nextLine();
					continue;
				}

				switch (menuNum) {
				case 1: // 관리자 로그인
					AdminVO admin = adminDAO.login();

					if (admin == null) {
						System.out.println("관리자가 아닙니다.");
						break;
					}

					if(!admin.getAps()) {
						System.out.println("아직 권한이 없습니다.");
						break;
					}

					// 관리자 메뉴
					System.out.println(admin.getAnm() + "님 환영합니다.");

					admin: while (true) {
						System.out.println("관리자메뉴 선택: 1.학생등록 2.학생출력 3.관리자출력 4.상태변경  5.역할변경  6.로그아웃");
						try {
							menuNum = ConnManager.getScanner().nextInt();
						} catch (InputMismatchException e1) {
							System.out.println("숫자만 입력해주세요.");
							ConnManager.getScanner().nextLine();
							continue;
						}

						switch (menuNum) {
						case 1: // 학생등록 기능
							adminDAO.regirster();
							break;
						case 2: // 학생출력 기능
							adminDAO.showList();
							break;
						case 3: // 관리자출력 기능
							adminDAO.AshowList();
							break;
						case 4: // 상태변경
							adminDAO.changeAps(admin);
							break;
						case 5: // 역할변경
							adminDAO.changeArole(admin);
							break;
						case 6:
							break admin;
						default:
							System.out.println("잘못된 접근입니다.");
							break admin;
						}
					}
					break;

				case 2: // 관리자 가입
					adminDAO.inswerAnMember();
					break;

				case 3: // 홈으로
					break;
				default:
					System.out.println("잘못된 접근입니다.");
				}
				break; // 관리자 종료

			case 2: // 학생
				System.out.println("학생 메뉴 시작");
				System.out.println("메뉴선택: 1.로그인  2.회원가입  3.홈으로");
				try {
					menuNum = ConnManager.getScanner().nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("숫자만 입력해주세요.");
					ConnManager.getScanner().nextLine();
					continue;
				}

				switch (menuNum) {
				case 1: // 로그인
					StudentVO student = studentDAO.loginStudent();

					if (student == null) {
						System.out.println("등록된 정보가 없거나 일치하지 않습니다.");
						break;
					}

					System.out.println(student.getSnm() + "님 환영합니다.");

					// 학생 메뉴
					student: while (true) {
						System.out.println("학생 메뉴 선택: 1.시험응시 2.점수확인  3.로그아웃");
						try {
							menuNum = ConnManager.getScanner().nextInt();
						} catch (InputMismatchException e1) {
							System.out.println("숫자만 입력해주세요.");
							ConnManager.getScanner().nextLine();
							continue;
						}

						switch (menuNum) {
						case 1: // 시험응시
							try {
								studentDAO.test(student);
							} catch (SQLException e) {
//								e.printStackTrace(); // 오류시 확인용
								System.out.println("오류가 발생하여 시험을 중단합니다.");
							}
							break;
						case 2: // 점수확인
							studentDAO.checkPoint(student);
							break;
						case 3:
						default:
							break student;
						}
					}

					break;

				case 2: // 회원가입
					studentDAO.joinStudent();
					break;

				case 3: // 홈으로
					break;
				default:
					System.out.println("잘못 입력하셨습니다.");
				}

				break; // 학생 종료

			case 3: // 종료
				System.out.println("시스템을 종료합니다.");
				ConnManager.CloseConnection();
				return;

			default:
				System.out.println("허용되지 않은 접근입니다.");
			}
		}
	}
}