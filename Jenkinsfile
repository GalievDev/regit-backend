pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Push to Registry') {
            steps {
                script {
                    docker.withRegistry('https://registry.gitlab.com/', 'CI_REGISTRY_PASSWORD') {
                        docker.image("regit-backend:${env.BUILD_ID}").push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }
}
