package org.scalatra.example.forms

import org.scalatra._
import org.scalatra.i18n.I18nSupport
import org.scalatra.forms._
import org.scalatra.scalate.ScalateSupport

case class ValidationForm(text: String, email: Option[String], number: Option[Int], checkbox: Seq[String])

class FormsController extends ScalatraServlet with FormSupport with I18nSupport with ScalateSupport {

  val form = mapping(
    "text"     -> label("Text", text(required, maxlength(100))),
    "email"    -> label("Email", optional(text(pattern(".+@.+"), maxlength(20)))),
    "number"   -> label("Number", optional(number())),
    "checkbox" -> list(text())
  )(ValidationForm.apply)

  get("/") {
    contentType="text/html"
    ssp("/index")
  }

  post("/") {
    contentType="text/html"
    validate(form)(
      errors => BadRequest(ssp("/index")),
      form   => ssp("/result", "form" -> form)
    )
  }

}
