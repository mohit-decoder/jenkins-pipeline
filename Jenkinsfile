pipeline {
    agent any 
    stages{
        stage('Execute playbook'){
            steps{
                ansiblePlaybook credentialsId: 'mohitjen', disableHostKeyChecking: true, installation: 'Ansible', inventory: '/etc/ansible/hosts', playbook: '/etc/ansible/nginx.yml'
            }
        }
    }
}