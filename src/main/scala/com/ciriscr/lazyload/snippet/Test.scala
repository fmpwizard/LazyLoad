package com.ciriscr.lazyload.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.{SHtml, S}
import net.liftweb.http.js._

class Test{

  //"Database"
  val objects = List.fill(30)("Random number: %s".format(math.random))
  val itemsPerLoad = 10
  var actual = 0

  private def nextPage: List[String] = this.objects.drop(this.actual).take(this.itemsPerLoad)

  def render = {
    "#elementsTable" #> (S.eagerEval andThen
      "@element *" #> nextPage.map{ elem =>
        "@number *" #> elem
      }
     &
      "#button [onclick]" #> SHtml.ajaxInvoke{ () =>
      this.actual += this.itemsPerLoad
      //Change this js to create a new table or rows, this isn't legal  html now, but you get the idea :)
      JsCmds.Run("""$("#table1").append("<p>""" + this.actual + """</p>")""")
    })
  } //render

}  //Test 
