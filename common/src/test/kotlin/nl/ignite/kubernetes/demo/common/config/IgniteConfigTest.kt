package nl.ignite.kubernetes.demo.common.config

import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.InetSocketAddress

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [IgniteConfig::class])
class IgniteConfigTest {

    @Autowired
    private lateinit var igniteConfiguration: IgniteConfiguration

    @Autowired
    private lateinit var igniteDiscoverySpi: TcpDiscoverySpi

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
            it.assertThat(igniteDiscoverySpi.ipFinder).isNotNull
            it.assertThat(igniteDiscoverySpi.ipFinder).isInstanceOf(TcpDiscoveryVmIpFinder::class.java)
            val tcpDiscoveryVmIpFinder = igniteDiscoverySpi.ipFinder as TcpDiscoveryVmIpFinder
            it.assertThat(tcpDiscoveryVmIpFinder.registeredAddresses).hasSize(10)
            it.assertThat(tcpDiscoveryVmIpFinder.registeredAddresses).contains(
                InetSocketAddress("localhost", 47500),
                InetSocketAddress("localhost", 47509)
            )
        }
    }
}