pipeline {
    agent any

    stages {        
        stage('Package-installation') {
            steps {
            sh '''sudo apt update -y && sudo apt install docker.io -y && sudo apt install awscli -y
            '''
        }
    }
        stage('build in container') {
            steps {
                sh  ''' << EOF >  dockerfile  
                FROM ubuntu
                RUN apt-get update -y && apt-get install openjdk-11-jre -y 
                RUN apt-get install maven -y && apt-get install git -y
                RUN git clone https://github.com/mohit-decoder/student-ui.git
                RUN cd student-ui && mvn clean package
                
                EOF
                
                docker build -t dockerimg11 .      
                
                '''
            }
        } 
        stage('img-to-ecs') {
            steps {
                withAWS(credentials: 'credi', region: 'us-west-1') {
                     sh '''
                    sudo chmod 666 /var/run/docker.sock
                    sudo aws ecr get-login-password --region us-west-1 | docker login --username AWS --password-stdin 616843820612.dkr.ecr.us-west-1.amazonaws.com
                    sudo docker tag dockerimg11:latest 616843820612.dkr.ecr.us-west-1.amazonaws.com/dockerimg11:latest
                    sudo docker push 616843820612.dkr.ecr.us-west-1.amazonaws.com/dockerimg11:latest
                '''
                }
            }  
        }
    }
}