import org.jmesa.view.html.component.HtmlColumnsGenerator;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.HtmlComponentFactory;

import java.util.List;
import java.util.ArrayList;

public class BookColumnGenerator implements HtmlColumnsGenerator {
    public List<HtmlColumn> getColumns(HtmlComponentFactory htmlComponentFactory) {
        List columns = new ArrayList();
        HtmlColumn title = htmlComponentFactory.createColumn("title");
        columns.add(title);
        HtmlColumn author = htmlComponentFactory.createColumn("author");
        columns.add(author);

        return columns;
    }
}
