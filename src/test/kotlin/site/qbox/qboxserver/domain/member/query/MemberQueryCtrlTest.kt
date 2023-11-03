package site.qbox.qboxserver.domain.member.query

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.DisplayName
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.depart.query.dto.DepartRes
import site.qbox.qboxserver.domain.member.query.dto.MemberRes

@WebMvcTest(MemberQueryCtrl::class)
@DisplayName("MemberQueryCtrl")
class MemberQueryCtrlTest : WebClientDocsTest() {
    @MockkBean
    private lateinit var memberDao: MemberDao

    init {
        it("본인 조회를 수행한다") {
            every { memberDao.getByEmail(any()) } returns MemberRes("hello@email.com", "nicknick", DepartRes(1,"경영학과", "email.com"))

            val action = performGet("/members/me")

            action.andExpect(status().isOk())

            action.andDo(
                print(
                    "get-me",
                    responseFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("depart.id").type(JsonFieldType.NUMBER).description("학과 ID"),
                        fieldWithPath("depart.name").type(JsonFieldType.STRING).description("학과 이름"),
                        fieldWithPath("depart.univId").type(JsonFieldType.STRING).description("대학 ID"),
                    )
                )
            )
        }
    }
}