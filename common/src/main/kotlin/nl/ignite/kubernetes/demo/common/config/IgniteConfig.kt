package nl.ignite.kubernetes.demo.common.config

import org.apache.ignite.configuration.DataStorageConfiguration
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.kubernetes.configuration.KubernetesConnectionConfiguration
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.kubernetes.TcpDiscoveryKubernetesIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.nio.file.Paths

@Configuration
class IgniteConfig {

    @Bean
    @ConfigurationProperties("ignite")
    fun defaultIgniteConfiguration(ipFinder: TcpDiscoveryIpFinder) =
        IgniteConfiguration().apply {
            this.gridLogger = Slf4jLogger()
            this.metricsLogFrequency = 0
            this.igniteHome = Paths.get(".").toAbsolutePath().toString()
            this.discoverySpi = TcpDiscoverySpi().apply {
                this.ipFinder = ipFinder
            }
            this.dataStorageConfiguration = DataStorageConfiguration()
        }

    @Bean
    @Profile("!kubernetes")
    @ConfigurationProperties(prefix = "ignite.discovery.local.ip-finder")
    fun tcpDiscoveryVmFinder() = TcpDiscoveryVmIpFinder()

    @Bean
    @Profile("kubernetes")
    fun tcpDiscoveryKubernetesIpFinder(configuration: KubernetesConnectionConfiguration) =
        TcpDiscoveryKubernetesIpFinder(configuration)

    @Bean
    @Profile("kubernetes")
    fun kubernetesConnectionConfiguration(
        @Value("\${KUBERNETES_NAMESPACE:default}") namespace: String,
        @Value("\${KUBERNETES_SERVICE:ignite}") serviceName: String
    ) = KubernetesConnectionConfiguration().apply {
        this.namespace = namespace
        this.serviceName = serviceName
    }

}