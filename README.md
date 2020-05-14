# employee-management
Employee Management System is used by employers to manage employee records of a company. This employee management system interacts with another
remote Payroll system and make sure that the two systems are always in sync. 

The dummy API for the payroll management system is present here: http://dummy.restapiexample.com

### CONSTRAINTS CONSIDERED
        1. name should only have alphabets.
        2. Salary should not be negative, should be numeric and must be greater than or equal to 1.
        3. Age must be in range 18 to 100 and must be numeric.
    

### Operations supported:
        1. Create Employee : if employee name already exists, it will create a record with incremental name based approach.
        2. Fetch Employee By Name : will return all the employees having the name similar to the provided name. 
                            Here, we are using fuzzy search. Hence, search of "bus" will return employee name albus if present.
                            
        3. Fetch employee by age :  will return all the employees with the same provided age.

### API CONTRACT:
![API Contract:] (https://documenter.getpostman.com/view/9464343/SzmiWbmS)

### INSTALLATION STEPS (Make sure docker is installed)

        1. cd <project_root_directory>
        2. mvn clean install
        3. docker build . -t amanjadon54/employee-application
        4. docker run -d -p 10001:10001 amanjadon54/employee-application

        If Docker is not installed, You can run it directly by cloning the repository and performing:
        1. cd <project_root_directory>
        2. mvn clean install
        3. java -jar target/employee-management-system-1.0-SNAPSHOT.jar


### DESIGN:
![create Employee Design](https://github.com/amanjadon54/employee-management/blob/master/design/cretateEmployeeDesign.png?raw=true)
#### CreateEmployee:
    To maintain consistency. We will call the payroll service first, and then on successful result we will save in employee
    management db.
    
    Scenarios for failure:
    1. Payroll service is down: 
        In which, we will end up with no data in our db as the failure has occured in a 
        transactional block.
        
    2. Our service is down while eaiting for response:
        In which, we will not have inconsistent data in our db.

![create Bulk_Employee_Design](https://github.com/amanjadon54/employee-management/blob/master/design/createBulkEmployeeDesign.png?raw=true)

### Bulk Create
    1. Service will recieve the bulk request and will push it to the async queue.
    2. User will be responded back immediately with job Id.
    3. This JobId will be taken into db for status tracking and an API will be exposed through which user can track the progress of the job.
    4. Listener will be listening to Queue for new events. On recieveing any it will call the createEmployee in the same manner as create Employee request for each event.
    5. Job will be continued even if one of the request fails.

### ADDITIONAL FEATURES:
1. Logging: 
Each of the request will be assigned a unique logId for better debugging purpose in case of errors.

    Custom Annotational Support:
        1. @RequestResponse : Logs the request and response of a controller.
        2. @MdcLog : if present on top of method, logs the methodName, with logId and request params.

2. Exception Handling
    Most of the known exceptions are handled and returns the error in user readable format, with scope for developer
    to trace the error quickly using developer message.
    
3. System Metrics
    Access Metrics:
    You can view metrices related to Jvm, threads memory, http traffic, etc. using the endpoint:
    
    <host>:<port>/actuator
    
    if you want specific metrics:
    you can provide further the metrics name in the above base url like:
    (<host>:<port>/actuator/<metrics_name>)
    
    example:
    <host>:<port>/actuator/prometheus
    
   
