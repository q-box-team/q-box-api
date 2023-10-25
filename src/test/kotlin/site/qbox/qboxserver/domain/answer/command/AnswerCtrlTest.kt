package site.qbox.qboxserver.domain.answer.command

import io.kotest.core.spec.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.answer.command.dto.CreateAnswerReq

@WebMvcTest(AnswerCtrl::class)
@DisplayName("AnswerCtrl")
class AnswerCtrlTest : WebClientDocsTest() {
    @MockBean
    lateinit var answerSvc: AnswerSvc

    init {
        it("answer 생성을 수행한다") {
            val req = CreateAnswerReq("내용입니다", 5)

            val action = performPost("/answers", req)

            action.andExpect(status().isCreated)

            action.andDo(
                print("create-answer",
                    requestFields(
                        fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용"),
                        fieldWithPath("question").type(JsonFieldType.NUMBER).description("관련 Question ID"),
                    ))
            )
        }
    }

}
