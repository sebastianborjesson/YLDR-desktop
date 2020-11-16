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
   private String rang;
   private String squadNumber;
   private String custom1;
    private String custom2;
    private String custom3;

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

    public Order(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String tillverkare, boolean isSent, String shop, String productName, String realName, String rang, String squadNumber, String custom1, String custom2, String custom3) {
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
        this.rang = rang;
        this.squadNumber = squadNumber;
        this.custom1 = custom1;
        this.custom2 = custom2;
        this.custom3 = custom3;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getSquadNumber() {
        return squadNumber;
    }

    public void setSquadNumber(String squadNumber) {
        this.squadNumber = squadNumber;
    }

    public String getCustom1() {
        return custom1;
    }

    public void setCustom1(String custom1) {
        this.custom1 = custom1;
    }

    public String getCustom2() {
        return custom2;
    }

    public void setCustom2(String custom2) {
        this.custom2 = custom2;
    }

    public String getCustom3() {
        return custom3;
    }

    public void setCustom3(String custom3) {
        this.custom3 = custom3;
    }

    public Order(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String realName) {
        this.orderNumber = orderNumber;
        this.articleNumber = articleNumber;
        this.size = size;
        this.nickname = nickname;
        this.countryFlag = countryFlag;
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
