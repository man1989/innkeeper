package org.zalando.spearheads.innkeeper.api

import java.time.LocalDateTime
import scala.collection.immutable.Seq

case class RouteName(name: String) {

  RouteName.validRouteNamePattern.r.unapplySeq(name) match {
    case None => throw InvalidRouteNameException
    case _    =>
  }
}

object RouteName {
  val validRouteNamePattern = "[a-zA-Z][a-zA-Z0-9_]*"
}

object InvalidRouteNameException
  extends RuntimeException(
    s"Invalid route name. The name should match ${RouteName.validRouteNamePattern}")

object InvalidUsereNameException
  extends RuntimeException(
    s"Invalid uid for your token")

sealed trait Route {

  def name: RouteName

  def route: NewRoute

  def description: Option[String]

}

case class RouteIn(
  pathId: Long,
  name: RouteName,
  route: NewRoute,
  activateAt: Option[LocalDateTime],
  disableAt: Option[LocalDateTime] = None,
  description: Option[String] = None) extends Route

case class RouteOut(
  id: Long,
  pathId: Long,
  name: RouteName,
  route: NewRoute,
  createdAt: LocalDateTime,
  activateAt: LocalDateTime,
  ownedByTeam: TeamName,
  createdBy: UserName,
  disableAt: Option[LocalDateTime] = None,
  description: Option[String] = None,
  deletedAt: Option[LocalDateTime] = None,
  deletedBy: Option[String] = None) extends Route

case class NewRoute(
  predicates: Option[Seq[Predicate]] = Some(Seq.empty),
  filters: Option[Seq[Filter]] = Some(Seq.empty),
  endpoint: Option[String] = None)

case class Predicate(name: String, args: Seq[Arg])

case class Filter(name: String, args: Seq[Arg])

sealed trait Arg {
  def value: String
}

object Arg {
  val string = "string"
  val number = "number"
  val regex = "regex"
}

case class RegexArg(value: String) extends Arg
case class NumericArg(value: String) extends Arg
case class StringArg(value: String) extends Arg
