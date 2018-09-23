package io.acme.marshallers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import io.acme.models.{ Credentials, UserToken }
import spray.json.DefaultJsonProtocol

trait AppJsonSupport extends SprayJsonSupport {
  import DefaultJsonProtocol._
  implicit val userTokenJsonFormat = jsonFormat1(UserToken)
  implicit val credentialsJsonFormat = jsonFormat2(Credentials)
}
