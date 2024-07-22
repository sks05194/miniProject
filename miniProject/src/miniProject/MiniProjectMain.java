package miniProject;

import java.sql.SQLException;

/*
 * 변경사항 2024-07-22 10:55
 * AdminVO			기타생성자 aps = false
 * MiniProjectMain	주석 일부와 바뀐 함수명
 * StudentDAO		시험, 점수 확인 기능 추가 및 개선
 */

public class MiniProjectMain {
	public static void main(String[] args) {
		int menuNum = 0;
		AdminDAO adminDAO = new AdminDAO(); 
		StudentDAO studentDAO = new StudentDAO();

		while (true) {
			System.out.println("메뉴선택:  1.관리자  2.학생   3.시스템종료");
			menuNum = ConnManager.getScanner().nextInt();
			switch (menuNum) {

			case 1: // 관리자
				System.out.println("관리자 메뉴 시작");
				System.out.println("메뉴선택: 1. 관리자 로그인  2. 관리자 가입  3.홈으로");
				menuNum = ConnManager.getScanner().nextInt();

				switch (menuNum) {
				case 1: // 관리자 로그인

					// FIXME 로그인 기능이 완료된다면 변경해주세요.
					AdminVO admin = null;

					while (true) {
						if (admin == null) {
							System.out.println("관리자가 아닙니다.");
							break;
						}

						// 관리자 메뉴
						System.out.println("관리자님 환영합니다.");

						admin: while (true) {
							System.out.println("관리자메뉴 선택: 1.학생등록 2.학생출력 3.관리자출력 4.상태변경  5.역할변경  6.로그아웃");
							menuNum = ConnManager.getScanner().nextInt();
							
							switch(menuNum) {
							case 1:
								// TODO 학생등록 기능
								break;
							case 2:
								// TODO 학생출력 기능
								break;
							case 3:
								// TODO 관리자출력 기능
								break;
							case 4: // 상태변경
								adminDAO.changeAps(admin);
								break;
							case 5: // 역할변경
								adminDAO.changeArole(admin);
								break;
							case 6:
							default:
								break admin;
							}
						}

						break;
					}
					break;

				case 2: // 관리자 가입

					// TODO 관리자 가입 기능.

					break;

//				case 3: // 홈으로
//					break;
//				default:
				}
				break; // 관리자 종료

			case 2: // 학생
				System.out.println("학생 메뉴 시작");
				System.out.println("메뉴선택: 1.로그인  2.회원가입  3.홈으로");
				menuNum = ConnManager.getScanner().nextInt();

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
						menuNum = ConnManager.getScanner().nextInt();

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

//				case 3: // 홈으로
//					break;
//				default:
				}

				break; // 학생 종료

			case 3: // 종료
				System.out.println("시스템을 종료합니다.");
				ConnManager.CloseConnection();
				return;

			default:
				// FIXME 추후 프로그램을 보고 출력문을 변경해주시길 바랍니다.
				System.out.println("허용되지 않은 접근입니다.");
				ConnManager.CloseConnection();
				break;
			}
		}
	}
}