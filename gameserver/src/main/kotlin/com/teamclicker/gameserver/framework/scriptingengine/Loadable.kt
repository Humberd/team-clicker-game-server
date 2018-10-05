package com.teamclicker.gameserver.framework.scriptingengine

interface Loadable {
    fun loadFromXml()
    fun reload()
    fun isLoaded(): Boolean
}