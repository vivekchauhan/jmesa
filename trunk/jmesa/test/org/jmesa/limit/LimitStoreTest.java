package org.jmesa.limit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class LimitStoreTest {
	private static final String ID = "pres";
	private static final String PREFIX_ID = "pres_";
	private static final int MAX_ROWS = 20;
	private static final int TOTAL_ROWS = 60;
	private static final int PAGE = 3;

	@Test
	public void createLimitAndRowSelect() {
		LimitFactory limitFactory = new LimitFactoryImpl(ID, getParameters());

		assertNotNull(limitFactory);

		LimitStore limitStore = new LimitStoreImpl(limitFactory);
		Limit limit = limitStore.createLimit();
		
		assertNotNull(limit);
		assertTrue(limit.getFilterSet().getFilters().size() > 0);
		assertTrue(limit.getSortSet().getSorts().size() > 0);
		
		RowSelect rowSelect = limitStore.createRowSelect(MAX_ROWS, TOTAL_ROWS);
		limit.setRowSelect(rowSelect);

		assertNotNull(rowSelect);
		assertTrue(rowSelect.getPage() > 0);
		assertTrue(rowSelect.getRowStart() > 0);
		assertTrue(rowSelect.getRowEnd() > 0);
		assertTrue(rowSelect.getMaxRows() > 0);
		assertTrue(rowSelect.getTotalRows() > 0);
	}
	
	private Map<String, ?> getParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();

		String maxRows = PREFIX_ID + Action.MAX_ROWS;
		parameters.put(maxRows, MAX_ROWS);

		String page = PREFIX_ID + Action.PAGE;
		parameters.put(page, new Integer[] { PAGE });

		String filter = PREFIX_ID + Action.FILTER + "name";
		List<String> filterList = new ArrayList<String>();
		filterList.add(filter);
		parameters.put(filter, filterList);

		String filter2 = PREFIX_ID + Action.FILTER + "nickName";
		List<String> filterList2 = new ArrayList<String>();
		filterList.add(filter);
		parameters.put(filter2, filterList2);

		String sort = PREFIX_ID + Action.SORT + "name";
		parameters.put(sort, new String[]{Order.ASC.getCode()});

		String sort2 = PREFIX_ID + Action.SORT + "nickName";
		parameters.put(sort2, new String[]{Order.DESC.getCode()});

		return parameters;
	}	
	
}
