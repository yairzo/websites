<%@ page  pageEncoding="UTF-8" %>

<table id="listId${listBean.id}" width="100%" cellspacing="0" cellpadding="1" dir="${listBean.horizontalDirection}">

<c:if test="${!iframeView || aCompoundView}">
	<tr>
       <td class="viewList" colspan="<c:out value="${listBean.viewableColumnsCount}"/>" align="${listBean.listDesign.displayNameAlignment}">
       		<c:if test="${editMode}">
       		<a href="" class="alignDisplayName">A</a>
       		<a href="list.html?id=${listBean.id}">
       		</c:if>
       		<b><c:out escapeXml="false" value="${listBean.displayName}"/></b>
       		<c:if test="${editMode}">
       		</a>
       		</c:if>
       </td>
    </tr>

	<c:choose>
    	<c:when test="${listBean.listDesign.showTableHeader}">
    		<c:set var="showTableHeaderText" value="-"/>
    		<c:set var="tableHeaderHeight" value="15px;"/>
    	</c:when>
    	<c:otherwise>
    		<c:set var="showTableHeaderText" value="+"/>
    		<c:set var="tableHeaderHeight" value="1px;"/>
    	</c:otherwise>
    </c:choose>


    <c:if test="${editMode && !aCompoundView}">
    	<tr class="addEntity">
    		<td colspan="<c:out value="${listBean.viewableColumnsCount}"/>">
    			<a class="addEntity" href="#">הוסף</a>
    			<div class="addEntity">
    				<form:input htmlEscape="true" size="50" cssClass="green addEntity"  path="addedEntity"/>
    				<a class="addEntityAction" href="#">הוסף</a>
    			</div>
    		</td>
    	</tr>
    </c:if>
    <c:if test="${editMode}">
    	<tr>
    		<td>
    			<a class="showTableHeader" href="#">th ${showTableHeaderText}</a>
    		</td>
    	</tr>
    </c:if>
 </c:if>



	<tr class="viewList" style="height: ${tableHeaderHeight}">
		<c:forEach items="${listBean.columnBeans}" var="column" varStatus="varStatus">
		
     	<c:set var="columnWidth" value="${column.width}"/>
 		
		<c:choose>
			<c:when test="${! column.hidden }">
				<th class="viewList" width="<c:out value="${columnWidth}%"/>">
					<table>
						<tr>
							<td>
					<c:if test="${editMode}">
						<nobr>
						<c:if test="${varStatus.index!=0}">
						<a class="wideColumn" href="#">&gt;</a>
						</c:if>
						&nbsp;
						<a class="nobrColumn" href="#" style="font-size: 9px">nbr</a>
						<a class="boldColumn" href="#" style="font-size: 9px">B</a>
						<a class="alignColumn" href="#" style="font-size: 9px">A.${column.alignSign}</a>
 						<span style="font-size: 9px">${columnWidth}</span>
 						</nobr>
					</c:if>
						   </td>
						   <th class="fillWidth" style="font-size: 12pt;">
					<c:if test="${listBean.listDesign.showTableHeader}">
					<c:choose>
						<c:when test="${listBean.sortEnabled && column.sortable }"><a href="/viewList.html?id=${listBean.id}&oc=${varStatus.index}<c:if test="${editMode}">&em=1</c:if><c:if test="${iframeView}">&iv=1</c:if>" <c:if test="${iframeView}">target=main</c:if> class="nounderline">
							<b><c:out value="${column.columnDisplayName}"/></b></a>
						</c:when>
						<c:otherwise>
							<b><c:out value="${column.columnDisplayName}"/></b>
						</c:otherwise>
					</c:choose>
					</c:if>
						  </th>
						  <td>
					<c:if test="${editMode}">
						<c:if test="${varStatus.index  != listBean.maxViewableColumnIndex}">
						   		<a class="narrowColumn" href="#">&lt;</a>
						</c:if>
					</c:if>
						   </td>
						</tr>
					</table>
				</th>
			</c:when>
			<c:otherwise>
				<a class="makeWider" style="visibility:hidden"/>
				<a class="makeNarrower" style="visibility:hidden"/>
			</c:otherwise>
			</c:choose>
		</c:forEach>
	</tr>

	 <c:forEach items="${listBean.viewableBeans}" var="viewableBean" varStatus="varStatus">
			<c:choose>
			<c:when test="${varStatus.index % 2 == 1}">
				<c:set var="rowBgBrightness" value="Bright"/>
			</c:when>
			<c:otherwise>
				<c:set var="rowBgBrightness" value="Dark"/>
			</c:otherwise>
			</c:choose>
			<tr class="viewList">
				<c:forEach items="${viewableBean.fields}" var="field" varStatus="varStatus">
				<td class="viewList${rowBgBrightness} viewList" align="${field.align}" style="overflow: hidden;">
					<c:if test="${editMode && varStatus.index==0 && listBean.editableAttribution}">
						<a href="/personAttribution.html?id=${viewableBean.id}&cp=viewList.html&cpoi=${listBean.id}">E</a>
					</c:if>
					<c:choose>
						<c:when test="${field.isImage}">
							<img src="/imageViewer?urlTitle=${field.text}&attachType=bodyImage" height="50px" width="50px"/>
						</c:when>
						<c:otherwise>
							<c:out escapeXml="false" value="${field.prefix}"/><c:out escapeXml="false" value="${field.text}"/><c:out escapeXml="false" value="${field.suffix}"/>
						</c:otherwise>
					</c:choose>
				</td>
				</c:forEach>
			</tr>
		</c:forEach>

		<tr style="height: ${listBean.listDesign.bottomPadding}px">
			<td>
				<c:if test="${editMode}">
       				<a href="#" class="increaseBottomPadding">+</a> / <a href="#" class="decreaseBottomPadding">-</a>
       			</c:if>
				&nbsp;
			</td>
		</tr>
</table>

