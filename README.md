# Scheduled jobs in Spring Microservices

Often services need some kind of "cronjobs" or rather scheduled tasks. [Spring supports scheduling](https://spring.io/guides/gs/scheduling-tasks) out of the box.

Very often, each scheduled tasks should only be executed once, but in typical microservice-setups there are multiple instances of a service running.

There are different approaches to execute a task only once.

- Trigger the job "from the outside", i.e. via HTTP-Request. A load balancer makes sure only one instance works on this job. This is great to leverage existing schedulers, e.g. [Kubernetes CronJobs](https://kubernetes.io/docs/concepts/workloads/controllers/cron-jobs/)
- Use distributed locking, e.g. via [SchedLock](https://github.com/lukas-krecan/ShedLock). This solution is independent of it's platform (although it requires something to manage the lock, e.g. a database)

## Kubernetes native solution

Kubernetes provides a lot of solutions for typical problems of distributed services. One of those problems is [leader election](https://en.wikipedia.org/wiki/Leader_election). 
Fortunately, [Spring Cloud Kubernetes](https://spring.io/projects/spring-cloud-kubernetes) provides a kubernetes-based [leader-election implementation](https://docs.spring.io/spring-cloud-kubernetes/reference/leader-election.html).

Using leader-election and spring-scheduling, it's very easy to implement a "run exactly one scheduled task" solution.

## Pros and Cons

Each solution has its pros and cons. 

Task scheduling based on kubernetes leader election has the advantage that it does not have any external dependencies (locks in a database, an HTTP-endpoint that needs to be secured).

# Example

To run the example, apply the `example.yaml` manifest to deploy the application, e.g. to [minikube](https://minikube.sigs.k8s.io/docs/start), but you can use any cluster you like.

```sh
kubectl --context minikube apply -f example.yaml
```

Then view logs any verify only one pod runs the job.

```sh
kubectl --context minikube logs --prefix -f -l app=scheduler 
```

