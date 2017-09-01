import java.util
import java.util.Iterator

import org.apache.poi.ss.usermodel.{Cell, Row, Sheet, Workbook}

object Helper {

  //helper method to display the dashline.

  def printDashLine(columnCounts: Int) {
    val i = columnCounts * 40
    for (i <- 1 to i) print("-")
    println
  }

  //Helper method to wrap the text.

  def wraptext(str: String): String = {
    val FIXED_WIDTH = true
    var temp = ""
    if (str != null && str.length > 30) temp = str.substring(0, 29) + "..."
    else temp = str
    temp
  }


}


