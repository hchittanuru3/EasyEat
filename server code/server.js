const express = require('express');
const app = express();
const port = process.env.PORT || 8081;
const path = require('path');

const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const session = require('express-session');

const request = require('request');

// For loading API keys with dotenv
require('dotenv').config()

app.use(cookieParser());
app.use(bodyParser());

// Authenticating to Yelp API
request.post(
    'https://api.yelp.com/oauth2/token',
    { json:
    	{
    		client_id: process.env.YELP_API_KEY,
    		client_secret: process.env.YELP_SECRET_KEY
    	}
	},
    function (error, response, body) {
        if (!error && response.statusCode == 200) {
            console.log(body)
        }
    }
);

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
	//req.body.schedule is the Activities JSON passed in the post request
	mealTimeJSON = findBestMealTimes(req.body.schedule);
	res.json(mealTimeJSON);
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
	bStart = bStart - range;
	var bEnd = getTime(mealTimeJSON.PreferredTimes.Breakfast.EndTime);
	bEnd = bEnd + range;

	var lStart = getTime(mealTimeJSON.PreferredTimes.Lunch.StartTime);
	lStart = lStart - range;
	var lEnd = getTime(mealTimeJSON.PreferredTimes.Lunch.EndTime);
	lEnd = lEnd + range;

	var dStart = getTime(mealTimeJSON.PreferredTimes.Dinner.StartTime);
	dStart = dStart - range;
	var dEnd = getTime(mealTimeJSON.PreferredTimes.Dinner.EndTime);
	dEnd = dEnd + range;

	var resBStart = null, resBEnd = null, resLStart = null, resLEnd = null,
			resDStart = null, resDEnd = null;

	// find max time interval within range for preferred breakfast time
	activities = mealTimeJSON.activities;
	var currEndTime, currStartTime;
	for (var i = 0; i + 1 < activities.length; i++) {
      if (i == -1) {
          currEndTime = bStart;
      } else {
          currEndTime = getTime(activities[i].endTime);
      }
		    currStartTime = getTime(activities[i + 1].startTime);
			if (currEndTime - bStart >= 0 && currStartTime - bEnd <= 0) {
				if (resBStart == null && resBEnd == null) {
					resBStart = currEndTime;
					resBEnd = currStartTime;
				} else if ((currEndTime - currStartTime) > (resBEnd - resBStart)) {
						resBStart = currEndTime;
						resBEnd = currStartTime;
				}
			} else if (currEndTime - lStart >= 0 && currStartTime - lEnd <= 0) {
				if (resLStart === null && resLEnd === null) {
					resLStart = currEndTime;
					resLEnd = currStartTime;
				} else if ((currEndTime - currStartTime) > (resLEnd - resLStart)) {
					resLStart = currEndTime;
					resLEnd = currStartTime;
				}

			} else if (currEndTime - dStart >= 0 && currStartTime - dEnd <= 0) {
					if (resDStart === null && resDEnd === null) {
						resDStart = currEndTime;
						resDEnd = currStartTime;
					} else if ((currEndTime - currStartTime) > (resDEnd - resDStart)) {
						resDStart = currEndTime;
						resDEnd = currStartTime;
					}
			}
	}

  if (resDStart == null) {
    //find the first activity that is in the dinner range
    for (var j = 0; j < activities.length; j++) {
      currStartTime = getTime(activities[i].startTime);
      if (currStartTime > dStart) {
          resDStart = dStart;
          resDEnd = currStartTime;
      }
    }
  }

	var resJSON = {
		'Breakfast': {
			'StartTime': minToString(resBStart),
			'EndTime': minToString(resBEnd),
		},
		'Lunch': {
			'StartTime': minToString(resLStart),
			'EndTime': minToString(resLEnd)
		},
		'Dinner': {
			'StartTime': minToString(resDStart),
			'EndTime': minToString(resDEnd)
		}
	}
	return resJSON;
}

function getTime(timeString) { //example string: "23:00"
    var arr = timeString.split(":");
    var hours = parseInt(arr[0]) * 60;
    var min = parseInt(arr[1]);
    return hours + min;
}

function minToString(min) {
    if (min === null) {
        return "No time available.";
    } else {
        var hours = min/60;
        var min = min%60;
    }
    if (min == 0) {
      min = "00";
    } else if (min < 10) {
      min = "0" + min;
    }
    return hours + ":" + min;
}
