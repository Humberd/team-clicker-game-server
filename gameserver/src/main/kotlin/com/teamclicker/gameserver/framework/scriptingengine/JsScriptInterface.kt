package com.teamclicker.gameserver.framework.scriptingengine

import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class JsScriptInterface(scriptEngineManager: ScriptEngineManager) {
    val engine: ScriptEngine
    init {
        engine = scriptEngineManager.getEngineByName("nashorn")
    }

    fun loadFile(fileName: String) {
        //language=JavaScript
        engine.eval("load('$fileName')")
    }

    fun hasEvent(eventName: String): Boolean {
        return engine.context.getAttributesScope(eventName) != -1
    }

    fun invokeFunction(funName: String, vararg args: Any) {
        (engine as Invocable).invokeFunction(funName, args)
    }
}