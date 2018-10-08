package com.teamclicker.gameserver.framework.scheduler

import com.teamclicker.gameserver.framework.scheduler.ThreadState.THREAD_STATE_RUNNING
import com.teamclicker.gameserver.framework.scheduler.ThreadState.THREAD_STATE_TERMINATED
import java.util.concurrent.PriorityBlockingQueue

class Scheduler : Thread() {
    var state = THREAD_STATE_TERMINATED
    var lastEventId: Long = 0L
    val eventList: PriorityBlockingQueue<Task>
    val eventIds: HashSet<Long>

    init {
        isDaemon = true
        eventList = PriorityBlockingQueue(10) { lhs, rhs ->
            lhs.executionTime.compareTo(rhs.executionTime)
        }
        eventIds = HashSet()
    }

    override fun start() {
        state = THREAD_STATE_RUNNING
        super.start()
    }

    override fun run() {
        while (state != THREAD_STATE_TERMINATED) {
            if (eventList.isEmpty()) {
                sleep()
                continue
            }

            if (!eventList.peek().isReady()) {
                sleep()
                continue
            }

            val task = eventList.remove()

            // check if the event was stopped
            if (!eventIds.contains(task.eventId)) {
                sleep()
                continue
            }
            eventIds.remove(task.eventId)
            task()
        }
    }

    fun sleep() {
        sleep(1000/30)
    }

    fun shutdown() {
        state = THREAD_STATE_TERMINATED
        println("Scheduler is shutting down")
        eventIds.clear()
        eventList.clear()
    }

    fun addEvent(task: Task): Long {
        if (state != THREAD_STATE_RUNNING) {
            throw Exception("Scheduler is not running")
        }

        if (task.eventId == 0L) {
            task.eventId = ++lastEventId
        }

        eventIds.add(task.eventId)
        eventList.add(task)

        return task.eventId
    }

    fun stopEvent(eventId: Long) {
        if (eventId == 0L) {
            throw Exception("Event has id of 0")
        }

        val found = eventIds.find { it == eventId }
        if (found === null) {
            throw Exception("Event $eventId does not exist")
        }
        eventIds.remove(eventId)
    }
}