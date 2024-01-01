package site.qbox.qboxserver.domain.member.command.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.member.command.exception.NotMatchPasswordException
import site.qbox.qboxserver.domain.member.email.entity.Email
import site.qbox.qboxserver.global.entity.BaseEntity

@Entity
class Member(
    @EmbeddedId val email: Email,
    @Column(nullable = false, unique = true) var nickname: String,
    @Column(nullable = false) var password: String,
    @Column(nullable = false) var departId: Long,
    val role: Set<Role> = setOf(Role.USER)
) : BaseEntity() {
    constructor(
        email: String,
        nickname: String,
        rawPassword: String,
        departId: Long,
        passwordEncoder: PasswordEncoder
    ) : this(Email(email), nickname, passwordEncoder.encode(rawPassword), departId)


    fun changePassword(beforePassword: String, newPassword: String, passwordEncoder: PasswordEncoder) {
        if (!passwordEncoder.matches(beforePassword, this.password)) {
            throw NotMatchPasswordException()
        }
        this.password = passwordEncoder.encode(newPassword)
    }

    fun changeNickname(newNickname: String) {
        this.nickname = newNickname
    }

    val emailDomain: String
        get() = email.domain
    val id: String
        get() = email.email
}