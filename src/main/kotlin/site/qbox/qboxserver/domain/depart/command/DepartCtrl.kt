package site.qbox.qboxserver.domain.depart.command

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import site.qbox.qboxserver.domain.depart.command.dto.CreateDepartReq
import site.qbox.qboxserver.global.dto.IdRes

@RestController
@RequestMapping("departs")
class DepartCtrl(
    private val departSvc: DepartSvc,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createDepart(@RequestBody @Valid req: CreateDepartReq): IdRes<Long> = departSvc.create(req)
}