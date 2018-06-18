# spaces-tcp-issue
Reproduction of issue with TCP MLLP in PCF

---
##### Summary
Interfaces built by teams on our platform typically use MLLP when communicating with TCP Comservers (even when not sending HL7 payloads)

The typical envelope for MLLP is:
* Start Bytes: **11** (Vertical Tab)
* End Bytes: **28,13** (File Separator Character followed by Carriage Return)

When such a server is deployed to PCF, I get 'Connection Reset' errors when I try to communicate with it.

Strangely, the problem does not happen when other combinations of bytes are used for start bytes
* For example, it works to use **12** instead of **11**
* See a number of working and non-working examples [here](spaces-tcp-issue-sender/src/main/resources/application.yml)

When both sender and receiver are deployed to PCF, they do not seem to have the same issue (I think I've seen the same issue in certain setups, currently unable to reproduce)

---
##### Reproduction
I have created two projects:
* **spaces-tcp-issue-sender** - a Spring Boot WebApp which acts as a client, sending to the server with configurable start bytes.
    * Trigger a test by starting the app and hitting port 8080
* **spaces-tcp-issue-receiver** - a Spring Boot App which acts as a TCP Server, echoing all bytes back to the sender until it sees end bytes.
    * While MLLP client/server pairs typically need to have the same start/end bytes, this app only cares about end bytes, so you can try different start byte combinations in the sender without reconfiguring the receiver.

##### Steps for local-local (works with any start bytes)
1. Configure [the sender's application.yml](spaces-tcp-issue-sender/src/main/resources/application.yml) to point to localhost  
  Optionally, change the start bytes to test a different scenario, as desired
2. In a terminal, start the sender  
  `cd spaces-tcp-issue-sender && mvnw clean package && java -jar target/spaces-tcp-issue-sender-0.0.1-SNAPSHOT.jar`  
3. In a separate terminal, start the receiver  
  `cd spaces-tcp-issue-receiver && mvnw clean package && java -jar target/spaces-tcp-issue-receiver-0.0.1-SNAPSHOT.jar`  
4. Open a web browser and navigate to [http://localhost:8080](http://localhost:8080)

##### Steps for local-remote (only works with certain start bytes)
1. Configure [the sender's application.yml](spaces-tcp-issue-sender/src/main/resources/application.yml) to point to PCF  
  Optionally, change the start bytes to test a different scenario, as desired  
2. In a terminal, start the sender  
  `cd spaces-tcp-issue-sender && mvnw clean package && java -jar target/spaces-tcp-issue-sender-0.0.1-SNAPSHOT.jar`  
3. In a separate terminal, push the receiver to PCF  
  `cd spaces-tcp-issue-receiver && mvnw clean package && cf push`  
4. Open a web browser and navigate to [http://localhost:8080](http://localhost:8080)
