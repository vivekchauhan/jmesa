import org.jmesa.facade.tag.TagUtils
import org.jmesa.util.SupportUtils

import org.jmesa.web.WebContext
import org.jmesa.facade.TableFacadeImpl
import org.jmesa.facade.TableFacade
import org.jmesa.view.html.component.HtmlTable
import org.jmesa.util.ItemUtils
import org.jmesa.view.html.component.HtmlColumn
import org.jmesa.view.html.renderer.HtmlCellRenderer
import org.jmesa.view.html.renderer.HtmlFilterRenderer
import org.jmesa.view.html.renderer.HtmlHeaderRenderer
import org.jmesa.web.HttpServletRequestWebContext
import org.jmesa.view.html.HtmlComponentFactory

/**
 * author:jeff jie
 *
 */
class JmesaTagLib {
    static namespace = "jmesa"

    //define some page scope attibute to share data between tags.for the limitation of Gsp tag.
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
        tableFacade.toolbar = createInstance(attrs.toolbar)
        tableFacade.view = createInstance(attrs.view)
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
        if(value){
            // don't konw how to check out if the body is empty
            def ret = body((name):value)
            if (ret == "")
                ret = ItemUtils.getItemValue(request[request[name_var]],attrs.property)
            return ret
        }
        return null
    }

    def htmlColumns = { attrs,body ->
        def pageItems = request[facade_pageItems]
        def row = request[facade_table].row
        if (pageItems.size() == 1) {
            def htmlColumnsGenerator = createInstance(attrs.htmlColumnsGenerator)
            SupportUtils.setWebContext (htmlColumnsGenerator,request[name_tableFacade].webContext)
            SupportUtils.setCoreContext (htmlColumnsGenerator,request[name_tableFacade].coreContext)
            def columns = htmlColumnsGenerator.getColumns(request[facade_componentFactory])
            columns.each{
                it.generatedOnTheFly = true
                validateColumn(it.property)
                row.addColumn it
            }
        }

        def pageItem = request[row_pageItem]
        if(pageItem.bean){
            def columns = row.getColumns()
            columns.each{
                if (it.isGeneratedOnTheFly()){
                    if(it.property){
                        pageItem[it.property] = ItemUtils.getItemValue(request[request[name_var]],it.property)
                    }
                }
            }
        }
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
        tableFacade.editable = attrs.editable == null ? false : attrs.editable.toBoolean()
        tableFacade.items = attrs.items
        tableFacade.maxRows = attrs.maxRows?.toInteger()
        tableFacade.stateAttr = attrs.stateAttr
        tableFacade.limit = attrs.limit
        //tableFacade.var = attrs.var
        request[name_var] = attrs.var

        tableFacade.maxRowsIncrements = TagUtils.getTableFacadeMaxRowIncrements(attrs.maxRowsIncrements)
        tableFacade.setExportTypes(null, TagUtils.getTableFacadeExportTypes(attrs.exportTypes))

        tableFacade.autoFilterAndSort(attrs.autoFilterAndSort == null ? false : attrs.autoFilterAndSort.toBoolean())
        tableFacade.preferences = createInstance(attrs.preferences)
        tableFacade.messages = createInstance(attrs.messages)
        tableFacade.columnSort = createInstance(attrs.columnSort)
        tableFacade.rowFilter = createInstance(attrs.rowFilter)

        tableFacade.addFilterMatcherMap(createInstance(attrs.filterMatcherMap))

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

        if(attrs.tableRenderer)
            table.tableRenderer = createInstance(attrs.tableRenderer)
        def tableRenderer = table.tableRenderer

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
        row.highlighter = attrs.highlighter == null ? false : attrs.highlighter.toBoolean()


        if(attrs.onclick)
            row.onclick = createInstance(attrs.onclick)
        if(attrs.onmouseover)
            row.onmouseover = createInstance(attrs.onmouseover)
        if(attrs.onmouseout)
            row.onmouseout = createInstance(attrs.onmouseout)
        if(attrs.rowRenderer)
            row.rowRenderer = createInstance(attrs.rowRenderer)
        def rowRenderer = row.rowRenderer

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
        //column.sortable = attrs.sortable != null
        column.sortable = attrs.sortable == null ? false : attrs.sortable.toBoolean()

        column.sortOrder = TagUtils.getColumnSortOrder(attrs.sortOrder);
        column.filterable = attrs.filterable == null ? false : attrs.filterable.toBoolean()
        column.editable = attrs.editable == null ? false : attrs.editable.toBoolean()
        column.width = attrs.width

        if(attrs.cellRenderer){
            HtmlCellRenderer renderer =  createInstance(attrs.cellRenderer)
            renderer.cellEditor = column.cellRenderer.cellEditor
            column.cellRenderer = renderer

        }
        HtmlCellRenderer cr = column.cellRenderer
        cr.style = attrs.style
        cr.styleClass = attrs.styleClass

        // worksheet

        if(attrs.worksheetEditor){
            cr.worksheetEditor = createInstance(cr.worksheetEditor)
        }

        // cell

        if(attrs.cellEditor){
            cr.cellEditor = createInstance(attrs.cellEditor)
            SupportUtils.setPattern(cr.cellEditor, attrs.pattern)
        }

        // filter

        if(attrs.filterRenderer){
            HtmlFilterRenderer renderer = createInstance(attrs.filterRenderer)
            renderer.filterEditor = column.filterRenderer.filterEditor
            column.filterRenderer = renderer
        }
        HtmlFilterRenderer fr = column.filterRenderer
        fr.style = attrs.filterStyle
        fr.styleClass = attrs.filterClass

        if(attrs.filterEditor){
            fr.filterEditor = createInstance(attrs.filterEditor)
        }

        // header

        if(attrs.headerRenderer){
            HtmlHeaderRenderer renderer = createInstance(attrs.headerRenderer)
            renderer.headerEditor = column.headerRenderer.headerEditor
            column.headerRenderer = renderer
        }
        HtmlHeaderRenderer hr = column.headerRenderer
        hr.style = attrs.headerStyle
        hr.styleClass = attrs.headerClass

        if(attrs.headerEditor){
            hr.headerEditor = createInstance(attrs.headerEditor)
        }

        return column;
    }

    def createInstance(cls){
        if(cls){
            def loader = this.class.classLoader
            return Class.forName(cls,true,loader).newInstance()
        }
        return null
    }
}
