package me.elenamakeeva.portMobiApps.repository

import me.elenamakeeva.portMobiApps.models.UserModel
import org.springframework.data.mongodb.repository.MongoRepository

interface UsersRepository : MongoRepository<UserModel, String>