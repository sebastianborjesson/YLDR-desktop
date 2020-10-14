import java.sql.Date;
import java.util.ArrayList;

public class Order {
   String orderNumber;
   String articleNumber;
   String size;
   String nickname;
   String countryFlag;
   String tillverkare;
   boolean isSent;

    public Order() {
    }

    public Order(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String tillverkare) {
        this.orderNumber = orderNumber;
        this.articleNumber = articleNumber;
        this.size = size;
        this.nickname = nickname;
        this.countryFlag = countryFlag;
        this.tillverkare=tillverkare;
    }
    public Order(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, boolean isSent, String tillverkare) {
        this.orderNumber = orderNumber;
        this.articleNumber = articleNumber;
        this.size = size;
        this.nickname = nickname;
        this.countryFlag = countryFlag;
        this.isSent=isSent;
        this.tillverkare=tillverkare;

    }

    public Order(boolean isSent) {
        this.isSent = isSent;
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
