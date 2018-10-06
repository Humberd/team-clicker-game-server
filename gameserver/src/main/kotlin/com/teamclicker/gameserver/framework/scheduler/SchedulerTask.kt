package com.teamclicker.gameserver.framework.scheduler

class SchedulerTask(
    delayMs: Long,
    taskFunction: TaskFunction
) : Task(delayMs, taskFunction) {
    var eventId: Long = 0

    fun getCycle() = expiration
}