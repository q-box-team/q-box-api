package site.qbox.qboxserver.domain.member.command.svc

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionalEventListener
import site.qbox.qboxserver.domain.member.command.dto.event.SignedUpMemberEvent
import site.qbox.qboxserver.global.annotation.CommandService

@CommandService
class MailSvc(
    private val mailSender: JavaMailSender,
) {
    @Async
    @TransactionalEventListener
    fun sendPassword(event: SignedUpMemberEvent) {
        val memeMessage: MimeMessage = mailSender.createMimeMessage()
        generateMailContent(memeMessage, event)
        mailSender.send(memeMessage)
    }

    private fun generateMailContent(
        memeMessage: MimeMessage,
        event: SignedUpMemberEvent
    ) {
        val messageHelper = MimeMessageHelper(memeMessage, false, "UTF-8")
        messageHelper.setTo(event.email)
        messageHelper.setSubject("[Q-BOX]임시 비밀번호입니다.")
        messageHelper.setText("임시 비밀번호는 [ ${event.rawPassword} ] 입니다.")
    }

}