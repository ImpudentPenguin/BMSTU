package me.elenamakeeva.portMobiApps.models

import lombok.Data
import org.springframework.data.mongodb.core.mapping.Document

@Data
@Document(collection="apps")
data class AppModel(
    var id: String? = null,
    var author: String,
    var userId: String,
    var name: String,
    var info: String,
    var github: String,
    var link: String,
    var qrCode: String,
    var tags: String
)