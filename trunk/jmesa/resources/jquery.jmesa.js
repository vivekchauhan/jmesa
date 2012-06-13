(function ($) {

    var coreapi = {
        getForm : function(id) {
            
            return $("#"+id).parents('form');
        },
        setSaveToWorksheet : function(id) {
            
            $(this.getForm(id)).find('input[name=' + id + '_sw_]').val('true');
        },
        setFilterToWorksheet : function(id) {
            
            $(this.getForm(id)).find('input[name=' + id + '_fw_]').val('true');
            $(this.getForm(id)).find('input[name=' + id + '_p_]').val('1');
        },
        setClearToWorksheet : function(id) {
            
            $(this.getForm(id)).find('input[name=' + id + '_cw_]').val('true');
            this.removeFilterFromWorksheet(id);
        },
        setAddRowToWorksheet : function(id) {
            
            $(this.getForm(id)).find('input[name=' + id + '_awr_]').val('true');
        },
        setRemoveRowToWorksheet : function(id, uniqueProperty) {
            
            $.each(uniqueProperty, function(key, value) {
                $("#"+id).parents('form').find('input[name=' + id + '_rwr_]').val(value);
            });
        },
        removeFilterFromWorksheet : function(id) {
            
            this.removeAllFilters(id);
            $(this.getForm(id)).find('input[name=' + id + '_p_]').val('1');
        },
        setPage : function(id, page) {
            
            $(this.getForm(id)).find('input[name=' + id + '_p_]').val(page);
        },
        setMaxRows : function(id, maxRows) {
            
            $(this.getForm(id)).find('input[name=' + id + '_mr_]').val(maxRows);
            $(this.getForm(id)).find('input[name=' + id + '_p_]').val('1');
        },
        setSort : function(id, property, position, sort) {
            
            $(this.getForm(id)).find('input[name=' + id + '_s_' + position + '_' + property + ']').val(sort);
        },
        removeAllFilters : function(id) {
            
            $(this.getForm(id)).find('input[name^=' + id + '_f_]').each(
                function(i, child) {
                    child.value = '';
                });
            $(this.getForm(id)).find('input[name=' + id + '_p_]').val('1');
        },
        filterKeypress : function(id, e) {
            
            var keynum;

            if (window.event) { // (IE)
                keynum = e.keyCode;
            } else if (e.which) { // (other browsers) 
                keynum = e.which;
            }
            
            if (keynum == 13) { // enter key pressed
                 $(this.getForm(id)).submit();
            }
        },
        setExport : function(id, exportType) {
            
            $(this.getForm(id)).find('input[name=' + id + '_e_]').val(exportType);
        },
        setExportToLimit : function(id, exportType) {
            
            $(this.getForm(id)).find('input[name=' + id + '_e_]').val(exportType);
        },
        submitWorksheetColumn : function(column, id, property, uniqueProperty, uniqueValue, originalValue) {

            var changedValue = $(column).val();
            
            if (originalValue == changedValue) { return; }

            var data = '{ "id" : "' + id + '"';
            data += ', "cp_" : "' + property + '"';
            data += ', "up_' + uniqueProperty + '" : "' + uniqueValue + '"';
            data += ', "ov_" : "' + encodeURIComponent(originalValue) + '"';
            data += ', "cv_" : "' + encodeURIComponent(changedValue) + '"';
            data += '}';

            var contextPath = $(this.getForm(id)).find('input[name=' + id + '_ctx_]').val();
            if (contextPath) {
               contextPath += "/";
            }

            $.post(contextPath + 'jmesa.wrk', jQuery.parseJSON(data), function(columnStatus) {
               //jQuery.jmesa.updateCssClass(columnStatus, cell, errorMessage);
            });
        },
        submitWorksheetCheckableColumn : function(checked, id, property, uniqueProperty, uniqueValue) {

            var changedValue = 'unchecked';
            if (checked) {
                changedValue = 'checked';
            }

            var originalValue = 'unchecked';
            if (!checked) { /* The first time the original value is the opposite. */
                originalValue = 'checked';
            }

            var data = '{ "id" : "' + id + '"';
            data += ', "cp_" : "' + property + '"';
            data += ', "up_' + uniqueProperty + '" : "' + uniqueValue + '"';
            data += ', "ov_" : "' + encodeURIComponent(originalValue) + '"';
            data += ', "cv_" : "' + encodeURIComponent(changedValue) + '"';
            data += '}';

            var contextPath = $(this.getForm(id)).find('input[name=' + id + '_ctx_]').val();
            if (contextPath) {
               contextPath += "/";
            }

            $.post(contextPath + 'jmesa.wrk', jQuery.parseJSON(data), function(columnStatus) {
               //jQuery.jmesa.updateCssClass(columnStatus, cell, errorMessage);
            });
        },        
        createHiddenInputFieldsForLimitAndSubmit : function(id) {

            $(this.getForm(id)).submit();
        },
        submitTableForm : function(id) {

            $(this.getForm(id)).submit();
        },
        createParameterStringForLimit : function(id) {
            
            var url = '';

            /* tip the API off that in the loop of working with the table */
            url += '&' + id + '_tr_=true';
            
            /* export fields */
            
            var exportType = $(this.getForm(id)).find('input[name=' + id + '_e_]').val();
            if (exportType) {
                url += '&' + id + '_e_=' + exportType;
            }

            /* limit fields */
            
            var maxRow = $(this.getForm(id)).find('input[name=' + id + '_mr_]').val();
            if (maxRow) {
                url += '&' + id + '_mr_=' + maxRow;
            }

            var page = $(this.getForm(id)).find('input[name=' + id + '_p_]').val();
            if (page) {
                url += '&' + id + '_p_=' + page;
            }
            
            $(this.getForm(id)).find('input[name^=' + id + '_f_]').each(
                function(i, child) {
                    if (child.value != null && child.value != '') {
                        url += '&' + child.name + '=' + child.value;                        
                    }
                });

            /* worksheet fields */

            var worksheetSave = $(this.getForm(id)).find('input[name=' + id + '_ws_]').val();
            if (worksheetSave == 'true') {
                url += '&' + id + '_sw_=true';
            }

            var worksheetFilter = $(this.getForm(id)).find('input[name=' + id + '_wf_]').val();
            if (worksheetFilter == 'true') {
                url += '&' + id + '_fw_=true';
            }

            var worksheetClear = $(this.getForm(id)).find('input[name=' + id + '_wc_]').val();
            if (worksheetClear == 'true') {
                url += '&' + id + '_cw_=true';
            }

            var worksheetAddRow = $(this.getForm(id)).find('input[name=' + id + '_awr_]').val();
            if (worksheetAddRow == 'true') {
                url += '&' + id + '_awr_=true';
            }

            var worksheetRemoveRow = $(this.getForm(id)).find('input[name=' + id + '_rwr_]').val();
            if (worksheetRemoveRow != null && worksheetRemoveRow != '') {
                url += '&' + id + '_rwr_=' + worksheetRemoveRow;
            }

            return url;            
        }
    };

    $.jmesa = {};
    $.extend($.jmesa, coreapi);

})(jQuery);
