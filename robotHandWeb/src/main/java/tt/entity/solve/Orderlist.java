package tt.entity.solve;

import java.util.List;

public class Orderlist {

	private String orderNo;
	private String updateTime;
	private String infoType;
	private String secondType;
	private String infoContent;
	private String handleStatus;
	private int status;
	private String lockMan;
	private List<Operation> operation;
	private int qcspTaskStatus;
	private int urgencyDegree;
	private int dueTime;
	private int totalTime;
	private int isFromEvent;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLockMan() {
		return lockMan;
	}

	public void setLockMan(String lockMan) {
		this.lockMan = lockMan;
	}

	public List<Operation> getOperation() {
		return operation;
	}

	public void setOperation(List<Operation> operation) {
		this.operation = operation;
	}

	public int getQcspTaskStatus() {
		return qcspTaskStatus;
	}

	public void setQcspTaskStatus(int qcspTaskStatus) {
		this.qcspTaskStatus = qcspTaskStatus;
	}

	public int getUrgencyDegree() {
		return urgencyDegree;
	}

	public void setUrgencyDegree(int urgencyDegree) {
		this.urgencyDegree = urgencyDegree;
	}

	public int getDueTime() {
		return dueTime;
	}

	public void setDueTime(int dueTime) {
		this.dueTime = dueTime;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public int getIsFromEvent() {
		return isFromEvent;
	}

	public void setIsFromEvent(int isFromEvent) {
		this.isFromEvent = isFromEvent;
	}

}