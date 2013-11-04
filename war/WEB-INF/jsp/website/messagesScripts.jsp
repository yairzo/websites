<script src="/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">

$(document).ready(function() {

	$(".message_arrow").click(function(e) {
		e.preventDefault();
		if($(".message"), $(this).closest("div.message").hasClass("open"))
			($(".message"), $(this).closest("div.message")).removeClass("open");
		else
			($(".message"), $(this).closest("div.message")).addClass("open");
	});	

});




</script>
        