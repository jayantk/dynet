
name := "dynet_scala_helpers"

scalaVersion := "2.11.8"

// This is where `make` does all its work, and it's where we'll do all our work as well.
val buildPath = sys.props.get("buildpath") match {
  case Some(p) => p
  case None => throw new IllegalArgumentException("must specify -Dbuildpath=")
}

// Look for the dynet_swig jar file there.
unmanagedBase := file( buildPath ).getAbsoluteFile

// Put all of the sbt generated classes there.
target := file(s"${buildPath}/target/")

// Put the uberjar there.
assemblyOutputPath in assembly := file(s"${buildPath}/dynet_swigJNI_scala.jar").getAbsoluteFile

fork in run := true

// Don't include Scala libraries in the jar
// see https://github.com/sbt/sbt-assembly/issues/3
// and http://stackoverflow.com/questions/15856739/assembling-a-jar-containing-only-the-provided-dependencies
assembleArtifact in packageScala := false

// And look there for java libraries when running.
javaOptions in run += s"-Djava.library.path=${buildPath}"