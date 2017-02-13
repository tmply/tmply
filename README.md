# tmply - Temporary Variable as a service [![Build Status](https://travis-ci.org/cbuschka/tmply.svg?branch=master)](https://travis-ci.org/cbuschka/tmply)

#### (No, this is no extension to going serverless!)

## What is tmply?
* You create a named bucket with data.
* Another person can access this bucket by name.
* The bucket will expire in 5 minutes or shortly after it has been accessed.

Live at [tmply on heroku](https://tmply.herokuapp.com).

## TODOs
* IP based throttling to prevent DOS
* expiry time customization
* info when bucket will expire
* generated bucket extension (e.g. shadow -> shadow 2017)
* explicit expiry?

## Author
Written by [Cornelius Buschka](https://github.com/cbuschka).

Based on an idea by [Marcel](https://github.com/niesfisch).

## License

[GPL-3.0](LICENSE)
