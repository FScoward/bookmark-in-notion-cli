package models.notion

import scopt.OParser
import io.circe._, io.circe.generic.semiauto._

implicit val titleDecoder: Decoder[Title] =
  deriveDecoder[Title]
case class Title(
    `type`: String,
    text: Text,
    annotation: Option[Map[String, Option[String]]],
    plain_text: String,
    href: Option[String]
)
