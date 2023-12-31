package site.qbox.qboxserver.domain.image

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import site.qbox.qboxserver.domain.image.exception.NotAllowedImageException

@Component
class ImageValidator {
    companion object {
        private val ALLOW_EXTENSIONS = listOf("png", "jpg", "jpeg", "gif", "bmp", "webp", "ico", "tiff", "tif", "svg")
    }

    fun validate(file: MultipartFile) {
        require(extractExtension(file) in ALLOW_EXTENSIONS) { throw NotAllowedImageException() }
    }

    private fun extractExtension(file: MultipartFile): String =
        file.name.substringAfterLast(".")
}
