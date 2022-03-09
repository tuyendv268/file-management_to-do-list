package model;

import java.time.LocalDate;

public class Task {
	private int id;
	private String taskname;
	private LocalDate startDate;
	private LocalDate endDate;
	private String address;
	private Boolean status;
	
	public Task() {
		
	}
	
	public Task(String taskname, LocalDate startDate,LocalDate endDate, String address, Boolean status) {
		this.taskname = taskname;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.status = status;
	}
	
	public Task(int ID, String taskname, LocalDate startDate,LocalDate endDate, String address, Boolean status) {
		this.id = ID;
		this.taskname = taskname;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setID(int iD) {
		id = iD;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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
