# employee-management


###CONSTRAINTS:
    1. name should only have alphabets.
    2. Salary should not be negative, should be numeric and must be greater than or equal to 1.
    3. Age must be in range 18 to 100 and must be numeric.

API CONTRACT:
[Postman Contract :] (https://documenter.getpostman.com/view/9464343/SzmiWbmS)


Logging: 
Each of the request will be assigned a unique logId for better debugging purpose in case of errors.

Custom Annotational Support:
1. @RequestResponse : Logs the request and response of a controller.
2. @MdcLog : if present on top of method, logs the methodName, with logId and request params.


Case:
A. CreateEmployee:
    To maintain consistency. We will call the payroll service first, and then on successful result we will save in employee
    management db.
    
    Scenarios for failure:
    1. Payroll service is down: 
        In which, we will end up with no data in our db as the failure has occured in a 
        transactional block.
        
    2. Our service is down while eaiting for response:
        In which, we will not have inconsistent data in our db.

B. Bulk Create
    
    
    
Steps to run: (Make sure docker is installed)
1. cd <project_root_directory>
2. mvn clean install
3. docker build . -t amanjadon54/employee-application
4. docker run -d -p 10001:10001 amanjadon54/employee-application

If Docker is not installed, You can run it directly by cloning the repository and performing:
1. cd <project_root_directory>
2. mvn clean install
3. java -jar target/employee-management-system-1.0-SNAPSHOT.jar


Access Metrics:
You can view metrices related to Jvm, threads memory, http traffic using the endpoint:
<host>:<port>/actuator

if you want specific metrics:
you can provide further the metrics name in the above base url like:
<host>:<port>/actuator/<metrics_name>

example:
<host>:<port>/actuator/prometheus


