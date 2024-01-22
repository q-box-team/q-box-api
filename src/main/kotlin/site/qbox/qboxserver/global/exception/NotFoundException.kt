package site.qbox.qboxserver.global.exception

abstract class NotFoundException : RuntimeException {
        constructor() : super("Not Found")
        constructor(message: String) : super(message)
}


