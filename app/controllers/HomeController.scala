package controllers

import javax.inject._

import models.TinyUrl
import play.api._
import play.api.mvc._

import scala.collection.mutable

/*
* How to try this:
* http://localhost:9000/dumpAllUrl
* http://localhost:9000/tinyurl?url=www.google.com
* */

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  val tinyUrlMap = new mutable.HashMap[String, TinyUrl]()
  var nextId = 1

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready. From Ellen"))
  }

  def dumpAllUrl = Action {
    var urlStr = ""
    tinyUrlMap.foreach(entry => urlStr = urlStr + entry + ",")
    Ok(views.html.index1(s"tinyUrl Map Dump: \n $urlStr"))
  }

  def tinyurl = Action { implicit request =>
    val urlListOpt = request.queryString.get("url")
    val url = if (urlListOpt.isDefined) {
      urlListOpt.get.head
    } else {
      "EMPTY_STRING"
    }

    val tinyUrl: TinyUrl = if (tinyUrlMap.contains(url)) {
      tinyUrlMap.get(url).get
    } else {
      println(s"put $url into tinyUrlMap, nextId: ${nextId}")
      tinyUrlMap.put(url, TinyUrl(url, nextId))
      nextId = nextId + 1
      tinyUrlMap.get(url).get
    }

    Ok(views.html.index1(s"tinyUrl for http://$url is \n\t http://${tinyUrl.getTinyUrl()}"))
  }

  //TODO: can you recover the original url from tiny url?

  /*Logic:
  * (1) Get original url via get params
  * (2) Store url into DB and assign a integer as url_id
  * (3) convert integer into a base 62 (26+26+10) representation of a tiny url
  * (4) return the converted tinyurl
  * */

}
