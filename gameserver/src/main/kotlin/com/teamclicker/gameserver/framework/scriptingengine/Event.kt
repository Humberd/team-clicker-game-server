package com.teamclicker.gameserver.framework.scriptingengine

import javax.xml.stream.XMLStreamReader

abstract class Event(
    protected val scriptInterface: JsScriptInterface
) {

    abstract fun configureEvent(sr: XMLStreamReader)

    fun invokeEvent(vararg args: Any) {
        scriptInterface.invokeFunction(getScriptEventName(), *args)
    }

    abstract fun getScriptEventName(): String

    fun loadLibrary(path: String) {
        scriptInterface.loadFile(path)
    }

    fun loadScript(path: String) {
        scriptInterface.loadFile(path)
        if (!scriptInterface.hasEvent(getScriptEventName())) {
            throw Exception("Event ${getScriptEventName()} does not exist")
        }
    }
}