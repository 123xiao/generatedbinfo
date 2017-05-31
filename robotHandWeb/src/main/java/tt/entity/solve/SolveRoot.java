package tt.entity.solve;

public class SolveRoot {

	private boolean ret;
	private int errcode;
	private String errmsg;
	private Data data;

	public void setRet(boolean ret) {
		this.ret = ret;
	}

	@Override
	public String toString() {
		return "SolveRoot [ret=" + ret + ", errcode=" + errcode + ", errmsg=" + errmsg + ", data=" + data + "]";
	}

	public boolean getRet() {
		return ret;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

}