/**
 * 
 */

$("document").ready(function() {

	$(".seat-seat-standard-active").click(function() {
		$(".seat-seat-standard-active").css("background-color", " #ed9900");
		$(".seat-seat-standard-active").css("color", "white");
	})
	
	$(".button-book").click(function(){
		$(".pay").css("display","flex");
	})

    $(".button-cancel-order").click(function(){
	    $(".pay").css("display","none");
    })

    $(".button-confirm").click(function(){
	    $(".orderSuccess").css("display","flex");
        $(".pay").css("display","none");
    })
    
    $(".orderSuccess").click(function(){
	    $(".orderSuccess").css("display","none");
    })
    
    

})