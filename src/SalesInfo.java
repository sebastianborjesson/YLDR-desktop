import java.util.ArrayList;

public class SalesInfo {
    private String shopName;
    private float salesSumm;
    private ArrayList<Product> amountSalesPerSKU = new ArrayList<>();
    private ArrayList<String> countryOfSales = new ArrayList<>();
    private  String valuta;
    private float shippingSum;

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public float getShippingSum() {
        return shippingSum;
    }

    public void setShippingSum(float shippingSum) {
        this.shippingSum = shippingSum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public float getSalesSumm() {
        return salesSumm;
    }

    public void setSalesSumm(float salesSumm) {

        this.salesSumm += salesSumm;
    }

    public void setProductInArray(Product productName) {
        amountSalesPerSKU.add(productName);
    }

    public Product[] getProductarray() {
        Product[] productsArray = new Product[amountSalesPerSKU.size()];
        return amountSalesPerSKU.toArray(productsArray);
    }

    public void setCountryOfSales(String country) {
        countryOfSales.add(country);

    }

    public String[] getCountryOfSales() {
        String[] countryArray = new String[countryOfSales.size()];
        return countryOfSales.toArray(countryArray);
    }

    public int[] countAmountOfSKUsSold(Product[] amountSalesPerSKU) {

        String jamforesleSKU = null;

        int count = 0;

        String[] distinctArray = new String[amountSalesPerSKU.length];



        for (int i = 0; i < amountSalesPerSKU.length; i++) {
            int count2=0;
            for (int j = 0; j < distinctArray.length; j++) {
             if (!amountSalesPerSKU[i].getSKU().equals(distinctArray[j]))
                count2++;

            }

            for ( int k=i; k < distinctArray.length; k++) {
                if (count2 == distinctArray.length) {
                    distinctArray[k] = amountSalesPerSKU[i].getSKU();
                    break;
                }
            }
        }


        for (String s:distinctArray) {
            System.out.println(s);
        }

       int[] amountOfSKUSold = new int[amountSalesPerSKU.length];


        for (int i = 0; i <distinctArray.length ; i++) {
            if (distinctArray[i] != null){
                jamforesleSKU=distinctArray[i];
                for (int j = 0; j <amountSalesPerSKU.length ; j++) {
                    if (jamforesleSKU.equals(amountSalesPerSKU[j].getSKU())){
                        count++;
                    }
                }
                amountOfSKUSold[i] = count;
                count = 0;
            }

        }

      /* for (int i = 0; i < amountSalesPerSKU.length; i++) {
            jamforesleSKU = (amountSalesPerSKU[i]);
            for (int j = 0; j < distinctArray.length; j++) {
                if (jamforesleSKU.equals(distinctArray[j])) {
                    count++;
                }
            }

            amountOfSKUSold[i] = count;
            count = 0;
        }*/

        ArrayList <Integer> temp=new ArrayList<>();

        for (int i:amountOfSKUSold) {
            if (i!=0){
                temp.add(i);
            }
        }

        int [] daRealArray=new int[temp.size()];

        int k=0;
        for (int i:amountOfSKUSold) {
            if (i!=0){
                daRealArray[k]=i;
                k++;
            }
        }
        return daRealArray;
    }


    public int countPerProduct(Product product){
        int count =0;
        for (Product p:getProductarray()) {
            if (product.getSKU().equals(p.getSKU())){
                count++;
            }
        }
        return count;
    }


}
