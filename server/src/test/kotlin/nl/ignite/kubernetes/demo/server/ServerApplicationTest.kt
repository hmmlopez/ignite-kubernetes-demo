package nl.ignite.kubernetes.demo.server

import org.apache.ignite.Ignite
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ServerApplicationTest {

    @Autowired
    private lateinit var ignite: Ignite

    @Test
    fun contextLoads() {
        SoftAssertions.assertSoftly {
            it.assertThat(ignite).isNotNull
        }
    }
}