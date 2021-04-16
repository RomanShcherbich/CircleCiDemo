pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Testing') {
            steps {
                // Get some code from a GitHub repository
                git branch: '$BRANCH', changelog: false, poll: false, url: 'https://github.com/RomanShcherbich/CircleCiDemo'

                // Run Maven on a Unix agent.
                sh "mvn test -Dmaven.test.failure.ignore=true"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }

        stage('Allure-report') {
            steps {
                script {
                    allure ([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                        ])
                }
            }
        }
    }
}
