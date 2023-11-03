package site.qbox.qboxserver.domain.member.email.entity

import jakarta.persistence.Embeddable
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable


@Embeddable
@RedisHash(value = "email")
data class Email(@Id val email: String) : Serializable {
    init {
        validate(email)
    }

    companion object {
        const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$"
    }

    val domain: String
        get() = email.split("@")[1]

    private fun validate(email: String) =
        require(isEmailPattern(email)) { "잘못된 이메일 형식입니다." }


    private fun isEmailPattern(email: String): Boolean =
        email.matches(Regex(EMAIL_REGEX))
}
