Number to String
================

## Problem Statement:

Using a JVM based language of choice, *produce a library* that has the following capability within it:

**given some integer (N) as an argument, return a string representation of that integer.**

##Analysis
String representation of a number in English language follows a few rules. 
 * From zero (0) to twenty (19) string representation is unique for each number i.e.
("Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
 "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
 "Eighteen", "Nineteen")
 * from 20 to 90 as multiple of 10 has uniques representation i.e. 
"Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety". 
 * Scales of ones, tens, hundreds, and thousands 
are increased by power of 10 and 
 * then larger numbers are constituted by 10 to the power 3 (10 cubed).   

Number | String Representation
-------|----------------------
1      |    One
10 | Ten
100 | Hundred
1,000 | Thousand
1,000,000 | Million
1,000,000,000 | Billion 
1,000,000,000,000 | Trillion

[Details](https://en.wikipedia.org/wiki/Long_and_short_scales#English-speaking)

Numbers can be negative and also contain decimal points. 
Decimal numbers are out of scope of this project.

Based on the english numbering system as stated above we can constitute below algorithm to
convert a number (non decimal) to string: 

### Algorithm
1. **Zero**
If the number is '0' then string representation is 'Zero' and no further processing is required
1. use absolute values for calculations
1. **Split in groups of three digit** numbers will be grouped by 3 digits (right to left). For example, 
   * 1st group (of 3 digits) represents values ones, tens and hundreds (1 to 999), 
   * 2nd group (of 3 digits) represents values thousands (1000 to 999,000)
   * 3rd group (of 3 digits) represents values millions (1,000,000 to 999,000,000)
   * continues similarly for billion, trillion and so forth
1. for each group apply below rules   
1. **Hundreds**
get the string representation of hundreds digit (3rd from right) / integer division by 100
from the list of unique numbers string values (one ... nineteen) corresponding to the integer 
digit and append the word Hundred. Ignore if the digit is '0'  
1. **Tens** 
if the digit  at tens is from 2 to 9 (2nd from right) / integer division by 10 to the remainder 
of hundreds rule then get the string representation of the number 20 to 90 string list. Ignore if the digit is '0'  
1. **Tens and Ones** 
If a number is in the range 1 to 19 use the unique number representation. 
1. **Combine groups**
if the number is split into multiple groups then combine the group's string representation
with corresponding scale in reverse order i.e. million then thousand and then hundred etc.
Symbols can be appended after the scale
1. prepend Negative if input was a negative value
   
#### Examples
 * __input '0' :__
   - String representation wil be 'Zero' in step 1
   - output = 'Zero'
   
 * __input '43' :__
   - not zero
   - group of 3 digit: 1 group i.e. 43
   - hundreds rule will be ignored
   - tens rule applies, string representation becomes Forty
   - tens and ones rule applies, appends Three
   - output = Forty Three
   
 * __input '23443'__  
   - non zero
   - split in 2 groups i.e. '443' and '23'
   - iterate each groups (as above example), string values 'Four Hundred Forty Three' and 'Twenty Three'
   - combine, reverse order with scale 'Twenty Three Thousand' then append 'Four Hundred Forty Three'
   - output: Twenty Three Thousand, Four Hundred Forty Three


## Key Architecture Goals
### API: 
features should be exposed by means of API. Initial version can expose REST API but should allow 
to implement support for using various other protocols e.g. gRPC etc. And make service language agnostic
i.e. clients can be build in any programming language.
### Extensibility: 
architecture should support adding / extending features easily
### Environment: 
system should support configurable items
### Deployability:
system should be individually deployable 
### Availability:
system should be ready to be deployed in cloud environment and highly available
### Scalability:
APIs should be stateless and scalable i.e. ready to support load balancing
### Security:
should support security implementation
### logging and tracing
should have appropriate level of logging and be ready to implement monitoring
 
   
## Technology (language, build tool and framework)
### MVC framework  
Spring-boot framework will be used as it is widely used in the industry and provides opinionated features and
dependency injection. Also this application can easily be extended to web application. 

Although it may seem like a overkill to use a full-fledged framework for solving a small problem,
using spring framework will help to achieve many of our architectural goals.
 
### Container
Docker container will be used which will enable us to make the deployment easy in different
environments and also introduce capability to deploy in containerized environments like
Kubernetes or ECS etc.
 
### Programming language, build tools and versions 
```
java (8 or above)
gradle (4.x)
SpringBoot (1.5.10-RELEASE)
```
## Design Considerations:
 * Strings are used for input and not converted to integer (or long etc.) to capture large numbers (longer than)
integer / long types' maximum values. Input string are split into smaller groups (3 digit) and then converted to
integers.
 * words corresponding to numbers are stored in properties files instead of hard-coded constants or enum types.
This feature enables flexibility of the service to cater for different languages that follows same number rules and
also enables configuring scales. For example, 1,500,000 'One Million, Five Hundred Thousand' can also be represented 
as 'One Thousand, Five Hundred Thousand'   
 * default scale has been configured to have max scale Trillion but can be configured to cater for higher scales like
Septillion, Octillion.. etc. [see properties file](application.properties) 
 * configuration items also provides opportunity to introduce internationalization in he future.
 
## Assumptions
 * Only supports integer numbers (negative and positive). +/- sign should only be at the beginning if present
 * Decimal numbers are not supported
 * Only digits, comma (',') and white-spaces are accepted
 
## Development methodology
 * User stories have been captured in file [UserStories](UserStories.md) and status will be tracked there
 * Created 'develop' branch in revision control
 * feature branches will be created per user stories. Once feature is complete then that 
branch will be merged to develop branch. Ideally, once successfully merged, deployed and tested the
feature branch can be deleted but for tracking purpose these branches will be deleted after completion
of the development (once final pull request to master is accepted and merged) 
 * release-v0.n version will be created for production release. Once pull request have been reviewed 
changed will merged to master     

## HOW TO
#### clone the project from bitbucket
```
git clone https://mrajibkhan@bitbucket.org/onprem/interview-rajib_khan.git
cd interview-rajib_khan
git checkout origin/<branch>
```
### Run Tests:
```
gradle --info clean test
```
test generates report in ```build/reports/tests/test```

### Build Application:
```
gradle --info clean build
```
an executable jar will be generated in ```build/libs/numberConverter-0.0.1-SNAPSHOT.jar```

### Run Application:
```
java -jar build/libs/numberConverter-0.0.1-SNAPSHOT.jar
```
or with gradle
```
gradle bootRun
```
### Use the API (REST endpoint)
 * Use browser: hit the URL http://localhost:8080/numberToString/{input number}
 * use wget
```
wget -qO - "http://localhost:8080/numberToString/123"
```
 * or curl
```
curl -v localhost:8080/numberToString/123
```

### Run in Docker:
If you don't want to install jdk and gradle and have docker installed you can build and run 
the application in docker container as well. 

#### build and run with docker-compose
```
docker-compose build
docker-compose up
```
Note: default host port is 8080. Change this in docker-compose.yml file
if you want to use a different port mapping

If you rather build and run separately / don't have docker-compose installed 
then you can build and run using below steps

#### build:
```
docker build -t number-converter .
```

#### Run:
```
docker run -it -p 9090:8080 number-converter:latest
```
you can change your localhost port (9090) to any other available port if you wish. 

## Application Configuration
Configuration can be found in [application.properties](src/main/resources/application.properties)
 * port
 ```
 server.port=8080
 ```
for running docker-compose port mapping is located in [docker-compose.yml](docker-compose.yml) 
 * logging 
 logs will be generated in logs folder
 * Scale config:
 ```
 words.large.scale=,Thousand,Million,Billion,Trillion
 ``` 
Maximum scale can be configured here. If you put the maximum as Million then 1 Billion will be
converted as 1000 Million. You can use other available scales in order. Example:
```
 words.large.scale=,Thousand,Million,Billion,Trillion,Quadrillion,Quadrillion
```  

## Usage Examples
```
curl -v localhost:<port>/numberToString/{input string}

example:
curl -v localhost:8080/numberToString/3332
```
Max. Scale | Input | Output
-----------|---------|-------
Thousand (or above)|0|{"input":"0","output":"Zero"}
Thousand (or above)|+000555000|{"input":"+000555000","output":"Five Hundred and Fifty-Five Thousand"}
Thousand (or above)|-000555000|{"input":"-000555000","output":"Negative Five Hundred and Fifty-Five Thousand"}
Thousand (or above)|3332|{"input":"3332","output":"Three Thousand, Three Hundred and Thirty-Two"}
Thousand|3332012|{"input":"3332012","output":"Three Thousand and Three Hundred and Thirty-Two Thousand and Twelve"}
Million (or above)|3,332,012|{"input":"3,332,012","output":"Three Million, Three Hundred and Thirty-Two Thousand and Twelve"}
Thousand (or above)|ABCD|Invalid input: ABCD
Thousand (or above)|-000+500|Invalid input: -000+500

## Development and test environment
```
OS X EI Capitan
Version 10.11.6
```



