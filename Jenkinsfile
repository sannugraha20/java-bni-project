pipeline {
    agent any

    enviroment {
        PROJECT_NAME = "sannugraha20-dev"
        BUILD_NAME = "san-odp-project-git"
    }

    stages {
        stage('Trigger Build in OpenShift') {
            steps {
                sh "oc start-build ${BUILD_NAME} --from-dir=. --follow -n ${PROJECT_NAME}"
            }
        }


        stage('Deploy to OpenShift') {
            steps {
                sh "oc rollout restart latest ${BUILD_NAME} -n ${PROJECT_NAME}"
            }
        }

    
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build or deployment failed.'
        }
    }

}