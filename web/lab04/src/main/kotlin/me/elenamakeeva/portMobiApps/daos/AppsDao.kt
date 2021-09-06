package me.elenamakeeva.portMobiApps.daos

import me.elenamakeeva.portMobiApps.models.AppModel
import me.elenamakeeva.portMobiApps.models.InfoAppModel
import me.elenamakeeva.portMobiApps.networkmodels.IResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.ModelAndView

interface AppsDao {

    fun getApps(): ResponseEntity<IResponse>
    fun changeApp(@PathVariable(value="id") id: String, @RequestBody appModel: InfoAppModel): ResponseEntity<IResponse>
    fun getAppsById(@PathVariable(value = "userId") userId: String): ResponseEntity<IResponse>
    fun changeApp(@PathVariable(value = "id") id: String): ModelAndView
    fun uploadApp(@RequestBody appModel: AppModel): ResponseEntity<IResponse>
    fun uploadApp(): ModelAndView
    fun deleteApp(@PathVariable(value="id") id: String): ResponseEntity<IResponse>
}