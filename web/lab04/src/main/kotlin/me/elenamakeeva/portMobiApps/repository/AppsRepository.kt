package me.elenamakeeva.portMobiApps.repository

import me.elenamakeeva.portMobiApps.models.AppModel
import org.springframework.data.mongodb.repository.MongoRepository

interface AppsRepository: MongoRepository<AppModel, String> {

    fun findAllAppsByUserId(userId: String): List<AppModel>
}