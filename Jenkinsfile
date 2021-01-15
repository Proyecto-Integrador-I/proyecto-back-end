#!/usr/bin/groovy

pipeline {
    agent any
    
    stages {
        stage('Instalación y compilación') {
            steps {
				withMaven(maven : 'maven-3.6.3'){
					bat 'mvn -B -DskipTests clean package'
				}
            }
        }
        stage('Ejecución de pruebas unitarias e integración') {
            steps {
				withMaven(maven : 'maven-3.6.3'){
					bat 'mvn test'
				}
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('SonarQube: Análisis de codigo estático') {
            steps {
                withSonarQubeEnv('sonar-server') {
					withMaven(maven : 'maven-3.6.3'){
						bat 'mvn sonar:sonar'
					}
                }
            }
        }
		
        stage('Nexus: Generación de artefacto') {
            steps {
				withMaven(maven : 'maven-3.6.3'){
					bat 'mvn clean deploy -P release'
				}
            }
        }
		
		stage('Despliegue'){
			steps {
				deploy adapters: [tomcat9(credentialsId: 'tomcat', path: '', url: 'http://localhost:8082/')], contextPath: registro-api, war: '**/*.war'
			}
		}
		
    }
}
