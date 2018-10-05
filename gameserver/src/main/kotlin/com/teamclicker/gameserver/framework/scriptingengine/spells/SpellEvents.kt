package com.teamclicker.gameserver.framework.scriptingengine.spells

import com.teamclicker.gameserver.framework.scriptingengine.BaseEvents
import com.teamclicker.gameserver.framework.scriptingengine.JsScriptInterface
import javax.script.ScriptEngineManager

class SpellEvents(
    val scriptEngineManager: ScriptEngineManager
) : BaseEvents() {
    override fun getScriptInterface() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getScriptBaseName() = "spells"

    override fun getEvent(): RuneSpell {
        return RuneSpell(JsScriptInterface(scriptEngineManager))
    }

    override fun registerEvent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}