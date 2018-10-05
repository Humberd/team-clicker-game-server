package com.teamclicker.gameserver.framework.scriptingengine.spells.data

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "rune")
class RuneXml {
    @JacksonXmlProperty
    var group: String = ""
    @JacksonXmlProperty
    var spellid: Int = 0
    @JacksonXmlProperty
    var name: String = ""
    @JacksonXmlProperty
    var id: Int = 0
    @JacksonXmlProperty
    var allowfaruse: Int = 0
    @JacksonXmlProperty
    var charges: Int = 0
    @JacksonXmlProperty
    var level: Int = 0
    @JacksonXmlProperty
    var magiclevel: Int = 0
    @JacksonXmlProperty
    var cooldown: Int = 0
    @JacksonXmlProperty
    var groupcooldown: Int = 0
    @JacksonXmlProperty
    var range: Int = 0
    @JacksonXmlProperty
    var mana: Int = 0
    @JacksonXmlProperty
    var aggressive: Int = 0
    @JacksonXmlProperty
    var selftarget: Int = 0
    @JacksonXmlProperty
    var needtarget: Int = 0
    @JacksonXmlProperty
    var blocktype: String = ""
    @JacksonXmlProperty
    var script: String = ""
    @JacksonXmlElementWrapper
    var vocations: List<VocationXml> = emptyList()

    override fun toString(): String {
        return "RuneXml(group='$group', spellid=$spellid, name='$name', id=$id, allowfaruse=$allowfaruse, charges=$charges, level=$level, magiclevel=$magiclevel, cooldown=$cooldown, groupcooldown=$groupcooldown, range=$range, mana=$mana, aggressive=$aggressive, selftarget=$selftarget, needtarget=$needtarget, blocktype='$blocktype', script='$script', vocations=$vocations)"
    }

}