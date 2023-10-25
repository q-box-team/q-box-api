package site.qbox.qboxserver.domain.univ.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.univ.command.entity.Univ

interface UnivRepo : JpaRepository<Univ, String>