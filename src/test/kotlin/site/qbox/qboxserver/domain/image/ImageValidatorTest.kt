package site.qbox.qboxserver.domain.image

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import site.qbox.qboxserver.domain.image.exception.NotAllowedImageException

@SpringBootTest
class ImageValidatorTest : DescribeSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var imageValidator: ImageValidator

    init {
        it("검증을 수행한다") {
            val image = MockMultipartFile("hello.jpg", byteArrayOf(9))
            shouldNotThrow<NotAllowedImageException> {
                imageValidator.validate(image)
            }
        }

        it("확장자가 명시되어있지 않으면 예외를 반환한다") {
            val image = MockMultipartFile("asdfasdf", byteArrayOf(9))
            shouldThrow<NotAllowedImageException> {
                imageValidator.validate(image)
            }
        }

        it("허용하지 않은 확장자면 예외를 반환한다") {
            val image = MockMultipartFile("hello.txt", byteArrayOf(9))
            shouldThrow<NotAllowedImageException> {
                imageValidator.validate(image)
            }
        }

    }

}
