package com.teamclicker.gameserver.framework.auth.session

import com.teamclicker.gameserver.framework.auth.jwt.models.AccountId
import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import com.teamclicker.gameserver.framework.auth.session.models.SessionId
import com.teamclicker.gameserver.framework.auth.session.models.WsSession
import com.teamclicker.gameserver.modules.characters.CharacterId
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Service
class SessionService {
    private val sessions = HashMap<SessionId, WsSession>()
    private val sessionsByCharId = HashSet<CharacterId>()
    private val sessionsByAccId = HashSet<AccountId>()

    fun newSession(jwt: JwtData, characterId: CharacterId): SessionId {
        if (sessionsByCharId.contains(characterId)) {
            throw Exception("Player already in game")
        }

        if (sessionsByAccId.contains(jwt.accountId)) {
            throw Exception("Only 1 character per account can be online")
        }

        sessionsByAccId.add(jwt.accountId)
        sessionsByCharId.add(characterId)
        val sessionId = getSessionId()
        val session = WsSession().also {
            it.sessionId = sessionId
            it.characterId = characterId
            it.accountId = jwt.accountId
            it.createdAt = Calendar.getInstance()
        }
        sessions.put(sessionId, session)

        return sessionId
    }

    fun getSessionId(): SessionId {
        return UUID.randomUUID().toString()
    }


    fun joinSession(sessionId: SessionId) {
        val session = sessions.get(sessionId)
        if (session === null) {
            throw Exception("Session does not exist")
        }
        session.connectedAt = Calendar.getInstance()
    }


}