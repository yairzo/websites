<%@page import="huard.website.menu.MenuSubCategory"%>
<%@page import="huard.website.menu.MenuCategory"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="language" scope="session" class="huard.website.session.Language" />

<jsp:useBean id="topMenu" scope="application" class="huard.website.menu.TopMenu" />

<jsp:useBean id="isMainPage" scope="session" class="huard.website.util.BooleanWrapper" />

<script>
//Menu object creation
oCMenu=new makeCM("oCMenu") //Making the menu object. Argument: menuname

oCMenu.frames = 0

//Menu properties
oCMenu.pxBetween=0
oCMenu.fromLeft=0
oCMenu.fromTop=113
oCMenu.rows=1
oCMenu.menuPlacement="center"
//oCMenu.align="center"
oCMenu.offlineRoot="file:///C|/Inetpub/wwwroot/dhtmlcentral/projects/coolmenus/examples/"
oCMenu.onlineRoot=""
oCMenu.resizeCheck=0
//oCMenu.onresize="location.reload()" // yair added this 12/10/04
oCMenu.wait=100
oCMenu.fillImg="cm_fill.gif"
oCMenu.zIndex=0
//42
//Background bar properties
//oCMenu.useBar=1
//oCMenu.barWidth="96%"
//oCMenu.barHeight="-menu"
//oCMenu.barClass="clBar"
//oCMenu.barX=0
//oCMenu.barY=0
//oCMenu.barBorderX=0
//oCMenu.barBorderY=0
//oCMenu.barBorderClass=""

//Level properties - ALL properties have to be specified in level 0
oCMenu.level[0]=new cm_makeLevel() //Add this for each new level
oCMenu.level[0].width=//(screen.width*0.96)/numberOfCategories

//we should devide the screen width
//(for resolution 1024*768) to the number of bars
                          //we have in this level. it's not good to use % because of the reload method
			  // I added in the body tag
oCMenu.level[0].height=20
oCMenu.level[0].regClass="clLevel0"
oCMenu.level[0].overClass="clLevel0over"
oCMenu.level[0].borderX=0
oCMenu.level[0].borderY=0
oCMenu.level[0].borderClass="clLevel0border"
oCMenu.level[0].offsetX=0
oCMenu.level[0].offsetY=0
oCMenu.level[0].rows=0
oCMenu.level[0].arrow=0
oCMenu.level[0].arrowWidth=0
oCMenu.level[0].arrowHeight=0
oCMenu.level[0].align="bottom"

//EXAMPLE SUB LEVEL[1] PROPERTIES - You have to specify the properties you want different from LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[1]=new cm_makeLevel() //Add this for each new level (adding one to the number)
oCMenu.level[1].width=oCMenu.level[0].width
oCMenu.level[1].height=22
oCMenu.level[1].regClass="clLevel1"
oCMenu.level[1].overClass="clLevel1over"
oCMenu.level[1].borderX=1
oCMenu.level[1].borderY=1
oCMenu.level[1].align="right"
oCMenu.level[1].offsetX=-(oCMenu.level[0].width-2)/2+20
oCMenu.level[1].offsetY=0
oCMenu.level[1].borderClass="clLevel1border"


//EXAMPLE SUB LEVEL[2] PROPERTIES - You have to spesify the properties you want different from LEVEL[1] OR LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[2]=new cm_makeLevel() //Add this for each new level (adding one to the number)
oCMenu.level[2].width=150
oCMenu.level[2].height=20
oCMenu.level[2].offsetX=0
oCMenu.level[2].offsetY=0
oCMenu.level[2].regClass="clLevel2"
oCMenu.level[2].overClass="clLevel2over"
oCMenu.level[2].borderClass="clLevel2border"


/******************************************
Menu item creation:
myCoolMenu.makeMenu(name, parent_name, text, link, target, width, height, regImage, overImage, regClass, overClass , align, rows, nolink, onclick, onmouseover, onmouseout)
*************************************/


<% 
	int k = 0;
	for(MenuCategory menuCategory: topMenu.getCategoriesArray(language.getLang())) {
		String sox = (menuCategory.getWidth() == 0 ? "null" : "" + menuCategory.getWidth())  + "-" + menuCategory.getMaxStringSize() + "-2";
%>
		oCMenu.makeMenu('<%= "top"+k%>','','<div dir="<%=language.isHebrewVer() ? "rtl" : "ltr"%>" align="center"><%= topMenu.getCategoriesArray(language.getLang())[k].getNameApostropheBackslashed()%></div>','','','<%=menuCategory.getWidth()%>','','','','','','','','','',' <% if(language.isHebrewVer()) %>sox=<%=sox%>')

   
<% 
	int j=0;
for(MenuSubCategory menuSubCategory: menuCategory.getSubCategoriesArray()) {%>

oCMenu.makeMenu('<%="sub"+k+j%>','<%= "top"+k%>','<div dir="<%=language.isHebrewVer() ? "rtl" : "ltr"%>" align="<%= language.isHebrewVer() ? "right" : "left" %>">&nbsp;&nbsp;<%= menuSubCategory.getNameApostropheBackslashed()%>&nbsp;&nbsp;</div>',
'<%= menuSubCategory.getLink()%>',
'<%= menuSubCategory.getLinkTarget()%>',
'<%= menuCategory.getMaxStringSize()%>')

<%		j++;
	}
	k++;	
}%>

//Leave this line - it constructs the menu
cmpage = new cm_page()
oCMenu.construct()

</script>




