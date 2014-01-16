<%@ page  pageEncoding="UTF-8" %>
<script language="Javascript">

	var submission = false;
	var saved = true;

	function isAnySubjectChecked(){
		var aSubjectChecked = false;
		$("input.subSubject").each(function(){
			if (this.checked == true)
				aSubjectChecked = true;
		});
		return aSubjectChecked;
	}

	function isAnySendTimeChecked(){
		var aSendTimeChecked = false;
		$("input.postReceiveImmediately").each(function(){
			if (this.checked == true)
				aSendTimeChecked = true;
		});
		$("input.postReceiveDay").each(function(){
			if (this.checked == true)
				aSendTimeChecked = true;
		});
		return aSendTimeChecked;
	}

	$(document).ready(function() {

		$.alerts.okButton = '<fmt:message key="${lang.localeId}.general.approve"/>';
		$.alerts.cancelButton = '<fmt:message key="${lang.localeId}.general.cancel"/>';

		$("button.action").click(function(){
			scroll(0,0);
		});

		$("input").click(function(){
			saved = false;
		});

		$("form#form").submit(function(){
			return submission;
		});

		$('.subscriptionCancel').click(function(){
			closeAll();
			if (saved == false){
				$.alerts.confirm('<fmt:message key="${lang.localeId}.post.subscriptionCancelNotSaved"/>',
   		 			'<fmt:message key="${lang.localeId}.eqfSystem.editProposal.confirm.title"/>',
    				function(confirm){
    					if(confirm==1){
    						submission = true;
    						$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
							$('form#form').submit();
	    				}
	    				else{
	    					return false;
	    				}
	   			 });
	    		return false;
	    	}
	    	else{
	    		submission = true;
    			$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
				$('form#form').submit();
	    	}
   		 });

   		 $('.done').click(function(){
   			closeAll();
			if ((isAnySubjectChecked() && isAnySendTimeChecked()) || !$(".receivePosts").prop('checked')){
				$.alerts.confirm('<fmt:message key="${lang.localeId}.post.subscriptionDone"/>',
   		 			'<fmt:message key="${lang.localeId}.eqfSystem.editProposal.confirm.title"/>',
    				function(confirm){
    					if(confirm==1){
    						submission = true;
    						$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"done\"/>');
							$('form#form').submit();
	    				}
	    				else{
	    					return false;
	    				}
	   			});
	    	}
	    	else{
	    		if ($(".receivePosts").prop('checked') && isAnySubjectChecked()){
	    			$.alerts.confirm('<fmt:message key="${lang.localeId}.post.subscriptionCancelNoSubject"/>',
   		 				'<fmt:message key="${lang.localeId}.eqfSystem.editProposal.confirm.title"/>',
    					function(confirm1){
    						if(confirm1==1){
    							submission = true;
    							$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"done\"/>');
    							$('form#form').submit();
	   						}
	   					}
	   				);
	   			}
	   			if ($(".receivePosts").prop('checked') && !isAnySendTimeChecked()){
	    			$.alerts.confirm('<fmt:message key="${lang.localeId}.post.subscriptionCancelNoSendTime"/>',
   		 				'<fmt:message key="${lang.localeId}.eqfSystem.editProposal.confirm.title"/>',
    					function(confirm2){
    						if(confirm2==1){
    							submission = true;
    							$('#postReceiveImmediately').prop('checked', true);
    							//$('form#form').append('<input type=\"hidden\" name=\"postReceiveImmediately\" value=\"true\"/>');
    							$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"done\"/>');
    							$('form#form').submit();
	    					}
	   					}
	   				);
	   			}
	    	}
   		 });

    	$('.save').click(function(){
    		submission = true;
    		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"save\"/>');
    		$("form#form").submit();
    	});
  });
</script>




<tr>
	<td align="right" bgcolor="#787669" height="20">
		<c:set var="applicationName" value="מערכת דיוור"/>
         <c:set var="pageName" value="בחירת נושאים וזמני קבלת הודעות"/>
       	 <%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
	</td>
</tr>
</table>

</td>
</tr>
<tr>
	<td>
	<table width="700" border="1" align="center" cellpadding="0"
		cellspacing="0" bordercolor="#767468" dir="${lang.dir}">
		<tr>
			<td valign="top" align="center"><br>
			<form:form id="form" name="form" method="POST" action="personPost.html"
				commandName="command">


				<form:hidden path="id" />
				<input type="hidden" name="cp" value="${cp}"/>
				<input type="hidden" name="cpoi" value="${cpoi}"/>


				<table width="600" border="0" align="center" cellpadding="2"
					cellspacing="0">

					<tr>
						<td colspan="2">
    							<%@ include file="/WEB-INF/jsp/content/editPost/subjects.jsp" %>
    		              </td>
             </tr>

			  <tr>
			  	<td colspan="2">
					&nbsp;
				</td>
			   </tr>
			  		<tr>
						<td width="200">
							מעוניינ/ת לקבל הודעות דואר אלקטרוני
						</td>
						<td>
 							<form:checkbox cssClass="green receivePosts" path="receivePosts"/>
						</td>
					</tr>
					<tr>
						<td width="200">
							<fmt:message key="${lang.localeId}.post.recieveImmediately"/>
						</td>
						<td>
							<form:checkbox id="postReceiveImmediately" cssClass="postReceiveImmediately grey" path="postReceiveImmediately"/>
						</td>
					</tr>

					<tr class="postReceiveTime">
						<td valign="top">
							<fmt:message key="${lang.localeId}.post.recieveAtPredefinedTimes"/>
						</td>
						<td>
							<table>
								<tr>
									<td>
										<fmt:message key="${lang.localeId}.post.everyday"/>: </td><td><input id="postReceiveEveryday" type="checkbox"/><br/>
									</td>
									</tr>
									<tr>
									<td>
							 <fmt:message key="${lang.localeId}.general.dayOfWeek.1"/>:</td><td><form:checkbox cssClass="postReceiveDay" path="postReceiveDays" value="1"/><br/>
							 		</td>
							 		</tr>
							 		<tr>
							 		<td>
							 <fmt:message key="${lang.localeId}.general.dayOfWeek.2"/>:</td><td><form:checkbox cssClass="postReceiveDay" path="postReceiveDays" value="2"/><br/>
							 		</td>
							 		</tr>
							 		<tr>
							 		<td>
							 <fmt:message key="${lang.localeId}.general.dayOfWeek.3"/>:</td><td><form:checkbox cssClass="postReceiveDay" path="postReceiveDays" value="3"/><br/>
							 		</td>
							 		</tr>
							 		<tr>
							 		<td>
							 <fmt:message key="${lang.localeId}.general.dayOfWeek.4"/>:</td><td><form:checkbox cssClass="postReceiveDay" path="postReceiveDays" value="4"/><br/>
							 		</td>
							 		</tr>
							 		<tr>
							 		<td>
							 <fmt:message key="${lang.localeId}.general.dayOfWeek.5"/>:</td><td><form:checkbox cssClass="postReceiveDay" path="postReceiveDays" value="5"/>
							 		</td>
							 </tr>
							</table>
						</td>
					</tr>
					<tr class="postReceiveTime">
						<td>
							<fmt:message key="${lang.localeId}.post.atHour"/>:
						</td>
						<td>
							<form:select path="postReceiveHour">
								<c:forEach var="x" begin="0" end="23" step="1">
									<form:option value="${x}">${x}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>

					<tr>
						<td width="200">
							<fmt:message key="${lang.localeId}.post.readsUTF8Mails"/>
						</td>
						<td>
							<form:checkbox id="readsUTF8Mails" cssClass="grey" path="readsUTF8Mails"/>
						</td>
					</tr>




					<tr>
						<td colspan="2">&nbsp;<br>
						<img src="/image/hr.gif" width="580" height="10"><br>
						&nbsp;</td>
					</tr>

					<tr>
						<td>
						<button class="save grey action"><fmt:message key="${lang.localeId}.post.save"/></button>

						&nbsp;&nbsp;

						<button class="done grey action"><fmt:message key="${lang.localeId}.post.saveAndDone"/></button>

						&nbsp;&nbsp;

						<button class="subscriptionCancel grey action"><fmt:message key="${lang.localeId}.post.cancel"/></button>

						</td>
					</tr>




				</table>

				<br></td>
		</tr>
	</table>

	</form:form></td>
</tr>

</table>
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>


</body>
</html>
