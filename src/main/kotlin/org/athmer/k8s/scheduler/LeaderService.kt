package org.athmer.k8s.scheduler

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
interface LeaderService {
    fun isLeader(): Boolean
}

@Component
@Profile("!kubernetes")
class DummyLeaderService : LeaderService {
    override fun isLeader(): Boolean = true
}