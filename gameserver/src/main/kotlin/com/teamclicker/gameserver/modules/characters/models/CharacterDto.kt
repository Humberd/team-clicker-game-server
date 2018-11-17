package com.teamclicker.gameserver.modules.characters.models

data class CharacterDto(
    val characterId: Long,
    val accountId: Long,
    val name: String
)