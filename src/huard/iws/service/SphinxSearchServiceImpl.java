package huard.iws.service;

import huard.iws.util.LanguageUtils;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.sphx.api.SphinxClient;
import org.sphx.api.SphinxException;
import org.sphx.api.SphinxMatch;
import org.sphx.api.SphinxResult;
import org.sphx.api.SphinxWordInfo;

public class SphinxSearchServiceImpl implements SphinxSearchService{

	private SphinxClient sphinxClient;

	public SphinxSearchServiceImpl () {
		try{
			//String host = configurationService.getConfigurationString("sphinx", "host");
			//int port = configurationService.getConfigurationInt("sphinx", "port");
			String host = "";
			int port = 9312;
			int offset = 0;
			int limit = 20;
			int sortMode = SphinxClient.SPH_SORT_RELEVANCE;
			String sortClause = "";
			String groupBy = "";
			String groupSort = "";

			sphinxClient = new SphinxClient();
			sphinxClient.SetServer ( host, port );
			sphinxClient.SetWeights ( new int[] { 100, 1 } );
			sphinxClient.SetLimits ( offset, limit );
			sphinxClient.SetSortMode ( sortMode, sortClause );
			if ( groupBy.length()>0 )
				sphinxClient.SetGroupBy ( groupBy, SphinxClient.SPH_GROUPBY_ATTR, groupSort );
			
		}
		catch (SphinxException se){
			System.out.println ( "Exeption: " + se );
		}
	}

	public SphinxResult getResult(String query, String index){
		try {
			sphinxClient.SetMatchMode ( SphinxClient.SPH_MATCH_EXTENDED );
			SphinxResult res = sphinxClient.Query(query.toString(), index);
			if ( res==null )
			{
				System.err.println ( "Error: " + sphinxClient.GetLastError() );
				return res;
				//System.exit ( 1 );
			}
			if ( sphinxClient.GetLastWarning()!=null && sphinxClient.GetLastWarning().length()>0 )
				System.out.println ( "WARNING: " + sphinxClient.GetLastWarning() + "\n" );

			/* print me out */
			System.out.println ( "Query '" + query + "' retrieved " + res.total + " of " + res.totalFound + " matches in " + res.time + " sec." );
			System.out.println ( "Query stats:" );
			for ( int i=0; i<res.words.length; i++ )
			{
				SphinxWordInfo wordInfo = res.words[i];
				System.out.println ( "\t'" + wordInfo.word + "' found " + wordInfo.hits + " times in " + wordInfo.docs + " documents" );
			}

			System.out.println ( "\nMatches:" );
			for ( int i=0; i<res.matches.length; i++ )
			{
				SphinxMatch info = res.matches[i];
				System.out.print ( (i+1) + ". id=" + info.docId + ", weight=" + info.weight );

				if ( res.attrNames==null || res.attrTypes==null )
					continue;


				for ( int a=0; a<res.attrNames.length; a++ )
				{
					System.out.print ( ", " + res.attrNames[a] + "=" );

					if ( res.attrTypes[a]==SphinxClient.SPH_ATTR_MULTI || res.attrTypes[a]==SphinxClient.SPH_ATTR_MULTI64 )
					{
						System.out.print ( "(" );
						long[] attrM = (long[]) info.attrValues.get(a);
						if ( attrM!=null )
							for ( int j=0; j<attrM.length; j++ )
							{
								if ( j!=0 )
									System.out.print ( "," );
								System.out.print ( attrM[j] );
							}
						System.out.print ( ")" );

					} else
					{
						switch ( res.attrTypes[a] )
						{
						case SphinxClient.SPH_ATTR_INTEGER:
						case SphinxClient.SPH_ATTR_ORDINAL:
						case SphinxClient.SPH_ATTR_FLOAT:
						case SphinxClient.SPH_ATTR_BIGINT:
						case SphinxClient.SPH_ATTR_STRING:
							/* ints, longs, floats, strings.. print as is */
							System.out.print ( info.attrValues.get(a) );
							break;

						case SphinxClient.SPH_ATTR_TIMESTAMP:
							Long iStamp = (Long) info.attrValues.get(a);
							Date date = new Date ( iStamp.longValue()*1000 );
							System.out.print ( date.toString() );
							break;

						default:
							System.out.print ( "(unknown-attr-type=" + res.attrTypes[a] + ")" );
						}
					}
				}

				System.out.println();

			}
			return res;
		}


		catch (SphinxException se){
			System.out.println ( se );      
		}
		return null;
	}
	
	
	public Set<Long> getMatchedIds (String query, String index){

		String localeId=LanguageUtils.getLanguage(query).getLocaleId();
		if(localeId.equals("iw_IL"))
			query = addPreWords(query);
		SphinxResult sphinxResult = getResult(query, index);
		Set<Long> matchedIds = new LinkedHashSet<Long>();
		if (sphinxResult==null) 
			return matchedIds;
		for ( int i=0; i<sphinxResult.matches.length; i++ ){
			matchedIds.add(sphinxResult.matches[i].docId);
		}
		return matchedIds;
	}
	
	public String addPreWords(String searchWords){
		
		String strippedFromOperators=searchWords;
		strippedFromOperators = strippedFromOperators.replace(" ","  ");
		strippedFromOperators = strippedFromOperators.replaceAll("(^|\\s)\".*?\"($|\\s)","");//leave phrase in brackets as is
		strippedFromOperators = strippedFromOperators.replace("|"," ");
		strippedFromOperators = strippedFromOperators.replace("-"," ");
		strippedFromOperators = strippedFromOperators.replace("!"," ");
		StringTokenizer st = new StringTokenizer(strippedFromOperators," ");
		while( st.hasMoreTokens()){
			String word= st.nextToken();
			String wordOptions= "";
			if(word.length()>1){
				wordOptions="('ה" + word +"' | 'ו"+word +"' | 'ש"+word +"' | 'ל"+word+"' | 'מ"+word+"' | 'ב"+word+"' | 'כ"+word+"' | 'וה"+word+"' | 'ול"+word+"' | 'ומ"+word+"' | 'וב"+word+"' | 'שה"+word +"'|'" +word+"')";
				searchWords=searchWords.replace(word,wordOptions);
			}
		}
		System.out.println("sphinx: new search words:" + searchWords);

		return searchWords.replace("  ", " ");
	}
	
	private ConfigurationService configurationService;	
	
	
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	public static void main (String [] args){
		SphinxSearchServiceImpl sphinxSearchServiceImpl = new SphinxSearchServiceImpl();
		sphinxSearchServiceImpl.getResult("google","call_for_proposal_index");
	}
	
	
	
}
