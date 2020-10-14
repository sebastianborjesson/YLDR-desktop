import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) throws IOException, SQLException, ParseException {
      YLDRStore yldrStore=new YLDRStore();
        //yldrStore.getOrdrarFromDatabase();



        YLDR yldr=new YLDR();

        //yldr.laggTillProdukt("Pontus SKU","PontusProduktnenen");
        /*Timestamp startDatum=Timestamp.valueOf("2020-10-06 11:45:37");
        Timestamp slutDatum=Timestamp.valueOf("2020-10-07 06:10:16");*/


        //yldr.createWB("2020-10-06 15:37:14","2020-10-07 08:14:20");


        //yldrStore.getOrdrarFromDatabase(startDatum,slutDatum);
       //yldrStore.setToIsSent(yldrStore.getOrdrarFromDatabase("2020-10-06 11:45:37","2020-10-06 11:45:40"),"2020-10-06 11:45:37","2020-10-06 11:45:40");
      //yldrStore.getIsSentorNot();
        //yldrStore.getCustomerFromDatabase();*/



































    /*throws Exception {
    try{
        XSSFWorkbook wb=new XSSFWorkbook();
        XSSFSheet sh= wb.createSheet();
        XSSFPivotTable pivotTable = sh.createPivotTable(new AreaReference("A1:D4"), new CellReference("H5"));

        XSSFRow row=sh.createRow(0);
        XSSFCell cell=row.createCell(0);
        cell.setCellValue("Vad fan du vill, vad fan som helst");
        wb.write(new FileOutputStream(new File("C:\\Users\\46762\\Downloads\\testfile.xlsx")));
        Desktop.getDesktop().open(new File("C:\\Users\\46762\\Downloads\\testfile.xlsx"));
    }catch (Exception e){
        System.out.println(e);
    }

    }*/

}}