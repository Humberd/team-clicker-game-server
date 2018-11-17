package com.teamclicker.gameserver.modules.characters

import com.teamclicker.gameserver.modules.characters.models.CharacterDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/characters")
class CharactersController(
    private val charactersService: CharactersService
) {

    @GetMapping()
    fun getCharacters(): ResponseEntity<List<CharacterDto>> {
        return ResponseEntity.ok(charactersService.getCharacters())
    }
}