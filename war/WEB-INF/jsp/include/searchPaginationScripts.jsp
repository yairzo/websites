var searchPhrase = '${command.searchCreteria.searchPhrase}';
searchPhrase = searchPhrase.replace("'","\\\'");

$(".rewindToFirst").click(function(){

	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"1\"/>');
	$("input#searchField").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchField\" value=\"${command.searchCreteria.searchField}\"/>');
 	$("input#searchPhrase").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchPhrase\" value=\""+searchPhrase+"\"/>');
 	$("form#form").submit();
 });

 $(".rewindOnePage").click(function(){
	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"${command.listView.page - 1}\"/>');
	$("input#searchField").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchField\" value=\"${command.searchCreteria.searchField}\"/>');
 	$("input#searchPhrase").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchPhrase\" value=\""+searchPhrase+"\"/>');
 	$("form#form").submit();
 });


 $(".nearPage").click(function(){
 	var id = $(this).attr("id");
 	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"'+ id + '\"/>');
	$("input#searchField").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchField\" value=\"${command.searchCreteria.searchField}\"/>');
 	$("input#searchPhrase").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchPhrase\" value=\""+searchPhrase+"\"/>');
 	$("form#form").submit();
 	});


 $(".advanceOnePage").click(function(){
	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"${command.listView.page + 1}\"/>');
	$("input#searchField").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchField\" value=\"${command.searchCreteria.searchField}\"/>');
 	$("input#searchPhrase").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchPhrase\" value=\""+searchPhrase+"\"/>');
 	$("form#form").submit();
 });



 $(".advanceToLast").click(function(){
	$("input#listViewPage").remove();
	$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"${command.listView.lastPage}\"/>');
	$("input#searchField").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchField\" value=\"${command.searchCreteria.searchField}\"/>');
	$("input#searchPhrase").remove();
	$("form#form").append('<input type=\"hidden\" name=\"searchCreteria.searchPhrase\" value=\""+searchPhrase+"\"/>');
 	$("form#form").submit();
 });