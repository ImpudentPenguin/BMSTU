package me.elenamakeeva.portMobiApps.controllers

import me.elenamakeeva.portMobiApps.daos.AuthorizeDao
import me.elenamakeeva.portMobiApps.models.RegistrUserModel
import me.elenamakeeva.portMobiApps.models.UserModel
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
class AuthorizeController : AuthorizeDao {

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @GetMapping("registration")
    override fun registration(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "registration.html"
        return modelAndView
    }

    @PostMapping("api/v1/registr")
    override fun registration(@RequestBody user: RegistrUserModel): ResponseEntity<IResponse> {
        if (user.password != user.repeatedPassword)
            return ResponseEntity(ErrorModel(false, "Пароли не совпадают."), HttpStatus.BAD_REQUEST)

        val model = usersRepository.findAll().firstOrNull {
            it.login == user.login
        }

        return model?.let {
            ResponseEntity(ErrorModel(false, "Вы уже зарегистрированы в системе."), HttpStatus.NOT_FOUND)
        } ?: run {
            val userModel = UserModel(login = user.login, email = user.email, password = user.password)
            usersRepository.save(userModel)
            ResponseEntity(SuccessModel(true, userModel), HttpStatus.OK)
        }
    }

    @PostMapping("api/v1/login")
    override fun login(@RequestBody user: UserModel): ResponseEntity<IResponse> {
        val userModel = usersRepository.findAll().firstOrNull {
            it.email == user.email && it.password == user.password
        }

        return userModel?.let {
            ResponseEntity(SuccessModel(true, userModel), HttpStatus.OK)
        } ?: run {
            ResponseEntity(ErrorModel(false, "Вы не зарегистрированы в системе."), HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("login")
    override fun login(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "login.html"
        return modelAndView
    }
}