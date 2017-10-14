const mongoose = require('mongoose');

var Schema = mongoose.Schema;

var userSchema = new Schema({
	name:  String,
	password: String,
	schedules:   [{
		date: String,
		activities: [{
			name: String,
			startTime: String,
			endTime: String,
			location: String
		}]
	}],
	preferences: {
		breakfast: {
			startTime: String,
			endTime: String 
		},
		lunch: {
			startTime: String,
			endTime: String 
		},
		dinner: {
			startTime: String,
			endTime: String 
		}
	}
});

module.exports = {
	instantiate: function() {
		var uri = 'mongodb://' + process.env.MLAB_USERNAME + ':' + process.env.MLAB_PASSWORD + '@ds121015.mlab.com:21015/hackgt';
		mongoose.Promise = global.Promise;
		mongoose.connect(uri, {useMongoClient: true}, function(error) {
			console.log(error);
		});
		
		var db = mongoose.connection;
		return db;
	}
}