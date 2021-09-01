pipeline {
  agent any
  tools {
    maven 'Maven3.8.2'
    jdk 'adoptjdk11'
  }
  environment {
    NEW_VERSION = 'X.X.X'
  }
  stages {
    stage("build") {
      steps {
        echo 'building the application...'
        sh 'mvn compile'
      }
    }
    
    stage("test") {
      when {
        expression {
          // env. is not mandatory
          // SOME_VARIABLE defined before pipeline == true
          BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'master'
        }
      }
      steps {
        echo 'testing the application...'
        sh 'mvn test'
      }
    }
    
    stage("deploy") {
      steps {
        echo "deploying the application in version ${NEW_VERSION}..."
        // nothing
      }
    }
  }
  // handy for notifications
  post {
    always {
      echo 'Always exectuted'
    }
    success {
      echo 'Build successed'
    }
    failure {
      echo 'Build failed'
    }
  }
}
