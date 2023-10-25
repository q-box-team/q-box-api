package site.qbox.qboxserver.domain.depart.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.depart.command.entity.Depart

interface DepartRepo : JpaRepository<Depart, Long>