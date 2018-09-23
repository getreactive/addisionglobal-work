package io.acme.services

import java.text.SimpleDateFormat
import java.util.Date

import io.acme.Exceptions
import io.acme.interfaces.AsyncTokenService
import io.acme.models.{ Credentials, User, UserToken }

import scala.concurrent.Future

/**
  * Created by Rahul Kumar on 23/09/18.
  */

class SimpleAsyncTokenServiceImpl extends AsyncTokenService {
  private val dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
  override protected def authenticate(credentials: Credentials): Future[User] = {
    //        Thread.sleep(Random.nextInt(5000))
    if (credentials.password == credentials.username.toUpperCase)
      Future.successful(User(credentials.username))
    else
      Future.failed(Exceptions.INVALID_CREDENTIALS_EXCEPTION)
  }

  override protected def issueToken(user: User): Future[UserToken] = {
    //        Thread.sleep(Random.nextInt(5000))
    if (user.userId.startsWith("A"))
      Future.failed(Exceptions.TOKEN_GENERATION_FAILURE_EXCEPTION)
    else
      Future.successful(UserToken(s"${user.userId}_${dateFormatter.format(new Date())}"))
  }
}
