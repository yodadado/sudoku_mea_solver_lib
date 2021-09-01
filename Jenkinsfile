pipeline {
  agent any
  stages {
    stage("build") {
      steps {
        echo 'building the application...'
        // mvn clean build
      }
    }
    
    stage("test") {
      steps {
        echo 'testing the application...'
        //mvn test
      }
    }
    
    stage("deploy") {
      steps {
        // nothing
      }
    }
  }
}
