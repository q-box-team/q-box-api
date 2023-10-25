package site.qbox.qboxserver.domain.member.command.dto.event

data class SignedUpMemberEvent(
    val email: String,
    val rawPassword: String,
)