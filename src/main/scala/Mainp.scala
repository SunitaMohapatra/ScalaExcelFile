//package scopt
import java.io.File

import jdk.nashorn.internal.runtime.regexp.joni.Config
import scopt._

object Mainp extends App{
  val version = "0.1"

  case class Config (
                      mode: String = "",
                      submode: String = "",
                      filepath: String = "",
                      worksheetNo: Int = 0,
                      columnNo : Int = 0,
                      operator: Char = '=',
                      operand: String = ""
                    )

  val parser = new scopt.OptionParser[Config]("") {
    head("Excel file reader", Mainp.version)

    //Command with filepath options for getting the metadata

    cmd("metadata") action ((_, c) => c.copy(mode = "metadata")) children (
      opt[String]("f") required() action((x, c) => c.copy(filepath = x)) text("filepath is a required  property")
    ) //text("metadata - Displaying Metadata")

    //command with filepath and worksheet option to get the sheet information

    cmd("displaysheet") action ((_, c) => c.copy(mode = "displaysheet")) children (
      opt[String]("f") required() action((x, c) => c.copy(filepath = x)) text("filepath is a required property"),
      opt[Int]("w") required() action((x, c) => c.copy(worksheetNo = x)) text("worksheet is a required file property")

      ) //text("Displaysheet - Displaying Sheetdata")

    cmd("querysheet") action ((_, c) => c.copy(mode = "querysheet")) children (
      opt[String]("f") required() action((x, c) => c.copy(filepath = x)) text("filepath is a required property"),
      opt[Int]("w") required() action((x, c) => c.copy(worksheetNo = x)) text("worksheet is a required file property"),

        opt[Int]("c") required() action((x, c) => c.copy(columnNo = x,submode = "without")) text("Column Number is required"),
      opt[Char]("opr") required() action((x, c) => c.copy(operator = x,submode = "without")) text("Operator is required"),
      opt[String]("opd")required() action((x, c) => c.copy(operand = x,submode = "without")) text("Operand is required")
    ) //text("Query Expression Serach - Displaying result")


    help("help") text(" Check usage")
  }

  parser.parse(args, Config()) match {
    case Some(config) => {
      println(config.mode)
      if(config.mode=="metadata") Modules.printMetadata(config.filepath)
      if(config.mode=="displaysheet") Modules.readworksheet(config.filepath,config.worksheetNo)
      if(config.mode=="querysheet") Modules.readWorksheetWitQuery(config.filepath,config.worksheetNo,config.columnNo,config.operator,config.operand)
    }
    case _ => println("Please use --help argument for usage")
  }



}

