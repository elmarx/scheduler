package org.athmer.k8s.scheduler

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Job {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedRate = 1000)
    fun log() {
        logger.info("Job started")
    }
}