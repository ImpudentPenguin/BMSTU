package me.elenamakeeva.modeling.models

enum class EventType {
    GENERATOR,
    APPARATUS
}

data class Event(
    var type: EventType,
    var value: Double
)