package site.qbox.qboxserver.domain.answer.query

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.annotation.DisplayName
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.util.LinkedMultiValueMap
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.answer.query.dto.AnswerCommentRes
import site.qbox.qboxserver.domain.answer.query.dto.AnswerRes
import site.qbox.qboxserver.domain.member.query.dto.MemberSummary

@WebMvcTest(AnswerQueryCtrl::class)
@DisplayName("AnswerQueryCtrl")
class AnswerQueryCtrlTest : WebClientDocsTest() {

    @MockkBean
    lateinit var answerDao: AnswerDao

    init {
        it("Question을 통한 목록 조회를 수행한다") {
            val questionId = 5L
            every { answerDao.findAllByQuestion(questionId) } returns
                    listOf(
                        AnswerRes("내용1", questionId, MemberSummary("aaa@bb.com", "닉넴1"), listOf(
                            AnswerCommentRes(1, "댓글1",MemberSummary("kkk@bb.com", "댓글작성자")),
                            AnswerCommentRes(2, "댓글2",MemberSummary("kkk2@bb.com", "댓글작성자2"))
                        )),
                        AnswerRes("내용3", questionId, MemberSummary("hhh@bb.com", "닉넴3"), listOf(
                            AnswerCommentRes(3, "댓글1",MemberSummary("kkk@bb.com", "댓글작성자")),
                            AnswerCommentRes(4, "댓글2",MemberSummary("kkk2@bb.com", "댓글작성자2"))
                        )),
                        AnswerRes("내용4", questionId, MemberSummary("jjj@bb.com", "닉넴4"), listOf(
                            AnswerCommentRes(5, "댓글1",MemberSummary("kkk@bb.com", "댓글작성자")),
                            AnswerCommentRes(6, "댓글2",MemberSummary("kkk2@bb.com", "댓글작성자2"))
                        )),
                        AnswerRes("내용2", questionId, MemberSummary("bbb@bb.com", "닉넴2"), listOf(
                            AnswerCommentRes(7, "댓글1",MemberSummary("kkk@bb.com", "댓글작성자"))
                        )),
                    )

            val params = LinkedMultiValueMap<String, String>()
            params["question"] = questionId.toString()

            val action = performGet("/answers", params)

            action.andDo(
                print(
                    "find-all-answers",
                    queryParameters(
                        parameterWithName("question").description("question ID"),
                    ),
                    responseFields(
                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("답변 내용"),
                        fieldWithPath("[].questionId").type(JsonFieldType.NUMBER).description("question ID"),
                        fieldWithPath("[].writer.email").type(JsonFieldType.STRING).description("작성자 email"),
                        fieldWithPath("[].writer.nickname").type(JsonFieldType.STRING).description("작성자 Nickname"),
                        fieldWithPath("[].comments.[].id").type(JsonFieldType.NUMBER).description("댓글 ID").optional(),
                        fieldWithPath("[].comments.[].content").type(JsonFieldType.STRING).description("댓글 내용").optional(),
                        fieldWithPath("[].comments.[].content").type(JsonFieldType.STRING).description("댓글 내용").optional(),
                        fieldWithPath("[].comments.[].writer.email").type(JsonFieldType.STRING).description("댓글 작성자 email").optional(),
                        fieldWithPath("[].comments.[].writer.nickname").type(JsonFieldType.STRING).description("댓글 작성자 Nickname").optional(),
                        )
                )
            )
        }
    }

}
