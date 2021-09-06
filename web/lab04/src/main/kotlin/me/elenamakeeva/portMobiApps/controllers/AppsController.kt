package me.elenamakeeva.portMobiApps.controllers

import me.elenamakeeva.portMobiApps.daos.AppsDao
import me.elenamakeeva.portMobiApps.models.AppModel
import me.elenamakeeva.portMobiApps.models.InfoAppModel
import me.elenamakeeva.portMobiApps.networkmodels.ErrorModel
import me.elenamakeeva.portMobiApps.networkmodels.IResponse
import me.elenamakeeva.portMobiApps.networkmodels.SuccessModel
import me.elenamakeeva.portMobiApps.repository.AppsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.lang.NullPointerException

@RestController
class AppsController : AppsDao {

    @Autowired
    private lateinit var appsRepository: AppsRepository

    @GetMapping("api/v1/apps/{userId}")
    override fun getAppsById(@PathVariable(value = "userId") userId: String): ResponseEntity<IResponse> {
        val models = appsRepository.findAllAppsByUserId(userId)

        return ResponseEntity(SuccessModel(true, models), HttpStatus.OK)
    }

    @GetMapping("api/v1/apps")
    override fun getApps(): ResponseEntity<IResponse> {
        val models = appsRepository.findAll()

        return ResponseEntity(SuccessModel(true, models), HttpStatus.OK)
    }

    @PutMapping("api/v1/apps/{id}")
    override fun changeApp(@PathVariable(value = "id") id: String, @RequestBody appModel: InfoAppModel): ResponseEntity<IResponse> {
        return try {
            val model = appsRepository.findById(id).get()

            if (appModel.name?.isNotEmpty() == true)
                appModel.name?.let { model.name = it }

            if (appModel.info?.isNotEmpty() == true)
                appModel.info?.let { model.info = it }

            if (appModel.github?.isNotEmpty() == true)
                appModel.github?.let { model.github = it }

            if (appModel.link?.isNotEmpty() == true)
                appModel.link?.let { model.link = it }

            if (appModel.qrCode?.isNotEmpty() == true)
                appModel.qrCode?.let { model.qrCode = it }

            if (appModel.tags?.isNotEmpty() == true)
                appModel.tags?.let { model.tags = it }

            appsRepository.save(model)
            ResponseEntity(SuccessModel(true, "Приложение обновлено."), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity(ErrorModel(false, "Изменение невозможно. Приложение не найдено."), HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("changeapp/{id}")
    override fun changeApp(@PathVariable(value = "id") id: String): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "changeApp.html"
        return modelAndView
    }

    @GetMapping("uploadapp")
    override fun uploadApp(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "uploadApp.html"
        return modelAndView
    }

    @PostMapping("api/v1/apps")
    override fun uploadApp(@RequestBody appModel: AppModel): ResponseEntity<IResponse> {
        return try {
            appsRepository.save(appModel)
            ResponseEntity(SuccessModel(true, "Приложение успешно сохранено."), HttpStatus.OK)
        } catch(e: NullPointerException) {
            ResponseEntity(ErrorModel(false, "Кажется, вы заполнили не все поля."), HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("api/v1/apps/{id}")
    override fun deleteApp(@PathVariable(value = "id") id: String): ResponseEntity<IResponse> {
        return try {
            appsRepository.findById(id).get()
            appsRepository.deleteById(id)
            ResponseEntity(SuccessModel(true, "Приложение успешно удалено."), HttpStatus.OK)
        } catch(e: NoSuchElementException) {
            ResponseEntity(ErrorModel(false, "Приложение не найдено."), HttpStatus.NOT_FOUND)
        }
    }
}