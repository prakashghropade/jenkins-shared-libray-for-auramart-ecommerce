def call(Map config = [:]){
    def imageName = config.imageName ?: error("Image name is required")
    def imageTag = config.imageTag ?: 'latest'
    def credentials = config.credentials ?: 'docker-hub-credentials'

    echo "Pushing DOcker image: ${imageName}:${imageTag}"

    withCredentials([usernamePassword(credentialsId: '${credentials}', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]){
        sh """
            docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
            docker push ${imageName}:${imageTag}
            docker logout
        """
    }
}