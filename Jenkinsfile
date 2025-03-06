pipeline {
    agent any

    environment {
        IMAGE_NAME = "shafaiz/banking-project:latest"  // Docker image name
        SONAR_PROJECT_KEY = "bankingProject"
        SONAR_HOST_URL = "http://localhost:9000"  // SonarQube URL
        COMPOSE_FILE = "docker-compose.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out the project..."
                git branch: 'main', url: 'https://github.com/shafez-ansari/bankingProject.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building the project..."
                sh 'ls -l'  // Debugging to check files
                sh 'mvn clean package'  // Java project build
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
                withSonarQubeEnv('SonarQube') {  // SonarQube server configuration
                    sh '''
                        sonar-scanner \
                        -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                        -Dsonar.sources=. \
                        -Dsonar.host.url=$SONAR_HOST_URL
                    '''
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo "Starting services using Docker Compose..."
                sh 'docker-compose -f $COMPOSE_FILE up -d'
            }
        }
    }

    post {
        success {
            echo "Build and deployment successful!"
        }
        failure {
            echo "Build failed! Check the logs for errors."
        }
    }
}

