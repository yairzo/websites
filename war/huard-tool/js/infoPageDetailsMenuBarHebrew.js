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



oCMenu2.makeMenu('top0','','פעולות','','','','','','','','','','','','undo(accessedInputControl)')
	oCMenu2.makeMenu('sub00','top0','בטל','','','','','','','','','','','','undo(accessedInputControl)')
  	oCMenu2.makeMenu('sub01','top0','הפוך טקסט','','','','','','','','','','','','reverseText(accessedInputControl)')

oCMenu2.makeMenu('top1','','הכנס','','')
  oCMenu2.makeMenu('sub10','top1','רד שורה','','','','','','','','','','','','insertBR(accessedInputControl)')
  
  
    
	
oCMenu2.makeMenu('top2','','עצב טקסט','')
  oCMenu2.makeMenu('sub20','top2','הדגשה','','','','','','','','','','','','makeSelectionBold(accessedInputControl)')
  
  oCMenu2.makeMenu('sub22','top2','הדגשה מיוחדת','','','','','','','','','','','','makeSelectionUnderlineAndColor(accessedInputControl)')	
  oCMenu2.makeMenu('sub23','top2','רשימה','','','','','','','','','','','','makeSelectionBulList(accessedInputControl)')
  
  oCMenu2.makeMenu('sub24','top2','רשימה ממוספרת','','','','','','','','','','','','makeSelectionNumList(accessedInputControl)')
  
  oCMenu2.makeMenu('sub25','top2','שורה','','','','','','','','','','','','makeSelectionListLine(accessedInputControl)')
  
  oCMenu2.makeMenu('sub26','top2','קישור','','','','','','','','','','','','makeSelectionLink(accessedInputControl)')
  
  oCMenu2.makeMenu('top3','','מבנה','','','','','','','','','','','','')	
  	oCMenu2.makeMenu('sub30','top3','פסקה','','','','','','','','','','','','makeSelectionParagraph(accessedInputControl)')	

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
' <רש> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
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
' <ד> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </ד> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+11,cursorOriginalPosition+11) 
accessedInputControl.focus()

}


function makeSelectionHeader1(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
'<כ1> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + '</כ1>' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()

}

function makeSelectionHeader2(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <כ2>' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </כ2> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()

}

function makeSelectionHeader3(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <כ3> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </כ3> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()

}

function makeSelectionHeader4(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <כ4> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </כ4> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()

}
function makeSelectionParagraph(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
'<פ>' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + '</פ> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+11,cursorOriginalPosition+11) 
accessedInputControl.focus()

}

function makeSelectionUnderlineAndColor(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <ק> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + '</ק>' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+33,cursorOriginalPosition+33) 
accessedInputControl.focus()

}

function makeSelectionBulList(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <רר> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </רר> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()

}

function makeSelectionNumList(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <רמ> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </רמ> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
accessedInputControl.setSelectionRange (cursorOriginalPosition+12,cursorOriginalPosition+12) 
accessedInputControl.focus()


}

function makeSelectionListLine(accessedInputControl)
{
cursorOriginalPosition = accessedInputControl.selectionEnd
originalText = accessedInputControl.value
accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) +
' <שו> ' + accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) + ' </שו> ' + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
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



function reverseText(accessedInputControl) {
      text = ""
      end = accessedInputControl.selectionEnd
      str = accessedInputControl.value.substr(accessedInputControl.selectionStart, accessedInputControl.selectionEnd - accessedInputControl.selectionStart) 
      i = str.length - 1
      while (i >= 0) {
         text += str.substr(i,1)
         i--
      }
      
      accessedInputControl.value = accessedInputControl.value.substr(0, accessedInputControl.selectionStart) + text + accessedInputControl.value.substr(accessedInputControl.selectionEnd)
      accessedInputControl.setSelectionRange(end, end)
      accessedInputControl.focus()
      //accessedInputControl.value.substr(0,accessedInputControl.selectionStart) + 
      //+ accessedInputControl.value.substr(accessedControlInput.selectionEnd)
}


function undo(accessedInputControl){
	accessedInputControl.value = originalText
}

function showResultLTR() {
parent.frame2.document.write ("<html><head><link href=\"style_ard.css\" rel=\"stylesheet\" type=\"text/css\"></head><body>"+document.form1.html.value + "</body></html>" )
parent.frame2.document.close()
}

function showResultRTL() {
parent.frame2.document.write ("<html><head><link href=\"style_ard.css\" rel=\"stylesheet\" type=\"text/css\"></head><body><div align=\"right\" dir=\"rtl\">"+moveHebrewTagsToHtmlTags(document.form1.html.value) + "</div></body></html>" )
parent.frame2.document.close()
}

function moveHebrewTagsToHtmlTags(str){
str = replace(str, "<כ1>", "<h1>")
str = replace(str, "</כ1>", "</h1>")
str = replace(str, "<כ2>", "<h2>")
str = replace(str, "</כ2>", "</h2>")
str = replace(str, "<כ3>", "<h3>")
str = replace(str, "</כ3>", "</h3>")
str = replace(str, "<כ4>", "<h4>")
str = replace(str, "</כ4>", "</h4>")
str = replace(str, "<פ>", "<p>")
str = replace(str, "</פ>", "</p>")
str = replace(str, "<רש>", "<br>")
str = replace(str, "<ד>", "<b>")
str = replace(str, "</ד>", "</b>")
str = replace(str, "<ק>", "<u>")
str = replace(str, "</ק>", "</u>")
str = replace(str, "<רר>", "<ul>")
str = replace(str, "</רר>", "</ul>" )
str = replace(str, "<רמ>", "<ol>")
str = replace(str, "<רמ>", "</ol>" )
str = replace(str, "<שו>", "<li>")
str = replace(str, "</שו>", "</li>" )
return str
}

function replace(s, t, u) {
  /*
  **  Replace a token in a string
  **    s  string to be processed
  **    t  token to be found and removed
  **    u  token to be inserted
  **  returns new String
  */
  i = s.indexOf(t);
  r = "";
  if (i == -1) return s;
  r += s.substring(0,i) + u;
  if ( i + t.length < s.length)
    r += replace(s.substring(i + t.length, s.length), t, u);
  return r;
  }
				
