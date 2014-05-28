<script src="/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$("a[href^='http']").attr('target','_blank');

	$(".kol_arrow").click(function(e) {
		e.preventDefault();
		if($(".kol"), $(this).closest("div.kol").hasClass("open"))
			($(".kol"), $(this).closest("div.kol")).removeClass("open");
		else
			($(".kol"), $(this).closest("div.kol")).addClass("open");
	});	
	
	$("#login_open").click(function(e) {
		var loginBox = $(".login_box_cp");
		//var topT=$("#login_open").offset().top+$("#login_open").height()*1.5; //under the button 
		//var leftT = $("#login_open").offset().left-loginBox.width()/4; //under the button 
		var topT=($(window).height() - loginBox.height())/2;//middle of page (also change to fixed) 
		var leftT = ($(window).width() - loginBox.width())/2; 
		$(".login_box_cp").css({
			position: 'fixed',
	   	 	top: topT,
	    	left: leftT
		});
		if (loginBox.is(":visible"))
		loginBox.fadeOut("fast");
		else
		loginBox.fadeIn("fast");
		return false;
	});

	$(".login_box_cp").hover(function(){
		mouse_is_inside=true;
	}, function(){
		mouse_is_inside=false;
	});
	$("body").click(function(){
		if(! mouse_is_inside) $(".login_box_cp").fadeOut("fast");
	});
	$('.login_forgot').click(function(e){
		  e.preventDefault();
		  var userName = $("#j_username").val();
		  if($("#j_username").val()=="")
			  alert("הכנס שם משתמש");
		  else
		 	window.location="/sendPasswordEmail.html?username="+ userName;     
	  });

});




</script>
        