function LimitManager() {
}

LimitManager.limits = new Object();

LimitManager.addLimit = function(limit) {
 	LimitManager.limits[limit.id] = limit;
}

LimitManager.getLimit = function(id) {
 	return LimitManager.limits[id];
}

function Limit(id) {
	this.id = id;
	this.page;
	this.maxRows;
	this.sortSet = new Array();
	this.filterSet = new Array();
	this.exportType;
}

function Sort(position, property, order) {
	this.position = position;
	this.property = property;
	this.order = order;
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
 	this.sortSet[this.sortSet.length] = sort;
}

Limit.prototype.setSortSet = function(sortSet) {
 	this.sortSet = sortSet;
}

Limit.prototype.getFilterSet = function() {
	return this.filterSet;
}

Limit.prototype.addFilter = function(filter) {
 	this.filterSet[this.filterSet.length] = filter;
}

Limit.prototype.setFilterSet = function(filterSet) {
 	this.filterSet = filterSet;
}
 
Limit.prototype.getExport = function() {
	return this.exportType;
}

Limit.prototype.setExport = function(exportType) {
 	this.exportType = exportType;
}
 
 /*other helper methods*/

Limit.prototype.createHiddenInputFields = function(form) {
	/* the current page */
	var input = document.createElement('input');
	input.type = 'hidden';
	input.name = this.id + '_' + 'p_';
	input.value = this.page;
	form.appendChild(input);

	/* the max rows */
	var input = document.createElement('input');
	input.type = 'hidden';
	input.name = this.id + '_' + 'mr_';
	input.value = this.maxRows;
	form.appendChild(input);
	
	/* the sort objects */
	for (var i = 0; i < this.sortSet.length; i++) {
		var sort = this.sortSet[i];
		var input = document.createElement('input');
		input.type = 'hidden';
		input.name = this.id + '_' + 's_' + sort.position + '_' + sort.property;
		input.value = sort.order;
		form.appendChild(input);
	}

	/* the filter objects */
	for (var i = 0; i < this.filterSet.length; i++) {
		var filter = this.filterSet[i];
		var input = document.createElement('input');
		input.type = 'hidden';
		input.name = this.id + '_' + 'f_' + filter.property;
		input.value = filter.value;
		form.appendChild(input);
	}
}

Limit.prototype.createParameterString = function() {
	var url = '';

	/* the current page */
	url += this.id + '_' + 'p_=' + this.page;

	/* the max rows */
	url += '&' + this.id + '_' + 'mr_=' + this.maxRows;
	
	/* the sort objects */
	for (var i = 0; i < this.sortSet.length; i++) {
		var sort = this.sortSet[i];
		url += '&' + this.id + '_' + 's_' + sort.position + '_' + sort.property + '=' + sort.order;
	}

	/* the filter objects */
	for (var i = 0; i < this.filterSet.length; i++) {
		var filter = this.filterSet[i];
		url += '&' + this.id + '_' + 'f_' + filter.property + '=' + filter.value;
	}
	
	/* the export */
	if (this.exportType) {
		url += '&' + this.id + '_' + 'e_=' + this.exportType;
	}
	
	return url;
}

/* convenience methods so do not have to manually work with the api */

function addLimitToManager(id) {
	var limit = new Limit(id);
	LimitManager.addLimit(limit);	
}

function setPageToLimit(id, page) {
	LimitManager.getLimit(id).setPage(page);
}

function setMaxRowsToLimit(id, maxRows) {
	LimitManager.getLimit(id).setMaxRows(maxRows);
	setPageToLimit(id, '1');
}
 
function addSortToLimit(id, position, property, order) {
	/*First remove the sort if it is set on the limit, 
		and then set the page back to the first one.*/
	removeSortFromLimit(id, property);
	setPageToLimit(id, '1');

	var limit = LimitManager.getLimit(id);
	var sort = new Sort(position, property, order); 
	limit.addSort(sort);
}

function removeSortFromLimit(id, property) {
	var limit = LimitManager.getLimit(id);
	var sortSet = limit.getSortSet();
	
	for (var i = 0; i < sortSet.length; i++) {
		var sort = sortSet[i];
		if (sort.property == property) {
			sortSet.splice(i, 1);
		}
	}
}

function removeAllSortsFromLimit(id) {
	var limit = LimitManager.getLimit(id);
	limit.setSortSet(new Array());
	setPageToLimit(id, '1');
}

function getSortFromLimit(id, property) {
	var limit = LimitManager.getLimit(id);
	var sortSet = limit.getSortSet();
	
	for (var i = 0; i < sortSet.length; i++) {
		var sort = sortSet[i];
		if (sort.property == property) {
			return sort;
		}
	}
}

function addFilterToLimit(id, property, value) {
	/*First remove the filter if it is set on the limit, 
		and then set the page back to the first one.*/
	removeFilterFromLimit(id, property);
	setPageToLimit(id, '1');

	var limit = LimitManager.getLimit(id);
	var filter = new Filter(property, value); 
	limit.addFilter(filter);
}

function removeFilterFromLimit(id, property) {
	var limit = LimitManager.getLimit(id);
	var filterSet = limit.getFilterSet();
	
	for (var i = 0; i < filterSet.length; i++) {
		var filter = filterSet[i];
		if (filter.property == property) {
			filterSet.splice(i, 1);
		}
	}
}

function removeAllFiltersFromLimit(id) {
	var limit = LimitManager.getLimit(id);
	limit.setFilterSet(new Array());
	setPageToLimit(id, '1');
}

function getFilterFromLimit(id, property) {
	var limit = LimitManager.getLimit(id);
	var filterSet = limit.getFilterSet();
	
	for (var i = 0; i < filterSet.length; i++) {
		var filter = filterSet[i];
		if (filter.property == property) {
			return filter;
		}
	}
}

function setExportToLimit(id, exportType) {
	LimitManager.getLimit(id).setExport(exportType);
}

function createHiddenInputFieldsForLimit(id) {
	var limit = LimitManager.getLimit(id);
	var form = getFormByTableId(id);
	limit.createHiddenInputFields(form);
}

function createHiddenInputFieldsForLimitAndSubmit(id) {
	var limit = LimitManager.getLimit(id);
	var form = getFormByTableId(id);
	limit.createHiddenInputFields(form);
	form.submit();
}

function createParameterStringForLimit(id) {
	var limit = LimitManager.getLimit(id);
	return limit.createParameterString();
}

function getFormByTableId(id) {
	var node = document.getElementById(id);
	var found = false;
	while (!found) {
		node = node.parentNode;
		if (node.nodeName == 'FORM') {
			found = true;
			return node;
		}
	}
}

/* Special Effects */

var dynFilter;

function DynFilter(filter, id, property) {
	this.filter = filter;
	this.id = id;
	this.property = property;
}

function createDynFilter(filter, id, property) {
	dynFilter = new DynFilter(filter, id, property);

	var dynFilterDiv = document.getElementById('dynFilterDiv');
	
	// already have a filter
	if (dynFilterDiv) {
		return; 
	}
	
	// create the div tag
	dynFilterDiv = document.createElement('div');
	dynFilterDiv.id = 'dynFilterDiv';
	filter.appendChild(dynFilterDiv);

	// create the input tag
	var dynFilterInput = document.createElement('input');
	dynFilterInput.type = 'text';
	dynFilterInput.id = 'dynFilterInput';
	dynFilterInput.name = 'filter';
	
	var inputValue = filter.firstChild.nodeValue;
	if (!inputValue) {
		inputValue = '';
	}
	
	dynFilterInput.value = inputValue;
	addEvent(dynFilterInput, 'keypress', keypressDynFilterInput, false);
	addEvent(dynFilterInput, 'blur', blurDynFilterInput, false);
	dynFilterDiv.appendChild(dynFilterInput);
	dynFilterInput.focus();
}

function blurDynFilterInput(event) {
	var dynFilterDiv = document.getElementById('dynFilterDiv');
	var dynFilterInput = document.getElementById('dynFilterInput');
	addFilterText(dynFilter.filter, dynFilterDiv)
	addFilterToLimit(dynFilter.id, dynFilter.property, dynFilterInput.value);
}

function keypressDynFilterInput(event) {
	if (event.keyCode == 13) {
		var dynFilterDiv = document.getElementById('dynFilterDiv');
		var dynFilterInput = document.getElementById('dynFilterInput');
		addFilterText(dynFilter.filter, dynFilterDiv)
		addFilterToLimit(dynFilter.id, dynFilter.property, dynFilterInput.value);
		onInvokeAction(dynFilter.id);
	}
}

function addFilterText(filter, dynFilterDiv) {
	var dynFilterInput = document.getElementById('dynFilterInput');
	var inputValue = dynFilterInput.value;
	var textNode = document.createTextNode(inputValue);
	
	var node = filter.firstChild;
	if (node.nodeType == 3) { // node type of text
		filter.replaceChild(textNode, node);
	} else {
		filter.insertBefore(textNode, node);
	}
	
	filter.removeChild(dynFilterDiv);
}

/*A cross browser way to add events to different objects.*/
function addEvent(obj, evType, fn, useCapture){
	if (obj.addEventListener){
		obj.addEventListener(evType, fn, useCapture);
		return true;
	} else if (obj.attachEvent){
		var r = obj.attachEvent("on"+evType, fn);
		return r;
	} else {
		alert("Handler could not be attached");
	}
}
