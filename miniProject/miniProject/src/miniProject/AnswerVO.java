package miniProject;

public class AnswerVO {
	private int sno = 0; // Student.sno foreign key
	private int ty = 0; // Test.ty foreign key
	private int ts = 0; // Test.ts foreign key
	private int tn = 0; // Test.tn foreign key
	private boolean ans = false; // Check 'O'(true), 'X'(false)
	
	public AnswerVO(int sno, int ty, int ts, int tn, boolean ans) {
		this.sno = sno;
		this.ty = ty;
		this.ts = ts;
		this.tn = tn;
		this.ans = ans;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
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

	public boolean isAns() {
		return ans;
	}

	public void setAns(boolean ans) {
		this.ans = ans;
	}

	@Override
	public String toString() {
		return "AnswerVO [sno=" + sno + ", ty=" + ty + ", ts=" + ts + ", tn=" + tn + ", ans=" + ans + "]";
	}
}
