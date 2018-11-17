package com.teamclicker.gameserver

import com.teamclicker.gameserver.framework.scriptingengine.JsScriptingManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class Main

fun main(args: Array<String>) {
    runApplication<Main>(*args)

    JsScriptingManager().also {
        it.loadGlobalTypes()
        it.loadTsTypeDefs()
        it.loadScripts()
    }
}