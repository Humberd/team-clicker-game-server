package com.teamclicker.gameserver.modules.characters

import com.teamclicker.gameserver.modules.characters.models.CharacterDto
import org.springframework.http.ResponseEntity
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
    fun getCharacters(): Mono<List<CharacterDto>> {
        return Mono.just(charactersService.getCharacters())
    }
}