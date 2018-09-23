package io.acme.actors

import akka.actor.{ Actor, ActorLogging, Props }
import io.acme.models.Credentials
import io.acme.services.SimpleAsyncTokenServiceImpl

/**
  * Created by Rahul Kumar on 23/09/18.
  */

object UserRegistryActor {
  final case class GetToken(credentials: Credentials)
  def props: Props = Props[UserRegistryActor]
}

class UserRegistryActor extends Actor with ActorLogging {
  import UserRegistryActor._
  def tokenservice = new SimpleAsyncTokenServiceImpl
  override def receive: Receive = {
    case GetToken(credentials: Credentials) => {
      val userTokenCreated = tokenservice.requestToken(credentials)

      sender ! userTokenCreated

    }
  }
}
