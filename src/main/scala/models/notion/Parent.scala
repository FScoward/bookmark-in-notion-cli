package models.notion

import models.notion._
import scopt.OParser
import io.circe._, io.circe.generic.semiauto._

implicit val parentDecoder: Decoder[Parent] =
  deriveDecoder[Parent]
case class Parent(
    id: String,
    created_time: String,
    last_editedTime: String
)
