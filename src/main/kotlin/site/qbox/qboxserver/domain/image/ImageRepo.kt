package site.qbox.qboxserver.domain.image

import org.springframework.web.multipart.MultipartFile

interface ImageRepo {
    fun save(file: MultipartFile, name: String) : String
}