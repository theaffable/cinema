package cinema

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler
    fun handleMovieNotFound(e: MovieNotFound): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)

    @ExceptionHandler
    fun handleCatalogEntryNotFound(e: CatalogEntryNotFound): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)

    @ExceptionHandler
    fun handleHttpClientException(e: HttpClientException): ResponseEntity<String> {
        val msg = "Internal http call returned status ${e.statusCode}"
        return ResponseEntity.status(HttpStatus.resolve(e.statusCode) ?: HttpStatus.BAD_REQUEST).body(msg)
    }
}