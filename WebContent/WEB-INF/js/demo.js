/**
 * 
 */

$("document").ready(function() {

	var i = 1;

	var issueFilm = {
		nameFilm: "",
		day: "",
		time: "",
	}

	function getIssue(date) {
		var newIssue = new Object();
		let da = ouputDay(date);
		console.log(da);
		newIssue.day=""+da;
		console.log(issueFilm);
	}
	$(".nextBT").click(function() {
		nextChooseDay(1);
	})

	$(".preBT").click(function() {
		nextChooseDay(-1);
	})

	function nextChooseDay(n) {
		let m;
		m = i + n;
		if (m == 0) { m = 1; }
		if (m == 8) { m = 7; }
		if (m > i) {
			$("#day" + m).prop('checked', true);
			$("#day" + m).prop('disabled', false);
			$("#day" + i).prop('checked', false);
			$("#day" + i).prop('disabled', true);
		}
		else if (m < i) {
			$("#day" + m).prop('checked', true);
			$("#day" + m).prop('disabled', false);
			$("#day" + i).prop('checked', false);
			$("#day" + i).prop('disabled', true);
		}
		return i = m;
	}

	function ouputDay(date) {
		return `${date.getDate()}/${date.getMonth() + 1}`
	}

	function getTomorrow(today) {
		return new Date(today.getTime() + 1000 * 60 * 60 * 24);
	}
	
    $('.day').on('change', function() {
    $('.day').not(this).prop('checked', false);
    })

	$(".choose").click(function chooseDay() {
		let date = new Date($(".choose-day").val());
		$(".list-days").css("display","block");
		let k = 1;
		do {
			$(".day" + k).html(ouputDay(date));
			getIssue(date);
			k++;
			date = getTomorrow(date);
		}
		while (k <= 7)
		$("#day"+i).prop('disabled', false);
		$("#day"+i).prop('checked', true);
		$(".choose-time").slideDown("slow");
	})
})


