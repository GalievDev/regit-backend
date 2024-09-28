# General Instructions

Dockerfile:

    - Save the Dockerfile in the root of your project.
    - Ensure the JAR file is built and located in the target directory.

Docker Compose:

    - Save the Docker Compose YAML file as docker-compose.yml in the same directory as your Dockerfile.

Jenkins:

    - Create a new pipeline job in Jenkins.
    - Point the job to your repository containing the Jenkinsfile.
    - Configure the necessary credentials for accessing your Docker registry.