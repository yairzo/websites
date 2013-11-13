<jsp:useBean id="MainMenuBar" scope="session" class="site.actions.MainMenuBar" />

<script>

/***
This is the menu creation code - place it right after you body tag
Feel free to add this to a stand-alone js file and link it to your page.
**/

//Menu object creation
oCMenu=new makeCM("oCMenu") //Making the menu object. Argument: menuname

oCMenu.frames = 0

//Menu properties
oCMenu.pxBetween=0
oCMenu.fromLeft=0
oCMenu.fromTop=<%if(MainMenuBar.getMainPage()==true)%><%=140%><%else%><%=125%>
oCMenu.rows=1
oCMenu.menuPlacement="center"
oCMenu.align="center"

oCMenu.offlineRoot="file:///C|/Inetpub/wwwroot/dhtmlcentral/projects/coolmenus/examples/"
oCMenu.onlineRoot=""
oCMenu.resizeCheck=0
//oCMenu.onresize="location.reload()" // yair added this 12/10/04
oCMenu.wait=100
oCMenu.fillImg="cm_fill.gif"
oCMenu.zIndex=0
42
//Background bar properties
oCMenu.useBar=1
oCMenu.barWidth="96%"
oCMenu.barHeight="menu"
oCMenu.barClass="clBar"
oCMenu.barX=0
oCMenu.barY=0
oCMenu.barBorderX=0
oCMenu.barBorderY=0
oCMenu.barBorderClass=""

//Level properties - ALL properties have to be spesified in level 0
oCMenu.level[0]=new cm_makeLevel() //Add this for each new level
oCMenu.level[0].width=//(screen.width*0.96)/<%=MainMenuBar.getTopCategoriesArray().length%>

//we should devide the screen width
//(for resolution 1024*768) to the number of bars
                          //we have in this level. it's not good to use % because of the reload method
			  // I added in the body tag
oCMenu.level[0].height=22
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


<% for(int k=0;k<MainMenuBar.getTopCategoriesArray().length; k++) {%>
oCMenu.makeMenu('<%= "top"+k%>','','<div align=center>&nbsp;&nbsp;<%=MainMenuBar.handleHebrew( MainMenuBar.getTopCategoriesArray()[k].getName())%>&nbsp;&nbsp;</div>','<%if(MainMenuBar.getTopCategoriesArray()[k].getLink().length()>4) {%> <%= MainMenuBar.getTopCategoriesArray()[k].getLink()%><%}%>','','<%=MainMenuBar.getTopCategoryArraySize(k)%>','','','','','','','','','','<%if(!MainMenuBar.getEng())%> sox=<%=MainMenuBar.getTopCategoryArraySize(k)%>-<%=MainMenuBar.getMaxStringSize(MainMenuBar.getTopCategoriesArray()[k].getTableName())%>-2')
 <% for(int j=0; j<MainMenuBar.getSubCategoriesArray( MainMenuBar.getTopCategoriesArray()[k].getTableName()).length; j++) {%>

oCMenu.makeMenu(
'<%="sub"+k+j%>',
'<%= "top"+k%>',
'<div align="<%= MainMenuBar.getEng()%>\"left\" : \"right\"%>">&nbsp;&nbsp;<%=MainMenuBar.handleHebrew( MainMenuBar.getSubCategoriesArray(MainMenuBar.getTopCategoriesArray()[k].getTableName())[j].getName())%>&nbsp;&nbsp;</div>','<%if(MainMenuBar.getSubCategoriesArray(MainMenuBar.getTopCategoriesArray()[k].getTableName())[j].getLink().length() >4) {%><%= MainMenuBar.getSubCategoriesArray(MainMenuBar.getTopCategoriesArray()[k].getTableName())[j].getLink()%><%}%>',
'<% if(!MainMenuBar.searchForArd(MainMenuBar.getSubCategoriesArray(MainMenuBar.getTopCategoriesArray()[k].getTableName())[j].getLink()) ||MainMenuBar.searchForPdf(MainMenuBar.getSubCategoriesArray(MainMenuBar.getTopCategoriesArray()[k].getTableName())[j].getLink()) )%><%= "_blank"%>',
'<%= MainMenuBar.getMaxStringSize(MainMenuBar.getTopCategoriesArray()[k].getTableName())%>')

<%}
}%>






//Leave this line - it constructs the menu
cmpage = new cm_page()
oCMenu.construct()
<%MainMenuBar.finishedConstructingBar();%>


</script>
<center>
<% if (MainMenuBar.getMainPage()==true){ %>
<img src="images/header_huji.gif" border="0" usemap="#Map">
<map name="Map">
 <area shape="rect" coords="22,50,281,107" href="menu1.jsp?eng=true" alt="Home Page">
<area shape="rect" coords="397,85,657,107" href="menu1.jsp?eng=false" alt="דף הבית">
<area shape="rect" coords="414,56,657,84" href="http://www.huji.ac.il" alt="אתר האוניברסיטה העברית" target=_blank>
<area shape="rect" coords="658,38,687,107" href="http://www.huji.ac.il" alt="אתר האוניברסיטה העברית" target=_blank>
</map>
<%} else {%><img src="images/inner_header.gif" border="0" usemap="#Map2">

<map name="Map2">
 <area shape="rect" coords="40,50,291,95" href="menu1.jsp?eng=true" alt="Home Page">
<area shape="rect" coords="397,65,607,92" href="menu1.jsp?eng=false" alt="דף הבית">
<area shape="rect" coords="400,46,630,74" href="http://www.huji.ac.il" alt="אתר האוניברסיטה העברית" target=_blank>
<area shape="rect" coords="648,28,670,117" href="http://www.huji.ac.il" alt="אתר האוניברסיטה העברית" target=_blank>
</map>
<%}%>

</center>

<br>



