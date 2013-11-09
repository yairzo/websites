function getCookie(c_name){
if (document.cookie.length>0){
  c_start=document.cookie.indexOf(c_name + "=");
  if (c_start!=-1){
    c_start=c_start + c_name.length+1;
    c_end=document.cookie.indexOf(";",c_start);
    if (c_end==-1)
    	c_end=document.cookie.length;
    return unescape(document.cookie.substring(c_start,c_end));
  }
}
return "";
}

function setCookie(c_name,value,expiredays){
var exdate=new Date();
exdate.setDate(exdate.getDate()+expiredays);
document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function delCookie (c_name) {
    var exdate = new Date();
    document.cookie = c_name + "=" +escape(" ")+
    	";expires="+exdate.toGMTString();
}




function checkCookie(currentLang)
{
lang=getCookie('lang');
if (lang!='null' && lang!="" && currentLang!='null'){
  if (lang!=currentLang){
  	delCookie("lang");
	setCookie("lang",currentLang,365);
  }
}else{
  if (currentLang==null)
  	currentLang="eng";
  setCookie('lang',currentLang,365);
}
}
function manageLanguage(){
	lang = getCookie("lang");
	if (lang!=null && lang!=""){
		if (window.location.href.indexOf("lang=")==-1){
			href1 = window.location.href;
			href1 = href1 + (href1.indexOf("?")==-1 ?  "?lang=" : "&lang=") + getCookie("lang");
			window.location.href = href1;
		}
	}
}