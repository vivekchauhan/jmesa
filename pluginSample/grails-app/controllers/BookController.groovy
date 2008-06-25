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
            books = prepareData()
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

    def prepareData(){
        def books = []
        50.times{ count ->
            def book = new Book(title:"learning Java part${count + 1}",author:"james",releaseTime:new Date())
            book.save()
            books << book
        }
        return books
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
        int count = Book.count()
        if(count == 0){
            def datas = prepareData()
            count = datas.size()
        }
        def books = Book.list(offset:((page - 1) * maxRows), max:maxRows)

        Limit limit = tableFacade.limit

        if(limit.isExported()){
            tableFacade.setExportTypes response,limit.getExportType()
            tableFacade.setColumnProperties "title","author"
            tableFacade.setItems Book.list()
            limit.setRowSelect new RowSelectImpl(1,count,count)
            tableFacade.render()
        }else{
            limit.setRowSelect new RowSelectImpl(page,maxRows,count)
            return [bookList:books,limit:limit]
        }

    }

    def list3 = {
        def loader = this.class.classLoader
        
        println Class.forName("BookColumnGenerator",true,loader).newInstance()
        def books = Book.list(params)
        if(!books || books.size() == 0){
            books = prepareData()
        }
        [bookList:books]
    }

}