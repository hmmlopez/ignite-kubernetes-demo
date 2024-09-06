package nl.ignite.kubernetes.demo.common.config

import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [IgniteConfig::class])
class IgniteConfigTest {

    @Autowired
    private lateinit var igniteConfiguration: IgniteConfiguration

    @Autowired
    private lateinit var ipFinder: TcpDiscoveryIpFinder

    @Test
    fun defaultIgniteConfiguration() {
        SoftAssertions.assertSoftly {
            it.assertThat(igniteConfiguration).isNotNull
            it.assertThat(igniteConfiguration.gridLogger).isInstanceOf(Slf4jLogger::class.java)
            it.assertThat(igniteConfiguration.metricsLogFrequency).isEqualTo(0)
            it.assertThat(igniteConfiguration.discoverySpi).isNotNull
            it.assertThat(igniteConfiguration.discoverySpi).isInstanceOf(TcpDiscoverySpi::class.java)
        }
    }

    @Test
    fun ipFinder() {
        SoftAssertions.assertSoftly {
            it.assertThat(ipFinder).isNotNull
            it.assertThat(ipFinder).isInstanceOf(TcpDiscoveryVmIpFinder::class.java)
        }
    }
}