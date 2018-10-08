package com.teamclicker.gameserver

import com.teamclicker.gameserver.framework.g_scheduler
import com.teamclicker.gameserver.framework.scheduler.Scheduler
import com.teamclicker.gameserver.framework.scheduler.Task
import com.teamclicker.gameserver.framework.scriptingengine.JsScriptingManager

fun main(args: Array<String>) {
    JsScriptingManager().also {
        it.loadGlobalTypes()
        it.loadTsTypeDefs()
        it.loadScripts()
    }

    g_scheduler = Scheduler().also {
        it.start()
    }

    g_scheduler.addEvent(Task {
        println("Now")
    })

    g_scheduler.addEvent(Task(2000) {
        println("Hello")
    })


    Thread.sleep(5000000)

}