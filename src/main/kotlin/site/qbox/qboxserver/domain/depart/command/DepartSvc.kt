package site.qbox.qboxserver.domain.depart.command

import site.qbox.qboxserver.domain.depart.command.dto.CreateDepartReq
import site.qbox.qboxserver.domain.depart.command.entity.Depart
import site.qbox.qboxserver.global.annotation.CommandService
import site.qbox.qboxserver.global.dto.IdRes

@CommandService
class DepartSvc(
    private val departRepo: DepartRepo,
) {
    fun create(req: CreateDepartReq): IdRes<Long> {
        val savedDepart = departRepo.save(Depart(req.name, req.univId))
        return IdRes(savedDepart.id)
    }
}