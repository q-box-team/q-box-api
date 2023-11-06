package site.qbox.qboxserver.domain.member.email

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import site.qbox.qboxserver.domain.member.email.dto.CertifyKeyReq
import site.qbox.qboxserver.domain.member.email.dto.RegisterEmailReq
import site.qbox.qboxserver.domain.member.email.svc.EmailAuthenticationSvc

@RestController
@RequestMapping("emails")
class EmailAuthenticationCtrl (
    private val emailAuthenticationSvc: EmailAuthenticationSvc,
){
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody @Valid req: RegisterEmailReq) =
        emailAuthenticationSvc.register(req)

    @PostMapping("key")
    fun authenticate(@RequestBody @Valid req : CertifyKeyReq) =
        emailAuthenticationSvc.authenticate(req)

}