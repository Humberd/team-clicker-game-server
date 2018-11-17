package com.teamclicker.gameserver

import com.teamclicker.gameserver.framework.scriptingengine.JsScriptingManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Main

fun main(args: Array<String>) {
    runApplication<Main>(*args)

    JsScriptingManager().also {
        it.loadGlobalTypes()
        it.loadTsTypeDefs()
        it.loadScripts()
    }
}