pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/shafez-ansari/bankingProject.git'
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

