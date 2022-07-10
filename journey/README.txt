#Code Explanation

Task 1:(API Development)
------
Microservice Name : journey

EndPoint:
----------

Best Journey details:
http://localhost:8080/plan/journey


Package Details
----------------
 Controller:
   JourneyController (API End point)
 Entity
   Entity files (Passenger,Journey)
 Repository
   DynamoDBMapper repository for the entity (Connected with AWS DynamoDB)
 Service
   Service files for UserOperation 
 Config
   DyanamoDB configuration
   RESTTemplate configuration
 Utils
   Constants
   JSON Reader (Parsing JSON)
   
application properties
-----------------------
  aws details

 
#Files Attached
----------------
 journey-LLD.png
    - LLD architectural diagram
 Testcase-Documet.xlsx
    - Testcases and proof attached - journey API
 
 #Build
 ------
 #Code Build in Maven
 
 
 #Testing
 ---------
 #JUnit used for unitTesting
 #Postman for API call testing 
 
 #Code
 -----
 Code committed in GIT Repository
  --https://github.com/santhosh27jd/journey
  
  
  
Task 2: (System design) 
------ 
Microservice Name : journey
 
EndPoint:
--------
SNS Notification:
http://localhost:8080/plan/journey/sns 

#Files Attached
----------------
System-design-SMS.png
 