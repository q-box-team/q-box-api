package site.qbox.qboxserver.domain.univ.command.dto

import jakarta.validation.constraints.NotBlank

data class CreateUnivReq(
    @NotBlank val emailDomain: String,
    @NotBlank val name: String,
)