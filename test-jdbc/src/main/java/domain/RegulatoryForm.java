package domain;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: johnson
 * Date: Feb 12, 2011
 * Time: 5:18:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegulatoryForm {
    private int formId; //ReturnId
    private String formName; //Return
    private int formVersion; //Version
    private String formDescription; //Name
    private Date activeDate;
    private Date deActiveDate;
    private boolean isExcludeHolidays;
    private boolean isExcludeWeekends;
    private boolean isSelected;

    private int dueDays; //deadline, constant variable = 15

    private int formOrder; //sort by this column
    private int intervalType; //ReportingPeriodEnum, IntervalType
    private int intervalFrequency; //offset
    private boolean isKeepNullNumeric; //just for computing , KeepNullNumeric
    private Boolean required;   //Required

    private List<String> module;
    private List<String> path;

	private String computeBatchRun; //CompBatchRun
    private String computeOutputTable;  //CompOutputTable
    private String newFormBatchRun; //NewFormBatchRun
    private String newFormOutputTable;  //newFormOutputTable
    private String transmitBatchRun;    //TransmitBatchRun
    private String processDefName;   //processDefName;

    private String intervalSetting;
    

 

	public List<String> getModule() {
		return module;
	}

	public void setModule(List<String> module) {
		this.module = module;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}
    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public int getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(int formVersion) {
        this.formVersion = formVersion;
    }

    public String getFormDescription() {
        return formDescription;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Date getDeActiveDate() {
        return deActiveDate;
    }

    public void setDeActiveDate(Date deActiveDate) {
        this.deActiveDate = deActiveDate;
    }

    public boolean isExcludeHolidays() {
        return isExcludeHolidays;
    }

    public void setExcludeHolidays(boolean excludeHolidays) {
        isExcludeHolidays = excludeHolidays;
    }

    public boolean isExcludeWeekends() {
        return isExcludeWeekends;
    }

    public void setExcludeWeekends(boolean excludeWeekends) {
        isExcludeWeekends = excludeWeekends;
    }

    public int getDueDays() {
        return dueDays;
    }

    public void setDueDays(int dueDays) {
        this.dueDays = dueDays;
    }

    public RegulatoryForm() {

    }

    public RegulatoryForm(int formId, String formName, int formVersion, String formDescription, Date activeDate, Date deActiveDate, boolean excludeHolidays, boolean excludeWeekends) {
        this.formId = formId;
        this.formName = formName;
        this.formVersion = formVersion;
        this.formDescription = formDescription;
        this.activeDate = activeDate;
        this.deActiveDate = deActiveDate;
        isExcludeHolidays = excludeHolidays;
        isExcludeWeekends = excludeWeekends;
        this.dueDays = 15;
    }

    public RegulatoryForm(int formId, String formName, int formVersion, String formDescription, Date activeDate, Date deActiveDate, boolean excludeHolidays, boolean excludeWeekends, int dueDays) {
        this(formId, formName, formVersion, formDescription, activeDate, deActiveDate, excludeHolidays, excludeWeekends);
        this.dueDays = dueDays;
    }

    public int getFormOrder() {
        return formOrder;
    }

    public void setFormOrder(int formOrder) {
        this.formOrder = formOrder;
    }

    public int getIntervalFrequency() {
        return intervalFrequency;
    }

    public void setIntervalFrequency(int intervalFrequency) {
        this.intervalFrequency = intervalFrequency;
    }

    public boolean isKeepNullNumeric() {
        return isKeepNullNumeric;
    }

    public void setKeepNullNumeric(boolean isKeepNullNumeric) {
        this.isKeepNullNumeric = isKeepNullNumeric;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public int getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(int intervalType) {
        this.intervalType = intervalType;
    }

   

    public String getComputeBatchRun() {
        return computeBatchRun;
    }

    public void setComputeBatchRun(String computeBatchRun) {
        this.computeBatchRun = computeBatchRun;
    }

    public String getComputeOutputTable() {
        return computeOutputTable;
    }

    public void setComputeOutputTable(String computeOutputTable) {
        this.computeOutputTable = computeOutputTable;
    }

    public String getNewFormBatchRun() {
        return newFormBatchRun;
    }

    public void setNewFormBatchRun(String newFormBatchRun) {
        this.newFormBatchRun = newFormBatchRun;
    }

    public String getNewFormOutputTable() {
        return newFormOutputTable;
    }

    public void setNewFormOutputTable(String newFormOutputTable) {
        this.newFormOutputTable = newFormOutputTable;
    }

    public String getTransmitBatchRun() {
        return transmitBatchRun;
    }

    public void setTransmitBatchRun(String transmitBatchRun) {
        this.transmitBatchRun = transmitBatchRun;
    }

    public String getProcessDefName() {
        return processDefName;
    }

    public void setProcessDefName(String processDefName) {
        this.processDefName = processDefName;
    }

    public String getIntervalSetting() {
        return intervalSetting;
    }

   
    public boolean getIsSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
