/**
 * 
 */

Array.prototype.removeByValue = function(value) {
	let idx = this.indexOf(value)
	if (idx > -1)
		this.splice(idx, 1)
	return this;
}

function makeCategoryElement(name) {
	return `<span class=\"category\">${name}</span>`
}

function makeOptionElement(name) {
	return `<option>${name}</option>`;
}

$("document").ready(function(){	
	var formModel = {
		casting: [],
		categories: [],
		callbacks: [],
		
		fireEvent: function(evt) {
			for (const callback of this.callbacks) {
				callback(evt)
			}
		},
		
		addCategory: function(cat) {
			this.categories.push(cat)
			var evt = {
				field: "categories",
				operation: "add",
				value: cat
			}
			this.fireEvent(evt)
		},
		
		removeCategory: function(cat) {
			this.categories.removeByValue(cat);
			var evt = {
				field: "categories",
				operation: "rmv",
				value: cat
			}
			this.fireEvent(evt)
		},
		
		onChange: function(callbackfn) {
			this.callbacks.push(callbackfn)
		}
		
	}
	
	jQuery.ajax(location.origin + "/Narga/api/category/all")
		.done(function(reponseData){
			reponseData.forEach(category => {
				console.log(category)
				$("#category-data-ls").append(makeOptionElement(category.name))
			})		
		})
	
	

	$("#show-category-selector-btn").click(function(){
		console.log("hello")
		$("#category-selector-wrapper")
			.css("z-index", "1")
			.fadeIn(200)
		$("#category-ip").focus()
	}) 
	
	$("#show-cast-selector-btn").click(function(){
		console.log("hello")
	    $("#actor-selector-wrapper")
			.css("z-index", "1")
			.fadeIn(200);	
    })
	
	$("#closeBt").click(function(){
		$("#actor-selector-wrapper").hide()
	})
	
	$("#category-selector-wrapper").focusout(function() {
		$(this).hide()
	})

	$("#category-list").click(".category", function categoryOnClick(evt){
		if ($("#category-list")[0] !== evt.target) {
			formModel.removeCategory($(evt.target).text())
		}
	})
	
	 

     $("#poster-inp").change(function (){
        var form = document.getElementById("new-film-form")
        var url = URL.createObjectURL(this.files[0])
        $(".imagePre").attr("src", url)
     })

    $(".imagePre").click(function(){
	   
}) 

	$("#category-ip").change(function categoryInpOnChange(){
		formModel.addCategory($(this).val());
		$(this).val("")
		$("#category-selector-wrapper").hide()
	})
	
	$("#new-film-form").submit(function newFilmFormOnSubmit(event) {
		event.preventDefault()
		let formElement = document.getElementById("new-film-form")
		let formData = new FormData(formElement)
		let url = $(formElement).attr("action")
		
		formModel.categories.forEach(cat => {
			formData.append("categories", cat)
		})
		
				
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
            success: function (responseData) {
				alert(responseData)
            },
            error: function (err) {
				alert("failed:" + err)
            }
		})
	}) 
	
	formModel.onChange(function(evt){
		if(evt.field === "categories" && evt.operation === "add") {
			$("#category-list").append(makeCategoryElement(evt.value))
		} else if (evt.field === "categories" && evt.operation === "rmv") {
			$(".category").each(function(index, element) {
				if (element.innerText === evt.value) {
					element.remove()
				}
			})
		}
	})
	
})/**
 * 
 */