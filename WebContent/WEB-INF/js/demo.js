/**
 * 
 */

$("document").ready(function() {
	
	var dem,cla;
	var m =1;
	
	$(".nextBT").click(function(){
	    demo(1,cla);
	})
	
	$(".preBT").click(function(){
	    demo(-1,cla);
	})
	
	$(".day").click(function(){
		cla=this.className;
		$(".day").css("background-color","red");
	})
	
	function demo(n,cla){
		var z=m;
		m=m+n;
		console.log(m);
		console.log(z);
		if(m>z){
			console.log(cla);
			cla=$("."+cla).next().className;
			console.log(cla);
			$("."+cla).attr("background","red");
		}
	}
	
	})