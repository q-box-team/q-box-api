package site.qbox.qboxserver.domain.member.command

import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.member.command.dto.SignUpReq
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.domain.member.email.svc.EmailAuthenticationSvc
import site.qbox.qboxserver.global.annotation.CommandService


@CommandService
class MemberSvc(
    private val memberRepo: MemberRepo,
    private val passwordEncoder: PasswordEncoder,
    private val emailAuthenticationSvc: EmailAuthenticationSvc,
) {
    fun signUp(req: SignUpReq) {
        emailAuthenticationSvc.checkAuthenticated(req.email)
        val newMember = Member(req.email, req.nickname, req.password, req.departId, passwordEncoder)
        memberRepo.save(newMember)
    }

}
