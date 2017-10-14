module.exports = {
	getTime: function (time) {
	    var timeString = JSON.parse(time); //example JSON string: "23:00"
	    var arr = timeString.split(":");
	    var hours = parseInt(arr[0]) * 60;
	    return hours + arr[1];
	},

	// Turning the times into 
	minToString: function (min) {
	    if (min == null) {
	        return "No time available.";
	    } else {
	        var hours = min / 60;
	        var min = min % 60;
	    }
	    return hours + ":" + min;
	},

	addMintoTime: function (oldTime, min) {
		return oldTime + min;
	},

	// Finding how long it is between two times
	timeDifference: function (date1, date2) {
	    return date2 - date1;
	},

	// Finding if two times are before one another
	compareTimes: function (date1, date2) {
		if (timeDifference(date1, date2) < 0) {
			return -1;
		} else if (timeDifference(date1, date2) == 0) {
			return 0;
		}
		return 1;
	}
}