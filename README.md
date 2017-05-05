### smi-service-swagger-aggregator

### Purpose

This repository is for building the service-swagger-aggregator docker container.

The service-swagger-aggregator container is a stateless spring-boot microservice that exposes a REST API for the purpose of downloading static swagger pdf api documents for all the smi micro-services with RackHD northbound and RackHD redfish api. It also aggregates all the SMI service swagger ui api to provide single url to access all SMI micro-services swagger UI.

---

### How to Use

#### Startup
Standalone, with no configuration settings provided:
```
sudo docker run --name service-server-inventory -p 0.0.0.0:46021:460021 -d rackhd/dell-swagger-aggregator:latest
```
The service can also start up to bootstrap its configuration from consul.  More information about registration with and using advanced configuration settings provided by a Consul K/V store can be found in the online help.

#### API Definitions

A swagger UI is provided by the micro-service will contain swagger ui for all SMI micro-services at http://<<ip>>:46021/swagger-ui.html

##### Syncronous API's

List the URL's for all the -static documents - http://<<ip>>:46021//api/1.0/swagger/documents/

Device-Discovery - http://<<ip>>:46021/api/1.0/swagger/documents/download/Device-Discovery-1.0.pdf/
Chassis-Inventory - http://<<ip>>:46021/api/1.0/swagger/documents/download/Chassis-Inventory-1.0.pdf/
Server-Inventory  - http://<<ip>>:46021/api/1.0/swagger/documents/download/Server-Inventory-1.0.pdf/
Server-Action - http://<<ip>>:46021/api/1.0/swagger/documents/download/Server-Action-1.0.pdf/
Server-Configuration - http://<<ip>>:46021/api/1.0/swagger/documents/download/Server-Configuration-1.0.pdf/
Server-Firmware-Update - http://<<ip>>:46021/api/1.0/swagger/documents/download/Server-Firmware-Update-1.0.pdf/
Server-OS-Deployment - http://<<ip>>:46021/api/1.0/swagger/documents/download/Server-OS-Deployment-1.0.pdf/
Virtual-Identity - http://<<ip>>:46021/api/1.0/swagger/documents/download/Virtual-Identity-1.0.pdf/
Virtual-Network - http://<<ip>>:46021/api/1.0/swagger/documents/download/Virtual-Network-1.0.pdf/
Power-Thermal-Monitor - http://<<ip>>:46021/api/1.0/swagger/documents/download/Power-Thermal-Monitor-1.0.pdf/
RackHD-Nothbound API 2.1- http://<<ip>>:46021/api/1.0/swagger/documents/download/RackHD-API-2.1.0.pdf/
RackHD-Redfish-API - http://<<ip>>:46021/api/1.0/swagger/documents/download/RackHD-Redfish-API-2.0.pdf/
All API documents - http://<<ip>>:46021/api/1.0/swagger/documents/download/api-documents.zip/


---

### Support
Slack Channel: codecommunity.slack.com