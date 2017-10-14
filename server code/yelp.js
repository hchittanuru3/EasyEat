const request = require('request');

module.exports = {
	// Authenticating to Yelp API
	authenticate: function() {
		var headers = {
		    'User-Agent': 'Super Agent/0.0.1',
		    'Content-Type': 'application/x-www-form-urlencoded'
		}

		var options = {
			url: 'https://api.yelp.com/oauth2/token',
		    method: 'POST',
		    headers: headers,
		    form: {
		    	"grant_type": "client_credentials", 
		    	"client_id": process.env.YELP_API_KEY,
		    	"client_secret": process.env.YELP_SECRET_KEY
		    }
		}

		request(options, function (error, response, body) {
		    	if (!error && response.statusCode == 200) {
		            console.log(body);
		        } else {
		        	console.log(response);
		        }
		    }
		);
	}
}