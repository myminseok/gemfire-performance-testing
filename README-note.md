## Gemfire 2.0.1
https://docs.vmware.com/en/VMware-GemFire-for-Tanzu-Application-Service/2.0/gf-tas/content-advanced-configuration.html


## 인스턴스 생성
https://docs.vmware.com/en/VMware-GemFire-for-Tanzu-Application-Service/2.0/gf-tas/content-create-instance.html

VM memory allocation: https://docs.vmware.com/en/VMware-GemFire-for-Tanzu-Application-Service/2.0/gf-tas/content-memory-allocation.html
If total VM memory is 2GB or less, 1GB is allocated for the locator or server Java heap, and the remainder to the OS.
If total VM memory is between 2G and 8GB, 2GB is allocated to the OS, and the remainder to the locator or server Java heap.
If total VM total memory is greater than 8G, 4GB is allocated to the OS, and the remainder to locator or server Java heap.


co-locate multi plan에서는 memory 4GB가 최소임. 최소 서버: 3
cf create-service p-cloudcache co-multivm1 my-cloudcache -c '{"tls": true, "num_servers": 3}' 

cf create-service p-cloudcache co-multivm1 my-cloudcache -c '{"tls": true, "num_servers": 3, "service_gateway": true, "distributed_system_id": 2}'


cf create-service-key my-cloudcache test-key
cf service-key my-cloudcache test-key

bosh -d service-instance_1d8cf652-27f5-4220-a04f-1cadb69b08fe ssh locator-server/0


####  소캣 개수
https://geode.apache.org/docs/guide/114/managing/monitor_tune/socket_communication_have_enough_sockets.html



#### 

## Region type, 테이블 타입, 서버를 만든 후 설정
https://docs.vmware.com/en/VMware-GemFire/10.1/gf/developing-region_options-region_types.html#region_types
Server regions must have region type partitioned or replicated. 

https://docs.vmware.com/en/VMware-GemFire/10.1/gf/reference-topics-region_shortcuts_reference.html#replicateheaplru-90
https://docs.vmware.com/en/VMware-GemFire/10.1/gf/reference-topics-region_shortcuts_reference.html#replicatepersistent-100


#### redundant copy 다이어그램: 
https://docs.vmware.com/en/Node.js-Client-for-VMware-GemFire/2.0/gf-nodeclient/topics-data-arch.html
https://docs.vmware.com/en/VMware-GemFire/10.1/gf/developing-distributed_regions-how_region_versioning_works.html

single hop: https://docs.vmware.com/en/VMware-GemFire/10.1/gf/developing-partitioned_regions-configure_pr_single_hop.html


create region --name=ClusteredSpringSessions --type=PARTITION_HEAP_LRU --redundant-copies=1

create region --name=ClusteredSpringSessions --type=REPLICATE_PERSISTENT


/var/vcap/packages/gemfire/apache-geode/bin# export JAVA_HOME=/var/vcap/packages/jdk
/var/vcap/packages/gemfire/apache-geode/bin/gfsh

locator-server/4c5fec0a-a29e-461a-a8a4-c9c6af900194:/var/vcap/packages/gemfire/apache-geode/bin# ./gfsh


Cluster-0 gfsh>list regions;
List of regions
-----------------------
ClusteredSpringSessions


Cluster-0 gfsh>describe region --name=ClusteredSpringSessions
Name            : ClusteredSpringSessions
Data Policy     : partition
Hosting Members : cacheserver-4c5fec0a-a29e-461a-a8a4-c9c6af900194
                  cacheserver-c93c9297-1f80-4f25-8ff2-fd8ea4aa4b3e
                  cacheserver-5a733fc4-05d2-47b5-b6b9-c13a7f8dfa3a

Non-Default Attributes Shared By Hosting Members

 Type  |    Name     | Value
------ | ----------- | ---------
Region | size        | 0
       | data-policy | PARTITION


destroy region --name=ClusteredSpringSessions

Cluster-0 gfsh>destroy region --name=ClusteredSpringSessions
                     Member                      | Status | Message
------------------------------------------------ | ------ | --------------------------------------------------------
cacheserver-4c5fec0a-a29e-461a-a8a4-c9c6af900194 | OK     | Region '/ClusteredSpringSessions' destroyed successfully
cacheserver-5a733fc4-05d2-47b5-b6b9-c13a7f8dfa3a | OK     | Region '/ClusteredSpringSessions' destroyed successfully
cacheserver-c93c9297-1f80-4f25-8ff2-fd8ea4aa4b3e | OK     | Region '/ClusteredSpringSessions' destroyed successfully

Cluster-0 gfsh>list regions;
No Regions Found

Cluster-0 gfsh>create region --name=ClusteredSpringSessions --type=REPLICATE_PERSISTENT
                     Member                      | Status | Message
------------------------------------------------ | ------ | -----------------------------------------------------------------------------------------------
cacheserver-4c5fec0a-a29e-461a-a8a4-c9c6af900194 | OK     | Region "/ClusteredSpringSessions" created on "cacheserver-4c5fec0a-a29e-461a-a8a4-c9c6af900194"
cacheserver-5a733fc4-05d2-47b5-b6b9-c13a7f8dfa3a | OK     | Region "/ClusteredSpringSessions" created on "cacheserver-5a733fc4-05d2-47b5-b6b9-c13a7f8dfa3a"
cacheserver-c93c9297-1f80-4f25-8ff2-fd8ea4aa4b3e | OK     | Region "/ClusteredSpringSessions" created on "cacheserver-c93c9297-1f80-4f25-8ff2-fd8ea4aa4b3e"

Cluster configuration for group 'cluster' is updated.

Cluster-0 gfsh>describe region --name=ClusteredSpringSessions
Name            : ClusteredSpringSessions
Data Policy     : persistent replicate
Hosting Members : cacheserver-4c5fec0a-a29e-461a-a8a4-c9c6af900194
                  cacheserver-c93c9297-1f80-4f25-8ff2-fd8ea4aa4b3e
                  cacheserver-5a733fc4-05d2-47b5-b6b9-c13a7f8dfa3a

Non-Default Attributes Shared By Hosting Members

 Type  |    Name     | Value
------ | ----------- | --------------------
Region | data-policy | PERSISTENT_REPLICATE
       | size        | 0
       | scope       | distributed-ack



https://cloudcache-1d8cf652-27f5-4220-a04f-1cadb69b08fe.sys.lab.pcfdemo.net/pulse/clusterDetail.html

 "password": "hasKuVIsyr3Gwc7CkvoRBQ",
        "roles": [
          "cluster_operator"
        ],
        "username": "cluster_operator_kSZXA6unTmdKT3slnH5EJg"


## 샘플 앱

https://github.com/myminseok/tas-dev-workshop/blob/master/lab-session-state-caching.md
https://github.com/myminseok/spring-cloud-sample
https://github.com/myminseok/session-state-caching-gemfire-demo.git



curl -k https://session-state-caching.apps.lab.pcfdemo.net/addSessionNote -H "Content-type: application/json" -d 'data1' -X POST -c session.txt

curl -k  https://session-state-caching.apps.lab.pcfdemo.net/getSessionNotes -H "cookie: SESSION=OWFhNDBkYmMtY2NmZS00Yzk2LWExOTYtMjc5ODM0MzBiNzI0"




### VSD
https://community.pivotal.io/s/article/troubleshooting-using-visual-statistics-display?language=en_US

https://docs.vmware.com/en/VMware-GemFire-for-Tanzu-Application-Service/2.0/gf-tas/content-using-pcc.html#exporting_logs


## Gemfire tuning


gemfire for TAS는 sysctl 설정이 별도로 있음.
```

locator-server/4c5fec0a-a29e-461a-a8a4-c9c6af900194:/var/vcap/jobs/gemfire-server# cat config/sysctl.gemfire.conf
fs.file-max=2097152
vm.swappiness=0
vm.zone_reclaim_mode=0
net.core.somaxconn=1024
net.core.netdev_max_backlog=4096
net.core.rmem_max=16777216
net.core.wmem_max=16777216
net.ipv4.tcp_wmem=4096 12582912 16777216
net.ipv4.tcp_rmem=4096 12582912 16777216
net.ipv4.tcp_syncookies=0
net.ipv4.tcp_max_syn_backlog=4096

```



### conserve-socket 
https://docs.vmware.com/en/VMware-GemFire/10.0/gf/getting_started-system_requirements-host_machine.html
여기 conserve-socket 설정을 false로 해야 성능이 최대로 나온다. 그러니, system socket갯수를 충분히 확보해라..
An adequate per-user limit on the number of file descriptors; for Unix/Linux, the recommended soft limit is 8192, and the hard limit is 81920.
An adequate per-user limit on the number of processes (nproc); for Unix/Linux, the recommended soft limit is 501408, with an unlimited hard limit.

### ulimit
ubuntu 20.04

```
#### edit the following file

user@ubuntu:~$ sudo vim /etc/security/limits.conf

####  add following lines to it

* soft nproc 65535 
* hard nproc 65535 
* soft nofile 8192 
* hard nofile 81920
root soft nproc 65535 
root hard nproc 65535 
root soft nofile 8192 
root hard nofile 8192
```

```
# edit the following file

user@ubuntu:~$ sudo vim /etc/pam.d/common-session

# add this line to it

session required pam_limits.so

# logout and login and try the following command
user@ubuntu:~$ ulimit -n

sudo vi /etc/sysctl.conf
```


### TIME_WAIT
net.ipv4.tcp_tw_reuse=1