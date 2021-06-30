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
                      -Dsonar.login=50a951d809a1c3674cf82b98d37d0b4d78805af4'
			}
		}

		stage('Deploy and Testing'){
			parallel{
				stage('Deploy'){
					steps{
					   timeout(time: 4, unit: 'MINUTES') {
					   			sh './mvnw clean'
								sh './mvnw -Pdev'
					   }
					}
					post{
						  failure{
							   echo 'DEPLOY Fail!!!'
							   mail to:'hexuanz@uvic.ca',
							   subject:'fail on Deploy',
							   body:'something wrong with deploy'
						  }
						  aborted{
							   echo 'DEPLOY Finished!!!'
						  }
					}
			   }
				stage('Testing'){
					steps {
								 sh 'sleep 100'
								 sh './mvnw verify'
					}
					post{
						failure{
								echo 'TESTING Fail!!!'
								mail to:'hexuanz@uvic.ca',
								subject:'fail on testing',
								body:'something wrong with testing'
						}
						success{
								echo 'TESTING SUCCESS!!!'
						}
					}
				}
			}
        }
    }
}
