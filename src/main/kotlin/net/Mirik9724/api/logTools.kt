package net.Mirik9724.api

import org.slf4j.LoggerFactory
import org.slf4j.Logger

fun logInit(logName: String): Logger {
    return LoggerFactory.getLogger(logName)
}
