package io.acme

import akka.actor.ActorRef
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.acme.actors.UserRegistryActor
import io.acme.models.Credentials
import io.acme.routes.AppRoutes
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ Matchers, WordSpec }

class AppRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest
  with AppRoutes {
  override val userRegistryActor: ActorRef =
    system.actorOf(UserRegistryActor.props, "userRegistry")

  lazy val routes = appRoutes

  "AppRoutes" should {
    "be able to get user token (POST /token)" in {
      val credentials = Credentials("Rahul", "RAHUL")
      val tokenEntity = Marshal(credentials).to[MessageEntity].futureValue

      val request = Post("/token").withEntity(tokenEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should include regex (credentials.username)
      }
    }
  }
}
