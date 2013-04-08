<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<table border="0" width="80%" dir="${lang.dir}">
		<tbody>
			<tr>
      			<td>
	      			<h1>${pageTitle}</h1>
    			</td>
             </tr>
			<tr>
		    <td colspan="2">
            <form:form id="form" name="form" method="POST" commandName="command" action="searchCallForProposals.html">
 			
              <table width="1000" border="0" align="center" cellpadding="3" dir="rtl">
               <tr>
      				<td class="container" style="width: 32%; vertical-align: top">
    					<table style="width: 100%;">
                		<tr class="form">
							<td>
						 	<fmt:message key="${lang.localeId}.callForProposal.searchWords"/>
							<input type="text" class="green" style="width:400px" name="searchWords"/> 
							</td>
							<td align="left">
 							<button class="grey">חפש</button>
       						</td>
       					</tr>
       					</table>
       				</td>
               </tr>
	          </table>
 
			  </form:form>
              </td>
              </tr>
     </tbody>
</table>
