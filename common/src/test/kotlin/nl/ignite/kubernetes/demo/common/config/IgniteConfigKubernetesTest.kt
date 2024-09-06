package nl.ignite.kubernetes.demo.common.config

import org.apache.ignite.kubernetes.configuration.KubernetesConnectionConfiguration
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.kubernetes.TcpDiscoveryKubernetesIpFinder
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@EnableConfigurationProperties
@ActiveProfiles(profiles = ["kubernetes"])
@ContextConfiguration(classes = [IgniteConfig::class])
@TestPropertySource(properties = ["ignite.discovery.kubernetes.configuration.service-name=my-service-test"])
class IgniteConfigKubernetesTest {

    @Autowired
    private lateinit var ipFinder: TcpDiscoveryIpFinder

    @Autowired
    private lateinit var connectionConfiguration: KubernetesConnectionConfiguration

    @Test
    fun ipFinder() {
        SoftAssertions.assertSoftly {
            it.assertThat(ipFinder).isNotNull
            it.assertThat(ipFinder).isInstanceOf(TcpDiscoveryKubernetesIpFinder::class.java)
        }
    }

    @Test
    fun connectionConfiguration() {
        SoftAssertions.assertSoftly {
            it.assertThat(connectionConfiguration).isNotNull
            it.assertThat(connectionConfiguration.namespace).isEqualTo("default")
            it.assertThat(connectionConfiguration.serviceName).isEqualTo("my-service-test")
            it.assertThat(connectionConfiguration.master).isEqualTo("https://kubernetes.default.svc.cluster.local:443")
            it.assertThat(connectionConfiguration.discoveryPort).isEqualTo(0)
            it.assertThat(connectionConfiguration.accountToken)
                .isEqualTo("/var/run/secrets/kubernetes.io/serviceaccount/token")
        }
    }
}