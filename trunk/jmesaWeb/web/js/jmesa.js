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
    var limit = this;
    
    var exists = $(form).find(':hidden[@name=' + limit.id + '_' + 'p_' + ']').val();
    if (exists) {
        return false;
    }

	/* the current page */
	$(form).append('<input type="hidden" name="' + limit.id + '_' + 'p_' + '" value="' + limit.page + '"/>');
    $(form).append('<input type="hidden" name="' + limit.id + '_' + 'mr_' + '" value="' + limit.maxRows + '"/>');
    
	/* the sort objects */
	var sortSet = limit.getSortSet();
	$.each(sortSet, function(index, sort) {
        $(form).append('<input type="hidden" name="' + limit.id + '_' + 's_'  + sort.position + '_' + sort.property + '" value="' + sort.order + '"/>');
	});

	/* the filter objects */
	var filterSet = limit.getFilterSet();
    $.each(filterSet, function(index, filter) {
        $(form).append('<input type="hidden" name="' + limit.id + '_' + 'f_' + filter.property + '" value="' + filter.value + '"/>');
    });
    
    return true;
}

Limit.prototype.createParameterString = function() {
    var limit = this;

	var url = '';

	/* the current page */
	url += limit.id + '_' + 'p_=' + limit.page;

	/* the max rows */
	url += '&' + limit.id + '_' + 'mr_=' + limit.maxRows;
	
	/* the sort objects */
	var sortSet = limit.getSortSet();
    $.each(sortSet, function(index, sort) {
        url += '&' + limit.id + '_' + 's_' + sort.position + '_' + sort.property + '=' + sort.order;
    });

	/* the filter objects */
	var filterSet = limit.getFilterSet();
    $.each(filterSet, function(index, filter) {
        url += '&' + limit.id + '_' + 'f_' + filter.property + '=' + encodeURIComponent(filter.value);
    });
	
	/* the export */
	if (limit.exportType) {
		url += '&' + limit.id + '_' + 'e_=' + limit.exportType;
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
	$.each(sortSet, function(index, sort) {
        if (sort.property == property) {
            sortSet.splice(index, 1);
            return false;
        }
    });
}

function removeAllSortsFromLimit(id) {
	var limit = LimitManager.getLimit(id);
	limit.setSortSet(new Array());
	setPageToLimit(id, '1');
}

function getSortFromLimit(id, property) {
	var limit = LimitManager.getLimit(id);
	var sortSet = limit.getSortSet();
    $.each(sortSet, function(index, sort) {
        if (sort.property == property) {
            return sort;
        }
    });
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
    $.each(filterSet, function(index, filter) {
        if (filter.property == property) {
            filterSet.splice(index, 1);
            return false;
        }
    });
}

function removeAllFiltersFromLimit(id) {
	var limit = LimitManager.getLimit(id);
	limit.setFilterSet(new Array());
	setPageToLimit(id, '1');
}

function getFilterFromLimit(id, property) {
	var limit = LimitManager.getLimit(id);
	var filterSet = limit.getFilterSet();
    $.each(filterSet, function(index, filter) {
        if (filter.property == property) {
            return filter;
        }
    });
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
	var created = limit.createHiddenInputFields(form);
	if (created) {
	   form.submit();
	}
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
    
    /* If already have a filter input box. */
    if ($('#dynFilterDiv').size() > 0) {
        return; //already created
    }
    
    var cell = $(filter);
    
    /* Get the original value from the filter. */
    var originalValue = cell.text();
    cell.text('')
    
    var width = cell.width();

    /* Create the dynamic filter input box. */
    cell.append('<div id="dynFilterDiv"><input id="dynFilterInput" name="filter" style="width:' + width + 'px" value="' + originalValue + '" /></div>');

    var input = $('#dynFilterInput');
    input.focus();
    
    /* The event if press keys in the filter input box. */
    $('#dynFilterInput').keypress(function(event) {
	    if (event.keyCode == 13) { // press the enter key 
	       var changedValue = input.val();
	       cell.text(changedValue);
	       addFilterToLimit(dynFilter.id, dynFilter.property, changedValue);
	       onInvokeAction(dynFilter.id);
	    }
    });
    
    /* The event if leaves the filter input box. */
    $('#dynFilterInput').blur(function() {
        var changedValue = input.val();
        cell.text(changedValue);
	    addFilterToLimit(dynFilter.id, dynFilter.property, changedValue);
	    $('#dynFilterDiv').remove();
    });
}

function createDynDroplistFilter(filter, id, property, options) {
    dynFilter = new DynFilter(filter, id, property);
    
    /* If already have a filter input box. */
    if ($('#dynFilterDiv').size() > 0) {
        return; //already created
    }
    
    var cell = $(filter);
    
    /* Get the original value from the filter. */
    var originalValue = cell.text();

    var width = cell.width();
    
    var size = 0;

    /* Create the dynamic select input box. */
    html = '<div id="dynFilterDiv" style="top:17px"><select id="dynFilterDroplist" name="filter">';
    html += '<option value=""> </option>';
    $.each(options, function(key, value) {
        size++;
    	if (key == originalValue) {
    		html += '<option selected="selected" value="' + key + '">' + value + '</option>';
    	} else {
    		html += '<option value="' + key + '">' + value + '</option>';
    	}
    });
    html += '</select></div>';
    
    cell.append(html);
    
    var div = $('#dynFilterDiv');
    var input = $('#dynFilterDroplist');
    
    var selectWidth = input.width();
    if (selectWidth > width) {
        width = selectWidth;
    }
    
    if (size > 10) {
        size = 10;
    }
    
    // Now show select list. This keeps the screen from blinking.
    input.width(width).attr('size',size);
    div.width(width).css( {visibility:"visible", borderStyle:"none"} ) 
    
    var originalBackgroundColor = cell.css("backgroundColor");
	cell.css({backgroundColor:div.css("backgroundColor")});

    input.focus();

    /* Something was selected */
    $('#dynFilterDroplist').change(function() {
        var changedValue = $("#dynFilterDroplist option:selected").val();
        cell.text(changedValue);
	    addFilterToLimit(dynFilter.id, dynFilter.property, changedValue);
	    $('#dynFilterDiv').remove();
	    onInvokeAction(dynFilter.id);
    });

    $('#dynFilterDroplist').blur(function() {
        var changedValue = $("#dynFilterDroplist option:selected").val();
        cell.text(changedValue);
	    addFilterToLimit(dynFilter.id, dynFilter.property, changedValue);
	    $('#dynFilterDiv').remove();
	    cell.css({backgroundColor:originalBackgroundColor});
    });
}

/* Create a dropshadow for the tables */
function addDropShadow(imagesPath, theme) {
    if (!theme) {
        theme = 'jmesa';
    }
    $('div.' + theme + ' .table')
        .wrap("<div class='wrap0'><div class='wrap1'><div class='wrap2'><div class='dropShadow'></div></div></div></div>")
        .css({'background': 'url(' + imagesPath + 'shadow_back.gif) 100% repeat'});
    
    $('.' + theme + ' div.wrap0').css({'background': 'url(' + imagesPath + 'shadow.gif) right bottom no-repeat', 'float':'left'});
    $('.' + theme + ' div.wrap1').css({'background': 'url(' + imagesPath + 'shadow180.gif) no-repeat'});
    $('.' + theme + ' div.wrap2').css({'background': 'url(' + imagesPath + 'corner_bl.gif) -18px 100% no-repeat'});
    $('.' + theme + ' div.dropShadow').css({'background': 'url(' + imagesPath + 'corner_tr.gif) 100% -18px no-repeat'});
    
    $('div.' + theme).append('<div style="clear:both">&nbsp;</div>');
}

/* Worksheet */

var wsColumn;

function WsColumn(column, id, uniqueProperties, property) {
    this.column = column;
    this.id = id;
    this.uniqueProperties = uniqueProperties;
    this.property = property;
}

function createWsColumn(column, id, uniqueProperties, property) {
    wsColumn = new WsColumn(column, id, uniqueProperties, property);
    
    /* If already have a column input box. */
    if ($('#wsColumnDiv').size() > 0) {
        return; //already created
    }
    
    var cell = $(column);
    
    cell.parent().removeAttr('style');
    
    /* Get the original value from the column. */
    var originalValue = cell.text();
    cell.text('')
    
    var width = cell.width();

    /* Create the worksheet column input box. */
    cell.append('<div id="wsColumnDiv"><input id="wsColumnInput" name="column" style="width:' + width + 'px" value="' + originalValue + '"/></div>');
    
    var input = $('#wsColumnInput'); 
    input.focus();
    
    /* The event if press keys in the column input box. */
    $('#wsColumnInput').keypress(function(event) {
        if (event.keyCode == 13) { // press the enter key 
           var changedValue = input.val();
           cell.text(changedValue);
           if (changedValue != originalValue) {
           
               var data = '{ "id" : "' + wsColumn.id + '"';
               
               var props = wsColumn.uniqueProperties;
               $.each(props, function(key, value) {
                   data += ', "up_' + key + '" : "' + value + '"';
               });
               
               data += '}'

               $.post('jmesa.wrk?', eval('(' + data + ')'), function(data) {
               
               });               
           }
        }
    });
    
    /* The event if leaves the column input box. */
    $('#wsColumnInput').blur(function() {
        var changedValue = input.val();
        cell.text(changedValue);
        $('#wsColumnDiv').remove();
    });
}
