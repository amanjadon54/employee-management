# employee-management

Logging: Each of the request will be assigned a logId for better debugging purpose in case of errors.

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
