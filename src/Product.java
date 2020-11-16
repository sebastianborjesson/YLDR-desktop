public class Product {
    private String SKU;
    private String name;
    private String manufacturer;
    private float price;

    public Product() {
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
