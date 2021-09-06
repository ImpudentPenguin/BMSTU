package me.elenamakeeva.portMobiApps.models

import lombok.Data
import org.springframework.data.mongodb.core.mapping.Document

@Data
@Document(collection="users")
data class UserModel(
    var id: String? = null,
    var login: String? = null,
    var email: String,
    val password: String,
    var about: String? = null,
    var telegram: String? = null
)
