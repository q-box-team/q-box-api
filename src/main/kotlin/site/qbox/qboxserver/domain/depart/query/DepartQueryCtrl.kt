package site.qbox.qboxserver.domain.depart.query

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.qbox.qboxserver.domain.depart.query.dto.DepartRes
import site.qbox.qboxserver.global.security.MemberAuth

@RestController
@RequestMapping("departs")
class DepartQueryCtrl (
    private val departDao: DepartDao
){

    @GetMapping
    fun getAllByAuth(@AuthenticationPrincipal auth: MemberAuth) : List<DepartRes> =
        departDao.getAllByGroup(auth.group)
}