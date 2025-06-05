package net.Mirik9724.api.net.Mirik9724.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory

lateinit var logger_: Logger

fun LogInit(logName: String) {
    logger_ = LoggerFactory.getLogger(logName)
}
