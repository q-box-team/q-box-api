package site.qbox.qboxserver.domain.image.command

import org.springframework.web.multipart.MultipartFile

interface ImageRepo {
    fun save(file: MultipartFile, name: String) : String
}