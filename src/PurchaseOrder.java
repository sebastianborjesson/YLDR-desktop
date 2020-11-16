import java.sql.Timestamp;
import java.util.Date;

public class PurchaseOrder {
    private String orderNumber;
    private String email;
    private float summa;
    private String valuta;
    private Date date;
    private String shoppen;
    private boolean isSent=false;

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public PurchaseOrder(String orderNumber, String email, float summa, String valuta, Date date, String shop, boolean isSent) {
        this.orderNumber = orderNumber;
        this.email = email;
        this.summa = summa;
        this.valuta = valuta;
        this.date = date;
        this.shoppen = shop;
        this.isSent = isSent;
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
}
