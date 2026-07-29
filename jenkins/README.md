# Jenkins 101: Building Your First Automated CI/CD Pipeline from Scratch
*A Beginner-Friendly, Production-Style Classroom Teaching & Live Demonstration Guide*

---

## Course Overview & Objectives
This training project serves as an interactive classroom guide for introducing modern CI/CD concepts using **Jenkins**. Students will learn how to automate the lifecycle of a lightweight Node.js web application—from pulling source code to running unit tests, packaging the application into a Docker container, and performing health-check verification (smoke testing) on a local environment.

### Core Concepts Covered:
* **Declarative Pipelines:** Writing pipeline-as-code using structured Jenkinsfiles.
* **Docker-out-of-Docker (DooD):** Running Jenkins inside a container while granting it control of the host's Docker engine.
* **Automated Quality Gates:** Ensuring the pipeline aborts immediately if unit tests fail.
* **Containerized Deployment:** Building custom images and orchestrating runtime containers.
* **Smoke Testing:** Programmatically verifying application health pre-release.

---

## Directory Structure
Below is the structural layout of the demonstration repository:
```text
jenkins-demo/
├── docker-compose.yaml     # Bootstrapping Jenkins with docker.sock mounted
├── Dockerfile.jenkins      # Custom Jenkins Controller with Docker CLI
├── package.json            # Node.js dependencies & scripts
├── app.js                  # Node.js Express REST API (development target)
├── test.js                 # Jest & Supertest endpoint unit tests
├── Dockerfile              # App containerization configuration
├── Jenkinsfile             # End-to-end Declarative Pipeline definition
└── README.md               # Classroom teaching guide (this file)
```

---

## Module 1: Classroom Setup & Jenkins Bootstrapping

To teach Jenkins, we must run it in a way that allows it to execute Docker commands (e.g. `docker build`, `docker run`). We achieve this using **Docker-out-of-Docker (DooD)**, mounting the host machine's `/var/run/docker.sock` into the Jenkins container.

### 1. Bootstrapping Configurations

#### Custom Jenkins Image (`Dockerfile.jenkins`)
Because the official Jenkins image does not include the Docker CLI tool by default, we build a custom image that adds it. 
```dockerfile
FROM jenkins/jenkins:lts

# Switch to root to install system-level packages
USER root

# Install dependencies, Docker GPG key, repository, and Docker CLI
RUN apt-get update && apt-get install -y \
    lsb-release \
    curl \
    gnupg \
    && curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg \
    && echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian \
    $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null \
    && apt-get update && apt-get install -y docker-ce-cli \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Switch back to the default jenkins user
USER jenkins
```

#### Orchestrating with Compose (`docker-compose.yaml`)
We launch the container using Docker Compose. We mount the `/var/run/docker.sock` and run the container as the `root` user to ensure no permission errors block socket access on student machines.
```yaml
version: '3.8'

services:
  jenkins:
    build:
      context: .
      dockerfile: Dockerfile.jenkins
    container_name: jenkins-control-plane
    restart: unless-stopped
    ports:
      - "8080:8080"   # Jenkins Web UI
      - "50000:50000" # Agent communication port (optional but standard)
    volumes:
      - jenkins_home:/var/jenkins_home
      # Mount host's Docker socket to allow Jenkins to control the host's Docker engine
      - /var/run/docker.sock:/var/run/docker.sock
    # Running as root ensures the jenkins container has sufficient permissions to access /var/run/docker.sock
    # Note: In a production setting, you would create a dedicated docker group and match group IDs, but
    # root is recommended for local classroom demonstrations to ensure zero permission errors on student laptops.
    user: root
    environment:
      - JAVA_OPTS=-Djenkins.install.runSetupWizard=true

volumes:
  jenkins_home:
    driver: local
```

### 2. Bootstrapping Instructions

1. **Launch the Container:**
   Execute the following command in the project directory:
   ```bash
   docker compose up -d --build
   ```
   > 💡 **Instructor Tip:** Point out to students that `--build` is critical because it tells Compose to build our custom `Dockerfile.jenkins` (which installs the Docker CLI) rather than pulling the stock Jenkins image.

2. **Retrieve the Initial Admin Password:**
   When Jenkins starts for the first time, it locks itself down. Retrieve the password from the logs or directly from the file within the container:
   ```bash
   docker exec -it jenkins-control-plane cat /var/jenkins_home/secrets/initialAdminPassworddoc
   ```
   *Alternative:* View the container logs and locate the password block:
   ```bash
   docker logs jenkins-control-plane
   ```

3. **Complete the Wizard:**
   * Open your browser to `http://localhost:8080`.
   * Paste the retrieved password to unlock Jenkins.
   * Click **Install suggested plugins** and wait for the plugin downloader to finish.
   * Create your first administrator account (e.g., username: `admin`, password: `password123`).
   * Complete the URL confirmation and enter the main Jenkins Dashboard.

---

## Module 2: Sample Application Codebase

The target application is a minimal Node.js REST API with automated unit tests and container specifications.

### 1. `package.json`
Specifies standard Express dependencies, Jest for the test runner, and Supertest for programmatically asserting against the API router.
```json
{
  "name": "jenkins-demo-app",
  "version": "1.0.0",
  "description": "Minimal Node.js Express REST API for Jenkins 101 classroom demo",
  "main": "app.js",
  "scripts": {
    "start": "node app.js",
    "test": "jest --detectOpenHandles --forceExit"
  },
  "dependencies": {
    "express": "^4.19.2"
  },
  "devDependencies": {
    "jest": "^29.7.0",
    "supertest": "^7.0.0"
  }
}
```

### 2. `app.js`
Exposes `/` (Welcome) and `/health` (Health check status indicator).
```javascript
const express = require('express');
const app = express();
const PORT = process.env.PORT || 3000;

// Root Endpoint
app.get('/', (req, res) => {
    res.status(200).json({
        status: "success",
        message: "Welcome to Jenkins 101: Building Your First Automated CI/CD Pipeline from Scratch!",
        timestamp: new Date()
    });
});

// Health Check Endpoint (crucial for Jenkins Smoke Test)
app.get('/health', (req, res) => {
    res.status(200).json({
        status: "UP",
        uptime: process.uptime(),
        timestamp: new Date()
    });
});

// Start listening only if executed directly (prevents EADDRINUSE during Jest tests)
if (require.main === module) {
    app.listen(PORT, () => {
        console.log(`Server is running and listening on port ${PORT}`);
    });
}

module.exports = app;
```

### 3. `test.js`
Verifies functionality and correct status codes.
```javascript
const request = require('supertest');
const app = require('./app');

describe('GET / (Root Route)', () => {
    it('should respond with a 200 status code and success message', async () => {
        const response = await request(app).get('/');
        expect(response.statusCode).toBe(200);
        expect(response.body.status).toBe('success');
        expect(response.body.message).toContain('Jenkins 101');
    });
});

describe('GET /health (Health Check Route)', () => {
    it('should respond with 200 status code and UP status', async () => {
        const response = await request(app).get('/health');
        expect(response.statusCode).toBe(200);
        expect(response.body.status).toBe('UP');
        expect(response.body).toHaveProperty('uptime');
        expect(response.body).toHaveProperty('timestamp');
    });
});
```

### 4. `Dockerfile`
A standard, minimal Alpine containerization config for production deployment.
```dockerfile
# Use a lightweight official Node.js Alpine base image
FROM node:20-alpine

# Create and define the application working directory
WORKDIR /usr/src/app

# Copy dependency definition files
COPY package*.json ./

# Install only production dependencies for a smaller, secure image
RUN npm ci --only=production

# Copy the core application source code
COPY app.js ./

# Expose the port the Express application listens on
EXPOSE 3000

# Set environment variables
ENV NODE_ENV=production
ENV PORT=3000

# Use node to execute the application
CMD [ "node", "app.js" ]
```

---

## Module 3: Declarative Jenkinsfile

The `Jenkinsfile` outlines the instructions for the pipeline. Every directive is commented inline.

```groovy
pipeline {
    /* 
       'agent any' defines that this pipeline can run on any available Jenkins executor.
       In our local classroom setup, it runs on the Jenkins Controller's built-in executor.
    */
    agent any

    /*
       'environment' defines variables available to all stages in this pipeline.
       Using variables prevents hardcoding values throughout the script.
    */
    environment {
        APP_IMAGE_NAME = 'jenkins-demo-app'
        CONTAINER_NAME = 'jenkins-demo-running'
        HOST_PORT      = '8081'
        CONTAINER_PORT = '3000'
    }

    stages {
        /*
           Stage 1: Checkout
           Pulls the latest source code from the configured Git repository.
           'checkout scm' automatically uses the credentials and repository configured in the Jenkins Job UI.
        */
        stage('Checkout') {
            steps {
                echo '=== STAGE 1: CHECKOUT ==='
                checkout scm
            }
        }

        /*
           Stage 2: Install Dependencies
           Runs local package installation. Jenkins runs this in the workspace context.
        */
        stage('Install Dependencies') {
            steps {
                echo '=== STAGE 2: INSTALL DEPENDENCIES ==='
                sh 'npm install'
            }
        }

        /*
           Stage 3: Run Unit Tests
           Executes Jest unit tests. If any test fails, the command exits with code 1.
           Jenkins captures the non-zero exit status, fails this stage, and aborts the remaining stages.
        */
        stage('Run Unit Tests') {
            steps {
                echo '=== STAGE 3: RUN UNIT TESTS ==='
                sh 'npm test'
            }
        }

        /*
           Stage 4: Build Docker Image
           Builds the application's container image using the project's Dockerfile.
           Tags the image with the unique build number (${BUILD_NUMBER}) and 'latest'.
        */
        stage('Build Docker Image') {
            steps {
                echo '=== STAGE 4: BUILD DOCKER IMAGE ==='
                sh "docker build -t ${APP_IMAGE_NAME}:${BUILD_NUMBER} ."
                sh "docker tag ${APP_IMAGE_NAME}:${BUILD_NUMBER} ${APP_IMAGE_NAME}:latest"
            }
        }

        /*
           Stage 5: Deploy & Smoke Test
           Deploys the built image as a running container on the host machine.
           1. Safely tears down any previous builds' containers to prevent port conflicts.
           2. Launches the container in detached mode (-d).
           3. Polls the container's health check API up to 5 times.
           4. Cleans up and fails the stage if the health check fails.
        */
        stage('Deploy & Smoke Test') {
            steps {
                echo '=== STAGE 5: DEPLOY & SMOKE TEST ==='
                
                // Tear down previous deployment container if running
                sh """
                    if docker ps -a --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}\$"; then
                        echo "Found existing running container: ${CONTAINER_NAME}. Stopping and removing..."
                        docker stop ${CONTAINER_NAME} || true
                        docker rm -f ${CONTAINER_NAME} || true
                    fi
                """
                
                // Dynamically fetch the network name of the Jenkins container
                script {
                    env.NETWORK_NAME = sh(
                        script: "docker inspect jenkins-control-plane -f '{{range \$k,\$v := .NetworkSettings.Networks}}{{\$k}}{{end}}'",
                        returnStdout: true
                    ).trim()
                }
                
                // Run the newly built container on the same network, exposing port 8081 on the host
                sh "docker run -d --name ${CONTAINER_NAME} --network ${env.NETWORK_NAME} -p ${HOST_PORT}:${CONTAINER_PORT} ${APP_IMAGE_NAME}:${BUILD_NUMBER}"
                
                // Poll the health check endpoint using the container's name and internal port
                sh """
                    echo "Waiting for application to bootstrap..."
                    sleep 3
                    
                    SUCCESS=false
                    for i in 1 2 3 4 5; do
                        echo "Polling health check endpoint: http://${CONTAINER_NAME}:${CONTAINER_PORT}/health (Attempt \$i/5)..."
                        RESPONSE=\$(curl -s http://${CONTAINER_NAME}:${CONTAINER_PORT}/health || true)
                        echo "Response: \$RESPONSE"
                        
                        if echo "\$RESPONSE" | grep -q '"status":"UP"'; then
                            echo "Smoke test passed successfully!"
                            SUCCESS=true
                            break
                        fi
                        
                        echo "App not ready. Retrying in 2 seconds..."
                        sleep 2
                    done
                    
                    if [ "\$SUCCESS" = false ]; then
                        echo "Smoke test failed! Stopping and removing container..."
                        docker stop ${CONTAINER_NAME} || true
                        docker rm -f ${CONTAINER_NAME} || true
                        exit 1
                    fi
                """
            }
        }
    }

    /*
       'post' execution blocks execute code at the end of the pipeline run.
       - always: Runs regardless of status (useful for notifications, global cleanups).
       - success: Runs only if all stages passed (ideal for publishing reports or notification logs).
       - failure: Runs if any stage failed (ideal for alerts and logs).
    */
    post {
        always {
            echo '=== PIPELINE COMPLETE ==='
            echo "Completed build #${BUILD_NUMBER} for pipeline job: ${JOB_NAME}"
        }
        success {
            echo '=================================================='
            echo "🎉 PIPELINE SUCCESSFUL 🎉"
            echo "Application is live at: http://localhost:${HOST_PORT}"
            echo '=================================================='
        }
        failure {
            echo '=================================================='
            echo "❌ PIPELINE FAILED ❌"
            echo "Check the console output above to debug the failure."
            echo '=================================================='
        }
    }
}
```

---

## Module 4: Step-by-Step Live Classroom Demonstration Plan

Follow this "show script" to execute a flawless live walkthrough in front of a class.

### Phase 1: Creating a Pipeline Job (Time: 5 mins)
1. **Navigate to Jenkins Dashboard** at `http://localhost:8080`.
2. Click **New Item** on the top-left sidebar menu.
3. Enter `jenkins-101-demo` as the item name, select **Pipeline** as the type, and click **OK**.
4. Scroll down to the **Pipeline** configuration section at the bottom of the page.
5. In the **Definition** dropdown, select **Pipeline script**.
   > 💡 **Instructor Tip:** Explain that in production, we choose **Pipeline script from SCM** (e.g. Git) to fetch the `Jenkinsfile` from a codebase. For this quick local demonstration, pasting the script directly allows us to experiment rapidly.
6. Copy the contents of the `Jenkinsfile` from Module 3 and paste it into the script editor text area.
7. Click **Save**.

---

### Phase 2: First Build Walkthrough (Time: 5 mins)
1. In the newly created job dashboard, click **Build Now** on the left menu.
2. Watch the **Build History** panel. Build `#1` will start.
3. Draw attention to the **Stage View** panel which will render grid blocks sequentially:
   * `Checkout` ➔ `Install Dependencies` ➔ `Run Unit Tests` ➔ `Build Docker Image` ➔ `Deploy & Smoke Test`.
4. Click on the progress bar or the `#1` text in Build History and select **Console Output**.
   > 💡 **Instructor Tip:** Point out the detailed command executions printed to the console. Highlight how Jenkins prefixes shell commands with `+` and streams the console output in real-time.
5. Once the build completes, scroll to the bottom of the log to see the custom success statement:
   ```text
   🎉 PIPELINE SUCCESSFUL 🎉
   Application is live at: http://localhost:8081
   ```
6. Open your browser and navigate to `http://localhost:8081` to show students the active, running container response.

---

### Phase 3: The "Broken Build" Test (Classroom Wow Factor) (Time: 8 mins)
To demonstrate the "quality gate" safety properties of CI/CD:

1. **Intentionally break a unit test:**
   * Open `test.js` in your editor.
   * Modify the health check status assertion to expect a failing response code (e.g., change `200` to `500`):
     ```javascript
     // Change this:
     expect(response.statusCode).toBe(200);
     // To this:
     expect(response.statusCode).toBe(500);
     ```
   * Save the file.
2. **Re-run the build:**
   * Go back to Jenkins and click **Build Now**.
3. **Show the Failure:**
   * The pipeline will execute Stage 1 and Stage 2 successfully.
   * During Stage 3 (`Run Unit Tests`), the test suite will fail. Jest returns exit code `1`.
   * Jenkins immediately aborts the run. Stage 3 turns **Red (Failed)**, and Stages 4 and 5 turn **Grey (Skipped)**.
   * Show that `http://localhost:8081` continues running the *previous stable build* (from Build #1), proving that broken code never reaches production.
4. **Examine the Console Logs:**
   * Open the console output for Build `#2`.
   * Scroll to the test execution output to show where Jest clearly pinpoints the failed expectation:
     ```text
     Expected: 500
     Received: 200
     ```
5. **Fix the code:**
   * Revert the change in `test.js` back to `expect(response.statusCode).toBe(200);` and save.
   * Trigger **Build Now** to watch the build succeed (Build #3) and turn green.

---

### Phase 4: Troubleshooting Workflow (Time: 7 mins)
Demonstrate to students how to query environment and job-level state:

* **Workspace Inspection:**
  * Click on the **Workspace** link on the project homepage.
  * Show students that Jenkins clones the files into `/var/jenkins_home/workspace/jenkins-101-demo/` inside the container. 
* **Environment Variables:**
  * Explain that Jenkins injects default variables like `${BUILD_NUMBER}`, `${JOB_NAME}`, and `${WORKSPACE}`.
  * Show how these were called in the `Jenkinsfile` using both Groovy syntax (`${BUILD_NUMBER}`) and bash syntax (`$BUILD_NUMBER`).
  
> ⚠️ **Common Student Trap:** Warn students that editing files directly inside the workspace directory (`/var/jenkins_home/workspace/`) on the server is an anti-pattern. Changes will be overwritten during the next `Checkout` stage. Always commit changes to Git.

---

## Module 5: Student Hands-on Practice & Assessment Exercises

---

### Challenge 1: Artifact Archiving
**Goal:** Modify the pipeline to archive the `package.json` and a simulated test report file so that they are saved directly in Jenkins and accessible from the build page dashboard.

* **Hint:** Use the `archiveArtifacts` step inside a `post { always { ... } }` block. Ensure the test command runs and exports a report, or simulate a report file creation before archiving.

#### Solution:
1. Update `package.json` to generate an execution output report:
   ```json
   "scripts": {
     "test": "jest --detectOpenHandles --forceExit --json --outputFile=test-results.json"
   }
   ```
2. Modify the `Jenkinsfile`'s `post` block to archive the resulting JSON file:
   ```groovy
   post {
       always {
           echo '=== PIPELINE COMPLETE ==='
           echo "Completed build #${BUILD_NUMBER} for pipeline job: ${JOB_NAME}"
           
           // Archive the Jest test results file
           archiveArtifacts artifacts: 'test-results.json', allowEmptyArchive: true
       }
       // ... success and failure blocks remain the same
   }
   ```
3. Once run, the `test-results.json` artifact will appear at the top-right of the Jenkins Build page.

---

### Challenge 2: Environment Variables & Parameters
**Goal:** Parameterize the pipeline execution. Allow the user to select the Target Environment (`dev`, `staging`) when starting the build. Echo this environment choice during deployment.

* **Hint:** Insert a `parameters` block immediately below the `agent` declaration. Access the value using `params.TARGET_ENV` in the deployment stage.

#### Solution:
```groovy
pipeline {
    agent any

    // Define parameters for execution
    parameters {
        choice(
            name: 'TARGET_ENV', 
            choices: ['dev', 'staging'], 
            description: 'The target environment deployment destination'
        )
    }

    environment {
        APP_IMAGE_NAME = 'jenkins-demo-app'
        CONTAINER_NAME = "jenkins-demo-${params.TARGET_ENV}" // Dynamic container name
        HOST_PORT      = params.TARGET_ENV == 'staging' ? '8082' : '8081' // Dynamic host ports
        CONTAINER_PORT = '3000'
    }

    stages {
        // Stages 1-4 remain identical
        
        stage('Checkout') { steps { checkout scm } }
        stage('Install Dependencies') { steps { sh 'npm install' } }
        stage('Run Unit Tests') { steps { sh 'npm test' } }
        stage('Build Docker Image') { 
            steps { 
                sh "docker build -t ${APP_IMAGE_NAME}:${BUILD_NUMBER} ." 
            } 
        }

        stage('Deploy & Smoke Test') {
            steps {
                echo "=== STAGE 5: DEPLOYING TO ${params.TARGET_ENV.toUpperCase()} ==="
                
                sh """
                    if docker ps -a --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}\$"; then
                        docker stop ${CONTAINER_NAME} || true
                        docker rm -f ${CONTAINER_NAME} || true
                    fi
                """
                
                // Dynamically fetch the network name of the Jenkins container
                script {
                    env.NETWORK_NAME = sh(
                        script: "docker inspect jenkins-control-plane -f '{{range \$k,\$v := .NetworkSettings.Networks}}{{\$k}}{{end}}'",
                        returnStdout: true
                    ).trim()
                }
                
                sh "docker run -d --name ${CONTAINER_NAME} --network ${env.NETWORK_NAME} -p ${HOST_PORT}:${CONTAINER_PORT} ${APP_IMAGE_NAME}:${BUILD_NUMBER}"
                
                sh """
                    sleep 3
                    if curl -s http://${CONTAINER_NAME}:${CONTAINER_PORT}/health | grep -q '"status":"UP"'; then
                        echo "Deployment to ${params.TARGET_ENV} succeeded!"
                    else
                        echo "Deployment failed!"
                        exit 1
                    fi
                """
            }
        }
    }
    
    post {
        always {
            echo "Completed build #${BUILD_NUMBER} on env: ${params.TARGET_ENV}"
        }
        success {
            echo "Application is live on ${params.TARGET_ENV} at http://localhost:${HOST_PORT}"
        }
    }
}
```
> 💡 **Instructor Tip:** Inform students that the first time they run the pipeline after adding a parameter, it will build with the default values. The "Build Now" button will then change to **Build with Parameters** for all subsequent runs.

---

### Challenge 3: Slack/Email Alert Simulation
**Goal:** Configure notification logging inside the pipeline. Trigger a simulated Slack/email alert web request log whenever a build fails, incorporating key metadata such as build number, failure stage, and author.

* **Hint:** Leverage the `post { failure { ... } }` hook. Use standard shell echoing or curl formatting to print out the payload block.

#### Solution:
```groovy
pipeline {
    agent any
    
    // ... stages, environment, etc.
    
    post {
        failure {
            // Emulate sending a message block payload to a Slack Webhook URL
            sh """
                echo "--------------------------------------------------------"
                echo "🚨 SIMULATING SLACK ALARM DISPATCH..."
                echo "Sending HTTP POST Request to: https://hooks.slack.com/services/T00/B00/X00"
                echo "Payload: {"
                echo "  'text': '❌ Jenkins Build Failed!',"
                echo "  'attachments': [{"
                echo "     'color': 'danger',"
                echo "     'title': 'Pipeline Failure Alert: ${JOB_NAME} (Build #${BUILD_NUMBER})',"
                echo "     'title_link': '${RUN_DISPLAY_URL}',"
                echo "     'text': 'The build failed during execution. Please check the logs.'"
                echo "  }]"
                echo "}"
                echo "Slack Alert logged successfully."
                echo "--------------------------------------------------------"
            """
        }
    }
}
```

---

## Classroom Q&A & Key Takeaways
1. **Why mount `/var/run/docker.sock`?**
   It allows Jenkins (running inside a container) to communicate with the host's Docker daemon. This avoids running docker-in-docker (DinD), which requires privileged containers and carries significant performance and security overhead.
2. **What is the difference between declarative and scripted pipelines?**
   Declarative pipelines (like the one used in this lab) use a structured, opinionated syntax inside a `pipeline` block, making them easier to read and maintain. Scripted pipelines use imperative Groovy scripting, offering more flexibility but with a much steeper learning curve.
3. **What is a Quality Gate?**
   A mechanism (such as Stage 3: `Run Unit Tests` or Stage 5: `Smoke Test`) that automatically breaks the build pipeline before shipping code, preventing bugs or outages from reaching production servers.