Releasing kWicket
=================

Snapshots
---------

The task to run to upload a snapshot to [oss.jfrog.org](https://oss.jfrog.org/) is `artifactoryPublish`.

The username and API key must be supplied on the command-line. Examples:

`.\gradlew.bat -PbintrayUser=<...> -PbintrayApiKey=<...> artifactoryPublish`
or
`.\gradlew.bat -DBINTRAY_USER=<...> -DBINTRAY_API_KEY=<...> artifactoryPublish`

Releases
--------

The task to run to upload a release to [bintray.com](https://bintray.com/) is `bintrayUpload`.

The username and API key must be supplied on the command-line. Examples:

`.\gradlew.bat -PbintrayUser=<...> -PbintrayApiKey=<...> bintrayUpload`
or
`.\gradlew.bat -DBINTRAY_USER=<...> -DBINTRAY_API_KEY=<...> bintrayUpload`