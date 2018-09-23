package io.acme.interfaces

import io.acme.models.{ Credentials, UserToken }

import scala.concurrent.Future

trait SimpleAsyncTokenService {
  def requestToken(credentials: Credentials): Future[UserToken]
}
