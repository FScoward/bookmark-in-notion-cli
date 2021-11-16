import scopt.OParser

val builder = OParser.builder[Config]
val parser1 = {
  import builder._
  OParser.sequence(
    programName("scopt"),
    head("scopt", "4.x"),
    opt[Int]('f', "foo")
      .action((x, c) => c.copy(foo = x))
      .text("foo is an integer property")
  )
}
@main def hello(args: String*): Unit =
  val env = sys.env.getOrElse("NOTION_TOKEN", "dummy")
  println(env)
  println(args)

  OParser.parse(parser1, args, Config()) match {
    case Some(config) =>
    // do something
    case _ =>
    // arguments are bad, error message will have been displayed
  }

case class Config(
    foo: Int = -1,
    xyz: Boolean = false,
    kwargs: Map[String, String] = Map()
)
