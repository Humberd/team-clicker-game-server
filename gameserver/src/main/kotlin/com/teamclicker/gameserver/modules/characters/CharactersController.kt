package com.teamclicker.gameserver.modules.characters

import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import com.teamclicker.gameserver.modules.characters.models.CharacterDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/characters")
class CharactersController(
    private val charactersService: CharactersService
) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    fun getCharacters(jwt: JwtData): Mono<List<CharacterDto>> {
        return Mono.just(charactersService.getCharacters(jwt.accountId))
    }

//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/{characterId}/choose")
//    fun chooseCharacter(
//        @PathVariable characterId: CharacterId,
//        jwt: JwtData
//    ): Mono<SessionId> {
//        return Mono.just(charactersService.chooseCharacter(jwt, characterId))
//    }

}