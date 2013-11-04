	<p dir="${lang.dir}" align="${lang.align}">
		${category.name}
		<br>
		<c:forEach items="${category.subCategories}" var="subCategory" varStatus="varStatus">			
			<a href="${subCategory.url}">${subCategory.name}</a>
			<br/>
		</c:forEach>
	</p>
	<p align="center">
    	<img src="/image/hr.gif" width="185" height="5"><br>
    	<a href="homePage.html"><fmt:message key="${lang.localeId}.website.homePage"/></a>
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<a href="textualPage.html?id=1"><fmt:message key="${lang.localeId}.website.language"/></a>
    	<img src="/image/hr.gif" width="185" height="5">
    </p>
	<p dir="${lang.dir}" align="${lang.align}">
 		<fmt:message key="${lang.localeId}.website.usefulLinks"/>
 		<br>
		<!-- <c:forEach items="${lowerSideMenuSubCategories}" var="lowerSideMenuCategory" varStatus="varStatus">			
			<a href="${lowerSideMenuCategory.url}">${lowerSideMenuCategory.name}</a>
			<br/>
		</c:forEach> -->
		<a href="/search.html"><fmt:message key="${lang.localeId}.website.search"/></a>
 		<br>
		<a href="/callForProposalCalendar.html"><fmt:message key="${lang.localeId}.website.calendar"/></a>
	</p>
