package site.qbox.qboxserver.global.exception

abstract class BadRequestException
    : RuntimeException {
    constructor() : super("bad request")
    constructor(message: String) : super(message)
}