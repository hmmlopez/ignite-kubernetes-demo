package nl.ignite.kubernetes.demo.common.config

import org.apache.ignite.configuration.DataStorageConfiguration
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.kubernetes.configuration.KubernetesConnectionConfiguration
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
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
    fun defaultIgniteConfiguration(discoverySpi: TcpDiscoverySpi) =
        IgniteConfiguration().apply {
            this.gridLogger = Slf4jLogger()
            this.metricsLogFrequency = 0
            this.igniteHome = Paths.get(".").toAbsolutePath().toString()
            this.discoverySpi = discoverySpi
            this.dataStorageConfiguration = DataStorageConfiguration()
        }

    @Bean
    @Profile("!kubernetes")
    fun discoverySpi() = TcpDiscoverySpi().apply {
        this.localAddress = "localhost"
        this.localPort = 47500
        this.localPortRange = 10
        this.ipFinder = TcpDiscoveryVmIpFinder().apply {
            this.setAddresses(listOf("localhost:47500..47509"))
        }
    }

    @Bean
    @Profile("kubernetes")
    fun kubernetesDiscoverySpi(configuration: KubernetesConnectionConfiguration) = TcpDiscoverySpi().apply {
        this.localAddress = "0.0.0.0"
        this.localPort = 47500
        this.localPortRange = 10
        this.ipFinder = TcpDiscoveryKubernetesIpFinder(configuration)
    }

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