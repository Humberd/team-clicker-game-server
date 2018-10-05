package com.teamclicker.gameserver.framework.scriptingengine.spells

import com.teamclicker.gameserver.framework.scriptingengine.BaseEvents
import com.teamclicker.gameserver.framework.scriptingengine.JsScriptInterface
import com.teamclicker.gameserver.framework.scriptingengine.JsScriptingConfig
import java.util.*

class Spells(
    private val scriptingConfig: JsScriptingConfig
) : BaseEvents() {
    private val runes = TreeMap<Int, RuneSpell>()

    override fun getScriptBaseName() = "spells"

    override fun getEvent(): RuneSpell {
        return RuneSpell(JsScriptInterface(scriptingConfig))
    }

    override fun registerEvent(event: RuneSpell) {
        runes.put(event.data.spellid, event)
    }

    override fun clear() {
        runes.clear()
    }
}