# Fast & Furious cinema

### Configuration
Before you run this application you need to configure OpenMovieDatabase api key. The key is loaded through an environment variable `OMDB_API_KEY`

If you're using bash simply run `export OMDB_API_KEY=<YOUR_API_KEY>`


### Running the project
You can use docker compose to start both the application and the database:
`docker compose up`

If you want to run the app separately (i.e. inside IntelliJ) add this env variable to your run configuration:
`SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/cinema`
The database might still be running inside a docker container, but the app itself is now launched outside of docker compose network - that's why we point it towards localhost.

### API documentation
Full API documentation is located in [/docs/api.yml](https://raw.githubusercontent.com/theaffable/cinema/refs/heads/main/docs/api.yaml). You can use [Swagger Editor](https://editor-next.swagger.io/) to view it

### Tests
I've used a http client to test API functionality, the requests file can be found [here](https://github.com/theaffable/cinema/blob/main/src/test/resources/requests.http)

### Q&A
**Q:** requirements state that I should use OpenAPI 2.0, but it's an old standard - should I use 3.0 instead?\
**A**: at first glance 2.0 seems to be good enough so I'll stick with that, but if needed I'll upgrade to 3.0

**Q:** How do I lock movie screening editing to owners only?\
**A:** Options to consider:  
  - `API_KEY`:
    - pros: easy to set up
    - cons: I'd need a separate mechanism to determine if someone can rate a movie or allow same client to rate a movie multiple times
  - `Basic Auth`:
    - pros: easy to set up, can be used for different purposes depending on what authorities we give to the users
    - cons: insecure, needs to be used together with HTTPS, but since this is a demo app I'm not that worried about it and I need to save time somewhere
  - `IP Whitelisting`:
    - pros: handled on infrastructure layer, doesn't affect application logic
    - cons: the app needs to be deployed for it to work and clients need to have a static IP
  - `Bearer Token`:
    - pros: industry standard, field tested, lots of libraries to help with implementation
    - cons: requires significantly more work than API_KEY / basic auth solutions

After some consideration I decided to go with Basic Auth since I can implement it quickly, and it can solve 2 most important issues:
  - verifying if the user is an owner
  - verifying if the user has already rated a movie

**Q:** Should I add pagination to movie screening and movies endpoints?\
**A:** No, I plan to implement filtering on the endpoint and both of those will have a rather limited amount of entries. Plus it's easy to add later on.

**Q:** How do I make sure that movie reviews don't get flooded with artificial ratings?\
**A:** The plan is to allow only one vote per user & movie

### Ideas & what's next
- add showtime booking
- add movie description/actors/directors full text filtering
- add movie sorting by rating/imdb_rating
- use Flyway for migration management