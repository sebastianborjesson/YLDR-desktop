import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class YLDR {


    YLDRStore yldrStore = new YLDRStore();

    Workbook workbook;
    XSSFSheet spreadsheet;

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


            ordernumber = o.getOrderNumber();

        }
    }

    public boolean checkDateIntervals(String startdatum, String slutdatum) throws SQLException {
        String startDatum1 = startdatum + ".0";
        String slutdatum1 = slutdatum + ".0";

        int count = 0;
        for (PurchaseOrder po : yldrStore.getIsSentorNot()) {

            if (po.getDate().toString().compareTo(startDatum1) < 0 && !po.isSent()) {
                count++;
                System.out.println("utanför  (innan) intervallet " + po.getDate() + " " + po.getOrderNumber());

            } else if (po.getDate().toString().compareTo(slutdatum1) > 0 && !po.isSent()) {

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



    public void laggTillProdukt(String SKU, String productName, String tillverkare) {
        yldrStore.addProduct(SKU, productName, tillverkare);
    }

    public SalesInfo getProductsSoldPerShop(String shop){

        /*for (SalesInfo si:yldrStore.getSalesInfoFromDatabase(shop)) {
            for (String s:si.getAmountSalesPerSKU()) {
                System.out.println(s);
            }
        }*/

        return yldrStore.getSalesInfoFromDatabase(shop);

    }

}

