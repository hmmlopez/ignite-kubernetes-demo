package nl.ignite.kubernetes.demo.server.enpoint

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.availability.ReadinessStateHealthIndicator
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.availability.ApplicationAvailability
import org.springframework.boot.availability.AvailabilityChangeEvent
import org.springframework.boot.availability.AvailabilityState
import org.springframework.boot.availability.ReadinessState
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import javax.management.MBeanServer
import javax.management.ObjectName

@Component
@Endpoint(id = "readiness", enableByDefault = true)
class ServerReadiness(availability: ApplicationAvailability, val context: ApplicationContext, val mBeanServer: MBeanServer) : ReadinessStateHealthIndicator(availability) {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    val searchableObjectName: ObjectName = ObjectName.getInstance("org.apache:clsLdr=*,name=cluster,*")
    val attribute = "Rebalanced"
    var isRebalanced = false

    @ReadOperation
    fun getHealth(): Health {
        return super.getHealth(true)
    }

    override fun getState(applicationAvailability: ApplicationAvailability): AvailabilityState {
        if (!isRebalanced) {
            queryMBeanServer()
        }
        val readinessState = applicationAvailability.readinessState
        logger.info("getState: $readinessState")
        return readinessState
    }

    private fun queryMBeanServer() {
        AvailabilityChangeEvent.publish(context, ReadinessState.REFUSING_TRAFFIC)
        val objectName = mBeanServer.queryNames(searchableObjectName, null).first()
        isRebalanced = mBeanServer.getAttribute(objectName, attribute) as Boolean
        logger.info("Node is rebalanced? $isRebalanced")
        if (isRebalanced) {
            AvailabilityChangeEvent.publish(context, ReadinessState.ACCEPTING_TRAFFIC)
        }
    }

}