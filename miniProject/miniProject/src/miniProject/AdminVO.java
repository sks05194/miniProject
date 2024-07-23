package miniProject;

public class AdminVO {
	private String aid = null; // 아이디 primary key
	private String apwd = null; // 비밀번호 not null
	private String anm = null; // 이름 not null
	private String arole = null; // 역할 Check = "super", "sub"
	private boolean aps = false; // 사용 가능 상태 = 'Y'(true), 'N'(false)

	// 로그인
	public AdminVO(String aid, String apwd, String anm, String arole, boolean aps) {
		this.aid = aid;
		this.apwd = apwd;
		this.anm = anm;
		this.arole = arole;
		this.aps = aps;
	}

	// 회원가입
	/** 역할은 sub, 사용 가능 상태는 Y로 저장됩니다. */
	public AdminVO(String aid, String apwd, String anm) {
		this.aid = aid;
		this.apwd = apwd;
		this.anm = anm;
		this.arole = "sub";
		this.aps = false;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getApwd() {
		return apwd;
	}

	public void setApwd(String apwd) {
		this.apwd = apwd;
	}

	public String getAnm() {
		return anm;
	}

	public void setAnm(String anm) {
		this.anm = anm;
	}

	public String getArole() {
		return arole;
	}

	public void setArole(String arole) {
		this.arole = arole;
	}

	public boolean getAps() {
		return aps;
	}

	public void setAps(boolean aps) {
		this.aps = aps;
	}

	@Override
	public String toString() {
		return "AdminVO [aid=" + aid + ", apwd=" + apwd + ", anm=" + anm + ", arole=" + arole + ", aps=" + aps + "]";
	}
}