import scopt.OParser

val builder = OParser.builder[Config]
val parser1 = {
  import builder._
  OParser.sequence(
    programName("scopt"),
    head("scopt", "4.x"),
    opt[Int]('f', "foo")
      .action((x, c) => c.copy(foo = x))
      .text("foo is an integer property"),
    opt[String]('d', "databaseId")
      .action((x, c) => c.copy(databaseId = x))
      .text("databaseId is String property")
  )
}
@main def hello(args: String*): Unit =
  val env = sys.env.getOrElse("NOTION_API_KEY", "dummy")
  println(env)

  OParser.parse(parser1, args, Config()) match {
    case Some(config) =>
      println(config.databaseId)
      post(apiKey = env, databaseId = config.databaseId)
    // do something
    case _ =>
    // arguments are bad, error message will have been displayed
  }

case class Config(
    foo: Int = -1,
    xyz: Boolean = false,
    kwargs: Map[String, String] = Map(),
    databaseId: String = ""
)

def post(apiKey: String, databaseId: String) = {
  import sttp.client3._
  import sttp.model._

  val query = """|{
                 |  "property": "name",
                 |  "text": {
                 |    is_not_empty: "false"
                 |  }
                 |}
                 |""".stripMargin

  val request = basicRequest
    .headers(
      Header.contentType(MediaType.ApplicationJson),
      Header.authorization("Bearer", apiKey),
      Header("Notion-Version", "2021-08-16")
    )
    .body(query)
    .post(uri"https://api.notion.com/v1/databases/${databaseId}/query")
  println(request.toCurl)
  val backend = HttpURLConnectionBackend()
  val response = request.send(backend)
  println(response)

}
