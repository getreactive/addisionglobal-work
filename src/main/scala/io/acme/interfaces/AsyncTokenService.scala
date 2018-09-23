package io.acme.interfaces

import io.acme.models.{ Credentials, User, UserToken }

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Rahul Kumar on 23/09/18.
  */

trait AsyncTokenService {
  protected def authenticate(credentials: Credentials): Future[User]
  protected def issueToken(user: User): Future[UserToken]

  def requestToken(credentials: Credentials): Future[UserToken] = for {
    user <- authenticate(credentials)
    userToken <- issueToken(user)
  } yield userToken
}
