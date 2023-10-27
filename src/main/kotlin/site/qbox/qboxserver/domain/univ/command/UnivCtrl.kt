package site.qbox.qboxserver.domain.univ.command

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import site.qbox.qboxserver.domain.univ.command.dto.CreateUnivReq
import site.qbox.qboxserver.global.dto.IdRes

@RestController
@RequestMapping("univ")
class UnivCtrl(
    private val univSvc: UnivSvc,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUniv(@RequestBody @Valid req: CreateUnivReq): IdRes<String> =
        univSvc.create(req)
}