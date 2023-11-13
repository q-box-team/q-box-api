package site.qbox.qboxserver.domain.image.command

import org.springframework.web.multipart.MultipartFile
import site.qbox.qboxserver.domain.image.command.dto.ImageRes
import site.qbox.qboxserver.global.annotation.CommandService
import java.util.*

@CommandService
class ImageSvc(
    private val imageRepo: ImageRepo,
) {
    fun save(file: MultipartFile): ImageRes {
        return ImageRes(imageRepo.save(file, generateName(file)))
    }

    private fun generateName(file: MultipartFile): String =
        "${UUID.randomUUID()}.${extractExtension(file)}"

    private fun extractExtension(file: MultipartFile): String =
        file.originalFilename!!.substringAfterLast(".")
}