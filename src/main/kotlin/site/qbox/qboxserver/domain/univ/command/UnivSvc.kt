package site.qbox.qboxserver.domain.univ.command

import site.qbox.qboxserver.domain.univ.command.dto.CreateUnivReq
import site.qbox.qboxserver.domain.univ.command.entity.Univ
import site.qbox.qboxserver.global.annotation.CommandService
import site.qbox.qboxserver.global.dto.IdRes

@CommandService
class UnivSvc(
    private val univRepo: UnivRepo,
) {
    fun create(req: CreateUnivReq): IdRes<String> {
        val savedUniv = univRepo.save(Univ(req.emailDomain, req.name))
        return IdRes(savedUniv.emailDomain)
    }

}