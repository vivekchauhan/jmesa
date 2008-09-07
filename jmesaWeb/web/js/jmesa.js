
/* convenience methods so do not have to manually work with the api */

function addTableFacadeToManager(id) {
    $.jmesa.addTableFacade(id);
}

function setSaveToWorksheet(id) {
    $.jmesa.setSaveToWorksheet(id);
}

function setFilterToWorksheet(id) {
    $.jmesa.setFilterToWorksheet(id);
}

function removeFilterFromWorksheet(id) {
    $.jmesa.removeFilterFromWorksheet(id);
}

function setPageToLimit(id, page) {
    $.jmesa.setPageToLimit(id, page);
}

function setMaxRowsToLimit(id, maxRows) {
    $.jmesa.setMaxRowsToLimit(id, maxRows);
}

function addSortToLimit(id, position, property, order) {
    $.jmesa.addSortToLimit(id, position, property, order);
}

function removeSortFromLimit(id, property) {
    $.jmesa.removeSortFromLimit(id, property);
}

function removeAllSortsFromLimit(id) {
    $.jmesa.removeAllSortsFromLimit(id);
}

function getSortFromLimit(id, property) {
    $.jmesa.getSortFromLimit(id, property);
}

function addFilterToLimit(id, property) {
    $.jmesa.addFilterToLimit(id, property);
}

function removeFilterFromLimit(id, property) {
    $.jmesa.removeFilterFromLimit(id, property);
}

function removeAllFiltersFromLimit(id) {
    $.jmesa.removeAllFiltersFromLimit(id);
}

function getFilterFromLimit(id, property) {
    $.jmesa.getFilterFromLimit(id, property);
}

function setExportToLimit(id, exportType) {
    $.jmesa.setExportToLimit(id, exportType);
}

function createHiddenInputFieldsForLimit(id) {
    $.jmesa.createHiddenInputFieldsForLimit(id);
}

function createHiddenInputFieldsForLimitAndSubmit(id) {
    $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
}

function createParameterStringForLimit(id) {
    return $.jmesa.createParameterStringForLimit(id);
}

/* Special Effects */

function createDynFilter(filter, id, property) {
    $.jmesa.createDynFilter(filter, id, property);
}

function createDynDroplistFilter(filter, id, property, options) {
    $.jmesa.createDynDroplistFilter(filter, id, property, options);
}

function addDropShadow(imagesPath, theme) {
    $.jmesa.addDropShadow(imagesPath, theme);
}

/* Worksheet */

function createWsColumn(column, id, uniqueProperties, property) {
    $.jmesa.createWsColumn(column, id, uniqueProperties, property);
}

function submitWsCheckboxColumn(column, id, uniqueProperties, property) {
    $.jmesa.submitWsCheckboxColumn(column, id, uniqueProperties, property);
}

function submitWsColumn(originalValue, changedValue) {
    $.jmesa.submitWsColumn(originalValue, changedValue);   
}
