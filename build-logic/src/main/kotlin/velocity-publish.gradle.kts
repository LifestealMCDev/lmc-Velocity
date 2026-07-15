plugins {
    java
    `maven-publish`
}

extensions.configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            credentials(PasswordCredentials::class.java)

            name = if (version.toString()
                    .endsWith("SNAPSHOT")
            ) "paperSnapshots" else "paper" // "paper" is seemingly not defined
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
                username = (project.findProperty("shard.username") as String?) ?: System.getenv("SHARD_USERNAME")
                password = (project.findProperty("shard.password") as String?) ?: System.getenv("SHARD_PASSWORD")
            }
        }
    }
}
