pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true clean install'
            }
        }
        stage ('Down') {
            steps {
                sh 'sudo docker-compose down --rmi all'
            }
        }        
        stage ('Deploy') {
            steps {
                sh 'sudo docker-compose up -d'
            }
        }
        stage ('Prune images') {
            steps {
                sh 'yes | sudo docker image prune'
            }
        }
    }
}