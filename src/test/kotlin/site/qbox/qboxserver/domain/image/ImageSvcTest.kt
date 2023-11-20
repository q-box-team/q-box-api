package site.qbox.qboxserver.domain.image

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile

@SpringBootTest
class ImageSvcTest : DescribeSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var imageSvc: ImageSvc

    @MockkBean
    lateinit var imageRepo: ImageRepo

    val image = MockMultipartFile("asdfasdf.jpg", byteArrayOf(9))
    init {
        it("저장을 수행한다.") {
            val savedUrl = "https://hello.com/user/kkkkk-sdf.jpg"
            every { imageRepo.save(image, any(String::class)) } returns savedUrl

            val result = imageSvc.save(image)
            result.location shouldBe  savedUrl
        }
    }

}
