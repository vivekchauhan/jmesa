function LimitManager() {
}

LimitManager.limits = new Object();

LimitManager.addLimit = function(limit) {
 	LimitManager.limits[limit.id] = limit;
}

LimitManager.getLimit = function(id) {
 	return LimitManager.limits[limit.id];
}

function Limit(id) {
	this.id = id;
	this.page;
	this.maxRows;
	this.sortSet = new Array();
	this.filterSet = new Array();
}

function Sort(property, order, position) {
	this.property = property;
	this.order = order;
	this.position = position;
}

function Filter(property, value) {
	this.property = property;
	this.value = value;
}

Limit.prototype.getId = function() {
	return this.id;
}

Limit.prototype.setId = function(id) {
 	this.id = id;
 }

Limit.prototype.getPage = function() {
	return this.page;
}

Limit.prototype.setPage = function(page) {
 	this.page = page;
 }

Limit.prototype.getMaxRows = function() {
	return this.maxRows;
}

Limit.prototype.setMaxRows = function(maxRows) {
 	this.maxRows = maxRows;
 }

Limit.prototype.getSortSet = function() {
	return this.sortSet;
}

Limit.prototype.addSort = function(sort) {
 	this.sortSet[sortSet.length] = sort;
 }

Limit.prototype.setSortSet = function(sortSet) {
 	this.sortSet = sortSet;
 }

Limit.prototype.getFilterSet = function() {
	return this.filterSet;
}

Limit.prototype.addFilter = function(filter) {
 	this.filterSet[filterSet.length] = filter;
 }

Limit.prototype.setFilterSet = function(filterSet) {
 	this.filterSet = filterSet;
 }

 /*other helper methods*/

Limit.prototype.createHiddenInputFields = function(form) {
	var input = document.createElement('input');
	input.type = 'hidden';
	input.name = this.id + '_' + 'p_';
	input.value = this.page;
	form.appendChild(input);
}
 
 