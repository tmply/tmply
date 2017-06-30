# tmply - Temporary Variable as a service [![Build Status](https://travis-ci.org/cbuschka/tmply.svg?branch=master)](https://travis-ci.org/cbuschka/tmply) [![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.github.cbuschka.tmply:tmply)](https://sonarcloud.io/dashboard?id=com.github.cbuschka.tmply%3Atmply)

#### (No, this is no extension to going serverless!)

## What is tmply?
* A small webpage.
* You create a named bucket with data.
* Another person can access this bucket by name.
* The bucket will expire in 5 minutes or shortly after it has been accessed.
* Bucket names and contents encrypted.

Live at [tmply on heroku](https://tmply.herokuapp.com).

## TODOs
* IP based throttling to prevent DOS
* expiry time customization
* info when bucket will expire

[See Issues for more](https://github.com/cbuschka/tmply/issues).

## Author
Written by [Cornelius Buschka](https://github.com/cbuschka).

Based on an idea by [Marcel](https://github.com/niesfisch).

## License

[GPL-3.0](LICENSE)
