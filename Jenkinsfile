pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm  // SCM se automatically checkout karega
            }
        }
        stage('Build') {
            steps {
                echo "Building the project..."
            }
        }
        stage('Test') {
            steps {
                echo "Running tests..."
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying the project..."
            }
        }
    }
}

