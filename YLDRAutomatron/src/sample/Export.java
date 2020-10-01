package sample;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLOutput;
import java.util.Date;

public class Export {
    //public static void exportToExcel(String ordernumber, String articlenumber, String size, String nickname, String countryflag, Date date) {
    public static void main(String[] args) throws Exception {

        try {


        /*HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet spreadsheet = workbook.createSheet("Summary");

        HSSFRow row = null;*/

        XWPFDocument doc = new XWPFDocument();
        FileOutputStream out = new FileOutputStream(new File("C:\\Users\\poje2\\Downloads\\testfile.docx"));

        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Virgil van Dijk");
        doc.write(out);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
