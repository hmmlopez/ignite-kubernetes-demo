package nl.ignite.kubernetes.demo.thinclient.config

import org.apache.ignite.Ignition
import org.apache.ignite.client.IgniteClient
import org.apache.ignite.configuration.ClientConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "ignite.client-configuration")
    fun clientConfiguration() = ClientConfiguration()

    @Bean
    fun igniteClient(clientConfiguration: ClientConfiguration): IgniteClient = Ignition.startClient(clientConfiguration)

}