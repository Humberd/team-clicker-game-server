package com.teamclicker.gameserver.framework.scriptingengine.spells

import com.teamclicker.gameserver.framework.scriptingengine.BaseEvents
import com.teamclicker.gameserver.framework.scriptingengine.JsScriptInterface
import java.util.*
import javax.script.ScriptEngineManager

class Spells(
    val scriptEngineManager: ScriptEngineManager
) : BaseEvents() {
    private val runes = TreeMap<Int, RuneSpell>()

    override fun getScriptBaseName() = "spells"

    override fun getEvent(): RuneSpell {
        return RuneSpell(JsScriptInterface(scriptEngineManager))
    }

    override fun registerEvent(event: RuneSpell) {
        runes.put(event.data.spellid, event)
    }

    override fun clear() {
        runes.clear()
    }
}