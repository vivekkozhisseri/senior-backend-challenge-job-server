# Java Spring Boot Service - MySQL DB

Standard service template for all new Spring/Spring Boot services at Viafoura.

Template Features : 
  - Standardized Jenkinsfile and Docker setup for CI/CD
  - Service Configuration with Viafoura logging / tracing / metrics standards  
  - Multi Module structure to separate domain, infrastructure and application logic

## How to create your project

If you are starting a new service, please use this template to bootstrap your new spring boot service. 

### Pre Requisities

#### Github Repository Setup

We use [terraform](https://www.terraform.io/intro/index.html) to manage github repositories at Viafoura. You will need to configure your new repository the terraform for it to be provisioned on github.com.

Link to terraform repo : https://github.com/viafoura/tf-github

Add the following repo name (in alphabetical order) to the terraform [variables.tf](https://github.com/viafoura/tf-github/blob/main/viafoura-repos/variables.tf) file

```
    variable "java_repo_names" {
    default = [
        "akismet-moderation",
        "console-moderation",
        "keepcon-moderation",
        "legacy-moderation",
        "phpmock",
        "ucs-moderation",
        "<your-repo-name>",
    ]
}
```

Once you open a pull request with that change, request a review from @sre team, they'll get back to you ASAP.

#### Cookiecutter & Template 

We use Cookiecutter to generate your project from a template. You will need to have cookiecutter installed locally to proceed : 

Linux:
```
sudo apt-get install cookiecutter
```

MacOS:
```
brew install cookiecutter
```

Command line
```
pip install --user cookiecutter
```

### Generating your project

Generate your Java Spring Boot service with the following commands and answer the interactive prompts :  

```bash
# This will always download the latest version of the templates directory to your cookiecutter config dir
# Ignore any error messages
cookiecutter -f git@github.com:viafoura/backend-repo-template.git

# This will kickstart the tempalate generation prompt
cookiecutter -f backend-repo-template/java-spring-boot-service
```

### Push your first commit!

Once your template has been generated, nagivate into the to the new directory and configure your git remotes

```bash
git remote add origin git@git@github.com:viafoura/<project-slug>.git
git add . && git commit -m "Initial Commit"
git push origin master
```

### Project Structure

Here is a skeleton of the project that is generated. 

```
.
├── application
|   ├── gradle.properties
│   ├── build.gradle
│   └── src
├── core
|   ├── gradle.properties
│   ├── build.gradle
│   └── src
├── infrastructure
|   ├── gradle.properties
│   ├── build.gradle
│   └── src
├── docker
│   ├── build.Dockerfile
│   ├── docker-compose.yaml
│   └── web.Dockerfile
├── k8s
│   ├── base
│   ├── dev
|   ├── staging
│   └── production
|── scripts
|   |── build-db-container.sh
├── gradle
│   └── wrapper
├── gradle.properties
├── gradlew
├── gradlew.bat
├── build.gradle
├── settings.gradle
├── gradle.properties
├── CODEOWNERS
├── Jenkinsfile
├── README.md
└── sonar-project.properties

```

#### Build 

`gradle` is the default build tool used across all Java projects at Viafoura. The spring boot gradle plugin provides default tasks to assemble a spring boot application and to run the service locally. 

##### Docker

Docker is used to build your service, deploy your service to Kubernetes cluster and to run unit-tests in Jenkins. The docker setup in the template enforces a model where your code is complied only once and the same
bits are used for testing and deployment. This ensures you only deploy a tested build!

Here are the docker and docker-compose files that are included with your project : 

 - `build.Dockerfile`
   - Builds / compiles your code. This is the base image for all steps in the build pipeline
 - `docker-compose.yaml`
   - Runs unit tests + other confiugured tests in Jenkins with a db
   - Not suitable for local development - specifically configured for clean room builds in Jenkins
 - `service.Dockerfile`
   - Contains *only* the final artifact produced by your build - the application webjar.
   - Optimized for size - does not contain intermediate build output and class files.

##### Jenkins Pipeline

TBD

#### Multi Module Structure

The generated service is a multi module gradle build that contains the following 3 modules : 

##### `core`
This module is meant to encapsulate all core busines / domain logic related to your service. The module *should not contain* references to any external 
frameworks like spring etc and would naturally contain the following abstractions : 

 - Service Intefaces (Use cases)
 - Repository Interfaces
 - Default or `BasicService` implementations
 - Unit tests for Services with mocked repository implementations.

##### `infrastructure`
This module is intended to encapsulate incoming and outgoing communication between the service and the external world. This module would contain the following:
- JPA Implementations of `core` repositories
- Database configurations and migrations
- Integration with other microservices (calling external API's, sending asynchronous messages)
- Message broker integrations (Apacha Kafka, RabbitMQ, so on)

##### `application`

This module is intended to encapsulate all middleware configurations and inferface implementations. Things that would typically belong in this module are : 
 
 - REST API Controllers and domain models serialization / deserialization
 - Autowired `core` services injected with the right repository implementations 
 - Service middleware


#### Rationale

Having a multi module structure creates a bit of friction, but has several advantages when it comes to long term maintainability of the code base. Here are some of the key advantages to maintaing this structure : 

 - Having a core module that is "pure" ensures that business and domain logic is not tightly coupled with nuances of a particular framework. This ensures that
   core business rules can be unit tested in isolation to ensure correctness.
 - Structure to accommodate for change -  As services evolve and get more complex, one might need to consider for other ways to interface with the core domain logic - say via a queue perhaps. By having strong interfaces that govern the inputs and output of your core domain logic, you can make infrastruture changes without impactig business logic.

License
-------

Copyright (C) 2022 Viafoura, Inc. - All Rights Reserved
