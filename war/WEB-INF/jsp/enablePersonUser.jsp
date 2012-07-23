<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>

		<tr>
          <td align="right" bgcolor="#787669" height="20">

		<table width="100%" border="0" dir="rtl">
          			<tr>

          <td align="right">
          		<p class="white">מערכת משולבת הרשות למו"פ > אישור רישום</p>
          </td>
          <td>
          		<c:if test="${userPersonBean != null && fn:length(userPersonBean.firstNameHebrew) > 0}">
          			<p class="white">משתמש: <c:out value="${userPersonBean.degreeFullNameHebrew}"/></p>
          		</c:if>
		  </td>
		  <td align="left">
		    	<a style="text-decoration: none; color: white; font-size:9pt;"  href="j_acegi_logout">צא</a>
		  </td>
        </tr>

      </table>
      </tr>
     </table>

    </td>
  </tr>
  <tr>
    <td>
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468">
        <tr>
          <td valign="top" align="center"><br>


              <table width="500" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>מערכת משולבת - הרשות למו"פ</h1>
                  </td>
                </tr>

              </table>

				<table width="500" border=0  cellspacing=0 cellpadding=2 rules="groups" dir="rtl">




		<tr>
			<th align="right">
				  הרישום הראשוני אושר.
				  <a style="text-decoration: none" href="${cp}?cp=j_acegi_logout<c:choose><c:when test="${id>0}">&id=${id}</c:when><c:otherwise>&action=new</c:otherwise></c:choose>">לחצ/י כאן כדי להמשיך הלאה</a>
				 <br/>
				 <br/>
				 תדרש/י להקליד שוב את שם המשתמש והסיסמה.
			</th>
		</tr>
		                  </table>
                </td>
              </tr>
			  <tbody>


            </table>

            <br>
          </td>
        </tr>
      </table>

    </td>
  </tr>
</table>
</body>
</html>