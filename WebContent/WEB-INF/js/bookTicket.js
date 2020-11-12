/**
 * 
 */

$("document").ready(function() {

	$(".seat-seat-standard-active").click(function() {
		$(".seat-seat-standard-active").css("background-color", " #ed9900");
		$(".seat-seat-standard-active").css("color", "white");
	})

	$(".button-book").click(function() {
		$(".pay").fadeIn(150);
		$(".pay").css("display", "flex");
	})

	$(".button-cancel-order").click(function() {
		$(".pay").css("display", "none");
	})

	$(".button-confirm").click(function() {
		$(".orderSuccess").css("display", "flex");
		$(".pay").css("display", "none");
	})

	$(".orderSuccess").click(function() {
		$(".orderSuccess").css("display", "none");
	})

	$(".day").click(function() {
		$(".list-times").slideDown();
		$(".list-times").css("display", "block");
	})

	$(".time").click(function() {
		$(".position-selector").slideDown("slow");
	})

	$(".button-cancel").click(function() {
		$(".position-selector").slideUp("slow");
	})

	$(".day").on('change', function() {
		$(".day").not(this).prop('checked', false);
	})

	$(".time").on('change', function() {
		$(".time").not(this).prop('checked', false);
	})
	
	when
})