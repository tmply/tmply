# tmply - Temporary Variable as a service [![Build Status](https://travis-ci.org/tmply/tmply.svg?branch=master)](https://travis-ci.org/tmply/tmply) [![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.github.cbuschka.tmply:tmply)](https://sonarcloud.io/dashboard?id=com.github.cbuschka.tmply%3Atmply)

#### (No, this is no extension to going serverless!)

Live at [tmply on heroku](https://tmply.herokuapp.com).

This repository is available at [tmply on github](https://github.com/tmply/tmply).

## What is tmply?
* A small webpage.
* You create a named bucket with data.
* Another person can access this bucket by name.
* The bucket will expire in 5 minutes or shortly after it has been accessed.
* Bucket names and contents encrypted.

## Developing
You need: 
* [Apache Maven](https://maven.apache.org/)
* for the development database: [Docker](https://docs.docker.com/engine/installation/), or alternatively [PostgreSQL](https://www.postgresql.org/) preinstalled
* [Google Chrome](https://www.google.com/chrome/browser/desktop/index.html) and [Chrome Driver](https://sites.google.com/a/chromium.org/chromedriver/) for the selenium tests.

### Build
```bash
mvn clean install
```
### Run integration tests
```bash
mvn clean install -Pintegration-tests
```
### Run selenium tests
```bash
mvn clean install -Pui-tests
```
### Development
Beside starting the webapp you need to run
```bash
yarn start
```
from frontend/ folder.

Node.JS, Yarn and node modules will be installed on first maven build.

### Starting from 

## TODOs
* IP based throttling to prevent DOS
* expiry time customization
* info when bucket will expire

[See Issues for more](https://github.com/tmpl/tmply/issues).

## Author
Written by [Cornelius Buschka](https://github.com/cbuschka).

Based on an idea by [Marcel](https://github.com/niesfisch).

## License

[GPL-3.0](LICENSE)
