package com.teamclicker.gameserver.framework.scriptingengine.spells

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.teamclicker.gameserver.framework.scriptingengine.JsScriptInterface
import com.teamclicker.gameserver.framework.scriptingengine.spells.data.RuneXml
import javax.xml.stream.XMLStreamReader

class RuneSpell(
    val scriptInterface: JsScriptInterface
) {
    lateinit var data: RuneXml

    fun configureEvent(sr: XMLStreamReader) {
        val mapper = XmlMapper()

        data = mapper.readValue(sr, RuneXml::class.java)
    }

    fun getScriptEventName() = "onCastSpell"

    fun loadLibrary(path: String) {
        scriptInterface.loadFile(path)
    }

    fun loadScript(path: String) {
        scriptInterface.loadFile(path)
        if (!scriptInterface.hasEvent(getScriptEventName())) {
            throw Exception("Event ${getScriptEventName()} does not exist")
        }
    }

    fun invokeEvent(vararg args: Any) {
        scriptInterface.invokeFunction(getScriptEventName(), args)
    }


}