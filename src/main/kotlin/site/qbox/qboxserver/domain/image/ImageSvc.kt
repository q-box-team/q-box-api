package site.qbox.qboxserver.domain.image

import org.springframework.web.multipart.MultipartFile
import site.qbox.qboxserver.domain.image.dto.ImageRes
import site.qbox.qboxserver.global.annotation.CommandService
import java.util.*

@CommandService
class ImageSvc(
    private val imageRepo: ImageRepo,
    private val imageValidator: ImageValidator,
) {
    fun save(file: MultipartFile): ImageRes {
        imageValidator.validate(file)
        return ImageRes(imageRepo.save(file, generateName(file)))
    }

    private fun generateName(file: MultipartFile): String =
        "${UUID.randomUUID()}.${extractExtension(file)}"

    private fun extractExtension(file: MultipartFile): String =
        file.originalFilename!!.substringAfterLast(".")
}