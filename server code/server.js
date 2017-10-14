const express = require('express');
const app = express();
const port = process.env.PORT || 8081;
const path = require('path');

const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const session = require('express-session');

app.use(cookieParser());
app.use(bodyParser());

app.listen(port, function() {
	console.log('Express server listening on port 8081');
});

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

});

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
	const range = 30
	bStart = getTime(mealTimeJSON.PreferredTimes.Breakfast.StartTime);
	bEnd = getTime(mealTimeJSON.PreferredTimes.Breakfast.EndTime);

	lStart = getTime(mealTimeJSON.PreferredTimes.Lunch.StartTime);
	lEnd = getTime(mealTimeJSON.PreferredTimes.Lunch.EndTime);

	dStart = getTime(mealTimeJSON.PreferredTimes.Dinner.StartTime);
	dEnd = getTime(mealTimeJSON.PreferredTimes.Dinner.EndTime);

	// find max time interval within range for preferred breakfast times
	activities = mealTimeJSON.Activities;
	for (var activity in activities) {

	}

function getTime(time) {
    var timeString = JSON.parse(time); //example JSON string: "2014-01-01T23:28:56.782Z"
    var time = new Date(timeString);
    return time;
}

function timeDifference(date1, date2) {
    var diff = date2 - date1;
    var min = diff/60;
    return min;
}
