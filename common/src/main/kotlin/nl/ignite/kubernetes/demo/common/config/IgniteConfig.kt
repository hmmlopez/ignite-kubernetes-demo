package nl.ignite.kubernetes.demo.common.config

import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.DiscoverySpi
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    fun ipFinder(): TcpDiscoveryIpFinder {
        return TcpDiscoveryMulticastIpFinder()
    }
}