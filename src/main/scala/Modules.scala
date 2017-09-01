import java.io.{File, FileInputStream, FileNotFoundException, IOException}

import com.innovative.interactexcel.XLS
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook

import scala.util.Try
object Modules {

    /*PrintMetadata method of the Excel File takes file path as an input of the Excel File and prints the
     list of worksheets and the summary of all worksheets */

    def printMetadata(filePath: String) = {
        try {
            val xls = new XLS(filePath)
            Helper.printDashLine(4)
            xls.getSheetNames.foreach(sheetname => {
                println()
            })
            Helper.printDashLine(4)
            xls.getAllSheets.foreach(sheet => {
                println(sheet.getSheetName)
                println("Row Counts: " + sheet.getRowCounts())
                println("Column Counts: " + sheet.getColumnCounts())
                print("Data Types: ")
                sheet.getColumnDataTypes().foreach(t => {
                    print(t + " ")
                })
                println("\n")
            })
        }

        catch {
            case ex: FileNotFoundException => {
                println("File path is invalid")
            }
            case ex: IOException => {
                println("Could not load the excel file")
            }
        }
    }

    /* Readworksheet method prints the data in the table format with the summary. It takes the file path, worksheet number as in input and prints the
      Row Counts, Column Counts, Data Types of each column */

        // Reading sheet from the excel
                def readworksheet(filepath: String, sheetno: Int) = {
                    try {
                        //Helper.printDashLine(4)

                        val xls = new XLS(filepath)

                        // Head and getting the cell value
                        val sheet = xls.getSheet(sheetno)
                        Helper.printDashLine(sheet.getColumnCounts)
                        sheet.getHeader().getCellValues().foreach(cellValue => {
                            System.out.format("%-35s", Helper.wraptext(cellValue))
                            System.out.format("%-5s", "|")
                        })
                        println()
                        Helper.printDashLine(sheet.getColumnCounts)

                        // Tail

                        sheet.getTail().foreach((row) => {
                            row.getCellValues.foreach(cellValue => {
                                System.out.format("%-35s", Helper.wraptext(cellValue))
                                System.out.format("%-5s", "|")
                            })
                            println
                        })
                    }catch {
                        case ex1: FileNotFoundException => {
                            println("File path is invalid")
                        }
                        case ex2: IOException => {
                            println("Could not load the excel file")
                        }
                        case ex3: IllegalArgumentException => {
                            println("One of the arguments is incorrect.")

                        }
                        case ex4:IndexOutOfBoundsException => {
                            System.out.println("No arguments are given.\n")

                        }
                    }

                }


    /*readworksheetwitquery takes the following params as input (String filePath, int sheetIndex, int columnNum, char operator, String Operand)
     and filters the given worksheet, column number and the given condition and prints the output. */


                def readWorksheetWitQuery(filepath: String, workSheetNo: Int, columnNum: Int, operator: Char, operand: String) = {
                    val xls = new XLS(filepath)
                    val sheet = xls.getSheet(workSheetNo)

                    //Head of the query
                    Helper.printDashLine(sheet.getColumnCounts)
                    sheet.getHeader().getCellValues().foreach(cellValue => {
                        System.out.format("%-35s", Helper.wraptext(cellValue))
                        System.out.format("%-5s", "|")
                    })
                    println()
                    Helper.printDashLine(sheet.getColumnCounts)


                    //Tail of the query method
                    sheet.getTailByQuery(columnNum, operator, operand).foreach((row) => {
                        row.getCellValues.foreach(cellValue => {
                            System.out.format("%-35s", Helper.wraptext(cellValue))
                            System.out.format("%-5s", "|")
                        })
                        println
                    })
                    Helper.printDashLine(sheet.getColumnCounts)
                }

}
