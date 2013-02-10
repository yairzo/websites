package huard.iws.service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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
			int mode = SphinxClient.SPH_MATCH_ALL;
			int offset = 0;
			int limit = 20;
			int sortMode = SphinxClient.SPH_SORT_RELEVANCE;
			String sortClause = "";
			String groupBy = "";
			String groupSort = "";

			sphinxClient = new SphinxClient();
			sphinxClient.SetServer ( host, port );
			sphinxClient.SetWeights ( new int[] { 100, 1 } );
			sphinxClient.SetMatchMode ( mode );
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
			SphinxResult res = sphinxClient.Query(query.toString(), index);
			if ( res==null )
			{
				System.err.println ( "Error: " + sphinxClient.GetLastError() );
				System.exit ( 1 );
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
		SphinxResult sphinxResult = getResult(query, index);
		Set<Long> matchedIds = new LinkedHashSet<Long>();
		for ( int i=0; i<sphinxResult.matches.length; i++ ){
			matchedIds.add(sphinxResult.matches[i].docId);
		}
		return matchedIds;
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
