pipeline {
    agent any

    environment {
        IMAGE_NAME = "banking-app:latest"
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
                echo 'Running Trivy filesystem scan...'
                sh 'trivy fs --exit-code 0 --no-progress --severity HIGH,CRITICAL .'
            }
        }

        stage('Trivy Image Scan') {
            steps {
                echo 'Running Trivy image scan...'
                sh 'trivy image --exit-code 0 --no-progress --severity HIGH,CRITICAL $IMAGE_NAME'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh "$SCANNER_HOME/bin/sonar-scanner -Dsonar.projectKey=bankingProject -Dsonar.projectName=bankingProject -Dsonar.sources=."
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

