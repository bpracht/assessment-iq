# Services for Password Validation and Generation
### Ben Pracht

### section-1.sql:
Section 1 SQL commands (based off MySQL database) to create the two requested tables and insert the data as represented. The database and files are UTF-8 encoded. While it may have been feasible to add constraints, foreign and/or primary keys, define indexes or similar, but more information would be needed to implement those.  This covers section 1 from the requirements.

### password-reset-module/ 
Java implementation of RESTful API to validate existing passwords and generate new valid ones for those that are invalid. The stack is Java 8 or higher, uses Java Streams has unit tests defined and makes queries using MyBatis. Validation is done in a clearly labeled way for auditing purposes. Connections are made in a very direct way, but in a production deployment, the most likely some JDNI method and/or Kubernetes password scheme would be used to make the deployment more portable. It would be possible to implement an update capability for the password, but I'd recommend placing it in a different column or at least re-labeling the initial password column for clarity. This is the source code files for section 2.2 and 2.3.

Internally, it will upon request, fetch the records in the BATCH_ITEM_TB to validate the passwords according to the rules specified. If the one or more
password rules are fail, it calls another service to generate a compliant password. Almost all steps can be individually unit tested for maintenance and
development.

### get-valid-items-as-csv.sh 
Curl request to the API to get the result as a CSV. The file sample.csv is the result of running this file.

### get-valid-items-as-json.sh
Curl request to the API to get the result as a JSON. The file sample.json is the result of running this file.

### IQVIA_InfoSec_Coding_Test.pdf
Original requirements PDF bundled for convenience

### sample.csv and sample.json
Outputs from password-rest-module given the data supplied.


##
To run this source code, the project 'password-reset-module' needs to be deployed to a Tomcat container. It is known to work with V9, but lower versions
might work also. The JVM needs to be at least 1.8 or higher.  Port 8080 needs to be free.  The service will be accessible via URL

localhost:8080/password-reset-module/rest/valid-passwords








