package huard.iws.model;

import java.util.List;

public class DayInCalendar{
	String day;
	String dayOnly;
	String monthOnly;
	List<FundInDay> fundsInDay;
	
	public String getDay(){
		return day;
	}
	public void setDay(String day){
		this.day= day;
	}
	public String getDayOnly(){
		return dayOnly;
	}
	public void setDayOnly(String dayOnly){
		this.dayOnly= dayOnly;
	}
	public String getMonthOnly(){
		return monthOnly;
	}
	public void setMonthOnly(String monthOnly){
		this.monthOnly= monthOnly;
	}
	
	public List<FundInDay> getFundsInDay() {
		return fundsInDay;
	}

	public void setFundsInDay(List<FundInDay> fundsInDay) {
		this.fundsInDay = fundsInDay;
	}
}


