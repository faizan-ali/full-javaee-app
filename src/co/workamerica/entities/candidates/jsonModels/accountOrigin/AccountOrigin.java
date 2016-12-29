package co.workamerica.entities.candidates.jsonModels.accountOrigin;

/**
 * Created by Faizan on 6/28/2016.
 */
public class AccountOrigin {

    private String dateAcquired;
    private String timeAcquired;
    private String workAmericaCreated;
    // Linked to Institutions table. 0 for Admin console
    private String sourceID;
    private String source;

    // Only non-empty if WorkAmericaCreated is No
    private String marketingCampaign;
    private String searchKeywords;
    private String device;
    private String deviceType;
    private String deviceVendor;
    private String os;
    private String osVersion;
    private String browser;
    private String browserVersion;


    // Only non-empty if WorkAmericaCreated is Yes
    private String adminConsoleMethod;
    private String teamMember;
    // E.g. schoolVisit, fax
    // Empty if account was individually created through Admin console
    private String sourceType;

    public AccountOrigin () {

    }

    public String getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(String dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public String getWorkAmericaCreated() {
        return workAmericaCreated;
    }

    public void setWorkAmericaCreated(String workAmericaCreated) {
        this.workAmericaCreated = workAmericaCreated;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getMarketingCampaign() {
        return marketingCampaign == null ? "" : marketingCampaign ;
    }

    public void setMarketingCampaign(String marketingCampaign) {
        this.marketingCampaign = marketingCampaign;
    }

    public String getSearchKeywords() {
        return searchKeywords == null ? "" : searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords) {
        this.searchKeywords = searchKeywords;
    }

    public String getDevice() {
        return device == null ? "" : device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBrowser() {
        return browser == null ? "" : browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getAdminConsoleMethod() {
        return adminConsoleMethod == null ? "" : adminConsoleMethod;
    }

    public void setAdminConsoleMethod(String adminConsoleMethod) {
        this.adminConsoleMethod = adminConsoleMethod;
    }

    public String getTeamMember() {
        return teamMember == null ? "" : teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember;
    }

    public String getSourceType() {
        return sourceType == null ? "" : sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTimeAcquired() {
        return timeAcquired;
    }

    public void setTimeAcquired(String timeAcquired) {
        this.timeAcquired = timeAcquired;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceVendor() {
        return deviceVendor;
    }

    public void setDeviceVendor(String deviceVendor) {
        this.deviceVendor = deviceVendor;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }
}
