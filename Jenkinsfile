pipeline {
    agent any  

    environment {
        SLACK_URL_UPLOAD = "${env.SLACK_URL_UPLOAD}"
    }

    triggers {
        // Runs daily at 12:00 PM (noon) / 24:00 (midnight: 0 0 * * *)
        // H 12 * * * avoids load spikes by hashing the exact minute around 12 PM
        cron('H 12 * * *')
    }

    tools {
        maven 'Apache Maven 3.3.9' 
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Giaphuc713/Selenium-Test-Framework.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean test-compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Reports') {
            steps {
                publishHTML(target: [
                    reportDir: 'src/test/resources/ExtentReport',  
                    reportFiles: 'ExtentReport.html',  
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/src/test/resources/ExtentReport/*.html', fingerprint: true, allowEmptyArchive: true
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            
            sh """
                curl -X POST --data-urlencode 'payload={
                    "text": "*[Topic] PROD*",
                    "attachments": [
                        {
                            "color": "${currentBuild.currentResult == 'SUCCESS' ? '#00B050' : '#FF0000'}",
                            "blocks": [
                                {
                                    "type": "section",
                                    "text": {
                                        "type": "mrkdwn",
                                        "text": "*Regression Test Results*\\n*Job:* ${env.JOB_NAME} #${env.BUILD_NUMBER}\\n*Status:* ${currentBuild.currentResult}\\n*URL:* ${env.BUILD_URL}"
                                    }
                                }
                            ]
                        }
                    ]
                }' "\${SLACK_URL_UPLOAD}"
            """
        }
    }
}

