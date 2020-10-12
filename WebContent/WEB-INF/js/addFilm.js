$("document").ready(function(){	
	
	
	var title = $("#title").val()
	var gender = $("#gender").val()
	var country = $("#country").val()
	var director = $("#director").val()
	var cast = []
	var selected_categories = []
	
	var selected_cat_listeners = []
	
	function trigger_selected_cat_change() {
		selected_cat_listeners.forEach(cb => cb())
	}
	
	
	
	$("#show-category-selector-btn").click(function(evt){
		$("#category-selector-wrapper").show()
		$("#category-input").focus()
	}) 
	
	
	$("#category-selector-wrapper").focusout(function(evt) {
		$(this).hide()
	})
	
	function remove_by_value(arr, v) {
		var idx = arr.indexOf(v)
		if (idx > -1)
			arr.splice(idx, 1)
		return arr;
	}
	
	$("#category-list").click(".category", function(evt){
		remove_by_value(selected_categories, $(evt.target).text())
		$(evt.target).remove()
	})
	
	$("#category-input").change(function(evt){
		selected_categories.push($(this).val())
		$(this).val("")
		trigger_selected_cat_change()
		$("#category-selector-wrapper").hide()
	})
	
	function make_cat_el(name) {
		return "<span class=\"category\">" + name + "</span>"
	}
	
	selected_cat_listeners.push(function(){
		$("#category-list").empty()
		selected_categories.forEach(v => {
			$("#category-list").append(make_cat_el(v))
		})
		
	})
	
	console.log(location)
	
})