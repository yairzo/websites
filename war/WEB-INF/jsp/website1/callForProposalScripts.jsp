		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/jquery-1.8.3.min.js"><\/script>')</script>
<script type="text/javascript">

$(document).ready(function() {

	$(".kol_arrow").click(function(e) {
		e.preventDefault();
		if($(".kol"), $(this).closest("div.kol").hasClass("open"))
			($(".kol"), $(this).closest("div.kol")).removeClass("open");
		else
			($(".kol"), $(this).closest("div.kol")).addClass("open");
	});	

});




</script>
        