<table width="500" border=0  cellspacing=2 cellpadding=2 rules="groups" dir="${lang.dir}">

				<tr>
                  <th colspan="8" align="center" valign="middle" bgcolor="#ded6b4"><fmt:message key="${lang.localeId}.post.selectSubjects"/> </th>
                </tr>

                <c:forEach items="${rootSubject.subSubjectsBeans}" var="subject">


                  <tr class="darker" id="subject${subject.id}">

                    <td width="28" align="${lang.align}" class="toggleSubject"><img id="subject${subject.id}Minus" class="minus" src="image/minus.gif" alt="-" width="25" height="25"><img id="subject${subject.id}ImgPlus" class="plus" src="image/plus.gif" alt="+" width="25" height="25"></td>
                    <td width="20" align="${lang.align}" class="selectSubject"><img src="image/emptySquare.gif" class="empty" id="${subject.id}"><img src="image/minusSquare.gif" class="partly" id="${subject.id}"><img src="image/vSquare.gif" class="v" id="${subject.id}"></td>
                    <td width="18" align="${lang.align}">&nbsp;</td>
                    <td colspan="3" width="496" align="${lang.align}"><c:out value="${subject.name}"/></td>
                  </tr>


				<tbody id="subject${subject.id}Sub" class="subSubjects">

                  <c:forEach items="${subject.subSubjectsBeans}" var="subSubject">



                  <tr class="brighter">
                    <td align="${lang.align}">&nbsp;</td>
                    <td align="${lang.align}">&nbsp;</td>
                    <td align="${lang.align}">&nbsp;</td>
                    <td align="${lang.align}">&nbsp;</td>
                    <td align="${lang.align}"><form:checkbox  id="${subject.id}.${subSubject.id}" cssClass="subSubject" path="subjectsIds" value="${subSubject.id}"/></td>
                    <td align="${lang.align}"><c:out value="${subSubject.name}"/></td>
                  </tr>


                 </c:forEach>

                 </tbody>


               </c:forEach>



                <tr>
                  <td colspan="8" align="center" valign="middle" bgcolor="#ded6b4">
                  	<fmt:message key="${lang.localeId}.post.selectAll"/>
                    <input id="selectAll" type="checkbox" name="checkbox" value="checkbox">
                   	<fmt:message key="${lang.localeId}.post.cleanAll"/>
                    <input id="diselectAll" type="checkbox" name="checkbox" value="checkbox">
                 </td>
                </tr>
               </table>
