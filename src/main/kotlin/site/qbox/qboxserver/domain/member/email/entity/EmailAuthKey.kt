package site.qbox.qboxserver.domain.member.email.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "emailAuthKey")
class EmailAuthKey(
    @Id val key: String,
    val email: String,
)