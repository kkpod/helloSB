#! /usr/bin/env groovy
pipeline {
  agent {
    label 'maven'
  }
  stages {
    stage('Build') {
      steps {
        echo 'Building..'
        sh 'mvn clean package'
      }
    }

    stage('Create Container Image') {
      steps {
        echo 'Create Container Image..'
        script {
			openshift.withCluster() { 
			  openshift.withProject("kranti-podd-dev") {
				def buildConfigExists = openshift.selector("bc", "hellospringboot").exists() 
				if(!buildConfigExists){ 
				  openshift.newBuild("--name=hellospringboot", "--docker-image=registry.redhat.io/jboss-eap-7/eap74-openjdk8-openshift-rhel7", "--binary") 
				} 
				openshift.selector("bc", "hellospringboot").startBuild("--from-file=target/hello_spring_boot_j-0.0.1-SNAPSHOT.war", "--follow") } }
        }
      }
    }

    stage('Deploy') {
      steps {
        echo 'Deploying....'
        script {
			openshift.withCluster() { 
			  openshift.withProject("kranti-podd-dev") { 
				def deployment = openshift.selector("dc", "hellospringboot") 
				if(!deployment.exists()){ 
				  openshift.newApp('hellospringboot', "--as-deployment-config").narrow('svc').expose() 
				} 
				timeout(5) { 
				  openshift.selector("dc", "hellospringboot").related('pods').untilEach(1) { 
					return (it.object().status.phase == "Running") 
				  } 
				} 
			  } 
			}
        }
      }
    }
  }
}