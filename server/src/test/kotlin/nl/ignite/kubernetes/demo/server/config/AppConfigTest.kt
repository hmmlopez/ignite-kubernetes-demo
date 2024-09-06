package nl.ignite.kubernetes.demo.server.config

import org.apache.ignite.Ignite
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
@TestPropertySource(properties = ["ignite.discovery.local.ip-finder.addresses=localhost:47500..47510"])
class AppConfigTest {

    @Autowired
    private lateinit var ignite: Ignite

    @Autowired
    private lateinit var ipFinder: TcpDiscoveryIpFinder

    @Test
    fun contextLoads() {
        SoftAssertions.assertSoftly {
            it.assertThat(ignite).isNotNull
            it.assertThat(ipFinder).isNotNull
            it.assertThat(ipFinder).isInstanceOf(TcpDiscoveryVmIpFinder::class.java)
        }
    }
}