package site.qbox.qboxserver.global.ctrl

import io.kotest.core.annotation.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest


@WebMvcTest(ApiDocsController::class)
@DisplayName("ApiDocsController")
class ApiDocsControllerTest : WebClientDocsTest() {


    init {
        it("api-docs를 가져온다") {
            val action = performGet("/api-docs")

            action.andExpect(status().isOk())
        }
    }
}
