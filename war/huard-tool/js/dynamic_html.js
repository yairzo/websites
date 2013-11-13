var editboxHTML = 
'<html>' +
'<head>' +
'<\/head>' +
'<body onload="document.f.ta.focus(); document.f.ta.select();">' +
'<form name="f">' +
'<textarea  style="background: #def;" name="ta" wrap="hard" spellcheck="false">' +
'<\/textarea>' +
'<\/form>' +
'<\/body>' +
'<\/html>';

var defaultStuff = '';

var extraStuff = '';

var old = '';
         
function init()
{
  window.editbox.document.write(editboxHTML);
  window.editbox.document.close();
  window.editbox.document.f.ta.value = defaultStuff;
  update();
}

function update()
{
  var textarea = window.editbox.document.f.ta;
  var d = dynamicframe.document; 

  if (old != textarea.value) {
    old = textarea.value;
    d.open();
    d.write(old);
    if (old.replace(/[\r\n]/g,'') == defaultStuff.replace(/[\r\n]/g,''))
      d.write(extraStuff);
    d.close();
  }

  window.setTimeout(update, 150);
}

function updater()
{
	alert('test');
	window.dynamic.document.write('oldssssss');
	
}
