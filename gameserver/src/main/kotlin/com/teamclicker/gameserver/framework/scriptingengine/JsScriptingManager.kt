package com.teamclicker.gameserver.framework.scriptingengine

import com.teamclicker.gameserver.framework.g_spells
import com.teamclicker.gameserver.framework.scriptingengine.spells.Spells
import cz.habarta.typescript.generator.*
import java.io.File
import javax.script.ScriptEngineManager

class JsScriptingManager {
    val config = JsScriptingConfig(ScriptEngineManager())

    fun loadGlobalTypes() {
    }

    fun loadTsTypeDefs() {
        generateEnumTypeDefs("data/enums.d.ts")
    }

    private fun generateEnumTypeDefs(fileName: String) {
        val input = Input.from(*config.enums.toTypedArray())
        val output = Output.to(File(fileName))
        val settings = Settings().also {
            it.outputKind = TypeScriptOutputKind.global
            it.jsonLibrary = JsonLibrary.jackson2
            it.mapEnum = EnumMapping.asEnum
            it.noFileComment = true
        }
        TypeScriptGenerator(settings).generateTypeScript(input, output)
    }

    fun loadScripts() {
        g_spells = Spells(config)
        try {
            g_spells.loadFromXml()
        } catch (e: Exception) {
            println("Unable to load spells!")
            throw e
        }
    }
}