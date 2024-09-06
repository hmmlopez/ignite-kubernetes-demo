package nl.ignite.kubernetes.demo.common.config

import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.kubernetes.configuration.KubernetesConnectionConfiguration
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.DiscoverySpi
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.kubernetes.TcpDiscoveryKubernetesIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.nio.file.Paths

@Configuration
class IgniteConfig {

    @Bean
    fun defaultIgniteConfiguration(discoverySpi: DiscoverySpi): IgniteConfiguration {
        return IgniteConfiguration().apply {
            this.gridLogger = Slf4jLogger()
            this.metricsLogFrequency = 0
            this.igniteHome = Paths.get(".").toAbsolutePath().toString()
            this.discoverySpi = discoverySpi
        }
    }

    @Bean
    fun discoverySpi(ipFinder: TcpDiscoveryIpFinder): DiscoverySpi {
        return TcpDiscoverySpi().apply {
            this.ipFinder = ipFinder
        }
    }

    @Bean
    @Profile("!kubernetes")
    @ConfigurationProperties(prefix = "ignite.discovery.local.ip-finder")
    fun tcpDiscoveryVmFinder(): TcpDiscoveryIpFinder {
//        return TcpDiscoveryVmIpFinder()
        return TcpDiscoveryMulticastIpFinder()
    }

    @Bean
    @Profile("kubernetes")
    fun tcpDiscoveryKubernetesIpFinder(configuration: KubernetesConnectionConfiguration): TcpDiscoveryIpFinder {
        return TcpDiscoveryKubernetesIpFinder(configuration)
    }

    @Bean
    @Profile("kubernetes")
    @ConfigurationProperties(prefix = "ignite.discovery.kubernetes.configuration")
    fun kubernetesConnectionConfiguration() : KubernetesConnectionConfiguration {
        return KubernetesConnectionConfiguration()
    }

}