pipeline {
  agent any
  stages {
    stage("build") {
      steps {
        echo 'building the application...'
        echo 'second message'
        // mvn compile
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
        echo 'deploying the application...'
        // nothing
      }
    }
  }
}
