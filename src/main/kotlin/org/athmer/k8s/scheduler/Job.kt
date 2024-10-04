package org.athmer.k8s.scheduler

import org.slf4j.LoggerFactory
import org.springframework.boot.cloud.CloudPlatform
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Job(private val leaderService: LeaderService) {
    val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedRate = 10000)
    fun log() {
        if(leaderService.isLeader()) {
            logger.info("Job started")
        }
    }
}