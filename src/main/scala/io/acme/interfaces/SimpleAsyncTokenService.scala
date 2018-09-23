package io.acme.interfaces

import io.acme.models.{ Credentials, UserToken }

import scala.concurrent.Future

/**
 * Created by Rahul Kumar on 23/09/18.
 */

trait SimpleAsyncTokenService {
  def requestToken(credentials: Credentials): Future[UserToken]
}
