pipeline {
    agent any

    environment {
        IMAGE_NAME = "banking-app:latest"
        SCANNER_HOME = "${tool 'sonar-scanner'}" // SonarQube Scanner path
    }

    triggers {
        pollSCM('H/5 * * * *') // SCM Polling every 5 minutes
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
                sh 'docker build -t ${IMAGE_NAME} .'  // ✅ Image name updated
            }
        }

        stage('Trivy FS Scan') {
            steps {
                sh "trivy fs --format table -o trivy-fs-report.html ."
            }
        }

        stage('Trivy Image Scan') {
            steps {
                sh "trivy image --format table -o trivy-image-report.html ${IMAGE_NAME}"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {  
                    sh "$SCANNER_HOME/bin/sonar-scanner -Dsonar.projectKey=bankingProject -Dsonar.projectName=bankingProject"
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

