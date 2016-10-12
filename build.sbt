

name := "FnMarketServer"

version := "1.0"

lazy val `fnmarketserver` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

//resolvers += "Project Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repository"

libraryDependencies ++= Seq(jdbc, cache, ws, specs2 % Test)

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.4",
  "org.springframework" % "spring-jdbc" % "4.3.3.RELEASE",
  "com.oracle" % "ojdbc6" % "11.2.0.4"
)


unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  