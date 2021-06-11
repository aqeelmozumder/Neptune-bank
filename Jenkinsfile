pipeline {
    agent any

    stages {

		stage('pull Code') {
			steps {
				git branch:'hexuan',
				credentialsId:'73d0a3b3-226d-4ee8-98f0-427e37abcb6f',
				url:'git@git.engr.uvic.ca:seng426/2021/teams/team-13/neptunebank.git'
			}
		}

        stage('Install Dependencies') {
        	steps {
        		sh 'npm install'
        	}
        }

        stage('Build') {
			steps {
				sh './mvnw clean package'
			}
		}

		stage('SonarQube Scan') {
			steps {
				sh './mvnw sonar:sonar \
                      -Dsonar.projectKey=team-13 \
                      -Dsonar.host.url=https://sonarqube.seng.uvic.ca \
                      -Dsonar.login=39b77db30b6939e53f3f7c1bd2ff34697be3681e'
			}
		}
    }
}
