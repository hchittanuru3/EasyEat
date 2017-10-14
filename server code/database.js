const mongoose = require('mongoose');

module.exports = {
	instantiate: function() {
		var uri = 'mongodb://' + process.env.MLAB_USERNAME + ':' + process.env.MLAB_PASSWORD + '@ds121015.mlab.com:21015/hackgt';
		mongoose.Promise = global.Promise;
		mongoose.connect(uri);
		
		var db = mongoose.connection;
		return db;
	}
}