package huard.iws.model;

import java.util.List;
import java.util.Calendar;

public class DayInCalendar{
	String day;
	String dayOnly;
	String monthOnly;
	String yearOnly;
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
	public String getYearOnly(){
		return yearOnly;
	}
	public void setYearOnly(String yearOnly){
		this.yearOnly= yearOnly;
	}
	
	public boolean getIsCurrentMonth(){
		Calendar now = Calendar.getInstance();
		if(new Integer(this.monthOnly)==now.get(Calendar.MONTH)+1)
			return true;
		else
			return false;
	}
	
	public boolean getIsCurrentYear(){
		Calendar now = Calendar.getInstance();
		if(new Integer(this.yearOnly)==now.get(Calendar.YEAR))
			return true;
		else
			return false;
	}
	
	public List<FundInDay> getFundsInDay() {
		return fundsInDay;
	}

	public void setFundsInDay(List<FundInDay> fundsInDay) {
		this.fundsInDay = fundsInDay;
	}
}


