

import java.io.IOException;
import java.sql.SQLException;

public class Main{


    public static void main(String[] args) throws SQLException, IOException {
        YLDR yldr= new YLDR();
        YLDRStore yldrStore= new YLDRStore();
       // yldr.getProductsSoldPerShop("yff8b6mq");

        SalesInfo salesInfo=new SalesInfo();

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

      /*  yldr.getProductsSoldPerShop("amp-esports");


        System.out.println(yldr.getProductsSoldPerShop("amp-esports").getSalesSumm());
        System.out.println(yldr.getProductsSoldPerShop("amp-esports").getSalesSumm());

        for (String si:salesInfo.getSKUarray()) {
            System.out.println(si);
        }
        for (String s:salesInfo.getCountryOfSales()) {
            System.out.println(s);
        }*/

    /*    SalesInfo salesInfo1=yldr.getProductsSoldPerShop("alliancestore","2020-11-02","2020-11-08");

        for (int i:salesInfo1.countAmountOfSKUsSold(salesInfo1.getSKUarray())) {
            System.out.println(i);
        }*/

      /*  for (String s: yldr.getShoppar()) {
            System.out.println(s);
        }*/
        /*
        salesInfo=yldr.getProductsSoldPerShop("alliancestore");
        System.out.println(salesInfo.getShopName());


        for (String s:salesInfo.getSKUarray()) {
            System.out.println(s);
        }

        System.out.println();

        for (int i:salesInfo.countAmountOfSKUsSold(salesInfo.getSKUarray())) {
            if (i!=0) {
                System.out.println(i + " ");
            }
        }*/

     //yldr.calculateNewTotalAmount("5fa3d1a12247b17e250f6598","rb50505_mythi_2020","S","","","");



        /*for (Order o: yldr.getProductsFromOrdercontent("5fa3d1a12247b17e250f6598")
             ) {
            OrderCalculator oc =yldrStore.getPricingInfo(o.getOrderNumber(),o.getArticleNumber(),o.getSize(),o.getNickname(),o.getRealName(),o.getCountryFlag());

            System.out.println(oc.getTax() + " || "+ oc.getRabatt()+" || "+oc.getShippingCost()+" || "+oc.getSumma());
        }


        //yldr.getProductsFromOrdercontent("5fa3d1a12247b17e250f6598");
        //yldr.getProductsFromOrdercontent("5fa27fead53acb32ae4608d4");

        //yldr.removeFromDB("5fa084389461205308220bee","rb22302_allia_2020","M","",null,null);

        //yldrStore.getPricingInfo("5fa3d1a12247b17e250f6598","rb50505_mythi_2020","S","",null,null);
        /*OrderCalculator oc = yldrStore.getPricingInfo("5fa3d1a12247b17e250f6598","rb50505_mythi_2020","S","","","");
        System.out.println(oc.getTax());*/

        //yldr.removeFromDB("5fa3d1fc7de0525ff3600cad", "rb50505_mythi_2020", "S", "", "", "");


      /*  yldrStore.getEverythingFromProduct();
        System.out.println();
        yldrStore.getEverythingFromProduct();*/

        LogistikInfo [] logistikInfo= new LogistikInfo[10];

        //yldrStore.getLogisticInfo("5f99db354d1c1a7c8d5f2f45", "2020-10-28 21:57:25", "2020-10-28 21:57:25");

        yldr.createLogisticExcel("2020-10-28 21:57:25", "2020-10-29 19:29:03");





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