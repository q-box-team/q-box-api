package site.qbox.qboxserver.domain.member.email.svc

import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionalEventListener
import site.qbox.qboxserver.domain.member.email.dto.event.RegisteredEmailEvent
import site.qbox.qboxserver.global.annotation.CommandService

@CommandService
class MailSvc(
    private val mailSender: JavaMailSender,

) {
    @Value("\${service.url}") private lateinit var serviceUrl: String
    @Async
    @TransactionalEventListener
    fun sendPassword(event: RegisteredEmailEvent) {
        val memeMessage: MimeMessage = mailSender.createMimeMessage()
        generateMailContent(memeMessage, event)
        mailSender.send(memeMessage)
    }

    private fun generateMailContent(
        memeMessage: MimeMessage,
        event: RegisteredEmailEvent
    ) {
        val messageHelper = MimeMessageHelper(memeMessage, false, "UTF-8")
        messageHelper.setTo(event.email)
        messageHelper.setSubject("[Q-BOX] 이메일 인증입니다.")
        messageHelper.setText("인증키는 [ ${event.key} ] 입니다")
    }

}