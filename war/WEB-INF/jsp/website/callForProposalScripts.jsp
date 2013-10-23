<script src="js/jquery-1.10.2.min.js"></script>
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
        