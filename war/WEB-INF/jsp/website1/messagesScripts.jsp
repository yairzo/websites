		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/jquery-1.8.3.min.js"><\/script>')</script>
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
        