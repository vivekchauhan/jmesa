import org.jmesa.facade.TableFacadeImpl
import org.jmesa.facade.TableFacade
import org.jmesa.limit.Limit
import org.jmesa.limit.RowSelectImpl
import org.jmesa.limit.LimitActionFactoryImpl
import org.jmesa.limit.LimitActionFactory


class BookController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    /** paginated by jmesa*/
    def list = {
        TableFacade tableFacade = new TableFacadeImpl("tag",request)
        def books = Book.list(params)
        if(!books || books.size() == 0){
            books = []
            50.times{ count ->
                def book = new Book(title:"learning Java part${count + 1}",author:"james",releaseTime:new Date())
                book.save()
                books << book
            }
        }
        tableFacade.items = books
        
        Limit limit = tableFacade.limit
        
        if(limit.isExported()){
            tableFacade.setExportTypes response,limit.getExportType()
            tableFacade.setColumnProperties "title","author"
            tableFacade.render()
        }else
            return [bookList : books]
    }

    /**
     * paginate manually
     */
    def list2 = {
        def id = "tag"
        TableFacade tableFacade = new TableFacadeImpl(id,request)
        LimitActionFactory laf = new LimitActionFactoryImpl(id,params)
        def maxRows = laf.maxRows == null ? 15:laf.maxRows
        def page = laf.page

        def books = Book.list(offset:((page - 1) * maxRows), max:maxRows)

        Limit limit = tableFacade.limit

        if(limit.isExported()){
            tableFacade.setExportTypes response,limit.getExportType()
            tableFacade.setColumnProperties "title","author"
            tableFacade.setItems Book.list()
            def count = Book.count()
            limit.setRowSelect new RowSelectImpl(1,count,count)
            tableFacade.render()
        }else{
            limit.setRowSelect new RowSelectImpl(page,maxRows,Book.count())
            return [bookList:books,limit:limit]
        }

    }

}