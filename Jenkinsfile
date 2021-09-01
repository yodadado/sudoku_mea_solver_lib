pipeline {
  agent any
  tools {
    maven 'Maven3.8.2'
    jdk 'adoptjdk11'
  }
  stages {
    stage("build") {
      steps {
        echo 'building the application...'
        echo 'second message'
        sh 'mvn compile'
      }
    }
    
    stage("test") {
      steps {
        echo 'testing the application...'
        sh 'mvn test'
      }
    }
    
    stage("deploy") {
      steps {
        echo 'deploying the application...'
        // nothing
      }
    }
  }
}
