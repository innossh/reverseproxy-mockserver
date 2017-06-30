# reverseproxy-mockserver

```console
$ cd /path/to/this/repo/docker
$ docker-compose up -d
$ docker-compose ps
         Name                       Command               State                      Ports
 ------------------------------------------------------------------------------------------------------------
 docker_backend1_1       /opt/mockserver/run_mockse ...   Up      1080/tcp, 1090/tcp, 0.0.0.0:1081->11111/tcp
 docker_backend2_1       /opt/mockserver/run_mockse ...   Up      1080/tcp, 1090/tcp, 0.0.0.0:1082->22222/tcp
 docker_reverseproxy_1   nginx -g daemon off;             Up      0.0.0.0:80->80/tcp

$ cd /path/to/this/repo
$ ./gradlew test
...
innossh.reverseproxy.mockserver.AccessDeniedTest > testAccessDenied(1.2.3.4,204) [0] STARTED

innossh.reverseproxy.mockserver.AccessDeniedTest > testAccessDenied(1.2.3.4,204) [0] PASSED

innossh.reverseproxy.mockserver.AccessDeniedTest > testAccessDenied(4.3.2.1,403) [1] STARTED

innossh.reverseproxy.mockserver.AccessDeniedTest > testAccessDenied(4.3.2.1,403) [1] PASSED

innossh.reverseproxy.mockserver.AccessDeniedTest > testNormalToBackend1 STARTED

innossh.reverseproxy.mockserver.AccessDeniedTest > testNormalToBackend1 PASSED

innossh.reverseproxy.mockserver.AccessDeniedTest > testNormalToBackend2 STARTED

innossh.reverseproxy.mockserver.AccessDeniedTest > testNormalToBackend2 PASSED

BUILD SUCCESSFUL

Total time: 7.684 secs
```
