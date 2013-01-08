   	<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
   	<tr>
 	  		<td valign="top" colspan="${fn:length(languageRootCategories)}" align="center"></td>
    </tr>
    <tr>
			<c:forEach items="${languageRootCategories}" var="languageRootCategory" varStatus="varStatus">			
				<td align="center">
          			<a href="${languageRootCategory.url}" class="menu">${languageRootCategory.name}</a></font>
				</td>
			</c:forEach>
           	<td width="5" align="center"></td>
    </tr>
   	</table>
