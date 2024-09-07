package nl.ignite.kubernetes.demo.client

import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.nio.file.Paths

@SpringBootTest
class ClientApplicationTest {

    @Autowired
    @Qualifier("igniteSpringBean")
    private lateinit var ignite: Ignite

    @Autowired
    private lateinit var discoverySpi: TcpDiscoverySpi

    @Test
    fun contextLoads() {
        SoftAssertions.assertSoftly {
            it.assertThat(ignite).isNotNull
            it.assertThat(ignite.cluster().localNode().isClient).isTrue
            it.assertThat(discoverySpi.ipFinder).isNotNull
            it.assertThat(discoverySpi.localAddress).isEqualTo("localhost")
            it.assertThat(discoverySpi.localPortRange).isEqualTo(10)
        }
    }

    @TestConfiguration
    class IgniteServerTestConfiguration {

        @Bean
        fun igniteServer(): Ignite =
            Ignition.start(IgniteConfiguration().apply {
                this.discoverySpi = TcpDiscoverySpi().apply {
                    localAddress = "localhost"
                    localPort = 47500
                    localPortRange = 10
                    ipFinder = ipFinder
                }
                this.igniteInstanceName = "igniteServer"
                this.igniteHome = Paths.get(".").toAbsolutePath().toString()
            })
    }
}