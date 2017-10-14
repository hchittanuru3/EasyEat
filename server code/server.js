const express = require('express');
const app = express();
const port = process.env.PORT || 8081;
const path = require('path');

const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const session = require('express-session');
const mongo = require('mongodb')

// Setting up the MongoDB database
const mongoose = require('mongoose');

const database = require('./database');
var db = database.instantiate();

// For authenticating to APIs
const request = require('request');

const yelp = require('./yelp');

// Extra crap for dealing with times and parsing that 
const time = require('./time');

// For loading API keys with dotenv
require('dotenv').config()

app.use(cookieParser());
app.use(bodyParser());

app.listen(port, function() {
	console.log('Express server listening on port 8081');
});

// Authenticating to Yelp
yelp.authenticate();

/** Request should be a JSON object in the form of:
Activities JSON {'Activities': [{
				'Name': String
				'startTime': String
				'endTime': String
				'location': String
			}]
		'PreferredTimes': {
				'Breakfast': {
					'StartTime': String
					'EndTime': String
				}
				'Lunch': {
					'StartTime': String
					'EndTime': String
				}
				'Dinner': {
					'StartTime': String
					'EndTime': String
				}
	}
} */
app.post('/mealTimes', function(req, res) {
	//req.body.schedule is the Activities JSON passed in the post request
	mealTimeJSON = findBestMealTimes(req.body.schedule);
	res.json(mealTimeJSON);
});

app.post('/register/:username/:password', function(req,res) {
	s = {"name": req.username, "password": req.password, "schedule": []}
	db.users.insert(s)
});

app.get('/signin/:username/:password', function(req, res) {
	if (db.users.find({name: req.username} != None)) {
		if(db.users.find({name: req.username}).password.equals(req.password)) {
			res.json({Message: "Authenticated"});
		} else {
			res.json({Message: "Error"});
		}
	} else {
		res.json({Message: "Error"});
	}
});

app.get('/:username/profile', function(req, res){
	jsonObject = db.users.find({query: {name: req.username}});
	res.json(jsonObject);
});

// /register/:username/:password --> post request and creates a user collection that only has username, password, schedule array = [] that is empty
// Assumes that all usernames are unique.  We didn't account for that.
// /:username/:password --> authentification = checks if username exists, then checks if password exists
// /:username/profile --> finds the user in the Mongo db and returns the json object for that user.

/**
	Takes in an Activities JSON object

	Return a mealTime JSON object in the form of:
	mealTime JSON {
		'Breakfast': {
				'StartTime': String
				'EndTime': String
			}
		'Lunch': {
			'StartTime': String
			'EndTime': String
		}
		'Dinner': {
			'StartTime': String
			'EndTime': String
	}

} */
function findBestMealTimes(mealTimeJSON) {
	const range = 30;
	var bStart = getTime(mealTimeJSON.PreferredTimes.Breakfast.StartTime);
	bStart = addMintoTime(bStart, -range);
	var bEnd = getTime(mealTimeJSON.PreferredTimes.Breakfast.EndTime);
	bEnd = addMintoTime(bEnd, range);

	var lStart = getTime(mealTimeJSON.PreferredTimes.Lunch.StartTime);
	lStart = addMintoTime(lStart, -range);
	var lEnd = getTime(mealTimeJSON.PreferredTimes.Lunch.EndTime);
	lEnd = addMintoTime(lEnd, range);

	var dStart = getTime(mealTimeJSON.PreferredTimes.Dinner.StartTime);
	dStart = addMintoTime(dStart, -range);
	var dEnd = getTime(mealTimeJSON.PreferredTimes.Dinner.EndTime);
	dEnd = addMintoTime(dEnd, range);

	var resBStart = null, resBEnd = null, resLStart = null, resLEnd = null,
			resDStart = null, resDEnd = null;

	// find max time interval within range for preferred breakfast time
	activities = mealTimeJSON.Activities;
	var currEndTime, currStartTime;
	for (var i = 0; i + 1 < activities.length; i++) {
			currEndTime = getTime(activities[i].endTime);
		  currStartTime = getTime(activities[i + 1].StartTime);
			if (compareTimes(currEndTime, bStart) >= 0 && compareTimes(currStartTime, bEnd) <= 0) {
				if (resBStart == null && resBEnd == null) {
					resBStart = currEndTime;
					resBEnd = currStartTime;
				} else if (timeDifference(currEndTime, currStartTime) > timeDifference(resBEnd, resBStart)) {
						resBStart = currEndTime;
						resBEnd = currStartTime;
				}
			} else if (compareTimes(currEndTime, lStart) >= 0 && compareTimes(currStartTime, lEnd) <= 0) {
				if (resLStart == null && resLEnd == null) {
					resLStart = currEndTime;
					resLEnd = currStartTime;
				} else if (timeDifference(currEndTime, currStartTime) > timeDifference(resLEnd, resLStart)) {
					resLStart = currEndTime;
					resLEnd = currStartTime;
				}

			} else if (compareTimes(currEndTime, dStart) >= 0 && compareTimes(currStartTime, dEnd) <= 0) {
					if (resDStart == null && resDEnd == null) {
						resDStart = currEndTime;
						resDEnd = currStartTime;
					} else if (timeDifference(currEndTime, currStartTime) > timeDifference(resDEnd, resDStart)) {
						resDStart = currEndTime;
						resDEnd = currStartTime;
					}
			}
	}

	var resJSON = {
		'Breakfast': {
			'StartTime': resBStart,
			'EndTime': resBEnd,
		},
		'Lunch': {
			'StartTime': resLStart,
			'EndTime': resLEnd
		},
		'Dinner': {
			'StartTime': resDStart,
			'EndTime': resDEnd
		}
	}
	return resJSON;
}
