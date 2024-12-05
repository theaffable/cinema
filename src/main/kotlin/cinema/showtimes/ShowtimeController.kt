package cinema.showtimes

import cinema.movies.MovieId
import java.time.ZonedDateTime
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ShowtimeController(
    private val showtimeService: ShowtimeService
) {

    @GetMapping("/showtimes")
    fun getAll(
        @RequestParam("movie_id") movieId: MovieId?,
        @RequestParam("date_start_gte") dateStartGte: ZonedDateTime?,
        @RequestParam("date_start_lte") dateStartLte: ZonedDateTime?
    ): List<ShowtimeResponse> =
        showtimeService.findBy(movieId = movieId, startsBefore = dateStartLte, startsAfter = dateStartGte).map { it.toResponse() }

    @PostMapping("/showtimes")
    fun create(@RequestBody request: CreateShowtimeRequest): ShowtimeResponse =
        showtimeService.create(
            movieCatalogId = request.movieCatalogId,
            dateTimeStart = request.dateStart,
            dateTimeEnd = request.dateEnd,
            priceOverride = request.priceOverride
        ).toResponse()
}

private fun Showtime.toResponse() = ShowtimeResponse(
    showtimeId = this.id,
    movieId = this.movie.id,
    title = this.movie.title,
    dateStart = this.dateStart,
    dateEnd = this.dateEnd,
    priceOverride = this.priceOverride
)