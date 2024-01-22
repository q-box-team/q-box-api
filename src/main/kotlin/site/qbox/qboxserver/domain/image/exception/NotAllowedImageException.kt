package site.qbox.qboxserver.domain.image.exception

import site.qbox.qboxserver.global.exception.BadRequestException

class NotAllowedImageException : BadRequestException("잘못된 이미지 형식입니다.")