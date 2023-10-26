package site.qbox.qboxserver.domain.answer.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.answer.command.entity.AnswerComment

interface AnswerCommentRepo : JpaRepository<AnswerComment, Long>