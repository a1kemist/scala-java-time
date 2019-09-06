
addSbtPlugin("com.47deg"  % "sbt-microsites" % "0.9.3")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.2")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.2")

addSbtPlugin("org.scala-native" % "sbt-crossproject"         % "0.2.2")

addSbtPlugin("org.scala-native" % "sbt-scalajs-crossproject" % "0.2.2")

addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.4.0-SNAPSHOT")

val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.28")

addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "0.6.1")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.6.1")

addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "0.3.2")
