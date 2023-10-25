package site.qbox.qboxserver.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestSecurityConfig {

    @Bean
    fun testUserDetailService(): TestUserDetailService =
        TestUserDetailService()
}