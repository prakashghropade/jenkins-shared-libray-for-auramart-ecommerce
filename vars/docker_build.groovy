//  This function build the docker images
def call (Map config = [:]) {
    def imageName = config.imageName ?: error("Image name is requied")
    def imageTag = config.imageTag ?: 'latest'
    def dockerfile = config.dockerfile ?: 'Dockerfile'
    def context = config.context ?: '.'

    echo "Building Docker image: ${imageName}:${imageTag} using ${dockerfile}"

    sh """
    docker build -t ${imageName}:${imageTag} -t  ${imageName}:latest -f ${dockerfile} ${context}
    
    """
} 