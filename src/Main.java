public class Main{


    public static void main(String[] args)  {
        YLDR yldr= new YLDR();
        YLDRStore yldrStore= new YLDRStore();
       // yldr.getProductsSoldPerShop("yff8b6mq");

        SalesInfo salesInfo;

        /*salesInfo.setAmountOfSalesPerSKU("Jersey");
        salesInfo.setAmountOfSalesPerSKU("Jersey Custom");

        salesInfo.setCountryOfSales("Sweden");
        salesInfo.setCountryOfSales("Swedestan");

        for (String s:salesInfo.getAmountSalesPerSKU()) {
            System.out.println(s);
        }

        for (String s:salesInfo.getCountryOfSales()) {
            System.out.println(s);
        }

        salesInfo.setSalesSumm(10);

        salesInfo.setSalesSumm(20);

        salesInfo.setSalesSumm(30);

        System.out.println(salesInfo.getSalesSumm());*/



       // String [] array={"SKU2","SKU3"};

      /*  salesInfo.setSKUInArray("SKU1");
        salesInfo.setSKUInArray("SKU1");
        salesInfo.setSKUInArray("SKU3");
        yldr.getProductsSoldPerShop("yff8b6mq");


        for (int i:salesInfo.countAmountOfSKUsSold(salesInfo.getAmountSalesPerSKU())) {
            System.out.println(i + " ");
        }
*/



        salesInfo=yldr.getProductsSoldPerShop("yff8b6mq");
        System.out.println(salesInfo.getShopName());

        for (String s:salesInfo.getSKUarray()) {
            System.out.println(s);
        }

        System.out.println();

        for (int i:salesInfo.countAmountOfSKUsSold(salesInfo.getSKUarray())) {
            if (i!=0) {
                System.out.println(i + " ");
            }
        }














      /*  for (SalesInfo si:yldrStore.getSalesInfoFromDatabase("yff8b6mq")) {
           for (String s:si.getAmountSalesPerSKU()) {
                System.out.println(s + " ");
            }
            System.out.println();
            System.out.println(si.getSalesSumm() + " ");
            System.out.println();
            for (String s:si.getCountryOfSales()) {
                System.out.println(s + " ");
            }
        }*/

    //FXmain.main(args);

}
}