# Hello API — Spring Boot Microservice on Kubernetes

A simple REST API microservice built with **Java 17 + Spring Boot**, containerized with **Docker**, and deployed on **Kubernetes** (free local cluster via Minikube or kind).

---

## Project Structure

```
microservice/
├── src/
│   └── main/
│       ├── java/com/example/api/
│       │   ├── Application.java       # Entry point
│       │   └── ApiController.java     # REST endpoints
│       └── resources/
│           └── application.properties
├── k8s/
│   ├── deployment.yaml                # K8s Deployment (2 replicas)
│   └── service.yaml                   # K8s NodePort Service
├── Dockerfile                         # Multi-stage build
├── docker-compose.yml                 # Local dev without k8s
└── pom.xml                            # Maven build
```

---

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/hello` | Returns a greeting |
| GET | `/api/hello?name=Alice` | Personalized greeting |
| GET | `/api/health` | Health check |

Example responses:
```json
// GET /api/hello?name=Alice
{ "message": "Hello, Alice!", "timestamp": "2026-05-07T10:00:00Z" }

// GET /api/health
{ "status": "UP", "service": "hello-api", "timestamp": "2026-05-07T10:00:00Z" }
```

---

## Prerequisites (all free)

| Tool | Purpose | Install |
|------|---------|---------|
| Java 17 | Build | https://adoptium.net |
| Maven 3.9+ | Build tool | https://maven.apache.org |
| Docker Desktop | Containers | https://docker.com |
| Minikube **or** kind | Local k8s | See below |
| kubectl | K8s CLI | https://kubernetes.io/docs/tasks/tools |

---

## Option A — Local Dev with Docker Compose (simplest)

```bash
# Build and run
docker compose up --build

# Test
curl http://localhost:8080/api/health
curl http://localhost:8080/api/hello?name=YourName

# Stop
docker compose down
```

---

## Option B — Deploy to Kubernetes (Minikube)

### 1. Install Minikube (free)
```bash
# macOS
brew install minikube

# Linux
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

# Windows (PowerShell as Admin)
winget install minikube
```

### 2. Start Minikube
```bash
minikube start
```

### 3. Point Docker to Minikube's registry
```bash
# This makes images built locally visible to Minikube
eval $(minikube docker-env)        # macOS/Linux
# Windows PowerShell:
# & minikube -p minikube docker-env --shell powershell | Invoke-Expression
```

### 4. Build the Docker image
```bash
docker build -t hello-api:latest .
```

### 5. Deploy to Kubernetes
```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### 6. Check everything is running
```bash
# Watch pods come up (Ctrl+C to exit)
kubectl get pods -w

# Check service
kubectl get svc hello-api-service
```

### 7. Access the API
```bash
# Get the URL from Minikube
minikube service hello-api-service --url

# Then test it
curl <URL>/api/health
curl <URL>/api/hello?name=Kubernetes
```

---

## Option C — Deploy to Kubernetes (kind)

### 1. Install kind
```bash
# macOS
brew install kind

# Linux/Windows: https://kind.sigs.k8s.io/docs/user/quick-start/#installation
```

### 2. Create a cluster
```bash
kind create cluster --name hello-cluster
```

### 3. Build and load image into kind
```bash
docker build -t hello-api:latest .
kind load docker-image hello-api:latest --name hello-cluster
```

### 4. Deploy
```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### 5. Access via port-forward (kind doesn't expose NodePort by default)
```bash
kubectl port-forward svc/hello-api-service 8080:80
curl http://localhost:8080/api/health
```

---

## Useful kubectl Commands

```bash
# View running pods
kubectl get pods

# View logs
kubectl logs -l app=hello-api

# Scale replicas
kubectl scale deployment hello-api --replicas=3

# Describe deployment
kubectl describe deployment hello-api

# Delete everything
kubectl delete -f k8s/
```

---

## How It Works

```
Internet / curl
      │
      ▼
┌─────────────────┐
│  K8s Service    │  NodePort :30080 → Pod :8080
│ (Load Balancer) │
└────────┬────────┘
         │
    ┌────┴────┐
    │         │
┌───▼──┐  ┌──▼───┐
│ Pod1 │  │ Pod2 │   ← 2 replicas for HA
└──────┘  └──────┘
  Spring Boot app
```

The Kubernetes **Deployment** ensures 2 replicas are always running. The **Service** load-balances traffic between them. Health checks (`/api/health`) tell Kubernetes when a pod is ready to receive traffic.
