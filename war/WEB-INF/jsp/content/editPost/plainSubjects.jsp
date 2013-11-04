<ul>
	<c:forEach items="${rootSubject.subSubjectsBeans}" var="subject">
		<li>
			${subject.name}
		<ul>
			<c:forEach items="${subject.subSubjectsBeans}" var="subSubject">
			<li>
				<a href="/postReports.html?sid=${subSubject.id}">${subSubject.name}</a>
			</li>
		 </c:forEach>
	 </ul>
	</li>
   </c:forEach>
</ul>
