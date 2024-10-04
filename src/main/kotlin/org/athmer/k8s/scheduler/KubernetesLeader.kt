package org.athmer.k8s.scheduler

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.integration.leader.Context
import org.springframework.integration.leader.event.OnGrantedEvent
import org.springframework.integration.leader.event.OnRevokedEvent
import org.springframework.stereotype.Component

@Component
@Profile("kubernetes")
class KubernetesLeader : LeaderService{
    val logger = LoggerFactory.getLogger(this::class.java)

    private var context: Context? = null

    override fun isLeader() = context != null

    @EventListener
    fun onGranted(event: OnGrantedEvent) {
        this.context = event.context
        logger.info("Granted leadership. Will execute scheduled tasks. {}", event)
    }

    @EventListener
    fun onRevoked(event: OnRevokedEvent) {
        this.context = null
        logger.info("Leadership revoked. Will not execute scheduled tasks.")
    }

}