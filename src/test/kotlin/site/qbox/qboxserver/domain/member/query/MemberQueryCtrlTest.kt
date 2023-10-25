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
import site.qbox.qboxserver.domain.member.query.dto.MemberRes

@WebMvcTest(MemberQueryCtrl::class)
@DisplayName("MemberQueryCtrl")
class MemberQueryCtrlTest : WebClientDocsTest() {
    @MockkBean
    private lateinit var memberDao: MemberDao

    init {
        it("본인 조회를 수행한다") {
            every { memberDao.getByEmail(any()) } returns MemberRes("hello@email.com", "nicknick")

            val action = performGet("/members/me")

            action.andExpect(status().isOk())

            action.andDo(
                print(
                    "get-me",
                    responseFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    )
                )
            )
        }
    }
}