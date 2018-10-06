package com.teamclicker.gameserver.framework.scheduler

typealias TaskFunction = () -> Void

open class Task {
    protected var expiration = 0L
        private set
    val taskFunction: TaskFunction

    constructor(delayMs: Long, taskFunction: TaskFunction) {
        expiration = System.currentTimeMillis() + delayMs
        this.taskFunction = taskFunction
    }

    constructor(taskFunction: TaskFunction) {
        this.taskFunction = taskFunction
    }

    fun setDontExpire() {
        expiration = 0L
    }

    fun hasExpired(): Boolean {
        if (expiration == 0L) {
            return false
        }

        return expiration < System.currentTimeMillis()
    }

    fun execute() {
        taskFunction()
    }
}