package nl.ignite.kubernetes.demo.server

import org.apache.ignite.Ignite
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.net.InetSocketAddress
import javax.management.MBeanServer

@SpringBootTest
class ServerApplicationTest {

    @Autowired
    private lateinit var ignite: Ignite

    @Autowired
    private lateinit var ipFinder: TcpDiscoveryIpFinder

    @Autowired
    private lateinit var mBeanServer: MBeanServer

    @Test
    fun contextLoads() {
        SoftAssertions.assertSoftly {
            it.assertThat(ignite).isNotNull
            it.assertThat(ignite.cluster().localNode().isClient).isFalse
            it.assertThat(ipFinder).isInstanceOf(TcpDiscoveryVmIpFinder::class.java)
            it.assertThat(ipFinder.registeredAddresses).hasSize(10)
            it.assertThat(ipFinder.registeredAddresses).contains(InetSocketAddress("localhost", 47500))
            it.assertThat(mBeanServer).isNotNull
        }
    }
}