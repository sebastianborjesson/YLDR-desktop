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
import java.time.LocalDate;
import java.time.ZoneId;

public class YLDR {


    YLDRStore yldrStore = new YLDRStore();

    Workbook workbook;
    XSSFSheet spreadsheet;


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


            String s=l.getCustomerOrdernumber().substring(17);
            XSSFCell cell2 = row2.createCell(columncount++);
            cell2.setCellValue(s);
            spreadsheet.autoSizeColumn(cell2.getColumnIndex());

            XSSFCell cell3 = row2.createCell(columncount++);
            cell3.setCellValue(l.getArticlenumber());
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
            cell22.setCellValue("(Y)");
            spreadsheet.autoSizeColumn(cell22.getColumnIndex());

            XSSFCell cell23 = row2.createCell(columncount++);
            cell23.setCellValue("(Y)");
            spreadsheet.autoSizeColumn(cell23.getColumnIndex());

            XSSFCell cell24 = row2.createCell(columncount++);
            cell24.setCellValue("(Y)");
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

        return salesInfo;

    }

    public String[] getShoppar() {
        return yldrStore.getShopsFromDatabase();
    }

    public Order[] getProductsFromOrdercontent(String ordernumber) throws SQLException {
        return yldrStore.getRowFromOrdercontent(ordernumber);
    }

    public void removeFromDB(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String realName, String rang, String squadNumber, String custom1, String custom2, String custom3) throws SQLException {
        calculateNewTotalAmount(orderNumber, articleNumber, size, nickname, countryFlag, realName, rang ,squadNumber, custom1, custom2, custom3);

        yldrStore.deleteRowFromOC(orderNumber, articleNumber, size, nickname, countryFlag, realName, rang ,squadNumber, custom1, custom2, custom3);

        if (yldrStore.getRowFromOrdercontent(orderNumber).length == 0) {
            yldrStore.deleteFromPO(orderNumber);
        }


    }

    public void calculateNewTotalAmount(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String realName, String rang, String squadNumber, String custom1, String custom2, String custom3) throws SQLException {
        float nyaTotalSumman;
        float nyaSkatteSumman;

        OrderCalculator oc = yldrStore.getPricingInfo(orderNumber, articleNumber, size, nickname, realName, countryFlag,rang, squadNumber, custom1, custom2, custom3);
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

