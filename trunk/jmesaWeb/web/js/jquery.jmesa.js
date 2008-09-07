(function ($) {
    
    var tableFacades = new Object();

    var addTableFacade = function(tableFacade) {
        tableFacades[tableFacade.limit.id] = tableFacade;
    }
    
    var getFormByTableId = function(id) {
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
 
    $.jmesa = {
        addTableFacadeToManager : function(id) {
            var tableFacade = new TableFacade(id);
            addTableFacade(tableFacade);	
        },
        getTableFacade : function(id) {
            return tableFacades[id];
        },
        setSaveToWorksheet : function(id) {
            this.getTableFacade(id).worksheet.save='true';
        },
        setFilterToWorksheet : function(id) {
            this.getTableFacade(id).worksheet.filter='true';
            this.setPageToLimit(id, '1');
        },
        removeFilterFromWorksheet : function(id) {
            this.getTableFacade(id).worksheet.filter=null;
            this.setPageToLimit(id, '1');
        },
        setPageToLimit : function(id, page) {
            this.getTableFacade(id).limit.setPage(page);
        },
        setMaxRowsToLimit : function(id, maxRows) {
            this.getTableFacade(id).limit.setMaxRows(maxRows);
            this.setPageToLimit(id, '1');
        },
        addSortToLimit : function(id, position, property, order) {
            /*First remove the sort if it is set on the limit, 
               and then set the page back to the first one.*/
            this.removeSortFromLimit(id, property);
            this.setPageToLimit(id, '1');
    
            var limit = this.getTableFacade(id).limit;
            var sort = new Sort(position, property, order); 
            limit.addSort(sort);
        },
        removeSortFromLimit : function(id, property) {
            var limit = this.getTableFacade(id).limit;
            var sortSet = limit.getSortSet();
            jQuery.each(sortSet, function(index, sort) {
                if (sort.property == property) {
                    sortSet.splice(index, 1);
                    return false;
                }
            });
        },
        removeAllSortsFromLimit : function(id) {
            var limit = this.getTableFacade(id).limit;
            limit.setSortSet(new Array());
            this.setPageToLimit(id, '1');
        },
        getSortFromLimit : function(id, property) {
            var limit = this.getTableFacade(id).limit;
            var sortSet = limit.getSortSet();
            jQuery.each(sortSet, function(index, sort) {
                if (sort.property == property) {
                    return sort;
                }
            });
        },
        addFilterToLimit : function(id, property, value) {
            /*First remove the filter if it is set on the limit, 
              and then set the page back to the first one.*/
            this.removeFilterFromLimit(id, property);
            this.setPageToLimit(id, '1');
    
            var limit = this.getTableFacade(id).limit;
            var filter = new Filter(property, value); 
            limit.addFilter(filter);
        },
        removeFilterFromLimit : function(id, property) {
            var limit = this.getTableFacade(id).limit;
            var filterSet = limit.getFilterSet();
            jQuery.each(filterSet, function(index, filter) {
                if (filter.property == property) {
                    filterSet.splice(index, 1);
                    return false;
                }
            });
        },
        removeAllFiltersFromLimit : function(id) {
            var tableFacade = this.getTableFacade(id);
    
            var limit = tableFacade.limit;
            limit.setFilterSet(new Array());
            this.setPageToLimit(id, '1');
    
            var worksheet = tableFacade.worksheet;
            worksheet.filter = null;
        },
        getFilterFromLimit : function(id, property) {
            var limit = this.getTableFacade(id).limit;
            var filterSet = limit.getFilterSet();
            jQuery.each(filterSet, function(index, filter) {
                if (filter.property == property) {
                    return filter;
                }
            });
        },
        setExportToLimit : function(id, exportType) {
            this.getTableFacade(id).limit.setExport(exportType);
        },
        createHiddenInputFieldsForLimit : function(id) {
            var tableFacade = this.getTableFacade(id);
            var form = getFormByTableId(id);
            tableFacade.createHiddenInputFields(form);
        },
        createHiddenInputFieldsForLimitAndSubmit : function(id) {
            var tableFacade = this.getTableFacade(id);
            var form = getFormByTableId(id);
            var created = tableFacade.createHiddenInputFields(form);
            if (created) {
                form.submit();
            }
        },
        createParameterStringForLimit : function(id) {
            var tableFacade = this.getTableFacade(id);
            return tableFacade.createParameterString();
        }
    };
    
    /******* Objects and Prototype Functions ********/
    
    function TableFacade(id) {
        this.limit = new Limit(id);
        this.worksheet = new Worksheet();
    }

    function Worksheet() {
        this.save;
        this.filter;
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

    TableFacade.prototype.createHiddenInputFields = function(form) {
        var limit = this.limit;
    
        var exists = jQuery(form).find(':hidden[@name=' + limit.id + '_p_]').val();
        if (exists) {
            return false;
        }
    
        if (this.worksheet.save) {
            jQuery(form).append('<input type="hidden" name="' + limit.id + '_sw_" value="true"/>');
        }
    
        if (this.worksheet.filter) {
            jQuery(form).append('<input type="hidden" name="' + limit.id + '_fw_" value="true"/>');
        }
    
        /* tip the API off that in the loop of working with the table */
        jQuery(form).append('<input type="hidden" name="' + limit.id + '_tr_" value="true"/>');
    
        /* the current page */
        jQuery(form).append('<input type="hidden" name="' + limit.id + '_p_" value="' + limit.page + '"/>');
        jQuery(form).append('<input type="hidden" name="' + limit.id + '_mr_" value="' + limit.maxRows + '"/>');
    
        /* the sort objects */
        var sortSet = limit.getSortSet();
        jQuery.each(sortSet, function(index, sort) {
            jQuery(form).append('<input type="hidden" name="' + limit.id + '_s_'  + sort.position + '_' + sort.property + '" value="' + sort.order + '"/>');
        });
    
        /* the filter objects */
        var filterSet = limit.getFilterSet();
        jQuery.each(filterSet, function(index, filter) {
            jQuery(form).append('<input type="hidden" name="' + limit.id + '_f_' + filter.property + '" value="' + filter.value + '"/>');
        });
    
        return true;
    }

    TableFacade.prototype.createParameterString = function() {
        var limit = this.limit;
    
        var url = '';
    
        /* the current page */
        url += limit.id + '_p_=' + limit.page;
    
        /* the max rows */
        url += '&' + limit.id + '_mr_=' + limit.maxRows;
    
        /* the sort objects */
        var sortSet = limit.getSortSet();
        jQuery.each(sortSet, function(index, sort) {
            url += '&' + limit.id + '_s_' + sort.position + '_' + sort.property + '=' + sort.order;
        });
    
        /* the filter objects */
        var filterSet = limit.getFilterSet();
        jQuery.each(filterSet, function(index, filter) {
            url += '&' + limit.id + '_f_' + filter.property + '=' + encodeURIComponent(filter.value);
        });
    
        /* the export */
        if (limit.exportType) {
            url += '&' + limit.id + '_e_=' + limit.exportType;
        }
    
        /* tip the API off that in the loop of working with the table */
        url += '&' + limit.id + '_tr_=true';
    
        if (this.worksheet.save) {
            url += '&' + limit.id + '_sw_=true';
        }
    
        if (this.worksheet.filter) {
            url += '&' + limit.id + '_fw_=true';
        }
    
        return url;
    }
    
    /* Special Effects */

    var dynFilter;

    function DynFilter(filter, id, property) {
        this.filter = filter;
        this.id = id;
        this.property = property;
    }

    var effects = {
        createDynFilter : function(filter, id, property) {
            if (dynFilter) {
                return;
            }
    
            dynFilter = new DynFilter(filter, id, property);
    
            var cell = jQuery(filter);
            var width = cell.width() + 1;
            var originalValue = cell.text();
    
            cell.html('<div id="dynFilterDiv"><input id="dynFilterInput" name="filter" style="width:' + width + 'px" value="' + originalValue + '" /></div>');
    
            var input = jQuery('#dynFilterInput');
            input.focus();
    
            jQuery(input).keypress(function(event) {
                if (event.keyCode == 13) { // press the enter key 
                    var changedValue = input.val();
                    cell.html(changedValue);
                    $.jmesa.addFilterToLimit(dynFilter.id, dynFilter.property, changedValue);
                    onInvokeAction(dynFilter.id);
                    dynFilter = null;
                }
            });
    
            jQuery(input).blur(function() {
                var changedValue = input.val();
                cell.html(changedValue);
                $.jmesa.addFilterToLimit(dynFilter.id, dynFilter.property, changedValue);
                dynFilter = null;
            });
        },
        createDynDroplistFilter : function(filter, id, property, options) {
            if (dynFilter) {
                return;
            }

            dynFilter = new DynFilter(filter, id, property);
    
            if (jQuery('#dynFilterDroplistDiv').size() > 0) {
                return; // filter already created
            }
    
            /* The cell that represents the filter. */
            var cell = jQuery(filter);
    
            /* Get the original filter value and width. */
            var originalValue = cell.text();
            var width = cell.width();
    
            /* Need to first get the size of the arrary. */
            var size = 1;
            jQuery.each(options, function() {
                size++;
                if (size > 10) {
                    size = 10;
                    return false;
                }
            });
    
            /* Create the dynamic select input box. */
            cell.html('<div id="dynFilterDroplistDiv" style="top:17px">');
    
            var html = '<select id="dynFilterDroplist" name="filter" size="' + size + '">';
            html += '<option value=""> </option>';
            jQuery.each(options, function(key, value) {
                if (key == originalValue) {
                    html += '<option selected="selected" value="' + key + '">' + value + '</option>';
                } else {
                    html += '<option value="' + key + '">' + value + '</option>';
                }
            });
    
            html += '</select>';

            var div = jQuery('#dynFilterDroplistDiv');
            div.html(html);

            var input = jQuery('#dynFilterDroplist');

            /* Resize the column if it is not at least as wide as the column. */    
            var selectWidth = input.width();
            if (selectWidth < width) {
                input.width(width + 5); // always make the droplist overlap some
            }

            input.focus();

            var originalBackgroundColor = cell.css("backgroundColor");
            cell.css({backgroundColor:div.css("backgroundColor")});

            /* Something was selected or the clicked off the droplist. */

            jQuery(input).change(function() {
                var changedValue = jQuery("#dynFilterDroplistDiv option:selected").val();
                cell.text(changedValue);
                $.jmesa.addFilterToLimit(dynFilter.id, dynFilter.property, changedValue);
                onInvokeAction(dynFilter.id);
                dynFilter = null;
            });

            jQuery(input).blur(function() {
                var changedValue = jQuery("#dynFilterDroplistDiv option:selected").val();
                cell.text(changedValue);
                jQuery('#dynFilterDroplistDiv').remove();
                cell.css({backgroundColor:originalBackgroundColor});
                dynFilter = null;
            });
        },
        addDropShadow : function(imagesPath, theme) {
            if (!theme) {
                theme = 'jmesa';
            }
            jQuery('div.' + theme + ' .table')
            .wrap("<div class='wrap0'><div class='wrap1'><div class='wrap2'><div class='dropShadow'></div></div></div></div>")
            .css({'background': 'url(' + imagesPath + 'shadow_back.gif) 100% repeat'});
    
            jQuery('.' + theme + ' div.wrap0').css({'background': 'url(' + imagesPath + 'shadow.gif) right bottom no-repeat', 'float':'left'});
            jQuery('.' + theme + ' div.wrap1').css({'background': 'url(' + imagesPath + 'shadow180.gif) no-repeat'});
            jQuery('.' + theme + ' div.wrap2').css({'background': 'url(' + imagesPath + 'corner_bl.gif) -18px 100% no-repeat'});
            jQuery('.' + theme + ' div.dropShadow').css({'background': 'url(' + imagesPath + 'corner_tr.gif) 100% -18px no-repeat'});
    
            jQuery('div.' + theme).append('<div style="clear:both">&nbsp;</div>');
        }
    }
    
    $.extend($.jmesa, effects);

    /* Worksheet */

    var wsColumn;

    function WsColumn(column, id, uniqueProperties, property) {
        this.column = column;
        this.id = id;
        this.uniqueProperties = uniqueProperties;
        this.property = property;
    }
    
    var ws = {
        createWsColumn : function(column, id, uniqueProperties, property) {
            if (wsColumn) {
                return;
            }

            wsColumn = new WsColumn(column, id, uniqueProperties, property);
    
            var cell = jQuery(column);
            var width = cell.width();
            var originalValue = cell.text();
    
            cell.parent().width(width); // set the outer width to avoid dynamic column width changes
    
            cell.html('<div id="wsColumnDiv"><input id="wsColumnInput" name="column" style="width:' + (width + 1) + 'px" value="' + originalValue + '"/></div>');
    
            var input = jQuery('#wsColumnInput'); 
            input.focus();
    
            jQuery('#wsColumnInput').keypress(function(event) {
                if (event.keyCode == 13) { // press the enter key 
                    var changedValue = input.val();
                    cell.html(changedValue);
                    if (changedValue != originalValue) {
                        submitWsColumn(originalValue, changedValue);
                    }
                    wsColumn = null;
                }
            });
    
            jQuery('#wsColumnInput').blur(function() {
                var changedValue = input.val();
                cell.html(changedValue);
                if (changedValue != originalValue) {
                    submitWsColumn(originalValue, changedValue);
                }
                wsColumn = null;
            });
        },
        submitWsCheckboxColumn : function(column, id, uniqueProperties, property) {
            wsColumn = new WsColumn(column, id, uniqueProperties, property);
    
            var checked = column.checked;
    
            var changedValue = 'unchecked';
            if (checked) {
                changedValue = 'checked';
            }
    
            var originalValue = 'unchecked';
            if (!checked) { // the first time the original value is the opposite
                originalValue = 'checked';
            }
    
            submitWsColumn(originalValue, changedValue);
    
            wsColumn = null;
        },
        submitWsColumn : function(originalValue, changedValue) {
            var data = '{ "id" : "' + wsColumn.id + '"';
    
            data += ', "cp_" : "' + wsColumn.property + '"';
    
            var props = wsColumn.uniqueProperties;
            jQuery.each(props, function(key, value) {
                data += ', "up_' + key + '" : "' + value + '"';
            });
    
            data += ', "ov_" : "' + originalValue + '"';
            data += ', "cv_" : "' + changedValue + '"';
    
            data += '}'
    
            jQuery.post('jmesa.wrk?', eval('(' + data + ')'), function(data) {});    
        }    
    }
    
    $.extend($.jmesa, ws);

})(jQuery);
