package site.qbox.qboxserver.domain.member.email.dto

import jakarta.validation.constraints.Email

data class RegisterEmailReq(
    @Email val email: String,
)