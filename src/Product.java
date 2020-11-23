public class Product {
    private String SKU;
    private String name;
    private String manufacturer;
    private float price;
    private  String countryOfPurchase;
    private String size;

    public Product() {
    }

    public Product(String SKU, String name, String manufacturer, float price, String countryOfPurchase) {
        this.SKU = SKU;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.countryOfPurchase = countryOfPurchase;
    }

    public Product(String SKU, String name, float price, String countryOfPurchase) {
        this.SKU = SKU;
        this.name = name;
        this.price = price;
        this.countryOfPurchase = countryOfPurchase;
    }

    public Product(String SKU, String name, float price) {
        this.SKU = SKU;
        this.name = name;
        this.price = price;
    }

    public Product(String SKU, String name, String manufacturer) {
        this.SKU = SKU;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Product(String SKU, String name, float price, String countryOfPurchase, String size) {
        this.SKU = SKU;
        this.name = name;
        this.price = price;
        this.countryOfPurchase = countryOfPurchase;
        this.size = size;
    }

    public String getCountryOfPurchase() {
        return countryOfPurchase;
    }

    public void setCountryOfPurchase(String countryOfPurchase) {
        this.countryOfPurchase = countryOfPurchase;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
