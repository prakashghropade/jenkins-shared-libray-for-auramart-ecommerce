def call(Map config = [:]) {
    def imageName = config.imageName ?: error("Image name is required")
    def imageTag = config.imageTag ?: "latest"
    def credentials = config.credentials ?: "docker-hub-credentials"

    echo "=============================="
    echo "Starting Docker Push"
    echo "Image      : ${imageName}:${imageTag}"
    echo "Credentials: ${credentials}"
    echo "=============================="

    withCredentials([
        usernamePassword(
            credentialsId: credentials,
            usernameVariable: 'DOCKER_USERNAME',
            passwordVariable: 'DOCKER_PASSWORD'
        )
    ]) {

        sh """
            set -ex

            echo "Current User:"
            whoami

            echo "Docker Version:"
            docker --version

            echo "Docker Info:"
            docker info

            echo "Checking if image exists..."
            docker images | grep "${imageName}" || true

            echo "Logging into Docker Hub..."
            echo "\$DOCKER_PASSWORD" | docker login -u "\$DOCKER_USERNAME" --password-stdin

            echo "Pushing image ${imageName}:${imageTag}..."
            docker push ${imageName}:${imageTag}

            echo "Logging out..."
            docker logout

            echo "Docker push completed successfully."
        """
    }

    echo "Finished Docker Push"
}