package services

import com.google.inject.Inject
import models.{Apprentice, ApprenticeList}

import scala.concurrent.Future

class ApprenticeService @Inject() (items: ApprenticeList) {

  def addItem(item: Apprentice): Future[String] = {
    items.add(item)
  }

  def deleteItem(id: Long): Future[Int] = {
    items.delete(id)
  }

  def updateItem(item: Apprentice): Future[Int] = {
    items.update(item)
  }

  def getById(id: Long): Future[Option[Apprentice]] = {
    items.get(id)
  }

  def listAllItems: Future[Seq[Apprentice]] = {
    items.listAll
  }
}