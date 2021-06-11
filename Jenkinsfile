pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
		stage('pull Code') {
			steps {
				git branch:'hexuan',
				credentialsId:'73d0a3b3-226d-4ee8-98f0-427e37abcb6f',
				url:'git@git.engr.uvic.ca:seng426/2021/teams/team-13/neptunebank.git'
			}
		}
		stage('List Directories') {
			steps {
               	sh 'ls -ltr'
            }
        }
        stage('Install Dependencies') {
        	steps {
        		sh 'npm install'
        	}
        }
        stage('Deployment') {
        	steps {
        		sh './mvnw clean'
        		sh './mvnw -Pdev'
        	}
        }
        stage('Testing') {
        	steps {
        		sh './mvnw verify'
        	}
        }
    }
}
