package nl.ignite.kubernetes.demo.server.endpoint

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.availability.ApplicationAvailability
import org.springframework.boot.availability.ReadinessState
import org.springframework.context.ApplicationContext
import javax.management.MBeanServer
import javax.management.ObjectName

@ExtendWith(MockKExtension::class)
class ServerReadinessTest {

    @MockK
    private lateinit var applicationAvailability: ApplicationAvailability

    @MockK
    private lateinit var mBeanServer: MBeanServer

    @MockK(relaxed = true)
    private lateinit var applicationContext: ApplicationContext

    @InjectMockKs
    private lateinit var serverReadiness: ServerReadiness

    @Test
    fun testRebalancing() {
        val objectName = ObjectName("object1", "key", "value")

        every { mBeanServer.queryNames(any(), null) } returns setOf(objectName)
        every { mBeanServer.getAttribute(objectName, "Rebalanced") } returns true
        every { applicationAvailability.readinessState } returns ReadinessState.REFUSING_TRAFFIC andThen ReadinessState.REFUSING_TRAFFIC andThen ReadinessState.ACCEPTING_TRAFFIC

        SoftAssertions.assertSoftly {
            it.assertThat(serverReadiness.isRebalanced).isFalse()
//            it.assertThat(serverReadiness.isRebalanced).isFalse()
//            it.assertThat(serverReadiness.isRebalanced).isTrue()
        }

        verify(exactly = 0) { mBeanServer.queryNames(any(), null) }
        verify(exactly = 0) { mBeanServer.getAttribute(objectName, "Rebalanced") }
    }

}