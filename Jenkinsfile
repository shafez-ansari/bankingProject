pipeline {
    agent any

    environment {
        IMAGE_NAME = "banking-app:latest"
        SCANNER_HOME = tool(name: 'sonar-scanner') // SonarQube Scanner path
    }

    triggers {
        pollSCM('H/2 * * * *') // SCM Polling every 2 minutes
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the project...'
                git branch: 'main', url: 'https://github.com/shafez-ansari/bankingProject.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh 'mvn -f pom.xml clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Trivy FS Scan') {
            steps {
                script {
                    echo 'Running Trivy filesystem scan...'
                    def status = sh(script: 'trivy fs --exit-code 0 --no-progress --severity HIGH,CRITICAL . || true', returnStatus: true)
                    if (status != 0) {
                        error("Trivy FS Scan failed!")
                    }
                }
            }
        }

        stage('Trivy Image Scan') {
            steps {
                script {
                    echo 'Running Trivy image scan...'
                    def status = sh(script: 'trivy image --exit-code 0 --no-progress --severity HIGH,CRITICAL $IMAGE_NAME || true', returnStatus: true)
                    if (status != 0) {
                        error("Trivy Image Scan failed!")
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh """
                        $SCANNER_HOME/bin/sonar-scanner \
                        -Dsonar.projectKey=bankingProject \
                        -Dsonar.projectName=bankingProject \
                        -Dsonar.sources=.
                    """
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo 'Deploying application with Docker Compose...'
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        failure {
            echo 'Build failed! Check the logs for errors.'
        }
    }
}

