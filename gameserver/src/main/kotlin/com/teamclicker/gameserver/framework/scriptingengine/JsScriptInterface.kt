package com.teamclicker.gameserver.framework.scriptingengine

import javax.script.Invocable
import javax.script.ScriptEngine

class JsScriptInterface(scriptingConfig: JsScriptingConfig) {
    val engine: ScriptEngine

    init {
        engine = scriptingConfig.scriptEngineManager.getEngineByName("nashorn")

        scriptingConfig.enums.forEach(this::loadType)
    }

    fun loadFile(fileName: String) {
        //language=JavaScript
        engine.eval("load('$fileName')")
    }

    fun loadType(type: Class<*>) {
        val name = type.simpleName
        val fullName = type.typeName
        //language=JavaScript
        engine.eval("var $name = Java.type('$fullName')")
    }

    fun hasEvent(eventName: String): Boolean {
        return engine.context.getAttributesScope(eventName) != -1
    }

    fun invokeFunction(funName: String, vararg args: Any) {
        (engine as Invocable).invokeFunction(funName, *args)
    }
}