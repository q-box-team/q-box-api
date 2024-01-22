package site.qbox.qboxserver.global.ctrl

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import site.qbox.qboxserver.global.dto.ErrorRes
import site.qbox.qboxserver.global.exception.BadRequestException
import site.qbox.qboxserver.global.exception.NotFoundException
import site.qbox.qboxserver.global.exception.UnauthorizedException

@RestControllerAdvice
class ExceptionCtrl {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgument(e : IllegalArgumentException) =
        ErrorRes(e.message!!)

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequest(e : BadRequestException) =
        ErrorRes(e.message!!)

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFound(e : NotFoundException) =
        ErrorRes(e.message!!)

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun unauthorized(e : UnauthorizedException) =
        ErrorRes(e.message!!)

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun runtime(e : RuntimeException) =
        ErrorRes(e.message!!)
}