import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubIssues
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.11"

project {

    vcsRoot(HttpsGithubComZhaojing1987springPetclinicRefsHeadsMain1)

    buildType(Build1)
    buildType(Build)

    features {
        githubIssues {
            id = "PROJECT_EXT_4"
            displayName = "zhaojing1987/spring-petclinic"
            repositoryURL = "https://github.com/zhaojing1987/spring-petclinic"
            authType = accessToken {
                accessToken = "credentialsJSON:69e87082-35f6-4662-b7c4-2ab9c42df02e"
            }
            param("tokenId", "")
        }
    }
}

object Build : BuildType({
    name = "Build"

    artifactRules = "target/*.jar => target"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            id = "Maven2"
            goals = "clean package"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
        commitStatusPublisher {
            vcsRootExtId = "${DslContext.settingsRoot.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:78ad34e0-40b4-4724-b670-c7f2f9f47051"
                }
            }
        }
        notifications {
            notifierSettings = emailNotifier {
                email = "jing.zhao@websoft9.com"
            }
            buildFailed = true
            buildFinishedSuccessfully = true
        }
        pullRequests {
            vcsRootExtId = "${DslContext.settingsRoot.id}"
            provider = github {
                authType = token {
                    token = "credentialsJSON:78ad34e0-40b4-4724-b670-c7f2f9f47051"
                }
                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
            }
        }
    }
})

object Build1 : BuildType({
    name = "Build (1)"

    vcs {
        root(HttpsGithubComZhaojing1987springPetclinicRefsHeadsMain1)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

    dependencies {
        snapshot(Build) {
        }
    }
})

object HttpsGithubComZhaojing1987springPetclinicRefsHeadsMain1 : GitVcsRoot({
    name = "https://github.com/zhaojing1987/spring-petclinic#refs/heads/main (1)"
    url = "https://github.com/zhaojing1987/spring-petclinic"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "zhaojing1987"
        password = "credentialsJSON:78ad34e0-40b4-4724-b670-c7f2f9f47051"
    }
})
