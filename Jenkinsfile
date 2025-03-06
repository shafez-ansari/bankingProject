pipeline {
    agent any
    triggers {
        pollSCM('H/2 * * * *')  // Har 2 min me SCM changes check karega
    }
    environment {
        IMAGE_NAME = "shafaiz/banking-app:latest"
        COMPOSE_FILE = "docker-compose.yml"
        SONAR_HOST_URL = 'http://your-sonarqube-url:9000'  // Replace with your SonarQube server
        SONAR_PROJECT_KEY = 'bankingProject'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/shafez-ansari/bankingProject.git'
            }
        }
        stage('Build') {
            steps {
                echo "Building the project..."
                sh 'mvn clean package'  // Java project hai toh use karein
            }
        }
        stage('Trivy FS Scan') {
            steps {
                echo "Running Trivy File System scan..."
                sh 'trivy fs --exit-code 0 --format table .'  
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                sh 'docker build -t $IMAGE_NAME .'
            }
        }
        stage('Trivy Image Scan') {
            steps {
                echo "Running Trivy Image scan..."
                sh 'trivy image --exit-code 0 --format table $IMAGE_NAME'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                echo "Running SonarQube analysis..."
                sh '''
                    sonar-scanner \
                    -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                    -Dsonar.sources=. \
                    -Dsonar.host.url=$SONAR_HOST_URL \
                    -Dsonar.login=your-sonar-token
                '''
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                echo "Starting services using Docker Compose..."
                sh 'docker-compose -f $COMPOSE_FILE up -d'
            }
        }
    }
}

