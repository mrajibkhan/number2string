User Stories
============

item |story | status
-----|------|-------
OnPrem-001 | Manage repository | done
OnPrem-002 | Analysis and Architecture | done
OnPrem-003 | Create bare-bone service displaying user input | done
OnPrem-004 | Implement Tens and Ones Rule |
OnPrem-005 | Implement Hundreds Rule |
OnPrem-006 | Implement Large Scales (Thousand, Million, Billion etc) |

#### OnPrem-001: Manage repository
In order to maintain the source code for the service being developed, as a team member I want 
to setup the repository
**Success Criteria:**
 * repository created

#### OnPrem-002: Analysis and Architecture
In order to deliver the system / service, as an product owner / architect, I want to analyze the 
problem (converting number to string) and decide on architectural goals
**Success Criteria:**
 * artifacts for analysis and architecture created 

#### OnPrem-003: Create bare-bone service displaying user input
In order to provide the API to the user, as a developer I want to create the application with 
a REST endpoint to display user input   
**Success Criteria:**
 * deployable application created
 * API endpoint accepts request and shows user input in response
 
####  OnPrem-004: Implement Tens and Ones Rule
In order to show the string representation, as a developer, I want to return string values
for number 0 to 99
**Success Criteria:**
 * should show string representation for any number between 0 and 99 (inclusive) 
 
####  OnPrem-005: Implement Hundreds Rule
In order to show the string representation, as a developer, I want to return string values
for number 100 to 999
**Success Criteria:**
 * should show string representation for any number between 100 and 999 (inclusive)  
 
####  OnPrem-006: Implement Large Scales (Thousand, Million, Billion etc) 
In order to show the string representation, as a developer, I want to return string values
for number 1,000 to any number
**Success Criteria:**
 * should show string representation for any number including large scales