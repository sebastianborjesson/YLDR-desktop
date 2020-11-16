import java.util.Date;

public class PurchaseOrder {
    private String orderNumber;
    private String email;
    private float summa;
    private String valuta;
    private Date date;
    private String shoppen;
    private boolean isEmailSent =false;
    private float discountPercentage;
    private float tax;
    private float shippingSum;

    public boolean getIsEmailSent() {
        return isEmailSent;
    }

    public void setEmailSent(boolean emailSent) {
        isEmailSent = emailSent;
    }

    public PurchaseOrder(String orderNumber, String email, float summa, String valuta, Date date, String shop, boolean isEmailSent) {
        this.orderNumber = orderNumber;
        this.email = email;
        this.summa = summa;
        this.valuta = valuta;
        this.date = date;
        this.shoppen = shop;
        this.isEmailSent = isEmailSent;
    }

    public PurchaseOrder(String orderNumber, String email, float summa, String valuta, Date date, String shoppen, boolean isEmailSent, float discoutPercentage, float tax, float shippingSum) {
        this.orderNumber = orderNumber;
        this.email = email;
        this.summa = summa;
        this.valuta = valuta;
        this.date = date;
        this.shoppen = shoppen;
        this.isEmailSent = isEmailSent;
        this.discountPercentage = discoutPercentage;
        this.tax = tax;
        this.shippingSum = shippingSum;
    }

    public PurchaseOrder(float summa, String valuta, String shoppen) {
        this.summa = summa;
        this.valuta = valuta;
        this.shoppen = shoppen;
    }

    public PurchaseOrder() {
    }

    public PurchaseOrder(Date date) {
        this.date = date;
    }

    public PurchaseOrder(String orderNumber, String email, float summa, String valuta, Date date, String shop) {
        this.orderNumber = orderNumber;
        this.email = email;
        this.summa = summa;
        this.valuta = valuta;
        this.date = date;
        this.shoppen = shop;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getSumma() {
        return summa;
    }

    public void setSumma(int summa) {
        this.summa = summa;
    }

    public String getShop() {
        return shoppen;
    }

    public void setShop(String shop) {
        this.shoppen = shop;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public void setSumma(float summa) {
        this.summa = summa;
    }

    public String getShoppen() {
        return shoppen;
    }

    public void setShoppen(String shoppen) {
        this.shoppen = shoppen;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discoutPercentage) {
        this.discountPercentage = discoutPercentage;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getShippingSum() {
        return shippingSum;
    }

    public void setShippingSum(float shippingSum) {
        this.shippingSum = shippingSum;
    }

}
