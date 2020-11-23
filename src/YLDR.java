import javafx.scene.paint.Color;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class YLDR {


    YLDRStore yldrStore = new YLDRStore();

    Workbook workbook;
    XSSFSheet spreadsheet;

    public XSSFRow createQuartillyHeaderRows(XSSFSheet sheet, String startdatum, String slutdatum, String shop) {
        XSSFRow row = sheet.createRow(0);

        XSSFCell cell1 = row.createCell(0);
        cell1.setCellValue(shop);
        sheet.autoSizeColumn(cell1.getColumnIndex());

        XSSFCell cell2 = row.createCell(1);
        cell2.setCellValue(startdatum);
        sheet.autoSizeColumn(cell2.getColumnIndex());

        XSSFCell cell3 = row.createCell(2);
        cell3.setCellValue(slutdatum);
        sheet.autoSizeColumn(cell3.getColumnIndex());

        XSSFCell cell4 = row.createCell(3);
        cell4.setCellValue("Product Name");

        XSSFCell cell5 = row.createCell(4);
        cell5.setCellValue("Price before tax");

        XSSFCell cell6 = row.createCell(5);
        cell6.setCellValue("Number of products sold sold");

        XSSFCell cell7 = row.createCell(6);
        cell7.setCellValue("Total");

        XSSFCell cell8 = row.createCell(7);
        cell8.setCellValue("Currency");

        XSSFCell cell9 = row.createCell(8);
        cell9.setCellValue("Shipping cost");



        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);


        return row;
    }

    public void createQuartillyRows(String startdatum, String slutdatum, SalesInfo salesInfo, String shop, float kickback) {
        XSSFSheet spreadsheet = (XSSFSheet) workbook.createSheet("Summary");
        createQuartillyHeaderRows(spreadsheet, startdatum, slutdatum, shop);
        createQuartilyPerCountrySheets(startdatum, slutdatum, shop, salesInfo);
        createQuartilyPerSizeSheets(startdatum, slutdatum, shop, salesInfo);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        ArrayList<String> arrayList = new ArrayList<>();
        float summa = 0;

        int totalAmountOfProducts = 0;

        String SKU = null;
        int rowCount = 1;
        if (0 < salesInfo.getProductarray().length) {
            SKU = salesInfo.getProductarray()[0].getSKU();
        }
        for (Product l : salesInfo.getProductarray()) {

            if (!arrayList.contains(l.getName())) {

                XSSFRow row2 = spreadsheet.createRow(rowCount++);
                int columncount = 3;

                if (!l.equals(SKU)) {
                    XSSFRow separationRow = spreadsheet.createRow(rowCount++);
                    XSSFCell separationCell= separationRow.createCell(6);
                    separationCell.setCellStyle(style);
                }


                XSSFCell cell = row2.createCell(columncount++);
                cell.setCellValue(l.getName());
                spreadsheet.autoSizeColumn(cell.getColumnIndex());


                XSSFCell cell2 = row2.createCell(columncount++);
                cell2.setCellValue(String.format("%.2f",l.getPrice()));
                spreadsheet.autoSizeColumn(cell2.getColumnIndex());


                XSSFCell cell3 = row2.createCell(columncount++);
                cell3.setCellValue(salesInfo.countPerProduct(l));

                totalAmountOfProducts +=salesInfo.countPerProduct(l);

                spreadsheet.autoSizeColumn(cell3.getColumnIndex());


                XSSFCell cell4 = row2.createCell(columncount++);
                cell4.setCellValue(String.format("%.2f",l.getPrice() * salesInfo.countPerProduct(l)));
                summa += l.getPrice() * salesInfo.countPerProduct(l);
                spreadsheet.autoSizeColumn(cell4.getColumnIndex());
                cell4.setCellStyle(style);




                arrayList.add(l.getName());
                SKU = l.getSKU();
            }
        }
        CellStyle gul = workbook.createCellStyle();
        gul.setFillForegroundColor(IndexedColors.YELLOW1.getIndex());
        gul.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFRow row2 = spreadsheet.createRow(rowCount + 3);
        XSSFCell cell4 = row2.createCell(6);
        cell4.setCellValue(String.format("%.2f",summa));
        cell4.setCellStyle(style);
        spreadsheet.autoSizeColumn(cell4.getColumnIndex());

        XSSFCell cell4b = row2.createCell(5);
        cell4b.setCellValue(totalAmountOfProducts);
        spreadsheet.autoSizeColumn(cell4.getColumnIndex());


        XSSFRow row3 = spreadsheet.createRow(rowCount + 5);
        XSSFCell cell5= row3.createCell(6);
        cell5.setCellValue(String.format("%.2f",summa*kickback));
        cell5.setCellStyle(gul);
        spreadsheet.autoSizeColumn(cell5.getColumnIndex());

        XSSFCell cell6= row3.createCell(5);
        cell6.setCellValue(String.format("Kickback     " +"%.2f%%",+kickback*100));
        spreadsheet.autoSizeColumn(cell6.getColumnIndex());

        XSSFCell cell6b=row3.createCell(7);
        cell6b.setCellValue(salesInfo.getValuta());
        spreadsheet.autoSizeColumn(cell6b.getColumnIndex());

        XSSFCell cellShippingSum = row3.createCell(8);
        cellShippingSum.setCellValue(String.format("%.2f",salesInfo.getShippingSum()));
        spreadsheet.autoSizeColumn(cellShippingSum.getColumnIndex());

        XSSFCell cellShippingSumCurrency = row3.createCell(9);
        cellShippingSumCurrency.setCellValue(salesInfo.getValuta());
        spreadsheet.autoSizeColumn(cellShippingSumCurrency.getColumnIndex());


        //XSSFRow row3 = spreadsheet.createRow(rowCount+2);
        XSSFCell cell7 = row2.createCell(7);
        cell7.setCellValue(salesInfo.getValuta());
        spreadsheet.autoSizeColumn(cell7.getColumnIndex());

        CellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cell4.setCellStyle(style2);

    }


    public void createQuartilyPerCountrySheets(String startDatum, String slutDatum, String shop, SalesInfo salesInfo) {
        spreadsheet = (XSSFSheet) workbook.createSheet("Products sold per country");

        int rowCount = 0;


        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        XSSFRow row2 = spreadsheet.createRow(rowCount);
        XSSFCell cell2 = row2.createCell(0);
        cell2.setCellValue(shop);
        cell2.setCellStyle(style);
        spreadsheet.autoSizeColumn(cell2.getColumnIndex());


        int columncount = 1;
        ArrayList<String> pNameArrayList = new ArrayList<>();

        for (Product p : salesInfo.getProductarray()) {

            if (!pNameArrayList.contains(p.getName())) {
                cell2 = row2.createCell(columncount++);
                cell2.setCellValue(p.getName());
                cell2.setCellStyle(style);
                spreadsheet.autoSizeColumn(cell2.getColumnIndex());

                pNameArrayList.add(p.getName());
            }
        }
        int rowCount3 = 1;
        int columnCount2 = 0;


        ArrayList<String> compareArray = new ArrayList<>();

        ArrayList<String> countryList = new ArrayList<>();

        Product [] products= new Product[salesInfo.getProductarray().length];

        ArrayList <Product> productArrayList= new ArrayList<>();

      /*  for (int i = 0; i <salesInfo.getProductarray().length ; i++) {
            for (int j = i+1; j <salesInfo.getProductarray().length ; j++) {
                if (salesInfo.getProductarray()[i].getCountryOfPurchase().compareTo(salesInfo.getProductarray()[j].getCountryOfPurchase())<0){
                    Product temp=salesInfo.getProductarray()[j];
                    salesInfo.getProductarray()[j]=salesInfo.getProductarray()[i];

                    if (!productArrayList.contains(salesInfo.getProductarray()[i].getCountryOfPurchase())) {
                        productArrayList.add(salesInfo.getProductarray()[i]);
                    }

                    salesInfo.getProductarray()[i]=temp;
                }
            }
        }

        for (Product p:productArrayList) {
            System.out.println("landet är " +p.getCountryOfPurchase());
        }*/

        for (Product p : salesInfo.getProductarray()) {


            if (!countryList.contains(p.getCountryOfPurchase())) {

                XSSFRow row3 = spreadsheet.createRow(rowCount3++);
                XSSFCell cell = row3.createCell(columnCount2++);
                cell.setCellValue(p.getCountryOfPurchase());
                cell.setCellStyle(style);
                spreadsheet.autoSizeColumn(cell.getColumnIndex());

                countryList.add(p.getCountryOfPurchase());

                for (Product p2 : salesInfo.getProductarray()) {

                    System.out.println(p.getCountryOfPurchase());

                    if (p.getCountryOfPurchase().equals(p2.getCountryOfPurchase())) {
                        compareArray.add(p2.getName());
                        System.out.println(p2.getName());

                    }
                }

                int count = 0;
                for (String s : pNameArrayList) {
                    for (String s2 : compareArray
                    ) {
                        if (s.equals(s2)) {
                            count++;

                        }

                    }
                    XSSFCell cell4 = row3.createCell(columnCount2++);
                    if (count!=0) {
                        cell4.setCellValue(count);
                    }
                    System.out.println(count);
                    count = 0;
                }
                compareArray.clear();
                columnCount2 = 0;
            }

        }
    }

    public void createQuartilyPerSizeSheets(String startDatum, String slutDatum, String shop, SalesInfo salesInfo) {
        spreadsheet = (XSSFSheet) workbook.createSheet("Products sold per size");

        int rowCount = 0;

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        XSSFRow row2 = spreadsheet.createRow(rowCount);
        XSSFCell cell2 = row2.createCell(0);
        cell2.setCellValue(shop);
        cell2.setCellStyle(style);
        spreadsheet.autoSizeColumn(cell2.getColumnIndex());

        float iteration1 = 0;
        float iteration2 = 0;
        ArrayList<String> baraNamn = new ArrayList<>();
        ArrayList<String> namnPlusStorlek = new ArrayList<>();

        int columncount = 1;
        ArrayList<String> pNameArrayList = new ArrayList<>();



        for (Product p : salesInfo.getProductarray()) {

            if (!pNameArrayList.contains(p.getName()) && p.getSize() != null) {
                cell2 = row2.createCell(columncount++);
                cell2.setCellStyle(style);
                cell2.setCellValue(p.getName());
                //spreadsheet.autoSizeColumn(cell2.getColumnIndex());

                pNameArrayList.add(p.getName());

                iteration1++;

            }
        }
        int rowCount3 = 1;
        int columnCount2 = 0;


        ArrayList<String> compareArray = new ArrayList<>();

        ArrayList<String> sizeList = new ArrayList<>();

        int columnCount3 = 1;

        String[] storleksArray = new String[salesInfo.getProductarray().length];

        ArrayList<String> temp = new ArrayList<>();
        String storlekar="";  //XS,S,M,L,XL,XXL,XXXL,XXXXL,128cl,140cl,152cl

        for (Product p :salesInfo.getProductarray()) {
            storlekar+=p.getSize()+",";
        }
        System.out.println(storlekar);




        temp.toArray(storleksArray);

        sizeList.clear();




       for (String p :  storlekar.split(",")) {


            if (!sizeList.contains(p)) {
                if (p != null ) {
                    if (!p.equals("null")) {
                        int count = rowCount3;
                        XSSFRow row3 = spreadsheet.createRow(rowCount3++);

                        XSSFCell cell = row3.createCell(columnCount2);
                        cell.setCellStyle(style);
                        cell.setCellValue(p);

                        sizeList.add(p);

                        if (count < rowCount3) {
                            columnCount3 = 1;
                            baraNamn.clear();
                        }

                    }
                }
            }
        }
        int antalProdukterPerSKU = 0;
        float antalProdukterAvSammaStorlek = 0;

        for (int i = 0; i < sizeList.size(); i++) {
            for (int j = 0; j < salesInfo.getProductarray().length; j++) {

                if (salesInfo.getProductarray()[j].getSize() != null) {
                    if (salesInfo.getProductarray()[j].getSize().equals(sizeList.get(i))) {
                        if (!namnPlusStorlek.contains(salesInfo.getProductarray()[j].getName() + salesInfo.getProductarray()[j].getSize())) {
                            namnPlusStorlek.add(salesInfo.getProductarray()[j].getName() + salesInfo.getProductarray()[j].getSize());
                            antalProdukterPerSKU = 0;
                            antalProdukterAvSammaStorlek = 0;

                            for (Product p : salesInfo.getProductarray()) {
                                if (salesInfo.getProductarray()[j].getName().equals(p.getName())) {
                                    antalProdukterPerSKU++;
                                    if (salesInfo.getProductarray()[j].getSize().equals(p.getSize())) {
                                        antalProdukterAvSammaStorlek++;
                                    }
                                }
                            }
                            float procent = antalProdukterAvSammaStorlek / antalProdukterPerSKU;

                            if (procent > 0) {
                                int pos = 0;
                                for (int k = 0; k < pNameArrayList.size(); k++) {
                                    if (salesInfo.getProductarray()[j].getName().equals(pNameArrayList.get(k))) {
                                        pos = k;
                                    }
                                }

                                XSSFCell cell4 = spreadsheet.getRow(i + 1).createCell(pos + 1);
                                float summa=procent*100;
                                cell4.setCellValue(String.format("%.0f%%",summa));
                            }


                        }

                    }
                }

            }

        }







       /* for (Product p : salesInfo.getProductarray()) {


            if (!sizeList.contains(p.getSize())) {
                if (p.getSize() != null) {
                    int count = rowCount3;
                    XSSFRow row3 = spreadsheet.createRow(rowCount3++);
                    XSSFCell cell = row3.createCell(columnCount2);
                    cell.setCellValue(p.getSize());

                    sizeList.add(p.getSize());

                    if (count < rowCount3) {
                        columnCount3 = 1;
                        baraNamn.clear();
                    }


                }
            }
            int antalProdukterPerSKU = 0;
            float antalProdukterAvSammaStorlek = 0;
            int sizeCount = 0;

            for (Product p2 : salesInfo.getProductarray()) {

                if (p.getName().equals(p2.getName()) && p.getSize() != null && p2.getSize() != null) {
                    antalProdukterPerSKU++;
                    if (p.getSize() != null && p2.getSize() != null && p.getSize().equals(p2.getSize())) {
                        antalProdukterAvSammaStorlek++;
                    }
                }
            }


            float procent = antalProdukterAvSammaStorlek / antalProdukterPerSKU;

            if (procent > 0) {


                int i;
                int pos=0;

                System.out.println(procent);
                for ( i = 0; i <pNameArrayList.size() ; i++) {
                    if (!baraNamn.contains(p.getName())){
                        if (pNameArrayList.get(i).equals(p.getName())) {
                            pos = i;
                        }
                    }
                }
                if (!baraNamn.contains(p.getName())) {
                    if (!namnPlusStorlek.contains(p.getName() + p.getSize())) {
                        XSSFCell cell4 = spreadsheet.getRow(rowCount3 - 1).createCell(pos + 1);
                        cell4.setCellValue(procent);
                        baraNamn.add(p.getName());
                        namnPlusStorlek.add(p.getName()+p.getSize());
                    }
                }

                iteration2++;
            }
        }*/
    }


    public void createQuartillyExcel(String startDatum, String slutDatum, String shop, float kickback) throws SQLException, FileNotFoundException {


        String path = System.getProperty("user.home") + "\\" + "Kvartalsrapport " + shop + " " + LocalDate.now(
                ZoneId.of("Europe/Stockholm")) + ".xlsx";
        File file = new File(path);


        if (!file.exists()) {
            workbook = new XSSFWorkbook();
            createQuartillyRows(startDatum, slutDatum, yldrStore.getSalesInfoFromDatabase(shop, startDatum, slutDatum), shop,kickback);

            //        createRows(startDatum, slutDatum, ordrar, manufacturer);
            try (OutputStream fileOut = new FileOutputStream(new File(String.valueOf(file)))) {
                workbook.write(fileOut);
                Desktop.getDesktop().open(new File(String.valueOf(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.exists()) {

            workbook = new XSSFWorkbook();
            createQuartillyRows(startDatum, slutDatum, yldrStore.getSalesInfoFromDatabase(shop, startDatum, slutDatum), shop, kickback);
            for (int i = 1; file.exists(); i++) {
                file = new File(String.format(System.getProperty("user.home") + "\\" + "Kvartalsrapport " + shop + " " + LocalDate.now(ZoneId.of("Europe/Stockholm")) + " (%d)" + ".xlsx", i));
            }
            //    createRows(startDatum, slutDatum, ordrar, manufacturer);

            try (OutputStream fileOut = new FileOutputStream(new File(String.valueOf(file)))) {
                workbook.write(fileOut);
                Desktop.getDesktop().open(new File(String.valueOf(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public XSSFRow createLogisticsHeaderRow(XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);

        XSSFCell cell1 = row.createCell(0);
        cell1.setCellValue("CustomerNumber");

        XSSFCell cell2 = row.createCell(1);
        cell2.setCellValue("OrderNo");

        XSSFCell cell3 = row.createCell(2);
        cell3.setCellValue("ArticleNumber");

        XSSFCell cell4 = row.createCell(3);
        cell4.setCellValue("ArticleName");

        XSSFCell cell5 = row.createCell(4);
        cell5.setCellValue("NumberOfItems");

        XSSFCell cell6 = row.createCell(5);
        cell6.setCellValue("OrderComment");

        XSSFCell cell7 = row.createCell(6);
        cell7.setCellValue("CustomerOrderNumber");

        XSSFCell cell8 = row.createCell(7);
        cell8.setCellValue("WayOfDelivery");

        XSSFCell cell9 = row.createCell(8);
        cell9.setCellValue("Customer.Name");

        XSSFCell cell10 = row.createCell(9);
        cell10.setCellValue("Customer.Address");

        XSSFCell cell11 = row.createCell(10);
        cell11.setCellValue("Customer.Address2");

        XSSFCell cell12 = row.createCell(11);
        cell12.setCellValue("Customer.PostCode");

        XSSFCell cell13 = row.createCell(12);
        cell13.setCellValue("Customer.City");

        XSSFCell cell14 = row.createCell(13);
        cell14.setCellValue("Customer.TelePhone");

        XSSFCell cell15 = row.createCell(14);
        cell15.setCellValue("Customer.Remark");

        XSSFCell cell16 = row.createCell(15);
        cell16.setCellValue("Customer.Email");

        XSSFCell cell17 = row.createCell(16);
        cell17.setCellValue("Customer.MobilePhone");

        XSSFCell cell18 = row.createCell(17);
        cell18.setCellValue("Customer.CountryCode");

        XSSFCell cell19 = row.createCell(18);
        cell19.setCellValue("Customer.CountryName");

        XSSFCell cell20 = row.createCell(19);
        cell20.setCellValue("Customer.CountryStateCode");

        XSSFCell cell21 = row.createCell(20);
        cell21.setCellValue("Customer.DeliveryInstruction");

        XSSFCell cell22 = row.createCell(21);
        cell22.setCellValue("Customer.NotifyBySMS");

        XSSFCell cell23 = row.createCell(22);
        cell23.setCellValue("Customer.NotifyByEmail");

        XSSFCell cell24 = row.createCell(23);
        cell24.setCellValue("Customer.NotifyByTelephone");


        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);
        cell11.setCellStyle(style);
        cell12.setCellStyle(style);
        cell13.setCellStyle(style);
        cell14.setCellStyle(style);
        cell15.setCellStyle(style);
        cell16.setCellStyle(style);
        cell17.setCellStyle(style);
        cell18.setCellStyle(style);
        cell19.setCellStyle(style);
        cell20.setCellStyle(style);
        cell21.setCellStyle(style);
        cell22.setCellStyle(style);
        cell23.setCellStyle(style);
        cell24.setCellStyle(style);

        return row;

    }

    public void createLogisticsRows(String startDatum, String slutDatum, LogistikInfo[] logistikInfo) {
        XSSFSheet spreadsheet = (XSSFSheet) workbook.createSheet("Summary");
        createLogisticsHeaderRow(spreadsheet);

        String ordernumber = null;
        int rowCount = 1;
        if (0 < logistikInfo.length) {
            ordernumber = logistikInfo[0].getCustomerOrdernumber();
        }
        for (LogistikInfo l : logistikInfo) {
            int columncount = 0;
            if (!l.getCustomerOrdernumber().equals(ordernumber)) {
                XSSFRow separationRow = spreadsheet.createRow(rowCount++);
            }
            XSSFRow row2 = spreadsheet.createRow(rowCount++);

            XSSFCell cell = row2.createCell(columncount++);
            cell.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell.getColumnIndex());


            String s = l.getCustomerOrdernumber().substring(17);
            XSSFCell cell2 = row2.createCell(columncount++);
            cell2.setCellValue(s);
            spreadsheet.autoSizeColumn(cell2.getColumnIndex());

            String strolek="_"+l.getNickname();
            if (l.getNickname() == null) {
                strolek=" ";
            }else if(l.getNickname().isEmpty()){
                strolek=" ";
            }
            if (l.getSize() == null) {
                l.setSize(" ");
            }

            XSSFCell cell3 = row2.createCell(columncount++);
            cell3.setCellValue(l.getArticlenumber() + "_" + l.getSize() + strolek);
            spreadsheet.autoSizeColumn(cell3.getColumnIndex());



            XSSFCell cell4 = row2.createCell(columncount++);
            cell4.setCellValue(l.getArticleName());
            spreadsheet.autoSizeColumn(cell4.getColumnIndex());

            XSSFCell cell5 = row2.createCell(columncount++);
            cell5.setCellValue("1");
            spreadsheet.autoSizeColumn(cell5.getColumnIndex());

            XSSFCell cell6 = row2.createCell(columncount++);
            cell6.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell6.getColumnIndex());

            XSSFCell cell7 = row2.createCell(columncount++);
            cell7.setCellValue(l.getCustomerOrdernumber());
            spreadsheet.autoSizeColumn(cell7.getColumnIndex());

            XSSFCell cell8 = row2.createCell(columncount++);
            cell8.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell8.getColumnIndex());

            XSSFCell cell9 = row2.createCell(columncount++);
            cell9.setCellValue(l.getCustomerName());
            spreadsheet.autoSizeColumn(cell9.getColumnIndex());

            XSSFCell cell10 = row2.createCell(columncount++);
            cell10.setCellValue(l.getCustomerAdress());
            spreadsheet.autoSizeColumn(cell10.getColumnIndex());

            XSSFCell cell11 = row2.createCell(columncount++);
            cell11.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell11.getColumnIndex());

            XSSFCell cell12 = row2.createCell(columncount++);
            cell12.setCellValue(l.getCustomerPostCode());
            spreadsheet.autoSizeColumn(cell12.getColumnIndex());

            XSSFCell cell13 = row2.createCell(columncount++);
            cell13.setCellValue(l.getCustomerCity());
            spreadsheet.autoSizeColumn(cell13.getColumnIndex());

            XSSFCell cell14 = row2.createCell(columncount++);
            cell14.setCellValue(l.getCustomerPhoneNumber());
            spreadsheet.autoSizeColumn(cell14.getColumnIndex());

            XSSFCell cell15 = row2.createCell(columncount++);
            cell15.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell15.getColumnIndex());

            XSSFCell cell16 = row2.createCell(columncount++);
            cell16.setCellValue(l.getCustomerEmail());
            spreadsheet.autoSizeColumn(cell16.getColumnIndex());

            XSSFCell cell17 = row2.createCell(columncount++);
            cell17.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell17.getColumnIndex());

            XSSFCell cell18 = row2.createCell(columncount++);
            cell18.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell18.getColumnIndex());

            XSSFCell cell19 = row2.createCell(columncount++);
            cell19.setCellValue(l.getCountry());
            spreadsheet.autoSizeColumn(cell19.getColumnIndex());

            XSSFCell cell20 = row2.createCell(columncount++);
            cell20.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell20.getColumnIndex());

            XSSFCell cell21 = row2.createCell(columncount++);
            cell21.setCellValue(" ");
            spreadsheet.autoSizeColumn(cell21.getColumnIndex());

            XSSFCell cell22 = row2.createCell(columncount++);
            cell22.setCellValue("1");
            spreadsheet.autoSizeColumn(cell22.getColumnIndex());

            XSSFCell cell23 = row2.createCell(columncount++);
            cell23.setCellValue("1");
            spreadsheet.autoSizeColumn(cell23.getColumnIndex());

            XSSFCell cell24 = row2.createCell(columncount++);
            cell24.setCellValue("");
            spreadsheet.autoSizeColumn(cell24.getColumnIndex());

            ordernumber = l.getCustomerOrdernumber();

        }


    }

    public void createLogisticExcel(String startDatum, String slutDatum) throws IOException, SQLException {


        String path = System.getProperty("user.home") + "\\" + "LogistikInfo" + " " + LocalDate.now(
                ZoneId.of("Europe/Stockholm")) + ".xlsx";
        File file = new File(path);


        if (!file.exists()) {
            workbook = new XSSFWorkbook();
            createLogisticsRows(startDatum, slutDatum, yldrStore.getLogisticInfo(startDatum, slutDatum));

            //        createRows(startDatum, slutDatum, ordrar, manufacturer);
            try (OutputStream fileOut = new FileOutputStream(new File(String.valueOf(file)))) {
                workbook.write(fileOut);
                Desktop.getDesktop().open(new File(String.valueOf(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.exists()) {

            workbook = new XSSFWorkbook();
            createLogisticsRows(startDatum, slutDatum, yldrStore.getLogisticInfo(startDatum, slutDatum));
            for (int i = 1; file.exists(); i++) {
                file = new File(String.format(System.getProperty("user.home") + "\\" + "LogistikInfo" + " " + LocalDate.now(ZoneId.of("Europe/Stockholm")) + " (%d)" + ".xlsx", i));
            }
            //    createRows(startDatum, slutDatum, ordrar, manufacturer);

            try (OutputStream fileOut = new FileOutputStream(new File(String.valueOf(file)))) {
                workbook.write(fileOut);
                Desktop.getDesktop().open(new File(String.valueOf(file)));
            }

        }
    }

    public void createRows(String startDatum, String slutDatum, Order[] ordrar, String manufacturer) {
        XSSFSheet spreadsheet = (XSSFSheet) workbook.createSheet("Summary");
        createSheets(startDatum, slutDatum, manufacturer);
        createHeaderRow(spreadsheet);
        String ordernumber = null;
        int rowCount = 1;
        if (0 < ordrar.length) {
            ordernumber = ordrar[0].getOrderNumber();
        }
        for (Order o : ordrar) {

            int columncount = 0;
            if (!o.getOrderNumber().equals(ordernumber)) {
                XSSFRow separationRow = spreadsheet.createRow(rowCount++);
            }
            XSSFRow row2 = spreadsheet.createRow(rowCount++);

            XSSFCell cell = row2.createCell(columncount++);
            cell.setCellValue(o.getOrderNumber());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getArticleNumber());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getProductName());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getSize());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getNickname());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getCountryFlag());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getRealName());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getShop());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getTillverkare());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getRang());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getSquadNumber());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getCustom1());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getCustom2());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());

            cell = row2.createCell(columncount++);
            cell.setCellValue(o.getCustom3());
            spreadsheet.autoSizeColumn(cell.getColumnIndex());


            ordernumber = o.getOrderNumber();

        }
    }

    public boolean checkDateIntervals(String startdatum, String slutdatum) throws SQLException {
        String startDatum1 = startdatum + ".0";
        String slutdatum1 = slutdatum + ".0";

        int count = 0;
        for (PurchaseOrder po : yldrStore.getIsSentorNot()) {

            if (po.getDate().toString().compareTo(startDatum1) < 0 && !po.getIsEmailSent()) {
                count++;
                System.out.println("utanför  (innan) intervallet " + po.getDate() + " " + po.getOrderNumber());

            } else if (po.getDate().toString().compareTo(slutdatum1) > 0 && !po.getIsEmailSent()) {

                System.out.println("utanför (efter) intervallet " + po.getDate() + " " + po.getOrderNumber());
                count++;
            }

        }
        if (count > 0) {
            return true;
        } else return false;
    }


    public XSSFRow createHeaderRow(XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);

        XSSFCell cell1 = row.createCell(0);
        cell1.setCellValue("Ordernumber");

        XSSFCell cell2 = row.createCell(1);
        cell2.setCellValue("Articlenumber");

        XSSFCell cell3 = row.createCell(2);
        cell3.setCellValue("Product name");

        XSSFCell cell4 = row.createCell(3);
        cell4.setCellValue("Size");

        XSSFCell cell5 = row.createCell(4);
        cell5.setCellValue("Nickname");

        XSSFCell cell6 = row.createCell(5);
        cell6.setCellValue("Countryflag");

        XSSFCell cell7 = row.createCell(6);
        cell7.setCellValue("Realname");

        XSSFCell cell8 = row.createCell(7);
        cell8.setCellValue("Shop");

        XSSFCell cell9 = row.createCell(8);
        cell9.setCellValue("Manufacturer");

        XSSFCell cell10 = row.createCell(9);
        cell10.setCellValue("Rang");

        XSSFCell cell11 = row.createCell(10);
        cell11.setCellValue("SquadNumber");

        XSSFCell cell12 = row.createCell(11);
        cell12.setCellValue("Custom1");

        XSSFCell cell13 = row.createCell(12);
        cell13.setCellValue("Custom2");

        XSSFCell cell14 = row.createCell(13);
        cell14.setCellValue("Custom3");


        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);
        cell11.setCellStyle(style);
        cell12.setCellStyle(style);
        cell13.setCellStyle(style);
        cell14.setCellStyle(style);
        return row;
    }


    public void createWB(String startDatum, String slutDatum, String manufacturer) throws IOException, SQLException {
        Order[] ordrar = new Order[yldrStore.getOrdrarFromDatabase(startDatum, slutDatum, manufacturer).length];
        System.arraycopy(yldrStore.getOrdrarFromDatabase(startDatum, slutDatum, manufacturer), 0, ordrar, 0, ordrar.length);

        //checkDateIntervals(startDatum,slutDatum);


        String path = System.getProperty("user.home") + "\\" + manufacturer + " " + LocalDate.now(
                ZoneId.of("Europe/Stockholm")) + ".xlsx";
        File file = new File(path);

        if (!file.exists()) {
            workbook = new XSSFWorkbook();
            createRows(startDatum, slutDatum, ordrar, manufacturer);
            try (OutputStream fileOut = new FileOutputStream(new File(String.valueOf(file)))) {
                workbook.write(fileOut);
                Desktop.getDesktop().open(new File(String.valueOf(file)));
            }
        } else if (file.exists()) {

            workbook = new XSSFWorkbook();
            for (int i = 1; file.exists(); i++) {
                file = new File(String.format(System.getProperty("user.home") + "\\" + manufacturer + " " + LocalDate.now(ZoneId.of("Europe/Stockholm")) + " (%d)" + ".xlsx", i));
            }
            createRows(startDatum, slutDatum, ordrar, manufacturer);

            try (OutputStream fileOut = new FileOutputStream(new File(String.valueOf(file)))) {
                workbook.write(fileOut);
                Desktop.getDesktop().open(new File(String.valueOf(file)));
            }

        }







          /*  for (int i = 1; file.exists(); i++) {
                //file=new File(String.format("C:\\Users\\46762\\Downloads\\Fuch " + LocalDate.now(ZoneId.of( "Europe/Stockholm" ))+" (%d)"+".xlsx",i));
                file = new File(String.format(System.getProperty("user.home") + "\\Fuch " + LocalDate.now(ZoneId.of("Europe/Stockholm")) + " (%d)" + ".xlsx", i));
            }
        try (OutputStream fileOut = new FileOutputStream(new File(String.valueOf(file)))) {
            workbook.write(fileOut);
            Desktop.getDesktop().open(new File(String.valueOf(file)));
        }*/


        yldrStore.setToIsSent(ordrar, startDatum, slutDatum, manufacturer);


    }

    public void createSheets(String startDatum, String slutDatum, String manufacturer) {

        int rowCountCustomSheet = 1;


        String[] shoppar = new String[yldrStore.getDistinctShopFromDatabase(manufacturer).length];
        System.arraycopy(yldrStore.getDistinctShopFromDatabase(manufacturer), 0, shoppar, 0, shoppar.length);

        String shop = "s";
        for (String s : shoppar) {
            if (!s.equals(shop)) {
                spreadsheet = (XSSFSheet) workbook.createSheet(s);
                createHeaderRow(spreadsheet);
                shop = spreadsheet.getSheetName();
                int rowCount = 1;
                String SKU = "s";
                Order[] ordrar = new Order[yldrStore.getOrdrarFromDatabase(startDatum, slutDatum, manufacturer).length];

                System.arraycopy(yldrStore.getOrdrarFromDatabase(startDatum, slutDatum, manufacturer), 0, ordrar, 0, ordrar.length);

                for (int i = 0; i < ordrar.length; i++) {
                    for (int j = i; j < ordrar.length; j++) {
                        if (ordrar[i].getArticleNumber().compareTo(ordrar[j].getArticleNumber()) > 0) {
                            Order temp = ordrar[i];
                            ordrar[i] = ordrar[j];
                            ordrar[j] = temp;
                        }
                    }
                }


                for (Order o : ordrar) {

                    // String[] parts = o.getArticleNumber().split("_");
                    if (spreadsheet.getSheetName().equals(o.getShop())) {
                        int columncountSimpleSheet = 0;

                        if (!SKU.equals(o.getArticleNumber())) {
                            if (!SKU.equals("s")) {
                                XSSFRow separationRow = spreadsheet.createRow(rowCount++);
                            }
                        }
                        XSSFRow row2 = spreadsheet.createRow(rowCount++);

                        XSSFCell cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getOrderNumber());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getArticleNumber());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getProductName());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getSize());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getNickname());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getCountryFlag());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getRealName());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getShop());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getTillverkare());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getRang());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getSquadNumber());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getCustom1());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getCustom2());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        cell = row2.createCell(columncountSimpleSheet++);
                        cell.setCellValue(o.getCustom3());
                        spreadsheet.autoSizeColumn(cell.getColumnIndex());

                        SKU = o.getArticleNumber();

                    }
                }
            }

      /*  String simpleSKU = "s";
        String customizedSKU = "s";
        String otherSKU = "s";
        for (String s : yldrStore.getDistinctSKU(startDatum, slutDatum)) {
            int rowCountSimpleSheet = 1;
            int count = 0;

            System.out.println("SKU:n är: " + s);
            for (int i = s.length() - 1; i > 0; i--) {
                if (s.charAt(i) == '_') {
                    count++;
                }
            }
            String[] parts = s.split("_");


            if (count == 2) {
                if (!simpleSKU.equals(parts[0])) {
                    spreadsheet = (XSSFSheet) workbook.createSheet(parts[0]);
                    createHeaderRow(spreadsheet);
                    simpleSKU = spreadsheet.getSheetName();
                    String articlenumber = "s";

                    for (Order o : yldrStore.getOrdrarFromDatabase(startDatum, slutDatum,manufacturer)) {
                        int columncountSimpleSheet = 0;

                        //XSSFCell headerCell=createHeaderRow(spreadsheet).createCell(columncountSimpleSheet++);

                        if (o.getArticleNumber().contains(parts[0]) && !o.getArticleNumber().contains("_c")) {
                            if (!o.getArticleNumber().equals(articlenumber) && !articlenumber.endsWith("c")) {
                                XSSFRow separationRow = spreadsheet.createRow(rowCountSimpleSheet++);
                            }
                            XSSFRow row2 = spreadsheet.createRow(rowCountSimpleSheet++);

                            XSSFCell cell = row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getOrderNumber());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getArticleNumber());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getProductName());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getSize());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getNickname());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getCountryFlag());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell=row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getRealName());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getShop());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountSimpleSheet++);
                            cell.setCellValue(o.getTillverkare());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            articlenumber = o.getArticleNumber();

                        }
                    }
                    if (yldrStore.getOrdrarFromDatabase(startDatum, slutDatum,manufacturer).length > 0) {
                        spreadsheet.shiftRows(2, spreadsheet.getLastRowNum() + 1, -1);
                    }
                }
            } else if (count == 3) {

                if (!customizedSKU.equals(parts[0] + "_" + parts[3])) {
                    spreadsheet = (XSSFSheet) workbook.createSheet(parts[0] + "_" + parts[3]);
                    createHeaderRow(spreadsheet);
                    customizedSKU = spreadsheet.getSheetName();
                    String articlenumber = "s";
                    for (Order o : yldrStore.getOrdrarFromDatabase(startDatum, slutDatum,manufacturer)) {

                        int collumnCountCustomSheet = 0;

                        if (o.getArticleNumber().contains(parts[0]) && o.getArticleNumber().contains("_c")) {
                            if (!o.getArticleNumber().equals(articlenumber)) {
                                XSSFRow separationRow = spreadsheet.createRow(rowCountCustomSheet++);
                            }
                            XSSFRow row2 = spreadsheet.createRow(rowCountCustomSheet++);

                            XSSFCell cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getOrderNumber());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getArticleNumber());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getProductName());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getSize());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getNickname());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getCountryFlag());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell=row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getRealName());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getShop());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getTillverkare());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            /*XSSFCell cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getOrderNumber());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getArticleNumber());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getSize());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getNickname());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());


                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getCountryFlag());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());


                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getShop());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getTillverkare());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(collumnCountCustomSheet++);
                            cell.setCellValue(o.getProductName());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            articlenumber = o.getArticleNumber();
                        }
                    }
                    if (yldrStore.getOrdrarFromDatabase(startDatum, slutDatum,manufacturer).length > 0) {
                        spreadsheet.shiftRows(2, spreadsheet.getLastRowNum() + 1, -1);
                    }
                }
            } else if (count < 1) {

                if (!otherSKU.equals(s)) {
                    spreadsheet = (XSSFSheet) workbook.createSheet(s);
                    createHeaderRow(spreadsheet);
                    otherSKU = spreadsheet.getSheetName();
                    int rowCountOtherSheet=1;
                    for (Order o : yldrStore.getOrdrarFromDatabase(startDatum, slutDatum)) {
                        int columncountOtherSheet = 0;

                        if (o.getArticleNumber().equals(s)) {
                            XSSFRow row2 = spreadsheet.createRow(rowCountOtherSheet++);

                            XSSFCell cell = row2.createCell(columncountOtherSheet++);
                            cell.setCellValue(o.getOrderNumber());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountOtherSheet++);
                            cell.setCellValue(o.getArticleNumber());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());

                            cell = row2.createCell(columncountOtherSheet++);
                            cell.setCellValue(o.getSize());
                            spreadsheet.autoSizeColumn(cell.getColumnIndex());
                        }

                    }
                }
            }




          /*  Timestamp startDatum=Timestamp.valueOf("2020-10-06 11:45:37");
          /*   Timestamp slutDatum=Timestamp.valueOf("2020-10-07 06:10:16");
        }*/

        }
    }


    public void laggTillProdukt(String SKU, String productName, String tillverkare, float pris) {
        yldrStore.addProduct(SKU, productName, tillverkare, pris);
    }

    public SalesInfo getProductsSoldPerShop(String shop, String startDate, String endDate) {

       /* for (SalesInfo si:yldrStore.getSalesInfoFromDatabase(shop)) {
            for (String s:si.getAmountSalesPerSKU()) {
                System.out.println(s);
            }
        }*/
        SalesInfo salesInfo = yldrStore.getSalesInfoFromDatabase(shop, startDate, endDate);

        for (int s : salesInfo.countAmountOfSKUsSold(salesInfo.getProductarray())) {
            System.out.println(s);
        }

        return salesInfo;

    }

    public String[] getShoppar() {
        return yldrStore.getShopsFromDatabase();
    }

    public Order[] getProductsFromOrdercontent(String ordernumber) throws SQLException {
        return yldrStore.getRowFromOrdercontent(ordernumber);
    }

    public void removeFromDB(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String realName, String rang, String squadNumber, String custom1, String custom2, String custom3) throws SQLException {
        calculateNewTotalAmount(orderNumber, articleNumber, size, nickname, countryFlag, realName, rang, squadNumber, custom1, custom2, custom3);

        yldrStore.deleteRowFromOC(orderNumber, articleNumber, size, nickname, countryFlag, realName, rang, squadNumber, custom1, custom2, custom3);

        if (yldrStore.getRowFromOrdercontent(orderNumber).length == 0) {
            yldrStore.deleteFromPO(orderNumber);
        }


    }

    public void calculateNewTotalAmount(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String realName, String rang, String squadNumber, String custom1, String custom2, String custom3) throws SQLException {
        float nyaTotalSumman;
        float nyaSkatteSumman;

        OrderCalculator oc = yldrStore.getPricingInfo(orderNumber, articleNumber, size, nickname, realName, countryFlag, rang, squadNumber, custom1, custom2, custom3);
        float temp;
        float temp2;

        temp = oc.getSumma() - oc.getTax() - oc.getShippingCost();
        temp2 = temp;
        System.out.println(temp);
        if (oc.getRabatt() == 0) {
            float tax;
            temp = temp - oc.getProductInfo().getPrice();
            temp = (float) ((temp + oc.getShippingCost()) * 1.25); //totalsumman färdigräknad
            nyaTotalSumman = temp;

            System.out.println("Ny totalsumma " + temp);
            tax = (float) (temp - (temp / 1.25)); //skatten färdigräknad
            nyaSkatteSumman = tax;
            System.out.println();
            System.out.println("Ny skatt " + tax);

        } else {
            float tax2;
            temp2 = temp2 - (oc.getProductInfo().getPrice() - (oc.getProductInfo().getPrice() * oc.getRabatt()));
            System.out.println("Ny totalsumma med discount " + temp2);
            nyaTotalSumman = temp2;

            tax2 = (float) (temp2 - (temp2 / 1.25));
            System.out.println();
            nyaSkatteSumman = tax2;
            System.out.println("Ny skatt med Discount " + tax2);
        }

        yldrStore.updatePurchaseOrderInfo(nyaTotalSumman, nyaSkatteSumman, orderNumber);

    }

    public void removeTestOrder(String orderNumber) throws SQLException {
        yldrStore.deleteTestOrderFromOC(orderNumber);

        if (yldrStore.getRowFromOrdercontent(orderNumber).length == 0) {
            yldrStore.deleteFromPO(orderNumber);
        }
    }


}

