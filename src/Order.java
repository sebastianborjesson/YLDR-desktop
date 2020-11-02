import java.sql.Date;
import java.util.ArrayList;

public class Order {
   private String orderNumber;
   private String articleNumber;
   private String size;
   private String nickname;
   private String countryFlag;
   private String tillverkare;
   private boolean isSent;
   private String shop;
   private String productName;
   private String realName;

    public Order() {
    }

    public Order(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String tillverkare, boolean isSent, String shop, String productName, String realName) {
        this.orderNumber = orderNumber;
        this.articleNumber = articleNumber;
        this.size = size;
        this.nickname = nickname;
        this.countryFlag = countryFlag;
        this.tillverkare = tillverkare;
        this.isSent = isSent;
        this.shop = shop;
        this.productName = productName;
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public Order(boolean isSent) {
        this.isSent = isSent;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public boolean getisSent() {
        return isSent;
    }

    public void setIsSent(boolean isSent) {
        this.isSent=isSent;
    }

    public String getTillverkare() {
        return tillverkare;
    }

    public void setTillverkare(String tillverkare) {
        this.tillverkare = tillverkare;
    }
}
