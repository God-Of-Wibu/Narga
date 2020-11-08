
$.widget( "narga.search", {
	options: {
		url: "#",
		cellFactoryFn: function(value) {
			return JSON.stringify(value)
		},
		title: "undefined",
		placeholder: "enter some text",
		
		/*event*/
		onClose: undefined,
		onSelect: undefined
	},
	_create: function() {
		let self = this;
		
		/* header */
		self.header = $('<div/>',{
				'class': "header"
			})
			.append(`<strong>${self.options.title}</strong>`)
			.appendTo(self.element)
			
		self.closeBtn = $("<img/>")
			.attr("src", "/Narga/static/images/icons/close-icon.png")
			.addClass("close-btn")
			.click( function() {
				self._trigger("onClick", null, null);
			})
			.appendTo(self.header)
		/* end of header*/
		
		/* content */
		self.content = $("<div/>")
			.addClass("content")
			.appendTo(self.element)
		
		self.input = $("<input/>")
			.attr("type", "text")
			.attr("placeholder", self.options.placeholder)
			.keyup( function() {
				self.refresh()
			})
			.appendTo(self.content)
		
			
		
		self.result = $("<div/>")
			.addClass("result")
			.appendTo(self.content)
		/* end of content */
		
		self.element.addClass("search")
		
	},
	refresh: function() {
		let self = this;
		$.get(self.options.url, {input: self.input.val()})
			.done( function(resultList) {
				self.result.empty();
				resultList.forEach( function(value) {
					let cell = self.options.cellFactoryFn(value);
					self.result.append(
						$(cell).click( function(event) {
							self._trigger("onSelect", event, {self: self, item: value})
						})
					)
				})
			})
			.fail( function(e) {
				self.result.empty()
				self.result.append(JSON.stringify(e))
			})
	}
});