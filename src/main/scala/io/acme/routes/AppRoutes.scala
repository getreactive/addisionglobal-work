package io.acme.routes

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging
import akka.http.scaladsl.server.Directives.{ concat, pathEnd, pathPrefix, _ }
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.post
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.pattern.ask
import akka.util.Timeout
import io.acme.actors.UserRegistryActor.GetToken
import io.acme.marshallers.AppJsonSupport
import io.acme.models.{ Credentials, UserToken }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.concurrent.duration._
import scala.util.{ Failure, Success }

/**
  * Created by Rahul Kumar on 23/09/18.
  */

trait AppRoutes extends AppJsonSupport {
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[AppRoutes])
  def userRegistryActor: ActorRef

  implicit lazy val timeout = Timeout(5.seconds)

  lazy val appRoutes: Route =
    pathPrefix("token") {
      concat(
        pathEnd {
          concat(
            post {
              entity(as[Credentials]) { credentials =>

                val pipeline = for {
                  token <- userRegistryActor.ask(GetToken(credentials)).mapTo[Promise[UserToken]]
                } yield token

                onComplete(pipeline) {
                  case Success(promise) =>
                    onComplete(promise.future) {
                      case Success(token) => complete(token)
                      case Failure(e) => failWith(e)
                    }
                  case Failure(e) => failWith(e)
                }
              }
            })
        })
    }
}
