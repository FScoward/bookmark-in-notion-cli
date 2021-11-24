import models.notion._
import scopt.OParser

val builder = OParser.builder[Config]
case class Config(
    kwargs: Map[String, String] = Map(),
    databaseId: String = "",
    title: String = ""
)

val parser1 = {
  import builder._
  OParser.sequence(
    programName("scopt"),
    head("scopt", "4.x"),
    opt[String]('d', "databaseId")
      .action((x, c) => c.copy(databaseId = x))
      .text("databaseId is String property"),
    opt[String]('t', "title")
      .action((x, c) => c.copy(title = x))
      .text("title is String property")
  )
}
@main def hello(args: String*): Unit =
  val env = sys.env.getOrElse("NOTION_API_KEY", "dummy")
  println(env)

  OParser.parse(parser1, args, Config()) match {
    case Some(config) =>
      println(config.databaseId)
      post(apiKey = env, databaseId = config.databaseId, title = config.title)
    // do something
    case _ =>
    // arguments are bad, error message will have been displayed
  }

def post(apiKey: String, databaseId: String, title: String) = {
  import sttp.client3._
  import sttp.model._

  val query = s"""|
                 |{
                 |	"filter": {
                 |		"or": [
                 |			{
                 |				"property": "title",
                 |				"text": {
                 |					"contains": "${title}"
                 |				}
                 |			}
                 |		]
                 |	}
                 |}
                 |""".stripMargin

  import sttp.client3._
  import sttp.client3.circe._

  import io.circe._, io.circe.generic.semiauto._

  implicit val rDecoder: Decoder[ResponseResult] =
    deriveDecoder[ResponseResult]
  implicit val qDecoder: Decoder[QueryDatabaseResponse] =
    deriveDecoder[QueryDatabaseResponse]

  val request = basicRequest
    .headers(
      Header.contentType(MediaType.ApplicationJson),
      Header.authorization("Bearer", apiKey),
      Header("Notion-Version", "2021-08-16")
    )
    .body(query)
    .post(uri"https://api.notion.com/v1/databases/${databaseId}/query")
    .response(asJson[QueryDatabaseResponse])
  println(request.toCurl)
  val backend = HttpURLConnectionBackend()
  val response = request.send(backend)
  val responseBody = response.body

  val results = responseBody match {
    case Right(r) => r.results
    case Left(e)  => throw new Exception(e)
  }

  println(results.map(_.title))
  println(results.map(_.urlProperty))
}

case class QueryDatabaseResponse(
    `object`: String,
    results: Seq[ResponseResult]
)

case class ResponseResult(
    `object`: String,
    id: String,
    created_time: String,
    last_edited_time: String,
    parent: Map[String, String],
    archived: Boolean,
    url: String,
    properties: Map[String, Property],
    hasMore: Option[Boolean]
) {
  val title =
    properties
      .get("名前")
      .map(_.title)
      .getOrElse(None)
      .getOrElse(Nil)
      .map(_.text.content)
      .headOption
      .getOrElse("")

  val urlProperty =
    properties.get("URL").map(_.url).getOrElse(None).getOrElse("")
}
