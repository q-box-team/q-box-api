package site.qbox.qboxserver.domain.member.command.svc

import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.dto.SignUpReq
import site.qbox.qboxserver.domain.member.command.dto.event.SignedUpMemberEvent
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.global.annotation.CommandService
import java.util.*


@CommandService
class MemberSvc(
    private val memberRepo: MemberRepo,
    private val passwordEncoder: PasswordEncoder,
    private val eventPublisher: ApplicationEventPublisher
) {
    fun signUp(req: SignUpReq) {
        val password = generatePassword()
        val newMember = Member(req.email, req.nickname, password, passwordEncoder)
        memberRepo.save(newMember)
        eventPublisher.publishEvent(SignedUpMemberEvent(req.email, password))
    }

    private fun generatePassword() = UUID.randomUUID().toString().substring(0, 8)

}
