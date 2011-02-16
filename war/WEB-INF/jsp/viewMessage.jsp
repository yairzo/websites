<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>

<script>
	function NoConfirm ()
	{
		win = top;
		win.opener = top;
		win.close ();
	}

	$(document).ready(function() {
		$.alerts.okButton = 'אישור';
		$.alerts.cancelButton = 'ביטול';
		$.alerts.confirm('${userMessage}','<fmt:message key="iw_IL.eqfSystem.editProposal.confirm.title"/>',
			function(){
				window.location='${cp}';
			});
	});
</script>

		<tr>
          <td align="right" bgcolor="#787669" height="20">

		<table width="100%" border="0" dir="rtl">
          			<tr>

          <td align="right">
          		<p class="white">מערכת משולבת הרשות למו"פ > רישום</p>
          </td>
          <td>
          			<c:if test="${fn:length(userPersonBean.firstNameHebrew)>0}">
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


              <table width="400" border="0" align="center" cellpadding="3" dir="rtl">
                <tr>
                  <td colspan="2" align="center"><h1>מערכת משולבת - הרשות למו"פ</h1>
                  </td>
                </tr>

              </table>
             </td>
            </tr>
           </table>
          </td>
         </tr>
       </table>
</body>
</html>

