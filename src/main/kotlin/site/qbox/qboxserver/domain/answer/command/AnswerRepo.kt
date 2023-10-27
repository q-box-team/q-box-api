package site.qbox.qboxserver.domain.answer.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.answer.command.entity.Answer
import site.qbox.qboxserver.domain.answer.command.entity.AnswerId

interface AnswerRepo : JpaRepository<Answer, AnswerId>