package huard3.utils;
import java.util.ArrayList;
import java.util.List;

public class WordsTokenizer {

	private String delimeter ;

	public WordsTokenizer( String delimeter )
	{
		this.delimeter = delimeter ;
	}

	public List<String> getSubstringsList( String s )
	{
		s = s + delimeter ;
		List<String> wordsList = new ArrayList<String>() ;
		int pos = 0 ;
		while ( ( pos = s.indexOf( delimeter ) ) >= 0  )
		{
			String word = s.substring( 0, pos ).trim() ;

			s = s.substring( pos+delimeter.length() ) ;

			if ( word.length() > 0 )
				wordsList.add( word ) ;
		}
		return wordsList ;
	}

	public List getSubstringsListNoTrim( String s )
		{
			s = s.trim() + delimeter ;
			List wordsList = new ArrayList() ;
			int pos = 0 ;
			while ( ( pos = s.indexOf( delimeter ) ) >= 0 )
			{
				String word = s.substring( 0, pos );

				int i=0;

				while (word.length()>0 && word.lastIndexOf("\\")==word.length()-1){
					word = word.substring(0,word.length()-1);
					String s1 = s.substring(pos+1);
					int newPos = s1.indexOf(delimeter)+pos+1;
					word = word.concat(s.substring(pos,newPos));
					pos = newPos;
					i++;
				}


				/*while (word.lastIndexOf("\\")==word.length()-1){
					String s1 = s.substring(pos+delimeter.length());
					int newPos =  s1.indexOf(delimeter)+pos+1;
					word = s.substring(0,pos-1).concat(s.substring(pos,newPos));
					System.out.println(i+": "+word);
					pos = newPos;
					i++;
				}*/

				s = s.substring( pos+1 ) ;
				wordsList.add( word ) ;
			}
			return wordsList ;
		}

	public List getSubstringsListWithDelimiter( String s )
		{
			s = s + delimeter ;
			List wordsList = new ArrayList() ;
			int pos = 0 ;
			while ( ( pos = s.indexOf( delimeter ) ) >= 0 )
			{
				String word = s.substring( 0, pos ) ;

				s = s.substring( pos+delimeter.length() ) ;

				if ( word.length() > 0 )
					wordsList.add( word ) ;
				wordsList.add(this.delimeter);
			}
			wordsList.remove(wordsList.size()-1);
			return wordsList ;
		}

	public List getSubstringsList(String s, int i){
		String originalS = s;
		int k=0;
		List wordsList = new ArrayList();
		while (k<i){
			s = s + delimeter;
			while (s.indexOf(delimeter)>=0){
				int j=0;
				StringBuffer sb = new StringBuffer("");
				while (j<i && s.indexOf(delimeter)>=0){
					sb.append(s.substring(0,s.indexOf(delimeter))+delimeter);
					s = s.substring(s.indexOf(delimeter)+delimeter.length());
					j++;
				}
				sb.delete(sb.length()-delimeter.length(),sb.length());
				wordsList.add(sb.toString());
			}
			originalS = originalS.substring(originalS.indexOf(delimeter)+delimeter.length());
			s = originalS;
			k++;
		}
		return wordsList;
	}

	public static void main (String [] args){
		WordsTokenizer wt = new WordsTokenizer(" ");
		List l = wt.getSubstringsList("Ganan gidel dagan bagan",2);
		for (int i=0 ; i< l.size(); i++){
			System.out.println((String)l.get(i));
		}
	}


}

