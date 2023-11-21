package site.qbox.qboxserver.domain.image

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.annotation.DisplayName
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.image.dto.ImageRes

@WebMvcTest(ImageCtrl::class)
@DisplayName("ImageCtrl")
class ImageCtrlTest : WebClientDocsTest() {
    @MockkBean
    lateinit var imageSvc: ImageSvc

    init {
        it("inage 저장을 수행한다") {
            every { imageSvc.save(any()) } returns ImageRes("https://q-box.site/images/asdf-asdf-asdf-asdf.jpg")

            val action = performFormData(HttpMethod.POST, "/images", "file");

            action.andExpect(status().isCreated)

            action.andDo(
                print(
                    "save-image",
                    responseFields(
                        fieldWithPath("location").description("이미지 파일 경로")
                    )
                )
            )
        }
    }
}
