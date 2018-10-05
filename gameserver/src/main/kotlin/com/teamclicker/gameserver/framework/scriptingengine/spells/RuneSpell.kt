package com.teamclicker.gameserver.framework.scriptingengine.spells

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.teamclicker.gameserver.framework.scriptingengine.Event
import com.teamclicker.gameserver.framework.scriptingengine.JsScriptInterface
import com.teamclicker.gameserver.framework.scriptingengine.spells.data.RuneXml
import javax.xml.stream.XMLStreamReader

class RuneSpell(
    scriptInterface: JsScriptInterface
): Event(scriptInterface) {
    lateinit var data: RuneXml

    override fun configureEvent(sr: XMLStreamReader) {
        val mapper = XmlMapper()

        data = mapper.readValue(sr, RuneXml::class.java)
    }

    override fun getScriptEventName() = "onCastSpell"
}