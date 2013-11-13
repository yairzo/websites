//Menu object creation
oCMenu2=new makeCM("oCMenu2") //Making the menu object. Argument: menuname

//Menu properties   
oCMenu2.pxBetween=0
oCMenu2.fromLeft=5 
oCMenu2.fromTop=0   
oCMenu2.rows=1 
oCMenu2.menuPlacement="right"
                                                             
oCMenu2.offlineRoot="" 
oCMenu2.onlineRoot="" 
oCMenu2.resizeCheck=1 
oCMenu2.wait=1000 
oCMenu2.fillImg="cm_fill.gif"
oCMenu2.zIndex=0

//Background bar properties
oCMenu2.useBar=0
oCMenu2.barWidth="menu"
oCMenu2.barHeight="menu" 
oCMenu2.barClass="cl2Bar"
oCMenu2.barX=0 
oCMenu2.barY=0
oCMenu2.barBorderX=0
oCMenu2.barBorderY=0
oCMenu2.barBorderClass=""

//Level properties - ALL properties have to be spesified in level 0
oCMenu2.level[0]=new cm_makeLevel() //Add this for each new level
oCMenu2.level[0].width=100
oCMenu2.level[0].height=25 
oCMenu2.level[0].regClass="cl2Level0"
oCMenu2.level[0].overClass="cl2Level0over"
oCMenu2.level[0].borderX=1
oCMenu2.level[0].borderY=1
oCMenu2.level[0].borderClass="cl2Level0border"
oCMenu2.level[0].offsetX=0
oCMenu2.level[0].offsetY=0
oCMenu2.level[0].rows=0
oCMenu2.level[0].arrow=0
oCMenu2.level[0].arrowWidth=0
oCMenu2.level[0].arrowHeight=0
oCMenu2.level[0].align="bottom"


//EXAMPLE SUB LEVEL[1] PROPERTIES - You have to specify the properties you want different from LEVEL[0] - If you want all items to look the same just remove this
oCMenu2.level[1]=new cm_makeLevel() //Add this for each new level (adding one to the number)
oCMenu2.level[1].width=oCMenu2.level[0].width-2
oCMenu2.level[1].height=22
oCMenu2.level[1].regClass="cl2Level1"
oCMenu2.level[1].overClass="cl2Level1over"
oCMenu2.level[1].borderX=1
oCMenu2.level[1].borderY=1
oCMenu2.level[1].align="right" 
oCMenu2.level[1].offsetX=-(oCMenu2.level[0].width-2)/2+20
oCMenu2.level[1].offsetY=0
oCMenu2.level[1].borderClass="cl2Level1border"


/******************************************
Menu item creation:
myCoolMenu.makeMenu(name, parent_name, text, link, target, width, height, regImage, overImage, regClass, overClass , align, rows, nolink, onclick, onmouseover, onmouseout) 
*************************************/

var accessedInputControl
var originalText


oCMenu2.makeMenu('top0','','Undo','','','','','','','','','','','','undo(accessedInputControl)')

oCMenu2.makeMenu('top1','','Insert','','')
  oCMenu2.makeMenu('sub10','top1','Break','','','','','','','','','','','','insertBR(accessedInputControl)')
  oCMenu2.makeMenu('sub11','top1','Omit','','','','','','','','','','','','insertOmitMarker(accessedInputControl)')
  
    
	
oCMenu2.makeMenu('top2','','Mark Text','')
  oCMenu2.makeMenu('sub20','top2','Bold','','','','','','','','','','','','makeSelectionBold(accessedInputControl)')
  oCMenu2.makeMenu('sub21','top2','Title','','','','','','','','','','','','makeSelectionTitle(accessedInputControl)')	
  oCMenu2.makeMenu('sub22','top2','U & C','','','','','','','','','','','','makeSelectionUnderlineAndColor(accessedInputControl)')	
  oCMenu2.makeMenu('sub23','top2','* List','','','','','','','','','','','','makeSelectionBulList(accessedInputControl)')
  
  oCMenu2.makeMenu('sub24','top2','123 List','','','','','','','','','','','','makeSelectionNumList(accessedInputControl)')
  
  oCMenu2.makeMenu('sub25','top2','List Line','','','','','','','','','','','','makeSelectionListLine(accessedInputControl)')
  
  oCMenu2.makeMenu('sub26','top2','Link','','','','','','','','','','','','makeSelectionLink(accessedInputControl)')		

	

//Leave this line - it constructs the menu
oCMenu2.construct()



function accessInputControl(accessed){
	accessedInputControl = accessed
	originalText = accessedInputControl.value
}


function insertBR(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <br> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+6,cursorOriginalPosition+6) 
accessedInputControl.focus()


}

function insertOmitMarker(accessedInputControl)
{
accessedInputControl.value = 'xxxxx'
}


function makeSelectionBold(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <b> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </b> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+11,cursorOriginalPosition+11) 
accessedInputControl.focus()

}


function makeSelectionTitle(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
'<br><br> <b><u> <font color=red> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + '</font></u></b><br> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+53,cursorOriginalPosition+53) 
accessedInputControl.focus()

}

function makeSelectionUnderlineAndColor(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <u><font color=red> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + '</font></u> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+33,cursorOriginalPosition+33) 
accessedInputControl.focus()

}

function makeSelectionBulList(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <ul> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </ul> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()

}

function makeSelectionNumList(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <ol> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </ol> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()


}

function makeSelectionListLine(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <li> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </li> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()

}

function makeSelectionLink(accessedInputControl)
{
var end

end = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' *' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + '* Replace this with link target ! * ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)

accessedInputControl.setSelectionRange(end+3, end+36)
accessedInputControl.focus()

}


function undo(accessedInputControl){
	accessedInputControl.value = originalText
}
				