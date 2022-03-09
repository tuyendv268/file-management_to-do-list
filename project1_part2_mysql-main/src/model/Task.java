package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Task {
	private int id;
	private String taskname;
	private Timestamp startDate;
	private Timestamp endDate;
	private String address;
	private Boolean status;
	private Boolean warning;
	
	public Task() {
		
	}
	
	public Task(String taskname, Timestamp startDate,Timestamp endDate, String address, Boolean status) {
		this.taskname = taskname;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.status = status;
		this.warning = false;
	}
	
	public Task(int ID, String taskname, Timestamp startDate,Timestamp endDate, String address, Boolean status, Boolean warning) {
		this.id = ID;
		this.taskname = taskname;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.status = status;
		this.warning = warning;
	}

	public Integer getId() {
		return id;
	}

	public void setID(int iD) {
		id = iD;
	}

	public Boolean getWarning() {
		return warning;
	}

	public void setWarning(Boolean warning) {
		this.warning = warning;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	
	

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String display() {
		System.out.println(getId().toString() + "_" + getTaskname()+"_"+getStartDate()+"_"+getEndDate()+"_"+getAddress()+"_"+getStatus());
		return getId().toString() + "_" + getTaskname()+"_"+getStartDate()+"_"+getEndDate()+"_"+getAddress()+"_"+getStatus()+"\n";

	}
	
}
