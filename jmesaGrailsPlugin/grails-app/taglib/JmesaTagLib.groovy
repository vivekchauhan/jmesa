import org.jmesa.facade.tag.TagUtils

import org.jmesa.web.WebContext
import org.jmesa.web.JspPageWebContext
import org.jmesa.facade.TableFacadeImpl
import org.jmesa.facade.TableFacade
import org.jmesa.view.View
import org.jmesa.view.html.component.HtmlTable
import org.jmesa.view.html.renderer.HtmlTableRenderer
import org.jmesa.util.ItemUtils
import org.jmesa.view.html.component.HtmlColumn
import org.jmesa.view.html.renderer.HtmlCellRenderer
import org.jmesa.worksheet.editor.WorksheetEditor
import org.jmesa.view.editor.CellEditor
import org.jmesa.view.html.renderer.HtmlFilterRenderer
import org.jmesa.view.editor.FilterEditor
import org.jmesa.view.html.renderer.HtmlHeaderRenderer
import org.jmesa.view.editor.HeaderEditor
import org.jmesa.web.HttpServletRequestWebContext
import org.jmesa.view.html.HtmlComponentFactory

/**
 * author:jeff jie
 *
 * limitation: one tag per page.
 */
class JmesaTagLib {
    static namespace = "jmesa"

    //define some page scope attibute to share data between tags.for the limit of Gsp tag.
    def name_tableFacade = "jmesa_tableFacade"
    def name_var = "jmesa_var"

    //attributes belongs to facade
    def facade_componentFactory = "facade_componentFacotry"
    def facade_table = "facade_table"
    def facade_pageItems = "facade_pageItems"

    //def name_view = "jmesa_view"
    def row_pageItem = "row_pageItem"

    //tag clouses
    def tableFacade = { attrs,body ->
        def tableFacade = createTableFacade(attrs)

        request[facade_pageItems] = []

        Collection pi = tableFacade.coreContext.pageItems

        if (pi.size() == 0) {
            body()
            request[facade_pageItems]?.clear()
        } else {
            pi.each{item ->
                request[attrs.var] = item
                body()
            }
        }

        flash.remove(attrs.var)

        tableFacade.table = request[facade_table];
        tableFacade.toolbar = TagUtils.getTableFacadeToolbar(attrs.toolbar)
        tableFacade.view = (TagUtils.getTableFacadeView(attrs.view))
        tableFacade.coreContext.pageItems = request[facade_pageItems]

        String html = tableFacade.view.render().toString();
        out << html
    }

    def htmlTable = { attrs,body ->
        if(!request[facade_table])
            //then create the table
            createTable(attrs)

        body()
    }

    def htmlRow = { attrs,body ->

        def var = request[name_var]   //get the var name
        Object bean = request[var]
        def pageItem = [(var):bean]
        pageItem[ItemUtils.JMESA_ITEM] = bean
        request[facade_pageItems] << pageItem
        request[row_pageItem] = pageItem


        HtmlTable table = request[facade_table]
        if (table.row == null)
            table.row = createRow(attrs)

        body()
    }

    def htmlColumn = { attrs,body ->
        def pageItems = request[facade_pageItems]
        if (pageItems.size() == 1) {

            def row = request[facade_table].row
            def column = createColumn(attrs)
            validateColumn(attrs.property)
            row.addColumn column
        }

        request[row_pageItem][attrs.property] = getValue(attrs,body)
    }

    def getValue(attrs,body){
        def name = request[name_var]
        def value = request[name]
        // don't konw how to check out if the body is empty
        def ret = body((name):value)
        if (ret == "")
            ret = ItemUtils.getItemValue(request[request[name_var]],attrs.property)
        return ret
    }

    def htmlColumns = {
        //TODO complate it.
    }

    //methods

    def validateColumn(property){
        if (property == null) {
            return true // no coflicts
        }

        def pageItem = request[row_pageItem]
        if (pageItem[property] != null) {
            String msg = "The column property ${property} is not unique. One column value will overwrite another."
            throw new IllegalStateException(msg)
        }

        if (request[name_var].equals(property)) {
            String msg = "The column property ${property} is the same as the TableFacadeTag var attribute ${request[name_var]}."
            throw new IllegalStateException(msg)
        }

        return true
    }

    def createTableFacade(attrs){
        WebContext webContext = new HttpServletRequestWebContext(request)
        TableFacade tableFacade = new TableFacadeImpl(attrs.id,null)

        tableFacade.webContext = webContext
        tableFacade.editable = attrs.editable != null
        tableFacade.items = attrs.items
        tableFacade.maxRows = attrs.maxRows?.toInteger()
        tableFacade.stateAttr = attrs.stateAttr
        tableFacade.limit = attrs.limit
        //tableFacade.var = attrs.var
        request[name_var] = attrs.var

        tableFacade.maxRowsIncrements = TagUtils.getTableFacadeMaxRowIncrements(attrs.maxRowsIncrements)
        tableFacade.setExportTypes(null, TagUtils.getTableFacadeExportTypes(attrs.exportTypes))

        tableFacade.performFilterAndSort = attrs.performFilterAndSort != null
        tableFacade.preferences = TagUtils.getTableFacadePreferences(attrs.preferences)
        tableFacade.messages = TagUtils.getTableFacadeMessages(attrs.messages)
        tableFacade.columnSort = TagUtils.getTableFacadeColumnSort(attrs.columnSort)
        tableFacade.rowFilter = TagUtils.getTableFacadeRowFilter(attrs.rowFilter)

        tableFacade.addFilterMatcherMap(TagUtils.getTableFacadeFilterMatcherMap(attrs.filterMatcherMap))

        def factory = new HtmlComponentFactory(tableFacade.webContext, tableFacade.coreContext)
        request[facade_componentFactory] = factory
        request[name_tableFacade] = tableFacade
    }

    def createTable(attrs){
        def factory = request[facade_componentFactory]
        HtmlTable table = factory.createTable()
        table.caption = attrs.caption
        table.captionKey = attrs.captionKey
        table.theme = attrs.theme

        HtmlTableRenderer tableRenderer = TagUtils.getTableTableRenderer(table, attrs.tableRenderer)
        table.tableRenderer = tableRenderer

        tableRenderer.width = attrs.width
        tableRenderer.style = attrs.style
        tableRenderer.styleClass = attrs.styleClass
        tableRenderer.border = attrs.border
        tableRenderer.cellpadding = attrs.cellpadding
        tableRenderer.cellspacing = attrs.cellspacing

        request[facade_table] = table
    }


    def createRow(attrs){
        def factory = request[facade_componentFactory]
        def row = factory.createRow();
        row.uniqueProperty = attrs.uniqueProperty
        row.highlighter = attrs.highlighter != null
        row.sortable = attrs.sortable != null
        row.filterable = attrs.filterable != null
        row.onclick = TagUtils.getRowOnclick(row, attrs.onclick)
        row.onmouseover = TagUtils.getRowOnmouseover(row, attrs.onmouseover)
        row.onmouseout = TagUtils.getRowOnmouseout(row, attrs.onmouseout)

        def rowRenderer = TagUtils.getRowRowRenderer(row, attrs.rowRenderer)
        row.rowRenderer = rowRenderer

        rowRenderer.style = attrs.style
        rowRenderer.styleClass = attrs.styleClass
        rowRenderer.evenClass = attrs.evenClass
        rowRenderer.oddClass = attrs.oddClass
        rowRenderer.highlightStyle = attrs.highlightStyle
        return row
    }


    def createColumn(attrs){
        def factory = request[facade_componentFactory]
        HtmlColumn column = factory.createColumn(attrs.property)
        column.title = attrs.title
        column.titleKey = attrs.titleKey
        column.sortable = attrs.sortable != null
        column.sortOrder = TagUtils.getColumnSortOrder(attrs.sortOrder);
        column.filterable = attrs.filterable != null
        column.editable = attrs.editable != null
        column.width = attrs.width

        HtmlCellRenderer cr = TagUtils.getColumnCellRenderer(column, attrs.cellRenderer)
        cr.style = attrs.style
        cr.styleClass = attrs.styleClass
        column.cellRenderer = cr

        // worksheet

        WorksheetEditor we = TagUtils.getColumnWorksheetEditor(column, attrs.worksheetEditor)
        cr.worksheetEditor = we

        // cell

        CellEditor ce = TagUtils.getColumnCellEditor(column, attrs.cellEditor, attrs.pattern)
        cr.cellEditor = ce

        // filter

        HtmlFilterRenderer fr = TagUtils.getColumnFilterRenderer(column, attrs.filterRenderer)
        fr.style = attrs.filterStyle
        fr.styleClass = attrs.filterClass
        column.filterRenderer = fr

        FilterEditor fe = TagUtils.getColumnFilterEditor(column, attrs.filterEditor)
        fr.filterEditor = fe

        // header

        HtmlHeaderRenderer hr = TagUtils.getColumnHeaderRenderer(column, attrs.headerRenderer)
        hr.style = attrs.headerStyle
        hr.styleClass = attrs.headerClass
        column.headerRenderer = hr

        HeaderEditor he = TagUtils.getColumnHeaderEditor(column, attrs.headerEditor)
        hr.setHeaderEditor(he);

        return column;
    }
}
