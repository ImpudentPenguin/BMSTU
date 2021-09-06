package me.elenamakeeva.portMobiApps.daos

import me.elenamakeeva.portMobiApps.models.InfoUserModel
import me.elenamakeeva.portMobiApps.networkmodels.IResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.ModelAndView

interface UserDao {

    fun profile(@PathVariable(value="username") username: String): ModelAndView
    fun changeProfile(): ModelAndView
    fun getUser(@PathVariable(value="value") value: String): ResponseEntity<IResponse>
    fun changeUser(@PathVariable(value="id") id: String, @RequestBody info: InfoUserModel): ResponseEntity<IResponse>
}