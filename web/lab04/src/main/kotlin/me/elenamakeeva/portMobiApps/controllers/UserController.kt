package me.elenamakeeva.portMobiApps.controllers

import me.elenamakeeva.portMobiApps.daos.UserDao
import me.elenamakeeva.portMobiApps.models.InfoUserModel
import me.elenamakeeva.portMobiApps.networkmodels.ErrorModel
import me.elenamakeeva.portMobiApps.networkmodels.IResponse
import me.elenamakeeva.portMobiApps.networkmodels.SuccessModel
import me.elenamakeeva.portMobiApps.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@RestController
class UserController : UserDao {

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @GetMapping("profile/{username}")
    override fun profile(@PathVariable(value = "username") username: String): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "profile.html"
        return modelAndView
    }

    @GetMapping("editprofile")
    override fun changeProfile(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "editProfile.html"
        return modelAndView
    }

    @GetMapping("api/v1/user/{value}")
    override fun getUser(@PathVariable(value = "value") value: String): ResponseEntity<IResponse> {
        val model = usersRepository.findAll().firstOrNull {
            it.id == value || it.login.equals(value, true)
        }

        return model?.let {
            ResponseEntity(SuccessModel(true, model), HttpStatus.OK)
        } ?: run {
            ResponseEntity(ErrorModel(false, "Пользователь не найден."), HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("api/v1/user/{id}")
    override fun changeUser(@PathVariable(value = "id") id: String, @RequestBody info: InfoUserModel): ResponseEntity<IResponse> {
        return try {
            val model = usersRepository.findById(id).get()
            if (!info.email.isNullOrEmpty())
                model.email = info.email

            if (!info.about.isNullOrEmpty())
                model.about = info.about

            if (!info.telegram.isNullOrEmpty())
                model.telegram = info.telegram

            usersRepository.save(model)
            ResponseEntity(SuccessModel(true, model), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity(ErrorModel(false, "Пользователь не найден."), HttpStatus.NOT_FOUND)
        }
    }
}