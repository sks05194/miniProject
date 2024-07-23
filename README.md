# miniProject

# 0723 1440
- AdminDAO.login(String, String)
    "Y"가 아닌 "O"와 비교하는 코드 수정
    regirster(), getStudent() 메소드 통합

- MiniProjectMain
    권한이 없는 관리자가 로그인되는 오류 수정

- StudentDAO
    엄청난 공사


# 0723 1600 

- studentDAO의 아이디 비밀번호 체크 메소드 정리

# 0723 1615

- 지혁님이 수정하신 내용 통합
    - AdminDAO 클래스

    1. 관리자 로그인 출력 메소드 아이디,비밀번호 제약조건 검사 추가 및 무한반복문 추가
    2. 관리자 가입 출력 메소드 아이디,비밀번호 제약조건 검사 추가 및 무한반복문 추가 + 이름 제약조건 검사 추가
    3. 관리자 상태변경 메소드 아이디 제약조건 검사 및 무한반복문
    4. 관리자 역할변경 아이디 제약조건 검사 및 무한반복문

    - Instance 클래스 생성

    - ConnManager 클래스 Instance관련 추가