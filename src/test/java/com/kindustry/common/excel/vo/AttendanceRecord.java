package com.kindustry.common.excel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kindustry.common.io.excel.ExcelVOAttribute;

public class AttendanceRecord {

    @JsonProperty("no")  
	@ExcelVOAttribute(name = "日付", column = "A")
    private String no;   //  30,
    
    @JsonProperty("worktimeYearmonth")  
    private String worktimeYearmonth;   //  "202212",
    
    @JsonProperty("worktimeStart")  
	@ExcelVOAttribute(name = "出社時刻", column = "B")
    private String worktimeStart;   //  "08:57:28",
    
    @JsonProperty("worktimeEnd")  
	@ExcelVOAttribute(name = "退社時刻", column = "C")
    private String worktimeEnd;   //  "18:11:40",
    
    @JsonProperty("worktimeHours")  
	@ExcelVOAttribute(name = "実時間", column = "D")
    private String worktimeHours;   //  8.23,
    
    
//    @JsonProperty("userId")  
//    private String userId;   //  46,
//    @JsonProperty("userRealname")  
//    private String userRealname;   //  "陳　軍",
//    @JsonProperty("week")  
//    private String week;   //  "休",
//    @JsonProperty("workplaceName")  
//    private String workplaceName;   //  null,
//    @JsonProperty("wpWorktimeStart")  
//    private String wpWorktimeStart;   //  "0900",
//    @JsonProperty("wpWorktimeEnd")  
//    private String wpWorktimeEnd;   //  "1800",
//    @JsonProperty("wpWorktimeRestStart")  
//    private String wpWorktimeRestStart;   //  "1200",
//    @JsonProperty("wpWorktimeRestEnd")  
//    private String wpWorktimeRestEnd;   //  "1300",
//    @JsonProperty("worktimeRestHours")  
//    private String worktimeRestHours;   //  1,
//    @JsonProperty("worktimeDay")  
//    private String worktimeDay;   //  "30",
//    @JsonProperty("content")  
//    private String content;   //  "[休日出勤]年末年始",
//    @JsonProperty("longitudeStart")  
//    private String longitudeStart;   //  "139.564910",
//    @JsonProperty("latitudeStart")  
//    private String latitudeStart;   //  "35.648204",
//    @JsonProperty("workplaceNameStart")  
//    private String workplaceNameStart;   //  "国領",
//    @JsonProperty("longitudeEnd")  
//    private String longitudeEnd;   //  "139.564566",
//    @JsonProperty("latitudeEnd")  
//    private String latitudeEnd;   //  "35.648804",
//    @JsonProperty("workplaceNameEnd")  
//    private String workplaceNameEnd;   //  "国領",
//    @JsonProperty("startFlag")  
//    private String startFlag;   //  true,
//    @JsonProperty("endFlag")  
//    private String endFlag;   //  true


	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}


	public String getWorktimeYearmonth() {
		return worktimeYearmonth;
	}


	public void setWorktimeYearmonth(String worktimeYearmonth) {
		this.worktimeYearmonth = worktimeYearmonth;
	}


	public String getWorktimeHours() {
		return worktimeHours;
	}


	public void setWorktimeHours(String worktimeHours) {
		this.worktimeHours = worktimeHours;
	}


	public String getWorktimeStart() {
		return worktimeStart;
	}


	public void setWorktimeStart(String worktimeStart) {
		this.worktimeStart = worktimeStart;
	}


	public String getWorktimeEnd() {
		return worktimeEnd;
	}


	public void setWorktimeEnd(String worktimeEnd) {
		this.worktimeEnd = worktimeEnd;
	}


    
    
    
}  
