<%@ page  pageEncoding="UTF-8" %>
		<script type="text/javascript">
		$(document).ready(function() {
			if(('${lang.localeId}')==('en_US')){
				$(".popup_title").addClass("popup_title_english");
				$(".popup").addClass("popup_english");
				$(".popup_close").addClass("popup_close_english");
				$(".popup_search_date").addClass("popup_search_date_english");
				$(".popup_search_financing").addClass("popup_search_financing_english");
			}
			else{
				$(".popup_title").addClass("popup_title_hebrew");
				$(".popup").addClass("popup_hebrew");
				$(".popup_close").addClass("popup_close_hebrew");
				$(".popup_search_date").addClass("popup_search_date_hebrew");
				$(".popup_search_financing").addClass("popup_search_financing_hebrew");
				
			}

			if($('div.popup_sum').html().length>1500){
				var trimmedHtml=$('div.popup_sum').html();
				trimmedHtml = trimmedHtml.substr(0,1500)+"...";
				$('div.popup_sum').html(trimmedHtml);
			}

			
		});


		</script>
		<div class="popup">
			<div class="clearfix">
				<h3 class="popup_title">${command.title}</h3>
				<a onclick="$('.popup_placeholder').hide();return false;" class="popup_close">סגור</a>
			</div>
			<c:if test="${command.expired}">
			<div class="clearfix">
				<h3 class="popup_careful"><fmt:message key="${lang.localeId}.website.isExpired"/></h3>
			</div>
			</c:if>
			<span class="clearfix search_icons">
				<span class="popup_search_financing"><fmt:message key="${lang.localeId}.callForProposal.fund"/></strong>${command.fund.name}</span>
				<span class="popup_search_date"><fmt:message key="${lang.localeId}.callForProposal.submissionTime"/> 
				<strong>
				<c:if test="${command.allYearSubmission}">
					<fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/>
				</c:if>
				<c:if test="${!command.allYearSubmission}">
					${finalSubmissionTime}
				</c:if> 
				</strong>
				</span>
			</span>
			<div class="clearfix popup_sum">
				<p>${command.description}</p>
			</div>
			<a href="#" onclick="$('.popup_placeholder').hide();location.href='/call_for_proposal/${command.urlTitle}';" class="popup_more"><fmt:message key="${lang.localeId}.callForProposal.furtherDetails"/></a>
		</div>
