plugins {
    java
    `maven-publish`
}

extensions.configure<PublishingExtension> {
    repositories {
        maven {
            credentials(PasswordCredentials::class.java)

            name = if (version.toString().endsWith("SNAPSHOT")) "paperSnapshots" else "paper" // "paper" is seemingly not defined
            val base = "https://artifactory.papermc.io/artifactory"
            val releasesRepoUrl = "$base/releases/"
            val snapshotsRepoUrl = "$base/snapshots/"
            setUrl(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
        }

        // Shard private repo
        maven {
            name = "shard"
            url = uri("https://repo.shard.rip/private")
            credentials {
                username = (findProperty("repoUser") as String?) ?: ""
                password = (findProperty("repoPass") as String?) ?: ""
            }
        }
    }
}
