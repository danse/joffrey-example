# joffrey-example

> show case app using joffrey

[![NPM version](https://badge.fury.io/js/joffrey-example.svg)](https://www.npmjs.com/package/joffrey-example)
[![Build Status](https://travis-ci.org/eHealthAfrica/joffrey-example.svg?branch=master)](https://travis-ci.org/eHealthAfrica/joffrey-example)

Simple Joffrey application with a [static index.html](public/index.html)
to submit income / expenses trough a free text for, which then gets
normalised into separate balance movement indicators, and indexed by
category.

### Setup

```
git clone git@github.com:eHealthAfrica/joffrey-example.git
npm install # not working yet, see below
npm start
```

#### Workarounds

After running `npm install`, you may need to explicitly instal joffrey-accounts:

```npm install joffrey-accounts```

And boom:

```npm install boom```

#### Couchdb setup

If you are not running CouchDB in admin party mode, you will need to add the user joffrey / secret.

If you then have problems logging in, go into CouchDB and change the password of joffrey-admin@example.com to whatever you see fit.

## Test

[![Dependency Status](https://david-dm.org/eHealthAfrica/joffrey-example.svg)](https://david-dm.org/eHealthAfrica/joffrey-example)
[![devDependency Status](https://david-dm.org/eHealthAfrica/joffrey-example/dev-status.svg)](https://david-dm.org/eHealthAfrica/joffrey-example#info=devDependencies)

```
npm test
```

## Credit

Brought to you by [eHealth Africa](http://ehealthafrica.org/)
— good tech for hard places.

## License

Apache-2.0
