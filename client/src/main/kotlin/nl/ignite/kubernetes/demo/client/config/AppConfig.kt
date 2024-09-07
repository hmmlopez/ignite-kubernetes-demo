package nl.ignite.kubernetes.demo.client.config

import nl.ignite.kubernetes.demo.common.config.IgniteConfig
import org.apache.ignite.IgniteSpringBean
import org.apache.ignite.configuration.IgniteConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(IgniteConfig::class)
class AppConfig {

    @Bean
    fun igniteSpringBean(igniteConfiguration: IgniteConfiguration) = IgniteSpringBean().apply {
        configuration = igniteConfiguration.apply { isClientMode = true }
    }

}