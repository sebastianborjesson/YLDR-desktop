public class OrderCalculator {
    private float summa;
    private float rabatt;
    private float tax;
    private float shippingCost;

    private Product productInfo;

    public OrderCalculator() {
    }

    public OrderCalculator(float summa, float rabatt, float tax, float shippingCost) {
        this.summa = summa;
        this.rabatt = rabatt;
        this.tax = tax;
        this.shippingCost = shippingCost;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public float getSumma() {
        return summa;
    }

    public void setSumma(float summa) {
        this.summa = summa;
    }

    public float getRabatt() {
        return rabatt;
    }

    public void setRabatt(float rabatt) {
        this.rabatt = rabatt;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }
}
