package com.teamclicker.gameserver.modules.characters.models

import com.teamclicker.gameserver.modules.characters.CharacterId

data class CharacterDto(
    val characterId: CharacterId,
    val accountId: Long,
    val name: String
)