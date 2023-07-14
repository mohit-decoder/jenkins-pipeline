pipeline {
    agent {
        label 'deployer'
    }
        stages {
            stage('Git pull') {
                steps {
                    sh 'sudo apt update -y'
                    sh 'sudo apt install git -y'
                    git 'https://github.com/usertan123/SnapShot.git'
                }
            }
            stage('Deploy') {
                steps {
                    sh'''
                      sudo apt-get update -y 
                      sudo apt autoremove nodejs npm -y
                      sudo dpkg -i --force-overwrite /var/cache/apt/archives/nodejs_16.19.1-deb-1nodesource1_amd64.deb
                      sudo apt-get install -y nodejs
                      sudo apt install -y npm
                      node --version
                      npm --version 
                      cd /home/ubuntu/workspace/node-js-pipeline
                      sudo npm install -g pm2
                      which npm 
                      pm2 start /usr/bin/npm -- start 
                      sudo npm install react-scripts
                      sudo npm run build
                    '''
                }
            }
        
        }
    }