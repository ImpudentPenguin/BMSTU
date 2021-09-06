package me.elenamakeeva.portMobiApps.daos

import me.elenamakeeva.portMobiApps.models.RegistrUserModel
import me.elenamakeeva.portMobiApps.networkmodels.IResponse
import me.elenamakeeva.portMobiApps.models.UserModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.ModelAndView

interface AuthorizeDao {

    fun login(): ModelAndView
    fun registration(): ModelAndView
    fun login(@RequestBody user: UserModel): ResponseEntity<IResponse>
    fun registration(@RequestBody user: RegistrUserModel): ResponseEntity<IResponse>
}