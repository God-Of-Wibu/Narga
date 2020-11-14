	function showInfoDialog(msg) {
	alert(msg)
}

function showErrorDialog(msg) {
	alert(msg)
}

$("document").ready( function() {	
	
	let director = null;

	$("#showCategorySelectorBtn").click(function() {
		$("#categorySelectorWrapper")
			.css("z-index", "1")
			.fadeIn(200)
	}) 
	
	$("#closeCategorySelectorBtn").click( function() {
		$("#categorySelectorWrapper").fadeOut(200)
	})
	
	$("#showActorSelectorBtn").click( function() {
	    $("#actorSelectorWrapper")
			.css("z-index", "1")
			.fadeIn(200);
		$("#actorSearchInp").focus()	
    })
	
	$("#closeActorSelectorBtn").click( function() {
		$("#actorSelectorWrapper").hide()
	})
	
	$("#categoryInp").change( function() {
		formModel.addCategory($(this).val());
		$(this).val("")
		$("#categorySelectorWrapper").hide()
	})
	
	$("#posterInp").change( function() {
		if (this.files && this.files[0]) {
			let file = this.files[0]
			let url = URL.createObjectURL(file)
			$("#preview").attr("src", url)
			$("#showPosterSelectorBtn").text("Change poster")
		}
	})
	
	$("#showDirectorSelectorBtn").click( function() {
		$("#directorSelectorWrapper").show()	
	})
	
	$("#closePosterSelectorBtn").click( function() {
		$("#posterSelectorWrapper").fadeOut(200)
	})
	
	$("#directorSelector").search({
		title: 'select director',
		placeholder: "enter director's name",
		url: location.origin + "/Narga/api/actor/search",
		onClose: function() {
			$("#directorSelectorWrapper").hide()
		},
		onSelect: function(_, data) {
			director = data.item;
			$("#director")
				.empty()
				.append( $(`
						<div class="item">
							<div class="avatar">
								<img src="${director.avatar.url}">
							</div>
							<div class="description">
								<img src="${director.country.flag.url}">
								<span>${director.name}</span>
							</div>
						</div>
					`).click( function() {
						$("#directorSelectorWrapper").show()
					})
				)
		},
		cellFactoryFn: function(director) {
			return $(`
			<div class="item">
				<div class="avatar">
					<img src="${director.avatar.url}">
				</div>
				<div class="description">
					<img src="${director.country.flag.url}">
					<span>${director.name}</span>
				</div>
			</div>
			`)[0]
		}
	})
	
	let casting =  $("#casting").list({
		cellFactoryFn: function(_, actor) {
			return $(`
			<div class="item">
				<div class="avatar">
					<img src="${actor.avatar.url}">
				</div>
				<div class="description">
					<img src="${actor.country.flag.url}">
					<span>${actor.name}</span>
				</div>
			</div>
			`)[0]
		},
		onSelect: function(_, data) {
			data.self.remove(data.item)
		}
	}).list( "instance" )
	
	let categories = $( "#categories" ).list({
		onClick: function(_, _) {
			
		},
		cellFactoryFn: function(self, category) {
			return $(`
				<div class="category">
					<span>${category.name}</span>
				</div>
			`).append(
				$("<span>&times;</span>")
				.click( function() {
					self.remove(category)
				})
			)[0]
		}
	}).list( "instance" )
	
	jQuery.ajax(location.origin + "/Narga/api/category/all")
		.done( function(reponseData) {
			reponseData.forEach( function(category) {
				$("#availiableCategories")
				.append(
					$(`<span class="category">${category.name}</span>`)
					.click( function() {
						categories.add(category)
					})
				)
			})		
		})
	
	
	$("#actorSelector").search({
		title: 'select actor',
		placeholder: "enter actor's name",
		url: location.origin + "/Narga/api/actor/search",
		onClose: function() {
			$("#actorSelectorWrapper").hide()
		},
		onSelect: function(_, data) {
			casting.add(data.item)
		},
		cellFactoryFn: function(actor) {
			return $(`
				<div class="item">
					<div class="avatar">
						<img src="${actor.avatar.url}">
					</div>
					<div class="description">
						<img src="${actor.country.flag.url}">
						<span>${actor.name}</span>
					</div>
				</div>
			`)[0]
		}
	})
	
	$("#newFilmForm").submit(function(event) {
		event.preventDefault()
		let formElement = $(this)[0];
		let formData = new FormData(formElement)
		let url = $(formElement).attr("action")
		
		casting.options.items.forEach( function(item) {
			formData.append( "casting", item.id )
		})
		
		categories.options.items.forEach( function(item) {
			formData.append( "categories", item.id )
		})
		
		formData.append("director", director.id)
		
		
		jQuery.ajax({
			type: "POST",
            enctype: 'multipart/form-data; charset=UTF-8',
            url: url,
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
			dataType: "text",
            success: showInfoDialog,
            error: showErrorDialog
		})
	}) 
})