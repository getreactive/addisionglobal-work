package io.acme.interfaces

import io.acme.models.{ Credentials, User, UserToken }

trait SyncTokenService {
  protected def authenticate(credentials: Credentials): Option[User]
  protected def issueToken(user: User): Option[UserToken]

  def requestToken(credentials: Credentials): Option[UserToken] = for {
    user <- authenticate(credentials)
    userToken <- issueToken(user)
  } yield userToken
}
