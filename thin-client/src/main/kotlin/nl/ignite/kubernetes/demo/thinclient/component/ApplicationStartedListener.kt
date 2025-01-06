package nl.ignite.kubernetes.demo.thinclient.component

import io.github.serpro69.kfaker.Faker
import nl.ignite.kubernetes.demo.common.domain.User
import org.apache.ignite.client.ClientCacheConfiguration
import org.apache.ignite.client.IgniteClient
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component

@Component
class ApplicationStartedListener(private val igniteClient: IgniteClient, val context: ConfigurableApplicationContext) :
    ApplicationListener<ApplicationStartedEvent> {

    private val faker = Faker()
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun onApplicationEvent(event: ApplicationStartedEvent) {

        val cacheConfiguration = ClientCacheConfiguration().apply {
            name = "UserCache"
            backups = 1
            isStatisticsEnabled = true
        }
        val userCache = igniteClient.getOrCreateCache<Int, User>(cacheConfiguration)

        logger.info("Adding data to the cache")
        (1..1_000_000).forEach {
            if (it % 100_000 == 0) {
                logger.info("Inserted: $it records in cache")
            }
            userCache.put(
                it, User(
                    faker.name.firstName(),
                    faker.name.lastName(),
                    faker.random.nextInt(12, 85),
                    faker.internet.email()
                )
            )
        }
        logger.info("Finished data to the cache")

        context.close()
    }
}