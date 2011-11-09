var searchPhrase = '${command.searchCreteria.searchPhrase}';
searchPhrase = searchPhrase.replace("'","\\\'");

$(".rewindToFirst").click(function(){

	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"1\"/>');
 	$("form#form").submit();
 });

 $(".rewindOnePage").click(function(){
	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"${command.listView.page - 1}\"/>');
 	$("form#form").submit();
 });


 $(".nearPage").click(function(){
 	var id = $(this).attr("id");
 	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"'+ id + '\"/>');
 	$("form#form").submit();
 	});


 $(".advanceOnePage").click(function(){
	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"${command.listView.page + 1}\"/>');
 	$("form#form").submit();
 });



 $(".advanceToLast").click(function(){
	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"${command.listView.lastPage}\"/>');
 	$("form#form").submit();
 });