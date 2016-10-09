

name := "FnMarketServer"

version := "1.0"

lazy val `fnmarketserver` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"


libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test )

libraryDependencies += "commons-io" % "commons-io" % "2.4"

libraryDependencies += "org.springframework" % "spring-jdbc" % "4.3.3.RELEASE"


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  