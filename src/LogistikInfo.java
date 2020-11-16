public class LogistikInfo {
    private String ordernumber;
    private String articlenumber;
    private String articleName;
    private String customerOrdernumber;
    private String wayOfDelivery;

    private String customerName;
    private String customerAdress;
    private String customerPostCode;
    private String customerCity;
    private String customerPhoneNumber;
    private String customerEmail;
    private String country;

    private boolean notifyBySMS;
    private boolean notifyByTelefone;
    private boolean notifyByEmail;

    public LogistikInfo() {
    }

    public LogistikInfo(String ordernumber, String articlenumber, String articleName, String customerOrdernumber, String wayOfDelivery, String customerName, String customerAdress, String customerPostCode, String customerCity, String customerPhoneNumber, String customerEmail, String country, boolean notifyBySMS, boolean notifyByTelefone, boolean notifyByEmail) {
        this.ordernumber = ordernumber;
        this.articlenumber = articlenumber;
        this.articleName = articleName;
        this.customerOrdernumber = customerOrdernumber;
        this.wayOfDelivery = wayOfDelivery;
        this.customerName = customerName;
        this.customerAdress = customerAdress;
        this.customerPostCode = customerPostCode;
        this.customerCity = customerCity;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerEmail = customerEmail;
        this.country = country;
        this.notifyBySMS = notifyBySMS;
        this.notifyByTelefone = notifyByTelefone;
        this.notifyByEmail = notifyByEmail;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getArticlenumber() {
        return articlenumber;
    }

    public void setArticlenumber(String articlenumber) {
        this.articlenumber = articlenumber;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getCustomerOrdernumber() {
        return customerOrdernumber;
    }

    public void setCustomerOrdernumber(String customerOrdernumber) {
        this.customerOrdernumber = customerOrdernumber;
    }

    public String getWayOfDelivery() {
        return wayOfDelivery;
    }

    public void setWayOfDelivery(String wayOfDelivery) {
        this.wayOfDelivery = wayOfDelivery;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAdress() {
        return customerAdress;
    }

    public void setCustomerAdress(String customerAdress) {
        this.customerAdress = customerAdress;
    }

    public String getCustomerPostCode() {
        return customerPostCode;
    }

    public void setCustomerPostCode(String customerPostCode) {
        this.customerPostCode = customerPostCode;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isNotifyBySMS() {
        return notifyBySMS;
    }

    public void setNotifyBySMS(boolean notifyBySMS) {
        this.notifyBySMS = notifyBySMS;
    }

    public boolean isNotifyByTelefone() {
        return notifyByTelefone;
    }

    public void setNotifyByTelefone(boolean notifyByTelefone) {
        this.notifyByTelefone = notifyByTelefone;
    }

    public boolean isNotifyByEmail() {
        return notifyByEmail;
    }

    public void setNotifyByEmail(boolean notifyByEmail) {
        this.notifyByEmail = notifyByEmail;
    }
}
