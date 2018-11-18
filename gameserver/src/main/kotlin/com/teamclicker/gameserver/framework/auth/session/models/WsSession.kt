package com.teamclicker.gameserver.framework.auth.session.models

import com.teamclicker.gameserver.framework.auth.jwt.models.AccountId
import com.teamclicker.gameserver.modules.characters.CharacterId
import java.util.*

typealias SessionId = String

class WsSession {
    lateinit var sessionId: SessionId
    lateinit var createdAt: Calendar
    lateinit var connectedAt: Calendar
    var accountId: AccountId = 0
    var characterId: CharacterId = 0
    lateinit var user: Any
}

