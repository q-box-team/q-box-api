package site.qbox.qboxserver.domain.member.email.svc

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import site.qbox.qboxserver.domain.member.email.dto.CertifyKeyReq
import site.qbox.qboxserver.domain.member.email.dto.RegisterEmailReq
import site.qbox.qboxserver.domain.member.email.dto.event.RegisteredEmailEvent
import site.qbox.qboxserver.domain.member.email.entity.Email
import site.qbox.qboxserver.domain.member.email.entity.EmailAuthKey
import site.qbox.qboxserver.domain.member.email.exception.EmailNotAuthenticatedException
import site.qbox.qboxserver.domain.member.email.infra.AuthenticatedEmailRepo
import site.qbox.qboxserver.domain.member.email.infra.EmailAuthKeyRepo
import java.util.*

@Service
class EmailAuthenticationSvc (
    private val authenticationEmailRepo: AuthenticatedEmailRepo,
    private val emailAuthKeyRepo: EmailAuthKeyRepo,
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun register(req : RegisterEmailReq) {
        val emailAuthKey = EmailAuthKey(generateKey(),req.email)
        emailAuthKeyRepo.save(emailAuthKey)
        eventPublisher.publishEvent(RegisteredEmailEvent(emailAuthKey))
    }
    private fun generateKey() : String =
        UUID.randomUUID().toString().substring(0, 8)

    fun authenticate(req: CertifyKeyReq) {
        val target = emailAuthKeyRepo.findById(req.key)
            .orElseThrow { EmailNotAuthenticatedException() }
        authenticationEmailRepo.save(Email(target.email))
    }

    fun checkAuthenticated(email: String) {
        authenticationEmailRepo.findById(email)
            .orElseThrow(::EmailNotAuthenticatedException)
    }
}