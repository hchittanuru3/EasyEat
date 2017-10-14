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

function getTime(time) {
    var timeString = JSON.parse(time); //example JSON string: "23:00"
    var arr = timeString.split(":");
    var hours = parseInt(arr[0]) * 60;
    return hours + arr[1];
}

function addMintoTime(oldTime, min) {
	return oldTime + min;
}

function timeDifference(date1, date2) {
    return date2 - date1;
}

function compareTimes(date1, date2) {
	if (timeDifference(date1, date2) < 0) {
		return -1;
	} else if (timeDifference(date1, date2) == 0) {
		return 0;
	return 1;

}
