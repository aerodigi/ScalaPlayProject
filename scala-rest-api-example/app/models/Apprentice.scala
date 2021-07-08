package models
import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

case class Apprentice(id: Long, name: String, dob: String, course: String, dlc: String)

case class ApprenticeFormData(name: String, dob: String, course: String, dlc: String)

object ApprenticeForm {
  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "dob" -> nonEmptyText,
      "course" -> nonEmptyText,
      "dlc" -> nonEmptyText
    )(ApprenticeFormData.apply)(ApprenticeFormData.unapply)
  )
}

class ApprenticeTableDef(tag: Tag) extends Table[Apprentice](tag, "apprentice") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def dob = column[String]("dob")
  def course = column[String]("course")
  def dlc = column[String]("dlc")

  override def * = (id, name, dob, course, dlc) <> (Apprentice.tupled, Apprentice.unapply)
}


class ApprenticeList @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  var ApprenticeList = TableQuery[ApprenticeTableDef]

  def add(ApprenticeItem: Apprentice): Future[String] = {
    dbConfig.db
      .run(apprenticeList += apprentice)
      .map(res => "ApprenticeItem successfully added")
      .recover {
        case ex: Exception => {
            printf(ex.getMessage())
            ex.getMessage
        }
      }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(apprenticeList.filter(_.id === id).delete)
  }

  def update(apprenticeItem: Apprentice): Future[Int] = {
    dbConfig.db
      .run(apprenticeList.filter(_.id === apprenticeItem.id)
            .map(x => (x.name, x.dob, x.course, x.dlc))
            .update(apprenticeItem.name, apprenticeItem.dob, apprenticeItem.course, apprenticeItem.dlc)
      )
  }

  def get(id: Long): Future[Option[Apprentice]] = {
    dbConfig.db.run(apprenticeList.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[Apprentice]] = {
    dbConfig.db.run(apprenticeList.result)
  }
}