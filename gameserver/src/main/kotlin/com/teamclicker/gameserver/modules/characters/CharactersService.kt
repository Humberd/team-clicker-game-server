package com.teamclicker.gameserver.modules.characters

import com.teamclicker.gameserver.modules.characters.models.CharacterDto
import org.springframework.stereotype.Service

@Service
class CharactersService {
    fun getCharacters(): List<CharacterDto> {
        return listOf(
            CharacterDto(
                1,
                1,
                "First"
            ), CharacterDto(
                2,
                1,
                "Second"
            )
        )
    }
}