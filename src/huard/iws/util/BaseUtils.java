package huard.iws.util;

import huard.iws.model.Base;
import huard.iws.model.IMailable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class BaseUtils {
	private static final Logger logger = Logger.getLogger(BaseUtils.class);

	public static  <T> boolean hasValue(T t){
		if (t==null) return false;
		if (t.getClass().getName().equals("java.lang.String")) return ((String)t).length() >0;
		return true;
	}

	public static boolean contains( int [] array, int value){
		for (int i: array){
			if (i==value) return true;
		}
		return false;
	}

	public static List<Integer> getIntegerList(String commaDelimitedString, String delimiter){
		List<Integer> integerList = new ArrayList<Integer>();
		for (String s: getList(commaDelimitedString, delimiter)){
			try{
				integerList.add(Integer.parseInt(s));
			}
			catch(Exception e){
				logger.info(e);
			}
		}
		return integerList;
	}


	public static Set<Integer> getIntegerSet(String commaDelimitedString, String delimiter){
		Set<Integer> integerSet = new HashSet<Integer>();
		for (String s: getSet(commaDelimitedString, delimiter)){
			try{
				integerSet.add(Integer.parseInt(s));
			}
			catch(Exception e){
				logger.info(e);
			}
		}
		return integerSet;
	}

	public static List<String> getList (String commaDelimitedString, String delimiter){
		StringTokenizer st = new StringTokenizer(commaDelimitedString, delimiter);
		List<String> list = new ArrayList<String>();
		while (st.hasMoreTokens()){
			String token = st.nextToken();
			list.add(token);
		}
		return list;
	}

	public static Set<String> getSet (String commaDelimitedString, String delimiter){
		StringTokenizer st = new StringTokenizer(commaDelimitedString, delimiter);
		Set<String> set = new HashSet<String>();
		while (st.hasMoreTokens()){
			set.add(st.nextToken());
		}
		return set;
	}

	public static String getString (Set<Integer> set){
		StringBuilder sb = new StringBuilder();
		if (set != null){
			for (int i: set){
				sb.append(i+",");
			}
			int j;
			if ((j=sb.lastIndexOf(","))!=-1)
				sb.deleteCharAt(j);
		}
		return sb.toString();
	}
	public static String getString (List<Integer> list){
		StringBuilder sb = new StringBuilder();
		if (list != null){
			for (int i: list){
				sb.append(i+",");
			}
			int j;
			if ((j=sb.lastIndexOf(","))!=-1)
				sb.deleteCharAt(j);
		}
		return sb.toString();
	}
	
	public static String getStringFromLongSet(Set<Long> aLongSet){
		String aString = "";
		Iterator<Long> it = aLongSet.iterator();
		while(it.hasNext()){
			aString+=",";
			String nextId=it.next().toString();
			aString+=nextId;
		}
		if(!aString.isEmpty())
			aString=aString.substring(1);
		return aString;
	}

	public static <T> Set<T> toSet (List<T> l){
		Set<T> set = new HashSet<T>();
		for (T t: l){
			set.add(t);
		}
		return set;
	}

	public static <T> List<T> toList (Set<T> set){
		List<T> list = new ArrayList<T>();
		for (T t: set){
			list.add(t);
		}
		return list;
	}

	public static <T extends Base> List<T> removeDups (List<T> list){
		Map<Integer,T> map = new HashMap<Integer,T>();
		for (T t: list){
			map.put(t.getId(), t);
		}
		List<T> uniqueList = new ArrayList<T>();
		for (T t: map.values()){
			uniqueList.add(t);
		}
		return uniqueList;
	}


	public static String [] toArray (String str, String delimeter){
		StringTokenizer st = new StringTokenizer(str, delimeter);
		String [] strArray = new String [st.countTokens()];
		int tokenCounter = 0;
		while (st.hasMoreTokens()){
			strArray[tokenCounter] = st.nextToken();
			tokenCounter ++;
		}
		return strArray;
	}

	public static <T extends IMailable> String [] toEmailsArray (List<T> objs){
		String [] emailsArray  = new String [objs.size()];
		for (int i=0; i < emailsArray.length; i++){
			emailsArray [i] = objs.get(i).getEmail();

		}
		return emailsArray;
	}
}
