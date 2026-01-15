package net.Mirik9724.api

import java.io.File

/**
 * Attempts to create a path
 * @param Path
 */
fun tryCreatePath(Path: File){
    if(!Path.exists()){
        Path.mkdirs()
    }
}