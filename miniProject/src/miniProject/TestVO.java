package miniProject;

public class TestVO {
	private int ty = 0; // 년도 default 2024
	private int ts = 0; // 학기 check 1 or 2
	private int tn = 0; // 문제 번호 not null
	private String tq = null; // 문제 not null
	private int ta = 0; // 답 not null

	public TestVO(int ty, int ts, int tn, String tq, int ta) {
		this.ty = ty;
		this.ts = ts;
		this.tn = tn;
		this.tq = tq;
		this.ta = ta;
	}

	public int getTy() {
		return ty;
	}

	public void setTy(int ty) {
		this.ty = ty;
	}

	public int getTs() {
		return ts;
	}

	public void setTs(int ts) {
		this.ts = ts;
	}

	public int getTn() {
		return tn;
	}

	public void setTn(int tn) {
		this.tn = tn;
	}

	public String getTq() {
		return tq;
	}

	public void setTq(String tq) {
		this.tq = tq;
	}

	public int getTa() {
		return ta;
	}

	public void setTa(int ta) {
		this.ta = ta;
	}

	@Override
	public String toString() {
		return "TestVO [ty=" + ty + ", ts=" + ts + ", tn=" + tn + ", tq=" + tq + ", ta=" + ta + "]";
	}
}
