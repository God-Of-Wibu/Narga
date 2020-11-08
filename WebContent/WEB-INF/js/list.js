
function removeEq(arr, value) {
	let idx = indexOfEq(arr, value)
	if (idx !== -1) {
		arr.splice(idx, 1)
	}
	return idx
}

function hasEq(arr, value) {
	return indexOfEq(arr, value) !== -1;
}

function indexOfEq(arr, value) {
	for (var i = 0; i < arr.length; ++i) {
		if (_.isEqual(arr[i], value)) {
			return i
		}
	}
	return -1;
}

$.widget("narga.list", {
	options: {
		items: [],
		cellFactoryFn: function(_, item) {
			return JSON.stringify(item)
		} 
	},
	_create: function() {
		this.items = this.options.items;
	},
	
	add: function(item) {
		let self = this;
		if (!hasEq(self.items, item)) {
			self.items.push(item)
			self._onAdd(item)
		}
	},
	
	remove: function(item) {
		let self = this;
		let idx = removeEq(self.items, item);
		if (idx != -1) {
			self._onRemove(item, idx)
		}
	},
	
	_onAdd: function(item) {
		let self = this;
		self.element.append(
			$(self.options.cellFactoryFn(self, item))
				.click( function(event) {
					self._trigger("onSelect", event, {self: self, item: item})
				})
		)		
	},
	
	_onRemove: function(_item, idx) {
		let self = this;
		self.element.children().eq(idx).remove()
	}
	
})