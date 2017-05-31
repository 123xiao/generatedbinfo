package tt.entity;

public class AiChangeLinkVo {
	private Integer likeid; // like表ID
	private Integer shopName;// 店铺类型【1、天泰航空2、天泰优选】
	private Integer ishuankai;// 0 表示不是 换开；1表示换开传过来的订单
	private Integer orderid;
	private String orderNo;
	private Integer msgid;// msgID
	private String oldflightno;// 原航班号
	private String oldstartdate;// 原起飞日期
	private String oldenddate;// 原到达日期
	private String oldstarttime;// 原出发时间
	private String oldendtime;// 原到达时间
	private String newflightno;// 新航变号
	private String newstarttime;// 航变后的出发时间
	private String newendtime;// 航变后的到达时间
	private Integer userid;
	private String username;
	private Integer changereson;
	private String oldstarcity;// 原出发城市
	private String oldreachcity;// 原到达城市
	private String newreachcity;// 航变后到达城市
	private String newstarcity;// 航变后出发城市
	private Integer isProtected;// 是否保护航班1保护 2非保护
	private Integer isSure;// 是否确认 0,待确认 1.确认航变 2.系统处理过但没确认航变
	private String newstartdate;// 新出发日期

	public Integer getLikeid() {
		return likeid;
	}

	public String getOldstartdate() {
		return oldstartdate;
	}

	public void setOldstartdate(String oldstartdate) {
		this.oldstartdate = oldstartdate;
	}

	public String getOldenddate() {
		return oldenddate;
	}

	public void setOldenddate(String oldenddate) {
		this.oldenddate = oldenddate;
	}

	public String getNewstartdate() {
		return newstartdate;
	}

	public void setNewstartdate(String newstartdate) {
		this.newstartdate = newstartdate;
	}

	@Override
	public String toString() {
		return "AiChangeLinkVo [likeid=" + likeid + ", shopName=" + shopName + ", ishuankai=" + ishuankai + ", orderid="
				+ orderid + ", orderNo=" + orderNo + ", msgid=" + msgid + ", oldflightno=" + oldflightno
				+ ", oldstartdate=" + oldstartdate + ", oldenddate=" + oldenddate + ", oldstarttime=" + oldstarttime
				+ ", oldendtime=" + oldendtime + ", newflightno=" + newflightno + ", newstarttime=" + newstarttime
				+ ", newendtime=" + newendtime + ", userid=" + userid + ", username=" + username + ", changereson="
				+ changereson + ", oldstarcity=" + oldstarcity + ", oldreachcity=" + oldreachcity + ", newreachcity="
				+ newreachcity + ", newstarcity=" + newstarcity + ", isProtected=" + isProtected + ", isSure=" + isSure
				+ ", newstartdate=" + newstartdate + "]";
	}

	public void setLikeid(Integer likeid) {
		this.likeid = likeid;
	}

	public Integer getShopName() {
		return shopName;
	}

	public void setShopName(Integer shopName) {
		this.shopName = shopName;
	}

	public Integer getIshuankai() {
		return ishuankai;
	}

	public void setIshuankai(Integer ishuankai) {
		this.ishuankai = ishuankai;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getMsgid() {
		return msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}

	public String getOldflightno() {
		return oldflightno;
	}

	public void setOldflightno(String oldflightno) {
		this.oldflightno = oldflightno;
	}

	public String getNewflightno() {
		return newflightno;
	}

	public void setNewflightno(String newflightno) {
		this.newflightno = newflightno;
	}

	public String getOldstarttime() {
		return oldstarttime;
	}

	public void setOldstarttime(String oldstarttime) {
		this.oldstarttime = oldstarttime;
	}

	public String getOldendtime() {
		return oldendtime;
	}

	public void setOldendtime(String oldendtime) {
		this.oldendtime = oldendtime;
	}

	public String getNewstarttime() {
		return newstarttime;
	}

	public void setNewstarttime(String newstarttime) {
		this.newstarttime = newstarttime;
	}

	public String getNewendtime() {
		return newendtime;
	}

	public void setNewendtime(String newendtime) {
		this.newendtime = newendtime;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getChangereson() {
		return changereson;
	}

	public void setChangereson(Integer changereson) {
		this.changereson = changereson;
	}

	public String getOldstarcity() {
		return oldstarcity;
	}

	public void setOldstarcity(String oldstarcity) {
		this.oldstarcity = oldstarcity;
	}

	public String getOldreachcity() {
		return oldreachcity;
	}

	public void setOldreachcity(String oldreachcity) {
		this.oldreachcity = oldreachcity;
	}

	public String getNewreachcity() {
		return newreachcity;
	}

	public void setNewreachcity(String newreachcity) {
		this.newreachcity = newreachcity;
	}

	public String getNewstarcity() {
		return newstarcity;
	}

	public void setNewstarcity(String newstarcity) {
		this.newstarcity = newstarcity;
	}

	public Integer getIsProtected() {
		return isProtected;
	}

	public void setIsProtected(Integer isProtected) {
		this.isProtected = isProtected;
	}

	public Integer getIsSure() {
		return isSure;
	}

	public void setIsSure(Integer isSure) {
		this.isSure = isSure;
	}

}
