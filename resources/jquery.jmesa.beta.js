/*global jQuery*/
/*jslint nomen: false, browser: true, evil: true */
// verified with JSLint at http://www.jslint.com/

// We'll wrap this is a function so that we can safely use $ even if it is
// in use by a different library in the global namespace.
(function ($) {

    // We'll store the TableFacadeManager state on the document so that
    // even if this script gets included twice, we don't overwrite the
    // existing data.
    if (!document.__jmesaData) {
        document.__jmesaData = {
            tableIds : [],
            tableFacades : {},
            events : {}
        };
    }

    // Unlike previous version of jmesa.js, this class does not store state,
    // it is simply an accessor.
    var TableFacadeManager = {
        addTableFacade : function (tableFacade) {
            if (!this.getTableFacade(tableFacade.limit.id)) {
                this.getEvents()[tableFacade.limit.id] = {};
            }
            this.getTableIds().push(tableFacade.limit.id);
            this.getTableFacades()[tableFacade.limit.id] = tableFacade;
        },
        getTableFacade : function (id) {
            return this.getTableFacades()[id];
        },
        getTableFacades : function () {
            return this.getJMesaData().tableFacades;
        },
        getTableIds : function () {
            return this.getJMesaData().tableIds;
        },
        getJMesaData : function () {
            return document.__jmesaData;
        },
        getEvents : function () {
            return this.getJMesaData().events;
        },
        getEventsFor : function (id) {
            return this.getEvents()[id];
        }
    };

    // takes a jQuery expression (string) or DOM node as a parameter.  If the
    // first argument is a string, the first node that is matched is used. If
    // the node is the top-level table node of a JMesa table, $(node) is
    // returned. Otherwise, the node's parents are searched for the JMesa table
    // node and $(table) is returned.
    // This is a shortcut for the API so that we don't have to rely on the
    // presence of the '$' or 'jQuery' variables in the global namespace.
    // @see jQuery.noConflict()
    document.jmesaTableFor = function (elem) {
        var result = null;
        if (typeof(elem) === 'string') {
            elem = $(elem).get(0);
        }
        if (elem.tagName && elem.tagName === 'TABLE' &&
            elem.id && TableFacadeManager.getTableFacade(elem.id)) {
            result = $(elem);
        } else {
            $(elem).parents("table").each(function () {
                if (this.id && TableFacadeManager.getTableFacade(this.id)) {
                    result = $(this);
                    return !result;
                }
            });
        }
        return result;
    };

    // This object will hold the utility classes for the JMesa API.  If you
    // are using jmesa.js, they get copied to the global namespace.  Otherwise,
    // they can be accessed like this:  $.jmesa.Limit, etc.
    var classes = {};

    // This object will hold all of the functions for the JMesa API.
    // Chainable and non-chainable functions will also be used for the jQuery
    // plugin.  If using jmesa.js, all of these functions are also copied into
    // the global namespace.  If you are not using jmesa.js, the functions can
    // be called like this:
    //
    // $.jmesa.createDynFilter(...)
    //
    var functions = {};

    $.extend(classes, {
        Limit : function (id) {
            this.id = id;
            this.page = null;
            this.maxRows = null;
            this.sortSet = [];
            this.filterSet = [];
            this.exportType = null;
        },
        Worksheet : function () {
            this.save = null;
            this.filter = null;
        },
        TableFacade : function (id) {
            this.limit = new classes.Limit(id);
            this.worksheet = new classes.Worksheet();
        },
        Sort : function (position, property, order) {
            this.position = position;
            this.property = property;
            this.order = order;
        },
        Filter : function (property, value) {
            this.property = property;
            this.value = value;
        },
        DynFilter : function (filter, id, property) {
            this.filter = filter;
            this.id = id;
            this.property = property;
        },
        WsColumn : function (column, id, uniqueProperties, property) {
            this.column = column;
            this.id = id;
            this.uniqueProperties = uniqueProperties;
            this.property = property;
        }
    });

    $.extend(classes.Limit.prototype, {
        getId : function () {
            return this.id;
        },
        setId : function (id) {
            this.id = id;
        },
        getPage : function () {
            return this.page;
        },
        setPage : function (page) {
            this.page = page;
        },
        getMaxRows : function () {
            return this.maxRows;
        },
        setMaxRows : function (maxRows) {
            this.maxRows = maxRows;
        },
        getSortSet : function () {
            return this.sortSet;
        },
        addSort : function (sort) {
            this.sortSet[this.sortSet.length] = sort;
        },
        setSortSet : function (sortSet) {
            this.sortSet = sortSet;
        },
        getFilterSet : function () {
            return this.filterSet;
        },
        addFilter : function (filter) {
            this.filterSet[this.filterSet.length] = filter;
        },
        setFilterSet : function (filterSet) {
            this.filterSet = filterSet;
        },
        getExport : function () {
            return this.exportType;
        },
        setExport : function (exportType) {
            this.exportType = exportType;
        }
    });

    $.extend(classes.TableFacade.prototype, {
        createHiddenInputFields : function (form) {
            var limit = this.limit;

            var exists = $(form).find(':hidden[@name=' + limit.id + '_p_]').val();
            if (exists) {
                return false;
            }

            if (this.worksheet.save) {
                $(form).append('<input type="hidden" name="' + limit.id + '_sw_" value="true"/>');
            }

            if (this.worksheet.filter) {
                $(form).append('<input type="hidden" name="' + limit.id + '_fw_" value="true"/>');
            }

            // tip the API off that in the loop of working with the table
            $(form).append('<input type="hidden" name="' + limit.id + '_tr_" value="true"/>');

            // the current page
            $(form).append('<input type="hidden" name="' + limit.id + '_p_" value="' + limit.page + '"/>');
            $(form).append('<input type="hidden" name="' + limit.id + '_mr_" value="' + limit.maxRows + '"/>');

            // the sort objects
            var sortSet = limit.getSortSet();
            $.each(sortSet, function (index, sort) {
                $(form).append('<input type="hidden" name="' + limit.id + '_s_'  + sort.position + '_' + sort.property + '" value="' + sort.order + '"/>');
            });

            // the filter objects
            var filterSet = limit.getFilterSet();
            $.each(filterSet, function (index, filter) {
                $(form).append('<input type="hidden" name="' + limit.id + '_f_' + filter.property + '" value="' + filter.value + '"/>');
            });

            return true;
        },
        createParameterString : function () {
            var limit = this.limit;

            var url = '';

            // the current page
            url += limit.id + '_p_=' + limit.page;

            // the max rows
            url += '&' + limit.id + '_mr_=' + limit.maxRows;

            // the sort objects
            var sortSet = limit.getSortSet();
            $.each(sortSet, function (index, sort) {
                url += '&' + limit.id + '_s_' + sort.position + '_' + sort.property + '=' + sort.order;
            });

            // the filter objects
            var filterSet = limit.getFilterSet();
            $.each(filterSet, function (index, filter) {
                url += '&' + limit.id + '_f_' + filter.property + '=' + encodeURIComponent(filter.value);
            });

            // the export
            if (limit.exportType) {
                url += '&' + limit.id + '_e_=' + limit.exportType;
            }

            // tip the API off that in the loop of working with the table
            url += '&' + limit.id + '_tr_=true';

            if (this.worksheet.save) {
                url += '&' + limit.id + '_sw_=true';
            }

            if (this.worksheet.filter) {
                url += '&' + limit.id + '_fw_=true';
            }

            return url;
        }
    });

    // Placing the dynFilter and wsColumn globals in this namespace is a
    // breaking change. I do not think that client code really uses this,
    // but in case it does,	that code can be modified to access $.jmesa instead.
    $.extend(functions, {
        getAllTables : function () {
            return $('#' + TableFacadeManager.getTableIds().join(',#'));
        },
        createDynFilter : function (filter, id, property) {
            if ($.jmesa.dynFilter) {
                return;
            }
            $.jmesa.dynFilter = new classes.DynFilter(filter, id, property);
            var cell = $(filter);
            var width = cell.width() + 1;
            var originalValue = cell.text();

            cell.html('<div id="dynFilterDiv"><input id="dynFilterInput" name="filter" style="width:' + width + 'px" value="' + originalValue + '" /></div>');

            var input = $('#dynFilterInput');
            input.focus();

            var table = $('#' + id);

            $(input).keypress(function (event) {
                if (event.keyCode === 13) { // press the enter key
                    var changedValue = input.val();
                    cell.html(changedValue);
                    table.addFilterToLimit(property, changedValue);
                    table.invokeAction();
                    $.jmesa.dynFilter = null;
                }
            });

            $(input).blur(function () {
                var changedValue = input.val();
                cell.html(changedValue);
                table.addFilterToLimit(property, changedValue);
                $.jmesa.dynFilter = null;
            });
        },
        createDynDroplistFilter : function (filter, id, property, options) {
            if ($.jmesa.dynFilter) {
                return;
            }

            $.jmesa.dynFilter = new classes.DynFilter(filter, id, property);

            if ($('#dynFilterDroplistDiv').size() > 0) {
                return; // filter already created
            }

            // The cell that represents the filter.
            var cell = $(filter);

            // Get the original filter value and width.
            var originalValue = cell.text();
            var width = cell.width();

            // Need to first get the size of the arrary.
            var size = 1;
            $.each(options, function () {
                size = size + 1;
                if (size > 10) {
                    size = 10;
                    return false;
                }
            });

            // Create the dynamic select input box.
            cell.html('<div id="dynFilterDroplistDiv" style="top:17px">');

            var html = '<select id="dynFilterDroplist" name="filter" size="' + size + '">';
            html += '<option value=""> </option>';
            $.each(options, function (key, value) {
                if (key === originalValue) {
                    html += '<option selected="selected" value="' + key + '">' + value + '</option>';
                } else {
                    html += '<option value="' + key + '">' + value + '</option>';
                }
            });

            html += '</select>';

            var div = $('#dynFilterDroplistDiv');
            div.html(html);

            var input = $('#dynFilterDroplist');

            // Resize the column if it is not at least as wide as the column.
            var selectWidth = input.width();
            if (selectWidth < width) {
                input.width(width + 5); // always make the droplist overlap some
            }

            input.focus();

            var originalBackgroundColor = cell.css("backgroundColor");
            cell.css({
                backgroundColor : div.css("backgroundColor")
            });

            // Something was selected or the clicked off the droplist.

            var table = $('#' + id);

            $(input).change(function () {
                var changedValue = $("#dynFilterDroplistDiv option:selected").val();
                cell.text(changedValue);
                table.addFilterToLimit(property, changedValue);
                table.invokeAction();
                $.jmesa.dynFilter = null;
            });

            $(input).blur(function () {
                var changedValue = jQuery("#dynFilterDroplistDiv option:selected").val();
                cell.text(changedValue);
                $('#dynFilterDroplistDiv').remove();
                cell.css({
                    backgroundColor : originalBackgroundColor
                });
                $.jmesa.dynFilter = null;
            });
        },
        addDropShadow : function (imagesPath, theme) {
            if (!theme) {
                theme = 'jmesa';
            }
            $('div.' + theme + ' .table')
            .wrap("<div class='wrap0'><div class='wrap1'><div class='wrap2'><div class='dropShadow'></div></div></div></div>")
            .css({
                'background': 'url(' + imagesPath + 'shadow_back.gif) 100% repeat'
                });

            $('.' + theme + ' div.wrap0').css({
                'background' : 'url(' + imagesPath + 'shadow.gif) right bottom no-repeat',
                'float' : 'left'
            });
            $('.' + theme + ' div.wrap1').css({
                'background' : 'url(' + imagesPath + 'shadow180.gif) no-repeat'
            });
            $('.' + theme + ' div.wrap2').css({
                'background' : 'url(' + imagesPath + 'corner_bl.gif) -18px 100% no-repeat'
            });
            $('.' + theme + ' div.dropShadow').css({
                'background': 'url(' + imagesPath + 'corner_tr.gif) 100% -18px no-repeat'
                });

            $('div.' + theme).append('<div style="clear:both">&nbsp;</div>');
        },
        createWsColumn : function (column, id, uniqueProperties, property) {
            if ($.jmesa.wsColumn) {
                return;
            }

            $.jmesa.wsColumn = new classes.WsColumn(column, id, uniqueProperties, property);

            var cell = jQuery(column);
            var width = cell.width();
            var originalValue = cell.text();

            cell.parent().width(width); // set the outer width to avoid dynamic column width changes

            cell.html('<div id="wsColumnDiv"><input id="wsColumnInput" name="column" style="width:' + (width + 1) + 'px" value="' + originalValue + '"/></div>');

            var input = jQuery('#wsColumnInput');
            input.focus();

            $('#wsColumnInput').keypress(function (event) {
                if (event.keyCode === 13) { // press the enter key
                    var changedValue = input.val();
                    cell.html(changedValue);
                    if (changedValue !== originalValue) {
                        functions.submitWsColumn(originalValue, changedValue);
                    }
                    $.jmesa.wsColumn = null;
                }
            });

            $('#wsColumnInput').blur(function () {
                var changedValue = input.val();
                cell.html(changedValue);
                if (changedValue !== originalValue) {
                    functions.submitWsColumn(originalValue, changedValue);
                }
                $.jmesa.wsColumn = null;
            });
        },
        submitWsCheckboxColumn : function (column, id, uniqueProperties, property) {

            $.jmesa.wsColumn = new classes.WsColumn(column, id, uniqueProperties, property);

            var checked = column.checked;

            var changedValue = 'unchecked';
            if (checked) {
                changedValue = 'checked';
            }

            var originalValue = 'unchecked';
            if (!checked) { // the first time the original value is the opposite
                originalValue = 'checked';
            }

            functions.submitWsColumn(originalValue, changedValue);

            $.jmesa.wsColumn = null;
        },
        submitWsColumn : function (originalValue, changedValue) {
            var data = '{ "id" : "' + $.jmesa.wsColumn.id + '"';

            data += ', "cp_" : "' + $.jmesa.wsColumn.property + '"';

            var props = $.jmesa.wsColumn.uniqueProperties;
            $.each(props, function (key, value) {
                data += ', "up_' + key + '" : "' + value + '"';
            });

            data += ', "ov_" : "' + originalValue + '"';
            data += ', "cv_" : "' + changedValue + '"';

            data += '}';

            $.post('jmesa.wrk?', eval('(' + data + ')'), function (data) {});
        }
    });

    // These functions will all be copied as jQuery-style chainable functions.
    // In order for this to work, these functions should accept the table ID
    // as the first parameter, and whatever other arguments are also needed.
    // In that case, calling something like $(table).addTableFacadeToManager()
    // will call chainable.addTableFacadeToManager(id) where the 'id' attribute
    // of the table is passed as the argument.  Any other arguments that are
    // specified by the caller will be passed as well.
    var chainable = {
        addTableFacadeToManager : function (id) {
            var tableFacade = new classes.TableFacade(id);
            TableFacadeManager.addTableFacade(tableFacade);
        },
        setSaveToWorksheet : function (id) {
            TableFacadeManager.getTableFacade(id).worksheet.save = 'true';
        },
        setFilterToWorksheet : function (id) {
            TableFacadeManager.getTableFacade(id).worksheet.filter = 'true';
            functions.setPageToLimit(id, '1');
        },
        removeFilterFromWorksheet : function (id) {
            TableFacadeManager.getTableFacade(id).worksheet.filter = null;
            functions.setPageToLimit(id, '1');
        },
        setPageToLimit : function (id, page) {
            TableFacadeManager.getTableFacade(id).limit.setPage(page);
        },
        setMaxRowsToLimit : function (id, maxRows) {
            TableFacadeManager.getTableFacade(id).limit.setMaxRows(maxRows);
            functions.setPageToLimit(id, '1');
        },
        addSortToLimit : function (id, position, property, order) {
            // First remove the sort if it is set on the limit,
            // and then set the page back to the first one.
            functions.removeSortFromLimit(id, property);
            functions.setPageToLimit(id, '1');

            var limit = TableFacadeManager.getTableFacade(id).limit;
            var sort = new classes.Sort(position, property, order);
            limit.addSort(sort);
        },
        removeSortFromLimit : function (id, property) {
            var limit = TableFacadeManager.getTableFacade(id).limit;
            var sortSet = limit.getSortSet();
            $.each(sortSet, function (index, sort) {
                if (sort.property === property) {
                    sortSet.splice(index, 1);
                    return false;
                }
            });
        },
        removeAllSortsFromLimit : function (id) {
            var limit = TableFacadeManager.getTableFacade(id).limit;
            limit.setSortSet([]);
            functions.setPageToLimit(id, '1');
        },
        addFilterToLimit : function (id, property, value) {
            // First remove the filter if it is set on the limit,
            // and then set the page back to the first one.
            functions.removeFilterFromLimit(id, property);
            functions.setPageToLimit(id, '1');

            var limit = TableFacadeManager.getTableFacade(id).limit;
            var filter = new classes.Filter(property, value);
            limit.addFilter(filter);
        },
        removeFilterFromLimit : function (id, property) {
            var limit = TableFacadeManager.getTableFacade(id).limit;
            var filterSet = limit.getFilterSet();
            $.each(filterSet, function (index, filter) {
                if (filter.property === property) {
                    filterSet.splice(index, 1);
                    return false;
                }
            });
        },
        removeAllFiltersFromLimit : function (id) {
            var tableFacade = TableFacadeManager.getTableFacade(id);

            var limit = tableFacade.limit;
            limit.setFilterSet([]);
            functions.setPageToLimit(id, '1');

            var worksheet = tableFacade.worksheet;
            worksheet.filter = null;
        },
        setExportToLimit : function (id, exportType) {
            TableFacadeManager.getTableFacade(id).limit.setExport(exportType);
        },
        createHiddenInputFieldsForLimit : function (id) {
            var tableFacade = TableFacadeManager.getTableFacade(id);
            var form = functions.getFormByTableId(id);
            tableFacade.createHiddenInputFields(form);
        },
        createHiddenInputFieldsForLimitAndSubmit : function (id) {
            var tableFacade = TableFacadeManager.getTableFacade(id);
            var form = functions.getFormByTableId(id);
            var created = tableFacade.createHiddenInputFields(form);
            if (created) {
                form.submit();
            }
        }
    };

    // Like the chainable functions, these get copied as jQuery functions and
    // will receive the table ID as the first parameter.  However, these
    // functions are meant to return some value other than the jQuery object,
    // so they are not chainable.
    var nonchainable = {
        createParameterStringForLimit : function (id) {
            var tableFacade = TableFacadeManager.getTableFacade(id);
            return tableFacade.createParameterString();
        },
        getFormByTableId : function (id) {
            return $('#' + id).parents("form:first").get(0);
        },
        getFilterFromLimit : function (id, property) {
            var limit = TableFacadeManager.getTableFacade(id).limit;
            var filterSet = limit.getFilterSet();
            var retval = null;
            $.each(filterSet, function (index, filter) {
                if (filter.property === property) {
                    // return filter; // same bug as getSortFromLimit I think
                    retval = filter;
                    return false;
                }
            });
            return retval;
        },
        getSortFromLimit : function (id, property) {
            var limit = TableFacadeManager.getTableFacade(id).limit;
            var sortSet = limit.getSortSet();
            var retval = null;
            $.each(sortSet, function (index, sort) {
                if (sort.property === property) {
                    // return sort;  // this I think was a bug in jmesa.js
                    retval = sort;
                    return false;
                }
            });
            return retval;
        }
    };

    // Centralize all of the functions on a single object.
    $.extend(functions, chainable, nonchainable);

    // Create a JMesa namespace and store the functions and classes in a way
    // that they can be added to the global namespace if necessary.
    $.jmesa = $.extend({
        TableFacadeManager: TableFacadeManager
    },
    classes, functions);

    // Any function on this object will get copied as a plugin method.
    var jmesaFunctions = {
        jmesaFacade : function () {
            var table = document.jmesaTableFor(this.get(0));
            var facade = TableFacadeManager.getTableFacade(table.attr("id"));
            return facade;
        },
        hasJMesaEvent : function (name) {
            var table = this.get(0);
            var events = TableFacadeManager.getEventsFor(table.id);
            return $.isFunction(events)[id];
        }
    };

    // Adds support for onInvokeAction and onInvokeExportAction
    $.each(['invokeAction', 'invokeExportAction'], function (i, name) {
        jmesaFunctions[name] = function (fnOrAction) {
            this.each(function () {
                var events = TableFacadeManager.getEventsFor(this.id);
                var event = events[name];
                if ($.isFunction(fnOrAction)) {
                    events[name] = fnOrAction;
                } else if ($.isFunction(event)) {
                    event.apply(this, [fnOrAction]);
                }
            });
        };
    });

    // Iterate through the functions and add them as jQuery-ish methods.
    // This assumes that each of the chainable functions takes the table ID
    // as the first argument, and it passes all the caller's arguments
    // as the second, third, etc...
    $.each(chainable, function (name) {
        var func = this; // this == the static function
        jmesaFunctions[name] = function () {
            var args = $.makeArray(arguments);
            return this.each(function () { // this == the jQuery object
                var a = [this.id].concat(args); // this == the table node
                func.apply(null, a);
            });
        };
    });

    // Some functions are non-chainable; they only act on the first match and
    // do not return the jQuery object. Same behavior as $.attr(), etc.
    // We'll add those now.
    $.each(nonchainable, function (name) {
        var func = this; // this == the static function
        jmesaFunctions[name] = function () {
            var args = [this.get(0).id].concat($.makeArray(arguments)); // this == the jQuery object
            return func.apply(null, args);
        };
    });

    $.fn.extend(jmesaFunctions);

    // An alias for the $.jmesa namespace (JMesa static functions and classes)
    document.jmesa = $.jmesa;

})(jQuery);