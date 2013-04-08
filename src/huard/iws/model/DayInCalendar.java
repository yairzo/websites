package huard.iws.model;

import java.util.List;

public class DayInCalendar{
	String day;
	List<FundInDay> fundsInDay;
	
	public String getDay(){
		return day;
	}
	public void setDay(String day){
		this.day= day;
	}
	
	public List<FundInDay> getFundsInDay() {
		return fundsInDay;
	}

	public void setFundsInDay(List<FundInDay> fundsInDay) {
		this.fundsInDay = fundsInDay;
	}
}


