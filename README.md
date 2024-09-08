# ignite-kubernetes-demo
Demo project for Apache Ignite running in Kubernetes

## Used with microk8s running on Ubuntu 22.04
#### Install the latest version of microk8s 
`snap install microk8s --classic`

#### Start microk8s
`microk8s start`

#### Enable addons
`microk8s enable registry observability`

#### create an alias for `microk8s helm` or `microk8s kubectl`

#### Install headlamp (optional)
`helm repo add headlamp https://headlamp-k8s.github.io/headlamp/`<br>
`helm install my-headlamp headlamp/headlamp --namespace headlamp --create-namespace`

### Build the application and upload the images
`./gradlew build jib`

### Deploy the application with Helm
`helm dependency update helm/ignite-k8s-demo`<br>
`helm install <release-name> helm/ignite-k8s-demo/ -n <namespace> --create-namespace`

#### You can list and uninstall the application with Helm
`helm list -n <namespace>`<br>
`helm uninstall <release-name> -n <namespace>`