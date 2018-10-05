package com.teamclicker.gameserver.framework.scriptingengine

import javax.script.ScriptEngineManager

class JsScriptingConfig(
    val scriptEngineManager: ScriptEngineManager
) {

    val enums = ArrayList<Class<*>>()
}