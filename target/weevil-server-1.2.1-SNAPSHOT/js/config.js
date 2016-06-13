/*******************************************************************************
 * Copyright (C) 2012 apeworks
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
var charityName = 'Running the Dublin City Marathon - Earning your donations!';
var charityImage2 = 'img/cmrf.png';
var charityUrl2 = 'http://www.cmrf.org';
var googleAnalyticsKey = 'UA-63577366-2';
var charityDonationUrl = 'http://www.myclubfinances.com/memberships.asp?LL_ID=654&CLb=1';
var YOUR_API_KEY = 'AIzaSyDt11u_xE-ucNvFQ1etMr9H6UGjSctXg3s';

var locations = [];

// live
var eventLatitude = 53.3306047;
var eventLongitude = -6.2628092;
var startDate = new Date(Date.UTC(2016, 10 -1, 30, 9, 0));
var endDate = new Date(Date.UTC(2016, 10 -1, 30, 14, 0));



var ucd = new Object();
ucd.name = "Centre";
ucd.latitude = 53.3306047;
ucd.longitude = -6.2628092;
ucd.radius = 1000;

locations.push(ucd);
////////////////

// Practice
//var eventLatitude = 53.006521;
//var eventLongitude = -6.078186;
//var startDate = new Date(Date.UTC(2011, 2 - 1, 14, 12, 0));
//var endDate = new Date(Date.UTC(2014, 2 - 1, 14, 15, 0));
//
//var practice = new Object();
//practice.latitude = 53.148726;
//practice.longitude = -6.073036;
//practice.radius = 150000;
//
//locations.push(practice);
/////////