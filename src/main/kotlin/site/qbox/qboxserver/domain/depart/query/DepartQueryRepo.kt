package site.qbox.qboxserver.domain.depart.query

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.depart.command.entity.Depart

interface DepartQueryRepo : JpaRepository<Depart, Long> {
    fun findAllByUnivId(univId: String): List<Depart>
}