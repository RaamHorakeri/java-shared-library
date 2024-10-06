def checkoutFromGit(String repoUrl) {
    try {
        // Checkout the code from Git
        checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: repoUrl]]])
    } catch (Exception e) {
        error "Git Checkout failed: ${e.message}"
    }
}

def buildDockerImage(String imageName, String tag) {
    try {
        // Build Docker image
        def app = docker.build("${imageName}:${tag}")
        return app
    } catch (Exception e) {
        error "Docker Build failed: ${e.message}"
    }
}

def pushDockerImage(String imageName, String tag, String registryCredentialsId) {
    try {
        // Push Docker image to DockerHub
        docker.withRegistry('', registryCredentialsId) {
            def app = docker.image("${imageName}:${tag}")
            app.push()
        }
    } catch (Exception e) {
        error "Docker Push failed: ${e.message}"
    }
}

def runDockerContainer(String imageName, String tag, String portMapping) {
    try {
        // Run Docker container
        def app = docker.image("${imageName}:${tag}")
        app.run("-d -p ${portMapping}")
    } catch (Exception e) {
        error "Docker Run failed: ${e.message}"
    }
}
