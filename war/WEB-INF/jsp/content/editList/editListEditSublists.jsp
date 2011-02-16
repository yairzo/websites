<%@ page  pageEncoding="UTF-8" %>



<tr>
	<td>
		&nbsp;
	</td>
</tr>

<tr>
   <td colspan="2" align="right">
		<button class="grey" onclick="window.location='viewList.html?id=<c:out value="${command.id}"/>'; return false;">צפייה ברשימה</button>
   </td>
</tr>

<tr>
	<td>
		&nbsp;
	</td>
</tr>

<tr>
	<td colspan="2">
		הרשימות המרכיבות את הרשימה וסדר הופעתן:
	</td>
</tr>

<tr>
	<td class="sublists" colspan="2">

	<c:forEach items="${command.sublists}" var="sublist" varStatus="varStatus">
		<c:out value="${sublist.name}"/>
		<form:input cssClass="green" cssStyle="width: 40px" path="sublists[${varStatus.index}].location"/>
		<img class="moveDown" src="image/icon_down.gif" alt="move down"/>
		&nbsp;
		<img class="moveUp" src="image/icon_up.gif" alt="move up"/>
		&nbsp;
		<img class="delete" src="image/icon_delete.gif" alt="delete"/>
		<br>
		<br>
	</c:forEach>

	</td>
</tr>

<tr>
	<td>
		&nbsp;
	</td>
</tr>
<a name="bottom">

<tr>
	<td>
		הוספת רשימה בסיסית:
	</td>
	<td>

	<select id="sublists" name="sublistId">
		<option value="0">בחר\י רשימה להוספה </option>
		<c:forEach items="${lists}" var="list">
			<option value="${list.id}">${list.name}</option>
		</c:forEach>
	</select>
	</td>
</tr>



