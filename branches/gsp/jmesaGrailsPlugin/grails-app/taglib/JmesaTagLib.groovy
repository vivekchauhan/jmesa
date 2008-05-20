import org.jmesa.facade.tag.TagUtils

import org.jmesa.web.WebContext
import org.jmesa.web.JspPageWebContext
import org.jmesa.facade.TableFacadeImpl
import org.jmesa.facade.TableFacade
import org.jmesa.view.View
import org.jmesa.view.html.component.HtmlTable
import org.jmesa.view.html.renderer.HtmlTableRenderer

/**
 * author:jeff jie
 *
 * limit: one tag per page.
 */
class JmesaTagLib {
    namespace = "jmesa"

    //define some pagescope attibute to store datas.for the limit of Gsp tag.
    def name_componentFactory = "jmesa_componentFacotry"
    def name_tableFacade = "jmesa_tableFacade"
    def name_table = "jmesa_table"
    def name_pageItems = "jmesa_pageItems"
    def name_view = "jmesa_view"
    def name_var = "jmesa_var"

    //tag clouses
    def tableFacade = { attrs,body ->
        if (!body){
            throw new IllegalStateException("You need to wrap the table in the facade tag.")
        }
        createTableFacade(attrs)

        //iterator pageItems
        Collection pi = tableFacade.coreContext.pageItems

        if (pi.size() == 0) {
            body.invoke()
            page[name_pageItems]?.clear()
        } else {
            pi.each{item ->
                page[attrs.var] = item
                body.invoke()
            }
        }

        page.remove(attrs.var)

        tableFacade.table = page[name_table];
        tableFacade.toolbar = TagUtils.getTableFacadeToolbar(attrs.toolbar)
        tableFacade.view = (TagUtils.getTableFacadeView(page[name_view]))
        tableFacade.coreContext.pageItems = page[name_pageItems]

        String html = tableFacade.view.render().toString();
        out << html
    }

    def htmlTable = { attrs,body ->
        if(!body){
           throw new IllegalStateException("You need to wrap the row in the html tag.")
        }
        if(!page[name_table])
            //then create the table
            createTable(attrs)

        body.invoke()
    }

    def htmlRow = { attrs,body ->
        if (!body) {
            throw new IllegalStateException("You need to wrap the columns in the row tag.");
        }

        page[name_pageItems] = []
        def pageItem = new HashMap()
        page[name_pageItems].add(pageItem)

        def var = page[name_var]   //get the var name
        Object bean = page[var]
        pageItem.put(var, bean)
        pageItem.put(ItemUtils.JMESA_ITEM, bean)

        HtmlTable table = page[name_table]
        if (table.row == null)
            table.row = createRow(attrs)

        body.invoke();
    }

    def htmlColumn = { attrs,body ->
        def pageItems = page[name_pageItems]
        if (pageItems.size() == 1) {
            def row = page[name_table].row
            def column = createColumn(attrs);
            //TagUtils.validateColumn(this, attrs.property)
            //TODO : validate the column
            row.addColumn column
        }

        //TODO fix
        //HtmlRowTag rowTag = (HtmlRowTag) findAncestorWithClass(this, HtmlRowTag.class);
        Map<String, Object> pageItem = rowTag.getPageItem();
        pageItem.put(getProperty(), getValue());
    }

    def htmlColumns = {
        //TODO complate it.
    }

    //methods

    def createTableFacade(attrs){
        WebContext webContext = new JspPageWebContext(page)
        TableFacade tableFacade = new TableFacadeImpl(attrs.id,null)

        tableFacade.webContext = attrs.webContext
        tableFacade.editable = attrs.editable?.toBoolean
        tableFacade.items = attrs.items
        tableFacade.maxRows = attrs.maxRows?.toInteger
        tableFacade.stateAttr = attrs.stateAttr
        tableFacade.limit = attrs.limit
        tableFacade.var = attrs.var
        page[name_var] = attrs.var

        tableFacade.maxRowsIncrements = TagUtils.getTableFacadeMaxRowIncrements(attrs.maxRowsIncrements)
        tableFacade.setExportTypes(null, TagUtils.getTableFacadeExportTypes(attrs.exportTypes))

        tableFacade.performFilterAndSort = attrs.performFilterAndSort?.ToBoolean
        tableFacade.preferences = TagUtils.getTableFacadePreferences(attrs.preferences)
        tableFacade.messages = TagUtils.getTableFacadeMessages(attrs.messages)
        tableFacade.columnSort = TagUtils.getTableFacadeColumnSort(attrs.columnSort)
        tableFacade.rowFilter = TagUtils.getTableFacadeRowFilter(attrs.rowFilter)

        tableFacade.addFilterMatcherMap(TagUtils.getTableFacadeFilterMatcherMap(attrs.filterMatcherMap))

        page[name_tableFacade] = tableFacade
    }

    def createTable(attrs){
        def factory = page[name_componentFactory]
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

        page[name_table] = table
    }


    def createRow(attrs){
        def factory = page[name_componentFactory]
        def row = factory.createRow();
        row.uniqueProperty = attrs.uniqueProperty
        row.highlighter = attrs.highlighter?.toBoolean
        row.sortable = attrs.sortable?.toBoolean
        row.filterable = attrs.filterable?.toBoolean
        row.onclick = TagUtils.getRowOnclick(row, attrs.onclick)
        row.onmouseover = TagUtils.getRowOnmouseover(row, attrs.onmouseover())
        row.onmouseout = TagUtils.getRowOnmouseout(row, attrs.onmouseout())

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
        //TODO 
    }
}
