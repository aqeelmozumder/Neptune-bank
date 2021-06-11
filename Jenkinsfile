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
				sh './mvnw -Pprod clean package'
				sh 'java -jar target/neptunebank-app*.jar'
			}
		}

    }
}
