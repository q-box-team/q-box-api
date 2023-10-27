package site.qbox.qboxserver.domain.depart.query

import site.qbox.qboxserver.domain.depart.query.dto.DepartRes
import site.qbox.qboxserver.global.annotation.QueryService

@QueryService
class DepartDao(
    private val departQueryRepo: DepartQueryRepo,
) {
    fun getAllByGroup(group: String): List<DepartRes> =
        departQueryRepo.findAllByUnivId(group).map(::DepartRes)

}
