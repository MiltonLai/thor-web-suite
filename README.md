# Thor Web Suite

## Prerequisition
* MySQL database
* Zookeeper server
* Tomcat server

## How To Run It
* Clone this repository and create an Idea project with name `thor-web-suite`
* In `Settings->Build,Execution,Deployment->Build Tools->Maven`, override `User settings file` with the `settings.xml` in code root
* Create a Maven Run Configuration, clean & package with project `dev`, run it to compile and package the code
* Import `schema.sql` into your local MySQL database
* Edit settings.xml, set up correct configuration according to your local environment
* Create a Tomcat Run Configuration, thor-commons-impl with any of the front modules: admin, app or mobile

## APP Mockup
* The mockup page is at http://127.0.0.1:8080/app/html/demo.html (if you deploy the thor-app module to your local port 8080), visit it in browser
* Fetch token: click `Get System Token`
* Define device identity strings: fill secure1, secure2, secure3 and click `Set Cookies`
* Create a new Session: click `Get New Session`
* Renew the session: click `Ping`
* Login: input username/password: milton / 123123 and click `Login`
* Renew the session: click `Ping`, check the respose, pay attentions to the userIds change in diffent user status. 