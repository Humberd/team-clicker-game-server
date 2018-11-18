package com.teamclicker.gameserver.modules.characters

import com.teamclicker.gameserver.framework.auth.jwt.models.AccountId
import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import com.teamclicker.gameserver.framework.auth.session.SessionService
import com.teamclicker.gameserver.framework.auth.session.models.SessionId
import com.teamclicker.gameserver.modules.characters.models.CharacterDto
import org.springframework.stereotype.Service

@Service
class CharactersService(
    private val sessionService: SessionService
) {

    val chars = listOf(
        CharacterDto(
            1,
            62,
            "First"
        ), CharacterDto(
            2,
            62,
            "Second"
        )
    )

    fun getCharacters(): List<CharacterDto> {
        return chars.map { it.copy() }
    }

    fun chooseCharacter(jwt: JwtData, characterId: CharacterId): SessionId {
        if (!characterExists(characterId, jwt.accountId)) {
            throw Exception("Character does not exist")
        }
        return sessionService.newSession(jwt, characterId)
    }

    fun characterExists(characterId: CharacterId, accountId: AccountId): Boolean {
        return chars.find { it.characterId == characterId && it.accountId == accountId } !== null
    }
}