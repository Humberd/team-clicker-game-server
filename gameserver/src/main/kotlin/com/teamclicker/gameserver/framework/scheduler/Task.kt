package com.teamclicker.gameserver.framework.scheduler

typealias TaskFunction = () -> Unit

open class Task {
    var executionTime = 0L
        private set

    var eventId: Long = 0

    val taskFunction: TaskFunction

    constructor(delayMs: Long, taskFunction: TaskFunction) {
        executionTime = System.currentTimeMillis() + delayMs
        this.taskFunction = taskFunction
    }

    constructor(taskFunction: TaskFunction) : this(0, taskFunction)

    fun isReady(): Boolean {
        return executionTime <= System.currentTimeMillis()
    }

    operator fun invoke() = taskFunction()
}