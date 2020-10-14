import java.sql.Timestamp;
import java.util.Date;

public class PurchaseOrder {
    private String orderNumber;
    private String email;
    private int summa;
    private String valuta;
    private Date date;
    private String shop;
    private boolean isSent=false;

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public PurchaseOrder(String orderNumber, String email, int summa, String valuta, Date date, String shop, boolean isSent) {
        this.orderNumber = orderNumber;
        this.email = email;
        this.summa = summa;
        this.valuta = valuta;
        this.date = date;
        this.shop = shop;
        this.isSent = isSent;
    }

    public PurchaseOrder() {
    }

    public PurchaseOrder(Date date) {
        this.date = date;
    }

    public PurchaseOrder(String orderNumber, String email, int summa, String valuta, Date date, String shop) {
        this.orderNumber = orderNumber;
        this.email = email;
        this.summa = summa;
        this.valuta = valuta;
        this.date = date;
        this.shop = shop;
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

    public int getSumma() {
        return summa;
    }

    public void setSumma(int summa) {
        this.summa = summa;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }
}
