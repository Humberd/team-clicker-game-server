package com.teamclicker.gameserver.modules.characters

import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import com.teamclicker.gameserver.framework.auth.session.SessionService
import com.teamclicker.gameserver.framework.auth.session.models.SessionId
import com.teamclicker.gameserver.modules.characters.models.CharacterDto
import org.springframework.stereotype.Service

@Service
class CharactersService(
    private val sessionService: SessionService
) {
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

    fun chooseCharacter(jwt: JwtData, characterId: CharacterId): SessionId {
        return sessionService.newSession(jwt, characterId)
    }
}