package com.teamclicker.gameserver.framework.scriptingengine

import com.teamclicker.gameserver.framework.scriptingengine.spells.RuneSpell
import java.io.File
import java.io.FileInputStream
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamReader


abstract class BaseEvents {
    protected var loaded = false

    abstract fun getScriptBaseName(): String
    abstract fun getEvent(): RuneSpell
    abstract fun registerEvent(event: RuneSpell)
    abstract fun clear()

    fun loadFromXml() {
        if (isLoaded()) {
            throw Exception("It's already loaded.")
        }

        val scriptsName = getScriptBaseName()
        val basePath = "data/$scriptsName"
        val xmlFileName = "$basePath/$scriptsName.xml"
        val libFileName = "$basePath/lib/$scriptsName.js"
        val isLibAvailable = File(libFileName).isFile

        val sr = try {
            XMLInputFactory.newFactory().createXMLStreamReader(FileInputStream(xmlFileName))
        } catch (e: Exception) {
            throw Exception("Cannot load $xmlFileName", e)
        }


        sr.next()
        while (true) {
            val eventType = sr.nextTag()
            if (eventType == XMLStreamReader.END_ELEMENT) {
                break
            }

            val event = getEvent()
            event.configureEvent(sr)

            if (isLibAvailable) {
                event.loadLibrary(libFileName)
            }

            val scriptFileName = "$basePath/scripts/${event.data.script}"
            event.loadScript(scriptFileName)
        }
        sr.close()

        loaded = true
    }

    fun reload() {
        loaded = false
        clear()
        loadFromXml()
    }

    fun isLoaded(): Boolean {
        return loaded
    }
}
