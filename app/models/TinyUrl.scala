package models

/**
  * Created by ellenwong on 2/11/17.
  */
case class TinyUrl (originalLongUrl: String, url_id: Int) {

  val alphanumericArray = Array("0","1","2","3", "4","5","6","7","8","9","A", "B", "C", "D", "E", "F") //TODO: fill in ABC.. and
  def getTinyUrl() = {
    // convert url_id into base 62 charaters

    // abc....ABC...012..9

    // to simply, do a int to hex convertion first.
    //e.g if Int is 1 , url would be "0"
    //e.g if Int is 10, url would be "A"
    //e.g if Int is 16, url would be "00"

    //***
    // For base 10: if Int is 32, url would be "" //22% 10 = 2,    32/10 = 3
    //TODO: fix this
    // first find out how many power to the 16 you need
    // then divide by one power less each time to get each charater
    // https://cboard.cprogramming.com/cplusplus-programming/23177-convert-decimal-int-hex.html

    var url_id_RemainingValue = url_id
    var tinyUrlStr = ""//url_id_RemainingValue % alphanumericArray.length

    while(url_id_RemainingValue > 0) {

      //val dividedResult = url_id_RemainingValue / alphanumericArray.length

      val dividedResult = url_id_RemainingValue % alphanumericArray.length
      if (dividedResult > 0) {
        tinyUrlStr = alphanumericArray(dividedResult) + tinyUrlStr //Bug: dividedResult could be 0, causing a -1 as index, 16 should be "10" .. fix it later??
        url_id_RemainingValue = url_id_RemainingValue / alphanumericArray.length
      } else {
        tinyUrlStr = alphanumericArray(0) + tinyUrlStr //????? not sure hhaha
      }

    }
    //?

    tinyUrlStr
  }

}

