package site.qbox.qboxserver.domain.member.command

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import site.qbox.qboxserver.domain.member.command.dto.SignUpReq
import site.qbox.qboxserver.domain.member.command.svc.MemberSvc

@RestController
@RequestMapping("members")
class MemberCtrl(
    private val memberSvc: MemberSvc,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody @Valid req: SignUpReq) = memberSvc.signUp(req)

}