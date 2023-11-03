package site.qbox.qboxserver.domain.member.email.dto.event

import site.qbox.qboxserver.domain.member.email.entity.EmailAuthKey

data class RegisteredEmailEvent(
    val email: String,
    val key: String,
) {
    constructor(emailAuthKey: EmailAuthKey) : this(emailAuthKey.email, emailAuthKey.key)
}