/**
 * 
 */


$("document").ready(function() {
	let currentDayIndex = 0;
	
	let issues = new Array(7);
	let dayOfWeek = ['thứ hai', 'thứ ba', 'thứ tư', 'thứ năm', 'thứ sáu', 'thứ bảy', 'chủ nhật']
	let time = ['8:00:00', '9:30:00', '12:00:00', '14:00:00', '17:00:00', '19:00:00', '21:00:00']
	let onCurrentDayChanged = function() {
		for (let i = 0; i <=7; ++i) {
			$(`.list-time input:eq(${i})`).prop("checked", issues[currentDayIndex].times[i])
		}
	}
	let film = null;
	
	function select(index) {
		currentDayIndex = index;
		onCurrentDayChanged();	
	}

	function getTomorrow(today) {
		return new Date(today.getTime() + 1000 * 60 * 60 * 24);
	}
	
	for (let i = 0; i <=7; ++i) {
		$(`.list-time input:eq(${i})`).change( function() {
			issues[currentDayIndex].times[i] = this.checked
		})
	}

	$(".date-input").change( function() {
		let dateList = $("#date-list").css("display","block").empty();
		
		for (let i = 0, date = new Date($(".date-input").val()); i <= 7; ++i, date = getTomorrow(date)) {
			dateList.append(
				$(`<span class="day">${dayOfWeek[date.getDay()]} ${date.getDate()}/${date.getMonth() + 1}</span>`)
				.click( function() {
					$(".day").removeClass("checked")
					$(this).addClass("checked");
					select(i)
				})
			)
			
			issues[i] = {
				date: date,
				times: [false, false, false, false, false, false, false]
			}
		}
		select(0)
		$(".time-selector").slideDown("slow");
	})

	$('.film').click(function() {
		$("#filmSelectorWrapper").fadeIn(200)
	})
	
	$("#filmSelector").search({
		title: 'select film',
		placeholder: "enter film, actor or director",
		url: location.origin + "/Narga/api/film/search",
		onClose: function() {
			$("#filmSelectorWrapper").hide()
		},
		onSelect: function(_, data) {
			film = data.item;
			$(".film-container")
				.empty()
				.append( $(`
							<div class="row">
								<div class="col-md-2">
									<img class="img-fluid" src="${film.poster.url}">
								</div>
								<div class="col-md-10">
									<span>${film.title}</span>
								</div>
							</div>
						
					`).click( function() {
						//$("#filmSelectorWrapper").show()
					})
				)
			$("#filmSelectorWrapper").hide()
		},
		cellFactoryFn: function(film) {
			return $(`
				<div class="container">
					<div class="row">
						<div class="col-md-2">
							<img class="img-fluid "src="${film.poster.url}">
						</div>
						<div class="col"><span>${film.title}</span></div>
					</div>
				</div>
			`)[0]
		}
	})
	
	function showInfoDialog(v) {
		alert(v)
	}
	
	function showErrorDialog(v) {
		alert(v)
	}
	
	$("#submitBtn").click( function() {
		
		
		if (film == null) {
			showErrorDialog("vui lòng chọn bộ phim")
			return;
		}
		
		let data = {
			filmId: film.id,
			vipCost: $("#vipCostInp").val(),
			basicCost: $("#basicCostInp").val(),
			dateTimes: []
		}
		
		issues.forEach( function(v) {
			for(let i = 0; i < 7; ++i) {
				if (v.times[i]) {
					data.dateTimes.push(JSON.stringify( {date: `${v.date.getFullYear()}-${v.date.getMonth() + 1}-${v.date.getDate()}`, time: time[i]} ));
				}
			}
		})
		
		if (data.dateTimes.length === 0) {
			showErrorDialog("vui lòng chọn ít nhất một xuất chiếu");
			return;
		}
		
		$.post(location.href, data).done(function(resposeBody) {
			alert(resposeBody)
		})
		
	})

})


