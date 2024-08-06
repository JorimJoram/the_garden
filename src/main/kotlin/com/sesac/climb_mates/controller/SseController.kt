package com.sesac.climb_mates.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@RestController
class SseController {
    private final val emitterList = CopyOnWriteArrayList<SseEmitter>()

    init{
        scheduleDailyNotification()
    }

    @GetMapping("/subscribe")
    fun subscribe(): SseEmitter {
        println("${LocalDateTime.now()} subscribe Test")
        val emitter = SseEmitter(Long.MAX_VALUE)
        emitterList.add(emitter)

        emitter.onCompletion{emitterList.remove(emitter)}
        emitter.onTimeout{emitterList.remove(emitter)}
        emitter.onError{emitterList.remove(emitter)}

        return emitter
    }

    private fun scheduleDailyNotification(){
        val executorService = Executors.newScheduledThreadPool(1)

        val dailyTask = Runnable {
            val now = LocalDateTime.now()
            if(now.hour == 14 && now.minute == 20){
                notifyAllClients("맛점하세요")
            }
        }

        val initialDelay = calculateInitialDelay()
        executorService.scheduleAtFixedRate(dailyTask, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS)
    }

    private fun calculateInitialDelay(): Long {
        val now = LocalTime.now()
        var next13 = LocalTime.of(13,0)
        if(now.isAfter(next13)){
            next13 = next13.plusHours(24)
        }

        return now.until(next13, java.time.temporal.ChronoUnit.SECONDS)
    }

    private fun notifyAllClients(message:String){
        val failedEmitterList = mutableListOf<SseEmitter>()

        emitterList.forEach { emitter ->
            try{
                emitter.send(SseEmitter.event().name("dailyNotification").data(message, MediaType.TEXT_PLAIN))
            }catch (e:Exception){
                failedEmitterList.add(emitter)
            }
        }

        emitterList.removeAll(failedEmitterList)
    }
}