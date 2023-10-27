package site.qbox.qboxserver.domain.depart.command.dto

import jakarta.validation.constraints.NotBlank

data class CreateDepartReq(
    @NotBlank val name: String,
    @NotBlank val univId: String,
)
