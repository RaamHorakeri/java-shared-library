def checkoutFromGit(String repoUrl) {
    // Checkout the code from Git
    checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: repoUrl]]])
}

def buildDockerImage(String imageName, String tag) {
    // Build Docker image
    def app = docker.build("${imageName}:${tag}")
    return app
}

def pushDockerImage(String imageName, String tag, String registryCredentialsId) {
    // Push Docker image to DockerHub
    docker.withRegistry('', registryCredentialsId) {
        def app = docker.image("${imageName}:${tag}")
        app.push()
    }
}

def runDockerContainer(String imageName, String tag, String portMapping) {
    // Run Docker container
    def app = docker.image("${imageName}:${tag}")
    app.run("-d -p ${portMapping}")
}
