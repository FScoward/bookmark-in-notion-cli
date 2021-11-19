package models.notion

import io.circe._, io.circe.generic.semiauto._

implicit val textDecoder: Decoder[Text] =
  deriveDecoder[Text]

case class Text(
    content: String,
    link: Option[String]
)
