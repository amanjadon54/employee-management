# Employee-management
Employee Management System is used by employers to manage employee records of a company. This employee management system interacts with another
remote Payroll system and make sure that the two systems are always in sync. 

The dummy API for the payroll management system is present here: http://dummy.restapiexample.com

### CONSTRAINTS CONSIDERED
        1. Name should only have alphabets.
        2. Salary should not be negative, should be numeric and must be greater than or equal to 1.
        3. Age must be in range 18 to 100 and must be numeric.
   

### Operations supported:
        1. Create Employee : if employee name already exists, it will create a record with incremental name based approach.
        2. Fetch Employee By Name : will return all the employees having the name similar to the provided name.
           Here, we are using partial search. Hence, search of "bus" will return employee name albus if present.
                           
        3. Fetch employee by age :  will return all the employees with the same provided age.

### API CONTRACT:
[API Contract](https://documenter.getpostman.com/view/9464343/SzmiWbmS)

### INSTALLATION STEPS (Make sure docker is installed)
[Docker Image](https://hub.docker.com/r/amanjadon54/employee-application) 
1. Get the docker image from above link or pull directly using:
  
           docker pull amanjadon54/employee-application
           
2. Execute the command
                
           docker run -d -p 10001:10001 amanjadon54/employee-application

       
If Docker is not installed, You can run it directly by cloning the repository and performing:
        1. cd <project_root_directory>
        2. mvn clean install
        3. java -jar target/employee-management-system-1.0-SNAPSHOT.jar


### DESIGN:
#### CreateEmployee:

![create Employee Design](https://github.com/amanjadon54/employee-management/blob/master/design/cretateEmployeeDesign.png?raw=true)

    To maintain consistency. We will call the payroll service first, and then on successful result we will save in employee
    management db.
   
    Scenarios for failure:
    1. Payroll service is down:
        In which, we will end up with no data in our db as the failure has occured in a
        transactional block.
       
    2. Our service is down while waiting for response:
        In which, we will not have inconsistent data in our db.

    3. Problem arises of unrelated data may be present at payroll service for which we can run scheduler jobs to clear such                     
       data.

### Bulk Create

![create Bulk_Employee_Design](https://github.com/amanjadon54/employee-management/blob/master/design/createBulkEmployeeDesign.png?raw=true)

#### Components:
1. Message Broker System : for asynchronous processing of employees. Also, to ensure even if\
one of the job fails, rest continues.
2. TaskManagementService : Purpose of service is to store the status of tasks/jobs. It provides an end point to keep\
the track of task whether in progress, success, or failure\
3. Consumer : For processing of CreateEmployeeEvent. \

#### Order:
         1. Each bulk request will be first stored in the message queue, and the user will be responded
           immediately with task id for tracking purpose. At this point a task will be created in the database
           containing 2 main fields:
           TaskId, status
           Initial status of each task will be CREATED.
       
        2. Once the queue gets a record, our consumer will listen to the event and start processing
           the create employee request by calling the createEmployee method (already created)
           If the event is completed, the status of the task will be updated to Success, or
           failure if it fails.
       
        3. Job will be continued even if one of the request fails.
   NOTE: We will be defining the batch size allowed for processing of bulk request.

### ADDITIONAL FEATURES:
1. Logging:
Each of the request will be assigned a unique logId for better debugging purpose in case of errorCustom\
 Annotational Support:\
        @RequestResponse : Logs the request and response of a controller.\
        \
        @MdcLog : if present on top of method, logs the methodName, with logId and passed parameters.

2. #### Exception Handling\
    Most of the known exceptions are handled and returns the error in user readable format, with scope for developer\
    to trace the error quickly using developer message.\
    A sample execption contains 3 fields:\

        StatusCode : appropriate status of each response.\
        Message : User understandable message so as to ensure better usability.\
        DeveloperMessage : will contain logId of the request and Error code, relevant to the developer for easy\
                           debugging of error.
   
3. System Metrics\
    Access Metrics:\
    You can view metrices related to Jvm, threads memory, http traffic, etc. using the endpoint:
   
        host:port/actuator
   
    if you want specific metrics:\
    you can provide further the metrics name in the above base url like:
    host:port/actuator/<metrics_name>
   
    example:\
    host:port/actuator/prometheus
