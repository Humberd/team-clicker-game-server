package com.teamclicker.gameserver.framework.scriptingengine

import com.teamclicker.gameserver.framework.scriptingengine.spells.RuneSpell
import com.teamclicker.gameserver.framework.scriptingengine.spells.SpellEvents
import java.io.File
import java.io.FileInputStream
import javax.script.ScriptEngineManager
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamReader

abstract class BaseEvents : Loadable {
    protected var loaded = false
    var scripted = false
        private set

    abstract fun getScriptInterface()
    abstract fun getScriptBaseName(): String
    abstract fun getEvent(): RuneSpell
    abstract fun registerEvent()
    abstract fun clear()

    override fun loadFromXml() {
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
            event.invokeEvent()
        }
        sr.close()
    }

    override fun reload() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isLoaded(): Boolean {
        return loaded
    }
}

fun main(args: Array<String>) {
    SpellEvents(ScriptEngineManager()).loadFromXml()
}