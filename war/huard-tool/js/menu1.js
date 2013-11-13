

/*** 
This is the menu creation code - place it right after you body tag
Feel free to add this to a stand-alone js file and link it to your page.
**/

//Menu object creation
oCMenu=new makeCM("oCMenu") //Making the menu object. Argument: menuname

oCMenu.frames = 0


//Menu properties   
oCMenu.pxBetween=0
oCMenu.fromLeft=20 
oCMenu.fromTop=0   
oCMenu.rows=1 
oCMenu.menuPlacement="left"
                                                             
oCMenu.offlineRoot="" 
oCMenu.onlineRoot="/coolmenus/" 
oCMenu.resizeCheck=0 
oCMenu.wait=1000 
oCMenu.fillImg="cm_fill.gif"
oCMenu.zIndex=0

//Background bar properties
oCMenu.useBar=1
oCMenu.barWidth="100%"
oCMenu.barHeight="menu" 
oCMenu.barClass="clBar"
oCMenu.barX=0 
oCMenu.barY=0
oCMenu.barBorderX=0
oCMenu.barBorderY=0
oCMenu.barBorderClass=""

//Level properties - ALL properties have to be spesified in level 0
oCMenu.level[0]=new cm_makeLevel() //Add this for each new level
oCMenu.level[0].width=220 //we should devide the screen width (for resolution 1024*768) to the number of bars
                          //we have in this level. it's not good to use % because of the reload method 
			  // I added in the body tag
oCMenu.level[0].height=25 
oCMenu.level[0].regClass="clLevel0"
oCMenu.level[0].overClass="clLevel0over"
oCMenu.level[0].borderX=1
oCMenu.level[0].borderY=1
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
oCMenu.makeMenu('top0','','&nbsp;News','example2.html','')
  oCMenu.makeMenu('sub00','top0','Newest news','/news/index.asp')
    oCMenu.makeMenu('sub001','sub00','- New DHTML API released','','',160,0)
		oCMenu.makeMenu('sub002','sub00','- Explorer 7 is out','','',160,0)
		oCMenu.makeMenu('sub003','sub00','- Opera 6 supports innerHTML','','',160,0)
	oCMenu.makeMenu('sub01','top0','News archive','/news/archive.asp')
	
oCMenu.makeMenu('top1','','&nbsp;Scripts','/scripts/index.asp')
	oCMenu.makeMenu('sub10','top1','New scripts','/scripts/index.asp?show=new')
	oCMenu.makeMenu('sub11','top1','All scripts','/scripts/index.asp?show=all')
	oCMenu.makeMenu('sub12','top1','Popular scripts','/scripts/index.asp?show=pop')
	
oCMenu.makeMenu('top2','','&nbsp;Articles','/articles/index.asp')
	oCMenu.makeMenu('sub21','top2','Tutorials','/tutorials/index.asp')
		oCMenu.makeMenu('sub210','sub21','New tutorials','/tutorials/index.asp')
		oCMenu.makeMenu('sub211','sub21','Tutorials archive','/tutorials/archive.asp')
	oCMenu.makeMenu('sub22','top2','Other articles','/articles/index.asp')
		oCMenu.makeMenu('sub220','sub22','New articles','/articles/index.asp?show=new')
		oCMenu.makeMenu('sub221','sub22','Article archive','/articles/archive.asp')

oCMenu.makeMenu('top3','','&nbsp;Forums','http://www.sdf.sdf.sdf/')
	oCMenu.makeMenu('sub30','top3','General','/forums/forum.asp?FORUM_ID=6&CAT_ID=1&Forum_Title=General+DHTML+issues')
	oCMenu.makeMenu('sub31','top3','Scripts','/forums/forum.asp?FORUM_ID=4&CAT_ID=1&Forum_Title=DHTML+Scripts')
	oCMenu.makeMenu('sub32','top3','Crossbrowser','/forums/forum.asp?FORUM_ID=3&CAT_ID=1&Forum_Title=Crossbrowser+DHTML')
	oCMenu.makeMenu('sub33','top3','CoolMenus','/forums/forum.asp?FORUM_ID=2&CAT_ID=1&Forum_Title=CoolMenus')
	oCMenu.makeMenu('sub34','top3','dhtmlcentral.com','/forums/forum.asp?FORUM_ID=5&CAT_ID=1&Forum_Title=dhtmlcentral%2Ecom')
	oCMenu.makeMenu('sub35','top3','Cool sites','/forums/forum.asp?FORUM_ID=1&CAT_ID=1&Forum_Title=Cool+sites')

oCMenu.makeMenu('top5','','&nbsp;CoolMenus','mailto:test.html')
	oCMenu.makeMenu('sub50','top5','Examples','/coolmenus/examples.asp')
		oCMenu.makeMenu('sub500','sub50','With frames','/coolmenus/examples.asp?show=with')
		oCMenu.makeMenu('sub501','sub50','Without frames','/coolmenus/examples.asp?show=without')
	oCMenu.makeMenu('sub51','top5','Download','/coolmenus/download.asp')
		oCMenu.makeMenu('sub510','sub51','Download the source code to this menu','/coolmenus/download.asp','',150,40)
	oCMenu.makeMenu('sub52','top5','Tutorial','/coolmenus/tutorial.asp')
		oCMenu.makeMenu('sub520','sub52','Learn how to set up the menu','/coolmenus/tutorial.asp','',150,40)
	oCMenu.makeMenu('sub53','top5','MenuMaker','','',0,0,'','','','','','','','window.open("/coolmenus/maker/","","width=800,height=600")')
		oCMenu.makeMenu('sub530','sub53','Use the menuMaker to make the menu code for you','','',150,40,'','','','','','','','window.open("/coolmenus/maker/","","width=800,height=600")')
	oCMenu.makeMenu('sub54','top5','FAQ','/coolmenus/faq.asp')
		oCMenu.makeMenu('sub540','sub54','Frequently asked questions','coolmenus/faq.asp','',150,40)
	oCMenu.makeMenu('sub55','top5','Help forum','/forums/forum.asp?FORUM_ID=2&CAT_ID=1&Forum_Title=CoolMenus')
		oCMenu.makeMenu('sub550','sub55','Go to this forum and post you problems or suggestions regarding the CoolMenus','/forum/forum.asp?forum_id=2','',150,40)

//Leave this line - it constructs the menu
oCMenu.construct()		



