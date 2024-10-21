pipeline {
    agent any

    environment {
        SCALA_HOME = '/path/to/scala'
        SBT_HOME = '/path/to/sbt'
        SPARK_HOME = '/path/to/spark'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from GitHub
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                // Compile and build the Scala project using SBT
                sh """
                    ${SBT_HOME}/bin/sbt clean compile
                    ${SBT_HOME}/bin/sbt package
                """
            }
        }

        stage('Spark Submit') {
            steps {
                // Run the JAR file using spark-submit
                sh """
                    ${SPARK_HOME}/bin/spark-submit \\
                        --class Main \\
                        --master yarn \\
                        --deploy-mode cluster \\
                        target/scala-2.12/your_project_name_2.12-1.0.jar
                """
            }
        }
    }

    post {
        always {
            // Clean up build artifacts
            sh "rm -rf target"
        }
        success {
            echo 'Job completed successfully!'
        }
        failure {
            echo 'Job failed.'
        }
    }
}
