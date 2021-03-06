package org.zalando.spearheads.innkeeper.dao

sealed trait Embed

case object PathEmbed extends Embed

case object HostsEmbed extends Embed

case object UnknownEmbed extends Embed