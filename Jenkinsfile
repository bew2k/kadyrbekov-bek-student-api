pipeline {
    agent any

    environment {
        STUDENT_NAME    = 'Bek Kadyrbekov'
        STUDENT_GROUP   = 'IT2-2306'
        STUDENT_ID      = '35534'
        IMAGE_NAME      = 'kadyrbekov-bek-student-api'
        IMAGE_TAG       = 'v1.0-35534'
        FULL_IMAGE      = "${IMAGE_NAME}:${IMAGE_TAG}"
    }

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "=== Student: ${STUDENT_NAME} | Group: ${STUDENT_GROUP} | ID: ${STUDENT_ID} ==="
                sh 'git log --oneline -3'
            }
        }

        stage('Build') {
            steps {
                echo "Build started by ${STUDENT_NAME}"
                sh 'chmod +x gradlew'
                sh './gradlew clean build -x test --no-daemon'
            }
        }

        stage('Test') {
            steps {
                echo "Running automated tests (${STUDENT_NAME})"
                // Если тесты падают, Gradle возвращает ненулевой код и пайплайн останавливается
                sh './gradlew test --no-daemon'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${FULL_IMAGE} .'
            }
        }

        stage('Verify') {
            steps {
                sh '''
                    if docker image inspect ${FULL_IMAGE} > /dev/null 2>&1; then
                        echo "VERIFY OK: image ${FULL_IMAGE} exists (student: ${STUDENT_NAME})"
                        docker images ${IMAGE_NAME}
                    else
                        echo "VERIFY FAILED: image ${FULL_IMAGE} not found"
                        exit 1
                    fi
                '''
            }
        }
    }

    post {
        success { echo "SUCCESS: pipeline for ${STUDENT_NAME} finished" }
        failure { echo "FAILURE: pipeline for ${STUDENT_NAME} stopped" }
    }
}
