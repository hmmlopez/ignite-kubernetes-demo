package nl.ignite.kubernetes.demo.server

import org.apache.ignite.Ignite
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.management.MBeanServer

@SpringBootTest(
    properties = ["ignite.dataStorageConfiguration.defaultDataRegionConfiguration.initialSize=536870912",
        "ignite.dataStorageConfiguration.defaultDataRegionConfiguration.maxSize=1073741824"]
)
class ServerApplicationTest {

    @Autowired
    private lateinit var ignite: Ignite

    @Autowired
    private lateinit var discoverySpi: TcpDiscoverySpi

    @Autowired
    private lateinit var mBeanServer: MBeanServer

    @Test
    fun contextLoads() {
        SoftAssertions.assertSoftly {
            it.assertThat(ignite).isNotNull
            it.assertThat(ignite.cluster().localNode().isClient).isFalse
            it.assertThat(discoverySpi.ipFinder).isNotNull
            it.assertThat(mBeanServer).isNotNull
        }
    }

    @Test
    fun testDataStorage() {
        SoftAssertions.assertSoftly {
            it.assertThat(ignite.configuration()).isNotNull
            val dataStorageConfiguration = ignite.configuration().dataStorageConfiguration
            it.assertThat(dataStorageConfiguration).isNotNull
            val defaultDataRegion = dataStorageConfiguration.defaultDataRegionConfiguration
            it.assertThat(defaultDataRegion).isNotNull
            it.assertThat(defaultDataRegion.initialSize).isEqualTo(512 * 1024 * 1024)
            it.assertThat(defaultDataRegion.maxSize).isEqualTo(1 * 1024 * 1024 * 1024)
        }
    }
}