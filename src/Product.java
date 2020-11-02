public class Product {
    String SKU;
    String name;
    String manufacturer;

    public Product() {
    }

    public Product(String SKU, String name, String manufacturer) {
        this.SKU = SKU;
        this.name = name;
        this.manufacturer = manufacturer;
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
}
