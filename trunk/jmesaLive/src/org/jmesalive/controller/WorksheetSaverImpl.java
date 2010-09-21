package org.jmesalive.controller;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.jmesa.model.WorksheetSaver;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowStatus;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesalive.dao.PresidentDao;
import org.jmesalive.domain.President;

public class WorksheetSaverImpl implements WorksheetSaver {
    
	private PresidentDao presidentDao;
	private StringBuffer saveResults = new StringBuffer();
	
	public WorksheetSaverImpl(PresidentDao presidentDao) {
    	this.presidentDao = presidentDao;
	}
	
	public String getSaveResults() {
	    return saveResults.toString();
	}
	
	public void saveWorksheet(Worksheet worksheet) {
		
		Iterator<WorksheetRow> worksheetRows = worksheet.getRows().iterator();
		Map<String, President> presidentsAsMap = presidentDao.getPresidentsAsMap();
		
		while (worksheetRows.hasNext()) {
			boolean valid = true;
			WorksheetRow worksheetRow = worksheetRows.next();
			
			String uniqueValue = worksheetRow.getUniqueProperty().getValue();
			String message = null;

			if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.ADD)) {
				President newPresident = PresidentDao.getNewPresident(); 
				
				for (WorksheetColumn worksheetColumn : worksheetRow.getColumns()) {
					valid = setProperty(worksheetColumn, newPresident) & valid;
				}
				
				if (valid) {
					presidentDao.savePresident(newPresident, true);
					message = getSuccessHtml("Saved new record: " + newPresident.getName().getFullName());
				} else {
					message = getErrorHtml("Error: Not saving new record (uniqueValue: " + uniqueValue + ")");
				}

			} else if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.MODIFY)) {
				President president = presidentsAsMap.get(uniqueValue);

				for (WorksheetColumn worksheetColumn : worksheetRow.getColumns()) {
					valid = setProperty(worksheetColumn, president) & valid;
				}

				if (valid) {
					presidentDao.savePresident(president, false);
					message = getSuccessHtml("Saved record: " + president.getName().getFullName());
				} else {
					message = getErrorHtml("Error: Not saving record (uniqueValue: " + uniqueValue + ")");
				}

			} else if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.REMOVE)) {
				valid = true;
				President president = presidentsAsMap.get(uniqueValue);
				presidentDao.deletePresident(president);
				message = getSuccessHtml("Deleted record (uniqueValue: " + uniqueValue + ")");
			}
			
			if (valid) {
				worksheetRows.remove();
			}

			saveResults.append(message);
		}
	}

	private boolean setProperty (WorksheetColumn worksheetColumn, President president) {
		String property = worksheetColumn.getProperty();
		String changedValue = worksheetColumn.getChangedValue();

		// return success for dummy column "remove", which is used to show delete icon
		if (property.equals("remove")) {
			return true;
		}
		
		//System.out.println("Property: " + property + ", changedValue: " + changedValue);
		
		try {
			if (worksheetColumn.getProperty().equals("selected")) {
				if (changedValue.equals(CheckboxWorksheetEditor.CHECKED)) {
					PropertyUtils.setProperty(president, property, "y");
				} else {
					PropertyUtils.setProperty(president, property, "n");
				}
			} else if (worksheetColumn.getProperty().equals("career")) {
				// validate in API
				if ("foo".equals(changedValue)) {
					worksheetColumn.setError("Enter valid Career");
					return false;
				}
				
				// has to remove if validating in API
				worksheetColumn.removeError();
				PropertyUtils.setProperty(president, property, changedValue);
			} else {
				// set by javascript validation framework
				if (worksheetColumn.hasError()) {
					return false;
				}
				PropertyUtils.setProperty(president, property, changedValue);
			}
		} catch (Exception ex) {
            ex.printStackTrace();
			worksheetColumn.setError("Some error occured");
			return false;
		}
		
		return true;
	}

	private String getSuccessHtml(String message) {
		return "<span style=\"color:green\">" + message + "</span><br/>";
	}

	private String getErrorHtml(String message) {
		return "<span style=\"color:red\">" + message + "</span><br/>";
	}
}
