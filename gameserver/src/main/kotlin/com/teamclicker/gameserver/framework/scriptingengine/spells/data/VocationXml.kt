package com.teamclicker.gameserver.framework.scriptingengine.spells.data

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "vocation")
class VocationXml {
    @JacksonXmlProperty
    var name: String = ""
    @JacksonXmlProperty
    var showInDescription: Int = 0
}