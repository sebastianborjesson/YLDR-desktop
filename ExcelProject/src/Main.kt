import org.apache.poi.xslf.usermodel.SlideLayout
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Paths

fun main(args: Array<String>) {
    printPOI()
    //This is an example for Microsoft Windows Operating System
    println("O======================== WRITE ========================O")
    createDocx("C:/Users/Public/Documents/document.docx", "Hello, world!")
    createXlsx("C:/Users/Public/Documents/workbook.xlsx", "Hello, world!")
    createPptx("C:/Users/Public/Documents/presentation.pptx", "Hello, world!")
    println("O======================================================O\n")
    println("O======================== READ ========================O")
    println("Output from reading docx file: "+readDocx("C:/Users/Public/Documents/document.docx"))
    println("Output from reading xlsx file: "+readXlsx("C:/Users/Public/Documents/workbook.xlsx"))
    println("Output from reading pptx file: "+readPptx("C:/Users/Public/Documents/presentation.pptx"))
    println("O======================================================O")
}

/**
 * This method is used to print Apache POI ASCII ART.
 */
fun printPOI(){
    println("     _                     _            ____   ___ ___ \n" +
            "    / \\   _ __   __ _  ___| |__   ___  |  _ \\ / _ \\_ _|\n" +
            "   / _ \\ | '_ \\ / _` |/ __| '_ \\ / _ \\ | |_) | | | | | \n" +
            "  / ___ \\| |_) | (_| | (__| | | |  __/ |  __/| |_| | | \n" +
            " /_/   \\_\\ .__/ \\__,_|\\___|_| |_|\\___| |_|    \\___/___|\n" +
            "         |_| ")
}

/**
 * This method is used to write simple docx file in a specific location.
 * @param path path where the file will be stored
 * Example in Microsoft Windows: "C:/Users/Public/Documents/document.docx"
 * Example in Unix-like OS: "/home/user/Documents/document.docx"
 * @param message message to be written in the file
 * Example: "Hello, world!"
 * @return Nothing.
 */
fun createDocx(path: String, message: String) {
    val paths = Paths.get(path)
    val fileName = paths.fileName
    val fileOutputStream = FileOutputStream(File(path))

    val document = XWPFDocument()
    val paragraph = document.createParagraph()
    val run = paragraph.createRun()
    run.setText(message)

    document.write(fileOutputStream)
    fileOutputStream.close()
    println("$fileName was successfully created and located in $path")
}

/**
 * This method is used to write simple xlsx file in a specific location.
 * @param path path where the file will be stored
 * Example in Microsoft Windows: "C:/Users/Public/Documents/workbook.xlsx"
 * Example in Unix-like OS: "/home/user/Documents/workbook.xlsx"
 * @param message message to be written in the file
 * Example: "Hello, world!"
 * @return Nothing.
 */
fun createXlsx(path: String, message: String) {
    val paths = Paths.get(path)
    val fileName = paths.fileName
    val fileOutputStream = FileOutputStream(File(path))

    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Sheet 1")
    val row = sheet.createRow(2)
    val cell = row.createCell(5)
    cell.setCellValue(message)

    workbook.write(fileOutputStream)
    fileOutputStream.close()
    println("$fileName was successfully created and located in $path")
}

/**
 * This method is used to write simple pptx file in a specific location.
 * @param path path where the file will be stored
 * Example in Microsoft Windows: "C:/Users/Public/Documents/presentation.pptx"
 * Example in Unix-like OS: "/home/user/Documents/presentation.pptx"
 * @param message message to be written in the file
 * Example: "Hello, world!"
 * @return Nothing.
 */
fun createPptx(path: String, message: String) {
    val paths = Paths.get(path)
    val fileName = paths.fileName
    val fileOutputStream = FileOutputStream(File(path))

    val slideShow = XMLSlideShow()
    val slideMaster = slideShow.slideMasters[0]
    val slideLayout = slideMaster.getLayout(SlideLayout.TITLE_ONLY)
    val slide = slideShow.createSlide(slideLayout)
    val title = slide.getPlaceholder(0)
    title.text = message

    slideShow.write(fileOutputStream)
    fileOutputStream.close()
    println("$fileName was successfully created and located in $path")
}

/**
 * This method is used to read simple docx file in a specific location.
 * @param path path where the file will be read
 * Example in Microsoft Windows: "C:/Users/Public/Documents/document.docx"
 * Example in Unix-like OS: "/home/user/Documents/document.docx"
 * @return String This returns text from file
 */
fun readDocx(path: String): String {
    var text = ""
    val fileInputStream = FileInputStream(File(path))
    val document = XWPFDocument(fileInputStream)
    val paragraphs = document.paragraphs
    for (paragraph in paragraphs) {
        text = paragraph.text
    }
    fileInputStream.close()
    return text
}

/**
 * This method is used to read simple xlsx file in a specific location.
 * @param path path where the file will be read
 * Example in Microsoft Windows: "C:/Users/Public/Documents/workbook.xlsx"
 * Example in Unix-like OS: "/home/user/Documents/workbook.xlsx"
 * @return String This returns text from file
 */
fun readXlsx(path: String): String {
    var text = ""
    val fileInputStream = FileInputStream(File(path))
    val workbook = XSSFWorkbook(fileInputStream)
    val rows = workbook.getSheetAt(0).getRow(2)
    for (row in rows){
        text = row.stringCellValue
    }
    fileInputStream.close()
    return text
}

/**
 * This method is used to read simple pptx file in a specific location.
 * @param path path where the file will be read
 * Example in Microsoft Windows: "C:/Users/Public/Documents/presentation.pptx"
 * Example in Unix-like OS: "/home/user/Documents/presentation.pptx"
 * @return String This returns text from file
 */
fun readPptx(path: String): String {
    var text = ""
    val fileInputStream = FileInputStream(File(path))
    val slideShow = XMLSlideShow(fileInputStream)
    val slides = slideShow.slides
    for (slide in slides){
        text = slide.getPlaceholder(0).text
    }
    fileInputStream.close()
    return text
}