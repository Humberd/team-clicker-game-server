package com.teamclicker.gameserver.modules.characters

import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import com.teamclicker.gameserver.framework.auth.session.models.SessionId
import com.teamclicker.gameserver.modules.characters.models.CharacterDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/characters")
class CharactersController(
    private val charactersService: CharactersService
) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    fun getCharacters(): Mono<List<CharacterDto>> {
        return Mono.just(charactersService.getCharacters())
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{characterId}/choose")
    fun chooseCharacter(@PathVariable characterId: CharacterId,
                        jwt: JwtData): Mono<SessionId> {
        return Mono.just(charactersService.chooseCharacter(jwt, characterId))
    }
}