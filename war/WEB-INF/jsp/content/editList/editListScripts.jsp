<script language="Javascript">
function onEdit()
{
    document.form.action = "list.html?action=edit"
    document.form.submit();
    return true;
}

function onDelete()
{
    document.form.action = "list.html?action=delete"
    document.form.submit();
    return true;
}

function onSave()
{
    document.form.action = "list.html?action=save"
    document.form.submit();
    return true;
}
function onColumnEdit()
{
    document.form.action = "list.html?action=editColumn"
    document.form.submit();
    return true;
}
function onColumnDelete()
{
    document.form.action = "list.html?action=deleteColumn"
    document.form.submit();
    return true;
}

$(document).ready(function() {
	if($('#listType').val()=='1')
		$('#personTypeTr').show();
	else
		$('#personTypeTr').hide();
	
	$('#listType').change(function(){
		if($('#listType').val()=='1')
			$('#personTypeTr').show();
		else
			$('#personTypeTr').hide();
	});
	
	$('.cancel').click(function(){
		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
		$('form#form').submit();
    });

	$('select#sublists').change(function(){
		document.form.action="list.html?action=addSublist";
    	document.form.submit();
    });

    $('img.moveUp').click(function(){
		$('form').append("<input type=\"hidden\" name=\"moveUp\" value=\""+$("td.sublists img.moveUp").index($(this))+"\"/>");
		$('form').submit();
	});
	$('img.moveDown').click(function(){
		$('form').append("<input type=\"hidden\" name=\"moveDown\" value=\""+$("td.sublists img.moveDown").index($(this))+"\"/>");
		$('form').submit();
	});
	$('img.delete').click(function(){
		$('form').append("<input type=\"hidden\" name=\"delete\" value=\""+$("td.sublists img.delete").index($(this))+"\"/>");
		$('form').submit();
	});
});



</script>