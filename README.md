A project to test various Http libs available in Android

-----
Most of the code for the libraries used in this is available under Apache Software License 2.0 (See respective licenses and LICENSE.md)

-----

						Volley							Retrofit					OK Http										HttpUrlConnection
First setup				AOSP (Github checkout)			Maven (jar available)		Maven (jar available)						None
First setup time		30 min (documentation !)		2 min						2 min										None
APK Size (Base)			1318 KB							1463 KB						1423 KB										1108 KB
Request Queue			Possible						Possible but custom			No											No
Request Prioritization	Possible but looks crappy		No							No											No
Thread Pool				Default = 4						Default = unlimited			No											No
Caching					Default + Custom				custom						Custom										Not working for me
Cache Type				RAM + Disk based				RAM + Disk based			RAM											NA
Cancellation			Possible						Only marks as cancelled		Only marks as cancelled						Only marks as cancelled
Image loading			Easy							No (With Picasso)			No 											No
Batch response 			Possible						No							No											No
Cache Sizes				Customizable					Customizable				Customizable								Not working for me
Retry Policy			Customizable					Customizable				Customizable								No
Responses				Default = Raw					Parsed						Raw											Raw
URL Manipulation		No								Yes							No											No

	
	