<%@ page language="java"  info="HUARD test page" import="site.actions.*" %>
<%@ page pageEncoding="UTF-8" %>
<jsp:useBean id="MainMenuBar" scope="session" class="site.actions.MainMenuBar" />
<jsp:setProperty name="MainMenuBar" property="*"/>
<jsp:useBean id="profileExecuter" scope="page" class="huard3.profileExecuter.PubDateProfileExecuter" />
<jsp:setProperty name="profileExecuter" property="*"/>
<jsp:useBean id="pageExecuter" scope="page" class="huard3.profileExecuter.PubPageExecuter" />
<jsp:setProperty name="pageExecuter" property="*"/>
<!--<jsp:useBean id="authorizer" scope="session" class="huard3.actions.Authorizer"/>
<jsp:setProperty name="authorizer" property="*"/>//-->
<!--//-->
<html>
<head>
<!--//-->

<title>The Authority for Research & Development</title>
<%@ include file="menuHead.jsp" %>
<link href="style_ard.css" rel="stylesheet" type="text/css">

</head>


<body onResize="window.location.reload()" topmargin="0" marginheight="0" >


<% MainMenuBar.setMainPage(true); %>

<%@ include file="menuBody.jsp" %>



<center>
<%if (!MainMenuBar.getEng()){%>

<TABLE cellSpacing=0 cellPadding=0 border=0 width="700">
        <TBODY>
       <TR>
        <TD><a href = "pubDateProfileExecuter.jsp?news=true&docType=Funding">
  <IMG height=120 src="images/icon_news_heb.gif" width=120 border=0></a></TD>
          <TD><a href = "calendarProfileExecuter.jsp"><IMG height=120 src="images/icon_calendar_heb.gif"
            width=120 border=0></a></TD>
          <TD><a href = "docTypeProfileExecuter.jsp?docType=Research Grant"><IMG height=120 src="images/icon_funding_heb.gif"
            width=120 border=0></a></TD>
          <TD><a href = "docTypeProfileExecuter.jsp?docType=Scholarship"><IMG height=120 src="images/icon_scholar_heb.gif"
            width=120 border=0></a></TD>
          <TD rowSpan=2>

	  <table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="images/icon_events_heb_top.gif" width="230" height="50"></td>
  </tr>
  <tr>
    <td background="images/icon_events_bottom.gif" height="130">
    <script language="JavaScript1.2">

/* USE WORDWRAP AND MAXIMIZE THE WINDOW TO SEE THIS FILE
========================================
 V-NewsTicker v2.2
 License : Freeware (Enjoy it!)
 (c)2003 VASIL DINKOV- PLOVDIV, BULGARIA
========================================
 For IE4+, NS4+, Opera5+, Konqueror3.1+
========================================
 Get the NewsTicker script at:
 http://www.smartmenus.org/other.php
 and don't wait to get the Great SmartMenus script at:
 http://www.smartmenus.org
 LEAVE THESE NOTES PLEASE - delete the comments if you want */

// BUG in Opera:
// If you want to be able to control the body margins
// put the script right after the BODY tag, not in the HEAD!!!

// === 1 === FONT, COLORS, EXTRAS...
v_font='arial';
v_fontSize='12px';
v_fontSizeNS4='11px';
v_fontWeight='normal';
v_fontColor='#4A49A8';
v_textDecoration='none';
v_fontColorHover='#ff0000';//		| won't work
v_textDecorationHover='underline';//	| in Netscape4
v_bgColor='url(bg.jpg)';
// set [='transparent'] for transparent
// set ='url(a.jpg)'
v_top=-12;//	|
v_left=26;//	relativeng  13
v_width=192;//	| the box  213
v_height=120;//	|
v_paddingTop=2;
v_paddingLeft=2;// 2
v_position='relative';// absolute/relative
v_timeout=1500;//1000 = 1 second 4500
v_slideSpeed=5;
v_slideDirection=0;//0=down-up;1=up-down
v_pauseOnMouseOver=true;
// v2.2+ new below
v_slideStep=1;//pixels
v_textAlign='center';// left/center/right
v_textVAlign='middle';// top/middle/bottom - won't work in Netscape4

// === 2 === THE CONTENT - ['href','text','target']
// Use '' for href to have no link item
v_content=[
            <% for(int j=0; j<pageExecuter.getToRollingMessages().length && pageExecuter.getToRollingMessages()[0].getArdNum()!=0; j++){%>
	   ['http://ard.huji.ac.il/<%= profileExecuter.getVersionName()%>/pubPageExecuter.jsp?ardNum=<%= pageExecuter.getToRollingMessages()[j].getArdNum()%>','<%if( pageExecuter.getToRollingMessages()[j].getArdNum()==167){%><B><%}else{%><asd><%}%><%if(pageExecuter.isHebrew(pageExecuter.getToRollingMessages()[j].getTitle())){%><div align="right"><%}else{%><div align="left"><%}%> <%= pageExecuter.getFormatedDate(pageExecuter.getToRollingMessages()[j].getPubDate())%><br><%= MainMenuBar.markApostrofWithBackSlash(pageExecuter.getToRollingMessages()[j].getTitle())%><%if( pageExecuter.getToRollingMessages()[j].getArdNum()==167){%></B>',''],<%}else{%><asd>',''],<%}%>
	   <%}%>


	   <!--['http://sites.huji.ac.il/rmp','<div align ="center"><B>Career Opportunities - The Hebrew University Joins the European Researchers Mobility Network</B>','_blank'],//-->

	   <%for(int i=0; i<3; i++){%>
	       <%if (profileExecuter.getInfoPagesSortedByPubDate()[i].isHasTabledVersion()){%>
	     ['http://ard.huji.ac.il/<%=profileExecuter.getVersionName()%>/pageExecuter.jsp?ardNum=<%=profileExecuter.getInfoPagesSortedByPubDate()[i].getArdNum()%>','<%if(pageExecuter.isHebrew(profileExecuter.getInfoPagesSortedByPubDate()[i].getTitle())){%><div align="right"><%}else{%><div align="left"><%}%><%=pageExecuter.getFormatedDate(profileExecuter.getInfoPagesSortedByPubDate()[i].getPubDate())%><br><%=MainMenuBar.markApostrofWithBackSlash(profileExecuter.getInfoPagesSortedByPubDate()[i].getTitle())%><br><%if(pageExecuter.isHebrew(profileExecuter.getInfoPagesSortedByPubDate()[i].getTitle())){%>תאריך הגשה - <%}else{%>Submission Date -<%}%> <%=profileExecuter.getSubmissionExpressionByInfoPage(profileExecuter.getInfoPagesSortedByPubDate()[i])%>',''],
                <%}%>
	    <%}%>
	   ['','','']
];

// ===
v_ua=navigator.userAgent;v_nS4=document.layers?1:0;v_iE=document.all&&!window.innerWidth&&v_ua.indexOf("MSIE")!=-1?1:0;v_oP=v_ua.indexOf("Opera")!=-1&&document.clear?1:0;v_oP7=v_oP&&document.appendChild?1:0;v_oP4=v_ua.indexOf("Opera")!=-1&&!document.clear;v_kN=v_ua.indexOf("Konqueror")!=-1&&parseFloat(v_ua.substring(v_ua.indexOf("Konqueror/")+10))<3.1?1:0;v_count=v_content.length-1;v_cur=1;v_cl=0;v_d=v_slideDirection?-1:1;v_TIM=0;v_fontSize2=v_nS4&&navigator.platform.toLowerCase().indexOf("win")!=-1?v_fontSizeNS4:v_fontSize;v_canPause=0;function v_getOS(a){return v_iE?document.all[a].style:v_nS4?document.layers["v_container"].document.layers[a]:document.getElementById(a).style};function v_start(){var o,px;o=v_getOS("v_1");px=v_oP&&!v_oP7||v_nS4?0:"px";if(parseInt(o.top)==v_paddingTop){v_canPause=1;if(v_count>1)v_TIM=setTimeout("v_canPause=0;v_slide()",v_timeout);return}o.top=(parseInt(o.top)-v_slideStep*v_d)*v_d>v_paddingTop*v_d?parseInt(o.top)-v_slideStep*v_d+px:v_paddingTop+px;if(v_oP&&o.visibility.toLowerCase()!="visible")o.visibility="visible";setTimeout("v_start()",v_slideSpeed)};function v_slide(){var o,o2,px;o=v_getOS("v_"+v_cur);o2=v_getOS("v_"+(v_cur<v_count?v_cur+1:1));px=v_oP&&!v_oP7||v_nS4?0:"px";if(parseInt(o2.top)==v_paddingTop){if(v_oP)o.visibility="hidden";o.top=v_height*v_d+px;v_cur=v_cur<v_count?v_cur+1:1;v_canPause=1;v_TIM=setTimeout("v_canPause=0;v_slide()",v_timeout);return}if(v_oP&&o2.visibility.toLowerCase()!="visible")o2.visibility="visible";if((parseInt(o2.top)-v_slideStep*v_d)*v_d>v_paddingTop*v_d){o.top=parseInt(o.top)-v_slideStep*v_d+px;o2.top=parseInt(o2.top)-v_slideStep*v_d+px}else{o.top=-v_height*v_d+px;o2.top=v_paddingTop+px}setTimeout("v_slide()",v_slideSpeed)};if(v_nS4||v_iE||v_oP||document.getElementById&&!v_kN&&!v_oP4){
document.write("<style>.vnewsticker,a.vnewsticker{text-align: right;font-family:"+v_font+";font-size:"+v_fontSize2+";color:"+v_fontColor+";text-decoration:"+v_textDecoration+";font-weight:"+v_fontWeight+"}a.vnewsticker:hover{font-family:"+v_font+";font-size:"+v_fontSize2+";color:"+v_fontColorHover+";text-decoration:"+v_textDecorationHover+"}</style>");
v_temp="<div "+(v_nS4?"name":"id")+"=v_container style='position:"+v_position+";top:"+v_top+"px;left:"+v_left+"px;width:"+v_width+"px;height:"+v_height+"px;background:"+v_bgColor+";layer-background"+(v_bgColor.indexOf("url(")==0?"-image":"-color")+":"+v_bgColor+";clip:rect(0,"+v_width+","+v_height+",0);overflow:hidden'>"+(v_iE?"<div style='position:absolute;top:0px;left:0px;width:100%;height:100%;clip:rect(0,"+v_width+","+v_height+",0)'>":"");for(v_i=0;v_i<v_count;v_i++)
v_temp+="<div "+(v_nS4?"name":"id")+"=v_"+(v_i+1)+" style='position:absolute;top:"+(v_height*v_d)+"px;left:"+v_paddingLeft+"px;width:"+(v_width-v_paddingLeft*2)+"px;height:"+(v_height-v_paddingTop*2)+"px;clip:rect(0,"+(v_width-v_paddingLeft*2)+","+(v_height-v_paddingTop*2)+",0);overflow:hidden"+(v_oP?";visibility:hidden":"")+";text-align:"+v_textAlign+"' class=vnewsticker>"+(!v_nS4?"<table width="+(v_width-v_paddingLeft*2)+" height="+(v_height-v_paddingTop*2)+" cellpadding=0 cellspacing=0 border=0><tr><td width="+(v_width-v_paddingLeft*2)+" height="+(v_height-v_paddingTop*2)+" align="+v_textAlign+" valign="+v_textVAlign+" class=vnewsticker>":"")+(v_content[v_i][0]!=""?"<a href='"+v_content[v_i][0]+"' target='"+v_content[v_i][2]+"' class=vnewsticker"+(v_pauseOnMouseOver?" onmouseover='if(v_canPause&&v_count>1){clearTimeout(v_TIM);v_cl=1}' onmouseout='if(v_canPause&&v_count>1&&v_cl)v_TIM=setTimeout(\"v_canPause=0;v_slide();v_cl=0\","+v_timeout+")'":"")+">":"<span"+(v_pauseOnMouseOver?" onmouseover='if(v_canPause&&v_count>1){clearTimeout(v_TIM);v_cl=1}' onmouseout='if(v_canPause&&v_count>1&&v_cl)v_TIM=setTimeout(\"v_canPause=0;v_slide();v_cl=0\","+v_timeout+")'":"")+">")+v_content[v_i][1]+(v_content[v_i][0]!=""?"</a>":"</span>")+(!v_nS4?"</td></tr></table>":"")+"</div>";v_temp+=(v_iE?"</div>":"")+"</div>";document.write(v_temp);setTimeout("v_start()",1000);if(v_nS4)onresize=function(){location.reload()}}

</script>



    </td>
  </tr>
  <tr>
    <TD><a href = "keywordsSearchQueryForm.jsp">
	  <img src="images/icon_search_heb.gif" width="230" height="79" border="0"></a></TD>


  </tr>

</table>



	  </TD></TR>
        <TR>
          <TD><a href = "docTypeProfileExecuter.jsp?docType=Researchers Exchange">
	  <IMG height=120 src="images/icon_exchange_heb.gif" width=120 border=0></a></TD>
          <TD><a href = "docTypeProfileExecuter.jsp?docType=Conference">
	  <IMG height=120 src="images/icon_conference_heb.gif" width=120 border=0></a></TD>
          <TD><a href = "http://www.huji.ac.il/cgi-bin/mm/new/data/ihoker/" target="_blank">
	  <IMG height=120 src="images/icon_faculty_heb.gif" width=120 border="0"></TD>
          <TD><a href = "messagesProfileExecuter.jsp?docType=Funding">
	  <IMG height=120 src="images/icon_messages_heb.gif"  width=120 border="0"></a></TD>


	  </TR>
	  </TBODY>


<%} else {%>

<TABLE cellSpacing=0 cellPadding=0 border=0 width="700">
        <TBODY>
        <TR>
          <TD><a href = "pubDateProfileExecuter.jsp?news=true&docType=Funding">
	  <IMG height=120 src="images/icon_news_eng.gif" width=120 border=0></a></TD>
          <TD><a href = "calendarProfileExecuter.jsp"><IMG height=120 src="images/icon_calendar_eng.gif"
            width=120 border=0></a></TD>
          <TD><a href = "docTypeProfileExecuter.jsp?docType=Research Grant">
	  <IMG height=120 src="images/icon_funding_eng.gif" width=120 border=0></TD>
          <TD><a href = "docTypeProfileExecuter.jsp?docType=Scholarship"><IMG height=120 src="images/icon_scholar_eng.gif"
            width=120 border=0></a></TD>
          <TD rowSpan=2>

	  <table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="images/icon_events_eng_top.gif" width="230" height="50"></td>
  </tr>
  <tr>
    <td background="images/icon_events_bottom.gif" height="130">
    <script language="JavaScript1.2">

/* USE WORDWRAP AND MAXIMIZE THE WINDOW TO SEE THIS FILE
========================================
 V-NewsTicker v2.2
 License : Freeware (Enjoy it!)
 (c)2003 VASIL DINKOV- PLOVDIV, BULGARIA
========================================
 For IE4+, NS4+, Opera5+, Konqueror3.1+
========================================
 Get the NewsTicker script at:
 http://www.smartmenus.org/other.php
 and don't wait to get the Great SmartMenus script at:
 http://www.smartmenus.org
 LEAVE THESE NOTES PLEASE - delete the comments if you want */

// BUG in Opera:
// If you want to be able to control the body margins
// put the script right after the BODY tag, not in the HEAD!!!

// === 1 === FONT, COLORS, EXTRAS...
v_font='arial';
v_fontSize='12px';
v_fontSizeNS4='11px';
v_fontWeight='normal';
v_fontColor='#4A49A8';
v_textDecoration='none';
v_fontColorHover='#ff0000';//		| won't work
v_textDecorationHover='underline';//	| in Netscape4
v_bgColor='url(bg.jpg)';
// set [='transparent'] for transparent
// set ='url(a.jpg)'
v_top=-12;//	|
v_left=26;//	relativeng
v_width=192;//	| the box
v_height=120;//	|
v_paddingTop=2;
v_paddingLeft=2;
v_position='relative';// absolute/relative
v_timeout=1500;//1000 = 1 second
v_slideSpeed=5;
v_slideDirection=0;//0=down-up;1=up-down
v_pauseOnMouseOver=true;
// v2.2+ new below
v_slideStep=1;//pixels
v_textAlign='left';// left/center/right
v_textVAlign='middle';// top/middle/bottom - won't work in Netscape4

// === 2 === THE CONTENT - ['href','text','target']
// Use '' for href to have no link item
v_content=[
            <% for(int j=0; j<pageExecuter.getToRollingMessages().length && pageExecuter.getToRollingMessages()[0].getArdNum()!=0; j++){%>
	   ['http://ard.huji.ac.il/<%= profileExecuter.getVersionName()%>/pubPageExecuter.jsp?ardNum=<%= pageExecuter.getToRollingMessages()[j].getArdNum()%>','<%if( pageExecuter.getToRollingMessages()[j].getArdNum()==167){%><B><%}else{%><asd><%}%><%if(pageExecuter.isHebrew(pageExecuter.getToRollingMessages()[j].getTitle())){%><div align="right"><%}else{%><div align="left"><%}%> <%= pageExecuter.getFormatedDate(pageExecuter.getToRollingMessages()[j].getPubDate())%><br><%= MainMenuBar.markApostrofWithBackSlash(pageExecuter.getToRollingMessages()[j].getTitle())%><%if( pageExecuter.getToRollingMessages()[j].getArdNum()==167){%></B>',''],<%}else{%><asd>',''],<%}%>
	   <%}%>


	  <!-- ['http://sites.huji.ac.il/rmp','<div align ="center">Career Opportunities - The Hebrew University Joins the European Researchers Mobility Network','_blank'],//-->

	   <%for(int i=0; i<3; i++){%>
	       <%if (profileExecuter.getInfoPagesSortedByPubDate()[i].isHasTabledVersion()){%>
	     ['http://ard.huji.ac.il/<%= profileExecuter.getVersionName()%>/pageExecuter.jsp?ardNum=<%= profileExecuter.getInfoPagesSortedByPubDate()[i].getArdNum()%>','<%if(pageExecuter.isHebrew(profileExecuter.getInfoPagesSortedByPubDate()[i].getTitle())){%><div align="right"><%}else{%><div align="left"><%}%><%= pageExecuter.getFormatedDate(profileExecuter.getInfoPagesSortedByPubDate()[i].getPubDate())%><br><%= profileExecuter.moveHebrewCharsFromAsciiToHebrewCharset(MainMenuBar.markApostrofWithBackSlash(profileExecuter.getInfoPagesSortedByPubDate()[i].getTitle()))%><br><%if(pageExecuter.isHebrew(profileExecuter.getInfoPagesSortedByPubDate()[i].getTitle())){%> תאריך הגשה - <%}else{%> Submission Date - <%}%><%=profileExecuter.getSubmissionExpressionByInfoPage(profileExecuter.getInfoPagesSortedByPubDate()[i])%>',''],
                <%}%>
	    <%}%>
	   ['','','']
];

// ===
v_ua=navigator.userAgent;v_nS4=document.layers?1:0;v_iE=document.all&&!window.innerWidth&&v_ua.indexOf("MSIE")!=-1?1:0;v_oP=v_ua.indexOf("Opera")!=-1&&document.clear?1:0;v_oP7=v_oP&&document.appendChild?1:0;v_oP4=v_ua.indexOf("Opera")!=-1&&!document.clear;v_kN=v_ua.indexOf("Konqueror")!=-1&&parseFloat(v_ua.substring(v_ua.indexOf("Konqueror/")+10))<3.1?1:0;v_count=v_content.length-1;v_cur=1;v_cl=0;v_d=v_slideDirection?-1:1;v_TIM=0;v_fontSize2=v_nS4&&navigator.platform.toLowerCase().indexOf("win")!=-1?v_fontSizeNS4:v_fontSize;v_canPause=0;function v_getOS(a){return v_iE?document.all[a].style:v_nS4?document.layers["v_container"].document.layers[a]:document.getElementById(a).style};function v_start(){var o,px;o=v_getOS("v_1");px=v_oP&&!v_oP7||v_nS4?0:"px";if(parseInt(o.top)==v_paddingTop){v_canPause=1;if(v_count>1)v_TIM=setTimeout("v_canPause=0;v_slide()",v_timeout);return}o.top=(parseInt(o.top)-v_slideStep*v_d)*v_d>v_paddingTop*v_d?parseInt(o.top)-v_slideStep*v_d+px:v_paddingTop+px;if(v_oP&&o.visibility.toLowerCase()!="visible")o.visibility="visible";setTimeout("v_start()",v_slideSpeed)};function v_slide(){var o,o2,px;o=v_getOS("v_"+v_cur);o2=v_getOS("v_"+(v_cur<v_count?v_cur+1:1));px=v_oP&&!v_oP7||v_nS4?0:"px";if(parseInt(o2.top)==v_paddingTop){if(v_oP)o.visibility="hidden";o.top=v_height*v_d+px;v_cur=v_cur<v_count?v_cur+1:1;v_canPause=1;v_TIM=setTimeout("v_canPause=0;v_slide()",v_timeout);return}if(v_oP&&o2.visibility.toLowerCase()!="visible")o2.visibility="visible";if((parseInt(o2.top)-v_slideStep*v_d)*v_d>v_paddingTop*v_d){o.top=parseInt(o.top)-v_slideStep*v_d+px;o2.top=parseInt(o2.top)-v_slideStep*v_d+px}else{o.top=-v_height*v_d+px;o2.top=v_paddingTop+px}setTimeout("v_slide()",v_slideSpeed)};if(v_nS4||v_iE||v_oP||document.getElementById&&!v_kN&&!v_oP4){
document.write("<style>.vnewsticker,a.vnewsticker{text-align: right;font-family:"+v_font+";font-size:"+v_fontSize2+";color:"+v_fontColor+";text-decoration:"+v_textDecoration+";font-weight:"+v_fontWeight+"}a.vnewsticker:hover{font-family:"+v_font+";font-size:"+v_fontSize2+";color:"+v_fontColorHover+";text-decoration:"+v_textDecorationHover+"}</style>");
v_temp="<div "+(v_nS4?"name":"id")+"=v_container style='position:"+v_position+";top:"+v_top+"px;left:"+v_left+"px;width:"+v_width+"px;height:"+v_height+"px;background:"+v_bgColor+";layer-background"+(v_bgColor.indexOf("url(")==0?"-image":"-color")+":"+v_bgColor+";clip:rect(0,"+v_width+","+v_height+",0);overflow:hidden'>"+(v_iE?"<div style='position:absolute;top:0px;left:0px;width:100%;height:100%;clip:rect(0,"+v_width+","+v_height+",0)'>":"");for(v_i=0;v_i<v_count;v_i++)
v_temp+="<div "+(v_nS4?"name":"id")+"=v_"+(v_i+1)+" style='position:absolute;top:"+(v_height*v_d)+"px;left:"+v_paddingLeft+"px;width:"+(v_width-v_paddingLeft*2)+"px;height:"+(v_height-v_paddingTop*2)+"px;clip:rect(0,"+(v_width-v_paddingLeft*2)+","+(v_height-v_paddingTop*2)+",0);overflow:hidden"+(v_oP?";visibility:hidden":"")+";text-align:"+v_textAlign+"' class=vnewsticker>"+(!v_nS4?"<table width="+(v_width-v_paddingLeft*2)+" height="+(v_height-v_paddingTop*2)+" cellpadding=0 cellspacing=0 border=0><tr><td width="+(v_width-v_paddingLeft*2)+" height="+(v_height-v_paddingTop*2)+" align="+v_textAlign+" valign="+v_textVAlign+" class=vnewsticker>":"")+(v_content[v_i][0]!=""?"<a href='"+v_content[v_i][0]+"' target='"+v_content[v_i][2]+"' class=vnewsticker"+(v_pauseOnMouseOver?" onmouseover='if(v_canPause&&v_count>1){clearTimeout(v_TIM);v_cl=1}' onmouseout='if(v_canPause&&v_count>1&&v_cl)v_TIM=setTimeout(\"v_canPause=0;v_slide();v_cl=0\","+v_timeout+")'":"")+">":"<span"+(v_pauseOnMouseOver?" onmouseover='if(v_canPause&&v_count>1){clearTimeout(v_TIM);v_cl=1}' onmouseout='if(v_canPause&&v_count>1&&v_cl)v_TIM=setTimeout(\"v_canPause=0;v_slide();v_cl=0\","+v_timeout+")'":"")+">")+v_content[v_i][1]+(v_content[v_i][0]!=""?"</a>":"</span>")+(!v_nS4?"</td></tr></table>":"")+"</div>";v_temp+=(v_iE?"</div>":"")+"</div>";document.write(v_temp);setTimeout("v_start()",1000);if(v_nS4)onresize=function(){location.reload()}}

</script>



    </td>
  </tr>
  <tr>
    <td><a href = "keywordsSearchQueryForm.jsp"><img src="images/icon_search_eng.gif" width="230" height="79" border="0"></td>
  </tr>
</table>


	  </TD></TR>
        <TR>
          <TD><a href = "docTypeProfileExecuter.jsp?docType=Researchers Exchange">
	  <IMG height=120 src="images/icon_exchange_eng.gif" width=120 border=0></a></TD>
          <TD><a href = "docTypeProfileExecuter.jsp?docType=Conference"><IMG height=120
            src="images/icon_conference_eng.gif"
            width=120 border=0></a></TD>
          <TD><a href = "http://www.huji.ac.il/cgi-bin/mm/new/data/ihoker/" target="_blank">
	  <IMG height=120 src="images/icon_faculty_eng.gif" width=120 border="0"></TD>
          <TD><a href = "messagesProfileExecuter.jsp?docType=Funding">
	  <IMG height=120 src="images/icon_messages_eng.gif" width=120 border="0"></a></TD>
	  </TR>
	  </TBODY>

	  </TABLE>

      </tr>




</table>

<%}%>
</center>




<%@ include file="allRightsReserved.jsp" %>
<% MainMenuBar.setMainPage(false); %>

</body>
</html>
