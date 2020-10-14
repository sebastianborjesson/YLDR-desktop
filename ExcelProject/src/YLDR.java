import org.apache.poi.ss.usermodel.Cell;
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
import java.util.Date;

public class YLDR {


    YLDRStore yldrStore = new YLDRStore();

    Workbook workbook;

    public void createRows(String startDatum, String slutDatum, Order[] ordrar) {
        XSSFSheet spreadsheet = (XSSFSheet) workbook.createSheet("Summary");
        createSheets(startDatum, slutDatum);
        createHeaderRow(spreadsheet);

        int rowCount = 1;
        String ordernumber=ordrar[0].getOrderNumber();
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
            cell.setCellValue(o.getSize());

            ordernumber=o.getOrderNumber();
            spreadsheet.autoSizeColumn(cell.getColumnIndex());
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
        cell3.setCellValue("Size");
        return row;
    }

    public void createWB(String startDatum, String slutDatum) throws IOException, SQLException {
        Order[] ordrar = new Order[yldrStore.getOrdrarFromDatabase(startDatum, slutDatum).length];
        System.arraycopy(yldrStore.getOrdrarFromDatabase(startDatum, slutDatum), 0, ordrar, 0, ordrar.length);

        //checkDateIntervals(startDatum,slutDatum);


        String path = System.getProperty("user.home") + "\\Fuch " + LocalDate.now(
                ZoneId.of("Europe/Stockholm")) + ".xlsx";
        File file = new File(path);

        if (!file.exists()) {
            workbook=new XSSFWorkbook();
            createRows(startDatum, slutDatum, ordrar);
            try (OutputStream fileOut = new FileOutputStream(new File(String.valueOf(file)))) {
                workbook.write(fileOut);
                Desktop.getDesktop().open(new File(String.valueOf(file)));
            }
        } else if (file.exists()) {

            workbook=new XSSFWorkbook();
            for (int i = 1; file.exists(); i++) {
                file = new File(String.format(System.getProperty("user.home") + "\\Fuch " + LocalDate.now(ZoneId.of("Europe/Stockholm")) + " (%d)" + ".xlsx", i));
            }
            createRows(startDatum, slutDatum, ordrar);

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


        yldrStore.setToIsSent(ordrar, startDatum, slutDatum);


    }

    public void createSheets(String startDatum, String slutDatum) {
        String[] SKU = new String[yldrStore.getDistinctSKU(startDatum, slutDatum).length];
        System.arraycopy(yldrStore.getDistinctSKU(startDatum, slutDatum), 0, SKU, 0, SKU.length);

        String articlenumber=SKU[0];


        for (String s : yldrStore.getDistinctSKU(startDatum,slutDatum)) {
            XSSFSheet spreadsheet = (XSSFSheet) workbook.createSheet(s);
            createHeaderRow(spreadsheet);
            int rowCount = 1;
            if (!s.equals(articlenumber)){
                XSSFRow separationRow = spreadsheet.createRow(rowCount++);
            }


          /*  Timestamp startDatum=Timestamp.valueOf("2020-10-06 11:45:37");
            Timestamp slutDatum=Timestamp.valueOf("2020-10-07 06:10:16");*/
            for (Order o : yldrStore.getOrdrarFromDatabase(startDatum, slutDatum)) {

                int columncount = 0;
                //XSSFCell headerCell=createHeaderRow(spreadsheet).createCell(columncount++);

                if (o.getArticleNumber().equals(s)) {
                    XSSFRow row2 = spreadsheet.createRow(rowCount++);

                    XSSFCell cell = row2.createCell(columncount++);
                    cell.setCellValue(o.getOrderNumber());
                    spreadsheet.autoSizeColumn(cell.getColumnIndex());

                    cell = row2.createCell(columncount++);
                    cell.setCellValue(o.getArticleNumber());
                    spreadsheet.autoSizeColumn(cell.getColumnIndex());

                    cell = row2.createCell(columncount++);
                    cell.setCellValue(o.getSize());
                    spreadsheet.autoSizeColumn(cell.getColumnIndex());

                    articlenumber=s;
                }
            }


        }


    }

    public void laggTillProdukt(String SKU, String productName, String tillverkare) {
        yldrStore.addProduct(SKU, productName, tillverkare);
    }

}
