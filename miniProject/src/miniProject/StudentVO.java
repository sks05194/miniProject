package miniProject;

public class StudentVO {
	private int sno = 0;	// 학번 primary key
	private String snm = null; // 이름 not null
	private String sid = null; // 아이디 unique
	private String spw = null; // 비밀번호 제약조건 없음
	private String sdate = null; // 입학일자 default 24/03/02
	private boolean slms = false; // LMS 가입여부 check = 'Y'(true), 'N'(false)
	
	public StudentVO(int sno, String snm, String sid, String spw, String sdate, boolean slms) {
		this.sno = sno;
		this.snm = snm;
		this.sid = sid;
		this.spw = spw;
		this.sdate = sdate;
		this.slms = slms;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getSnm() {
		return snm;
	}

	public void setSnm(String snm) {
		this.snm = snm;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSpw() {
		return spw;
	}

	public void setSpw(String spw) {
		this.spw = spw;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public boolean getSlms() {
		return slms;
	}

	public void setSlms(boolean slms) {
		this.slms = slms;
	}

	@Override
	public String toString() {
		return "StudentVO [sno=" + sno + ", snm=" + snm + ", sid=" + sid + ", spw=" + spw + ", sdate=" + sdate
				+ ", slms=" + slms + "]";
	}
}
