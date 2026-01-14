package net.Mirik9724.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory

lateinit var log: Logger

fun logInit(logName: String) {
    log = LoggerFactory.getLogger(logName)
}
