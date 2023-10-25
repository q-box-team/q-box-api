package site.qbox.qboxserver.domain.lecture.query

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.DisplayName
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.lecture.query.dto.LectureRes

@WebMvcTest(LectureQueryCtrl::class)
@DisplayName("LectureQueryCtrl")
class LectureQueryCtrlTest : WebClientDocsTest() {

    @MockkBean
    lateinit var lectureDao: LectureDao

    init {
        it("depart 별 lecture 전체 조회를 수행한다") {
            val depart = 4L
            every { lectureDao.findAllByDepartId(depart, any()) } returns
                    listOf(
                        LectureRes("컴퓨터구조","AAA" ,depart),
                        LectureRes("영어1","AAB" ,depart),
                        LectureRes("영어2","AAC" ,depart),
                        LectureRes("심리학기초","AAD" ,depart),
                        LectureRes("학국문학이해","AAE" ,depart),
                    )
            val params = LinkedMultiValueMap<String, String>()
            params["depart"] = depart.toString()
            params["page"] = "0"
            params["size"] = "5"

            val action = performGet("/lectures", params)

            action.andExpect(status().isOk())

            action.andDo(
                print(
                    "find-all-lectures",
                    queryParameters(
                        parameterWithName("depart").description("학과 ID"),
                        parameterWithName("page").description("page 번호"),
                        parameterWithName("size").description("페이지 당 보여줄 컨텐츠 수(default: 10)"),
                    ),
                    responseFields(
                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("과목명"),
                        fieldWithPath("[].code").type(JsonFieldType.STRING).description("과목코드"),
                        fieldWithPath("[].departId").type(JsonFieldType.NUMBER).description("학과ID"),
                    )
                )
            )

        }
    }

}
