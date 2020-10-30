Array.prototype.removeEq = function(value) {
	let idx = this.indexOfEq(value)
	if (idx !== -1) {
		this.splice(idx, 1)
		return true
	}
	return false
}

Array.prototype.hasEq = function (value) {
	return this.indexOfEq(value) !== -1;
}

Array.prototype.indexOfEq = function(value) {
	for (var i = 0; i < this.length; ++i) {
		if (_.isEqual(this[i], value)) {
			return i
		}
	}
	return -1;
}

function showInfoDialog(msg) {
	alert(msg)
}

function showErrorDialog(msg) {
	alert(msg)
}

$("document").ready(function(){	
	var formModel = {
		casting: [],
		categories: [],
		director: null,
		
		addCategory: function(cat) {
			this.categories.push(cat)
			this.onAddCategory(cat);
		},
		
		removeCategory: function(cat) {
			this.categories.removeEq(cat)
			this.onRemoveCategory(cat)
		},
		
		addActor: function(actor) {
			if (!this.casting.hasEq(actor)) {
				this.casting.push(actor)
				this.onAddActor(actor)
			}
		},
		
		removeActor: function(actor) {
			if (this.casting.removeEq(actor)) {
				this.onRemoveActor(actor)
			}
		},
		onAddActor : function(actor) {},
		onRemoveActor: function(actor){},
		onAddCategory: function(category) {},
		onRemoveCategory: function(category) {}
	} 
	
	jQuery.ajax(location.origin + "/Narga/api/category/all")
		.done(function(reponseData) {
			reponseData.forEach(function(category) {
				$("#availiableCategories")
				.append(
					$(`<span class="category">${category.name}</span>`)
					.click(function() {
						formModel.addCategory(category)
						$(this).remove()
					})
				)
			})		
		})

	$("#showCategorySelectorBtn").click(function() {
		$("#categorySelectorWrapper")
			.css("z-index", "1")
			.fadeIn(200)
	}) 
	
	$("#closeCategorySelectorBtn").click(function() {
		$("#categorySelectorWrapper").fadeOut(200)
	})
	
	$("#showActorSelectorBtn").click(function() {
	    $("#actorSelectorWrapper")
			.css("z-index", "1")
			.fadeIn(200);
		$("#actorSearchInp").focus()	
    })
	
	$("#closeActorSelectorBtn").click(function() {
		$("#actorSelectorWrapper").hide()
	})
	
	$("#categoryInp").change(function categoryInpOnChange() {
		formModel.addCategory($(this).val());
		$(this).val("")
		$("#categorySelectorWrapper").hide()
	})
	
	$("#posterInp").change(function() {
		if (this.files && this.files[0]) {
			let file = this.files[0]
			let url = URL.createObjectURL(file)
			$("#previewImage").attr("src", url);
			$("#preview").attr("src", url)
			$("#showPosterSelectorBtn").text("Change poster")
		}
	})
	
	$("#showPosterSelectorBtn").click(function() {
		$("#posterSelectorWrapper")
			.css("z-index", 1)
			.fadeIn(200)
	})
	
	$("#closePosterSelectorBtn").click(function() {
		$("#posterSelectorWrapper").fadeOut(200)
	})
	
	let actorSearchList = $("#actorSearchResult")
	
	$("#actorSearchInp").keydown(function() {
		jQuery.get(location.origin + "/Narga/api/actor/search", {input: $(this).val()})
			.done(function(searchResult) {
				actorSearchList.empty()
				if (searchResult.length == 0) {
					actorSearchList.append(
						$("<div ><span>No result</span></div>")
						.css("text-align", "center")
					)
				} else {
					for (let actor of searchResult) {
						actorSearchList.append(
							$(`
								<div class="item">
									<div class="avatar">
										<img src="${actor.avatar.url}">
									</div>
									<div class="description">
										<img src="${actor.country.flag.url}">
										<span>${actor.name}</span>
									</div>
								</div>
							`)
							.click(function() {
								formModel.addActor(actor)
							})
						)
					}
				}
			})
			.fail(function(){
				actorSearchList.empty()
				actorSearchList.append("<span>error</span>")
			})
	})
	
	$("#newFilmForm").submit(function(event) {
		event.preventDefault()
		let formElement = $(this)[0];
		let formData = new FormData(formElement)
		let url = $(formElement).attr("action")
		let posterInputElement = $("#posterInp")[0];
		
		if (!posterInputElement.files || !posterInputElement.files[0]) {
			showErrorDialog("please select film poster")
			return
		}
		
		formData.append("poster", posterInputElement.files[0])
		
		formModel.categories.forEach(function(category) {
			console.log(category)
			formData.append("categories", category.id)
		})
		
		formModel.casting.forEach(function(actor) {
			formData.append("casting", actor.id)
		})
		
		formData.append("director", "null")
				
		jQuery.ajax({
			type: "POST",
            enctype: 'multipart/form-data',
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
	
	formModel.onAddCategory  = function(category) {
		$("#categoryList").append(
			$(`<span class="category">${category.name}</span>`)
			.click(function() {
				$(this).remove()
				$("#availiableCategories").append(
					$(`<span class="category">${category.name}</span>`)
					.click(function() {
						formModel.onRemoveCategory(category)
						$(this).remove()
					})
				)
			})
		)
	}
	
	formModel.onAddActor = function(actor) {
		$("#casting").append(
			$(`
			<div class="item">
				<div class="avatar">
					<img src="${actor.avatar.url}">
				</div>
				<div class="description">
					<img src="${actor.country.flag.url}">
					<span>${actor.name}</span>
				</div>
			</div>
			`)
			.click(function() {
				formModel.removeActor(actor)
				$(this).remove()
			})
		)
	}
})