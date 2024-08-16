# Helm commands
### creates a new helm chart
`helm create <application>`
### adds dependencies to the chart
`helm dependency update <helm-chart-location> (-n <namespace>)`
### installs the chart (if no namespace is defined it will install in default namespace)
`helm install <release-name> <chart> (-n <namespace> --create-namespace)`
### upgrades the release
`helm upgrade <release-name> <chart> (-n <namespace>)`
### uninstalls the release
`helm uninstall <release-name> (-n <namespace>)`

# Kubectl commands
### get all pods, services and deployments in the namespace
`kubectl get all (-n <namespace>)`
### get the logs of a pod
`kubectl logs pod/<pod-name> (-n <namespace>)`
### forwards a port from localhost to the service / pod
`kubectl port-forward svc/<service> <local-port>:<service-port> (-n <namespace>)`