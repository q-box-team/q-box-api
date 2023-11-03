package site.qbox.qboxserver.config

import com.redis.testcontainers.RedisContainer
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
@SpringBootTest
abstract class IntegrationTest: DescribeSpec(){
    companion object {
        private const val REDIS_PORT = 6379
        @Container
        private val redisContainer = RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(REDIS_PORT)

        @JvmStatic
        @DynamicPropertySource
        fun redisProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.redis.repositories.enabled") { true }
            registry.add("spring.data.redis.host") { redisContainer.host }
            registry.add("spring.data.redis.port") { redisContainer.getMappedPort(REDIS_PORT).toString() }

            redisContainer.start()
        }
    }

    override fun extensions() = listOf(SpringExtension)
}