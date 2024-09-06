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
@TestPropertySource(properties = ["KUBERNETES_NAMESPACE=namespace1", "KUBERNETES_SERVICE=service1"])
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
            it.assertThat(connectionConfiguration.namespace).isEqualTo("namespace1")
            it.assertThat(connectionConfiguration.serviceName).isEqualTo("service1")
        }
    }
}