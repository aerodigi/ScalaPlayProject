package controllers.api

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.{Apprentice, ApprenticeForm}
import play.api.data.FormError

import services.ApprenticeService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ApprenticeController @Inject()(
    cc: ControllerComponents,
    apprenticeService: ApprenticeService
) extends AbstractController(cc) {

    implicit val apprenticeFormat = Json.format[Apprentice]

    def getAll() = Action.async { implicit request: Request[AnyContent] =>
        apprenticeService.listAllItems map { items =>
          Ok(Json.toJson(items))
        }
      }

      def getById(id: Long) = Action.async { implicit request: Request[AnyContent] =>
        apprenticeService.getItem(id) map { item =>
          Ok(Json.toJson(item))
        }
      }

      def add() = Action.async { implicit request: Request[AnyContent] =>
        ApprenticeForm.form.bindFromRequest.fold(
          // if any error in submitted data
          errorForm => {
            errorForm.errors.foreach(println)
            Future.successful(BadRequest("Error!"))
          },
          data => {
            val newApprenticeItem = Apprentice(0, data.name, data.dob, data.course, data.dlc)
            apprenticeService.addItem(newApprenticeItem).map( _ => Redirect(routes.ApprenticeController.getAll))
          })
      }

      def update(id: Long) = Action.async { implicit request: Request[AnyContent] =>
        ApprenticeForm.form.bindFromRequest.fold(
          // if any error in submitted data
          errorForm => {
            errorForm.errors.foreach(println)
            Future.successful(BadRequest("Error!"))
          },
          data => {
            val apprenticeItem = Apprentice(id, data.name, data.dob, data.course, data.dlc)
            apprenticeService.updateItem(apprenticeItem).map( _ => Redirect(routes.ApprenticeController.getAll))
          })
      }

      def delete(id: Long) = Action.async { implicit request: Request[AnyContent] =>
        apprenticeService.deleteItem(id) map { res =>
          Redirect(routes.ApprenticeController.getAll)
        }
      }
}