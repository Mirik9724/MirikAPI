package com.example

import net.Mirik9724.api.copyFileFromJar
import net.Mirik9724.api.loadYmlFile
import net.Mirik9724.api.logInit
import net.Mirik9724.api.tryCreatePath
import org.junit.jupiter.api.Test
import java.io.File

class UsageTest {
    @Test
    fun setup() {
        val log = logInit("U.T.")
        log.info("Init test")

        val data = loadYmlFile("src/test/resources/data.yml") //this.javaClass.classLoader)
        log.info(data["key1"])
        log.info(data["k.key2"])

        tryCreatePath(File("scr/test/resources/TEST!!!"))
    }
}