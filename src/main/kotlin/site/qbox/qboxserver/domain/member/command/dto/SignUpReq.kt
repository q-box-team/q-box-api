package site.qbox.qboxserver.domain.member.command.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SignUpReq(
    @Email val email: String,
    @NotBlank val nickname: String,
    @NotBlank val password: String,
    @NotNull val departId: Long,
)