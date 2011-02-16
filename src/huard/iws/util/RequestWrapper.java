package huard.iws.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestWrapper {

	private HttpServletRequest request;

	public RequestWrapper(HttpServletRequest request){
		this.request = request;
	}

	public int getIntParameter ( String parameterName, int defaultValue ){
		String strValue;
		if ( ( strValue = request.getParameter(parameterName) ) == null)
			return defaultValue;
		int intValue = 0;
		intValue = Integer.parseInt(strValue);
		if (intValue == 0 && intValue == defaultValue)
			return defaultValue;
		return intValue;
	}

	public boolean isRequestHasParameter( String parameterName){
		return request.getParameter(parameterName)  != null;
	}

	public int getSessionIntParameter (String parameterName, int defaultValue){

		if (isRequestHasParameter( parameterName )) {
			int intValue = getIntParameter( parameterName, defaultValue);
			request.getSession(true).setAttribute( parameterName, intValue);
			return intValue;
		}
		Integer sessionIntValue = (Integer) request.getSession(true).getAttribute( parameterName );
		if (sessionIntValue == null){
			request.getSession(true).setAttribute( parameterName, defaultValue);
			return defaultValue;
		}
		return sessionIntValue;
	}

	public String getParameter ( String parameterName, String defaultValue ){
		String strValue;
		if ( ( strValue = request.getParameter(parameterName) ) == null)
			return defaultValue;
		return strValue;
	}

	public int [] getIntParameterValues ( String parameterName ){
		String [] strValues;
		if ( ( strValues = request.getParameterValues(parameterName) ) == null)
			return new int [0];
		int  [] intValues = new int [strValues.length];
		for (int i = 0; i < strValues.length ; i++){
			intValues [i] = Integer.parseInt( strValues[i] );
		}
		return intValues;
	}


	public boolean getBooleanParameter ( String parameterName, boolean defaultValue ){
		String strValue;
		if ( ( strValue = request.getParameter(parameterName) ) == null)
			return defaultValue;
		boolean booleanParameter = false;
		booleanParameter = Boolean.parseBoolean(strValue);
		return booleanParameter;
	}

	public boolean [] getBooleanParameters (String parameterName, boolean ... defaultValue){
		String [] values = request.getParameterValues(parameterName);
		if (values == null) return defaultValue;
		boolean [] booleanValues = new boolean [values.length];
		for (int i = 0 ; i < booleanValues.length ; i++){
			booleanValues [i] = false;
			booleanValues [i] = Boolean.parseBoolean(values[i]);
		}
		return booleanValues;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpSession getSession(){
		return request.getSession(true);
	}

}
