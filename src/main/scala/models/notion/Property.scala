package models.notion

import models.notion._
import scopt.OParser
import io.circe._, io.circe.generic.semiauto._

implicit val propertyDecoder: Decoder[Property] =
  deriveDecoder[Property]

case class Property(
    id: String,
    `type`: String,
    title: Option[Seq[Title]]
)
