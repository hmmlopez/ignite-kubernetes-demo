package nl.ignite.kubernetes.demo.server.config

import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@EnableConfigurationProperties
@ContextConfiguration(classes = [AppConfig::class])
@TestPropertySource(properties = ["ignite.discovery.local.ip-finder.addresses=localhost:47500..47511"])
class AppConfigTest {

//    @Autowired
//    private lateinit var ignite: Ignite

    @Autowired
    private lateinit var ipFinder: TcpDiscoveryIpFinder

    @Autowired
    private lateinit var igniteConfiguration: IgniteConfiguration

    @Test
    fun contextLoads() {
        SoftAssertions.assertSoftly {
//            it.assertThat(ignite).isNotNull
//            it.assertThat(ipFinder).isNotNull
//            it.assertThat(ipFinder).isInstanceOf(TcpDiscoveryVmIpFinder::class.java)
            Ignition.start(
                IgniteConfiguration().setDiscoverySpi(
                    TcpDiscoverySpi()
                        .setLocalPort(47500)
                        .setLocalAddress("127.0.0.1")
                        .setLocalPortRange(10)
                        .setIpFinder(TcpDiscoveryVmIpFinder().setAddresses(setOf("127.0.0.1:47500..47510")))
                )
            )
        }
    }
}