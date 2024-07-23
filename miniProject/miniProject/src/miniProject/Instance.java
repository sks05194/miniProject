package miniProject;

// 임시 메소드 모음집
public class Instance {
	
	// 아이디 대소문자, 길이, 빈 문자열 등 제약조건 검사
	public boolean idCheck(String input) {
		// 받은 문자열이 null일 경우
		if (input == null) {
			System.out.println("아이디가 정상적으로 입력되지 않았습니다.");
			return true;
		}
		
		// 받은 문자열의 길이가 0일 경우
		if (input.length() == 0 || input.equals("")) {
			System.out.println("아이디를 입력해주세요.");
			return true;
		}

		// 아이디를 20자를 넘게 입력했을 경우
		if (input.length() >= 20) {
			System.out.println("아이디를 20자 이내로 입력해주세요.");
			return true;
		}

		// 받은 문자열에서 숫자나 영문이 아닌 문자가 포함된 경우
		for (int i = 0; i < input.length(); i++) {
			int asciiCode = (int) input.charAt(i);
			boolean NumorEng = (asciiCode < 48) || (asciiCode > 58 && asciiCode < 65)
					|| (asciiCode > 90 && asciiCode < 97) || (asciiCode > 122);

			if (NumorEng) {
				System.out.println("숫자나 영문만 입력 가능합니다.");
				return true;
			}
		}

		return false;
	}

	// 비밀번호 대소문자, 길이, 빈 문자열 등 제약조건 검사
	public boolean passwordCheck(String input) {

		// 받은 문자열이 null일 경우
		if (input == null) {
			System.out.println("비밀번호가 정상적으로 입력되지 않았습니다.");
			return true;
		}
		
		// 비밀번호를 20자 넘게 입력했을 경우
		if (input.length() >= 20) {
			System.out.println("비밀번호를 20자 이내로 작성해주세요.");
			return true;
		}
		
		// 빈 문자열일 경우
		if (input.length() == 0 || input.equals("")) {
			System.out.println("비밀번호를 입력해주세요.");
			return true;
		}

		return false;
	};
	
	// 이름 제약조건 검사
	public boolean nameCheck(String input) {
		String check = "^[가-힣a-z-A-Z]*$";
		if (check != input) {
			return true;
		}
		
		return false;
	}

	// 학번 제약조건 검사
	public boolean numCheck(int input) {
		String numberStr = String.valueOf(input);
		String check = "\\d{8}";

		return !numberStr.matches(check);
	}

}
