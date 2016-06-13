/*******************************************************************************
 * Copyright (C) 2012 apeworks
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
var UPDATE_INTERVAL = 5;
var MILLIS = 1000;
var MAX_TIMEOUT = 2147483647;

var otherPlayers = new Array();

var player = new Object();

var lastSynchronised = new Date();
var lastModified;

var map;
var map2;
var currentInfoWindow;

var markerCluster;
var markers = {};

var cursorImage = createImage('img/cursor.png', 23, 45);
var markerImage = createImage('img/marker.png', 9, 28);
var markerShadowImage = createImage('img/marker-shadow.png', 0, 20);

var runnerImages = {};
runnerImages['default'] = createImage('img/primate-runner.png', 31, 43);

var oldDelta;
var totalCollected;

function createImage(url, anchorX, anchorY) {
    return new google.maps.MarkerImage(
        url,
        null,
        new google.maps.Point(0, 0),
        new google.maps.Point(anchorX, anchorY)
    );
}


function createMapObject()
{
    var startPoint = {lat: 53.33471121266484, lng: -6.2512725032866};
    var endPoint = {lat: 53.34003531374037, lng: -6.247610868886113};

    var encodedRoute = "wjpdItoce@?AcGeG??}E}E??w@u@??YpA??{GmG??k@zC??}@B??_HZ??wAH??iCrU??_BbMm@jG??{@pHWrBc@tCI|AFzBA@\fBbBtD??^j@??w@OiBI??}ES??gAtB?NkAH??iCT??uAJ_T~G_Bv@CP??Jd@b@dA??r@xBI\eBdD??DJ??EK??yGvLcAxD??kAyAuAmA??oCaC??{HiH??a@c@??sBbL??iDrP{@|E??aBQ??yASq@De@V??MvCC`C??UfH?~M??NTV`Ab@fC??~D`T??tGp^`D|VfAjLJd@??hQj[jFdK??|ApCND??FlAGlBs@vF??UfAs@hBiBpCkCpB??qAb@oEbAuHzD??VdC@?R`CCxBUfDDzE??hDwB??|@i@`Bm@r@OnAAbDTtB\fDlA??EN??kGdS??cVbv@??oNpd@WzB[D[v@wEfO??GD}HtV??hBzN??bC~O??jD|TvArKtCfZJ~AHhE??~@dB??r@|AVRVAXUbAqBZYbAYhAK~B???r@FlAn@n@F^Ml@i@j@iAbAaHRST@|AlAX@??^QrAqA|BoA~CyCvAeBl@eA\}@hAmF??rFuZd@{Al@mAx@_AvDcChD{C??RSdBP??]|HO~A??]pCi@xBeC~H??q@xBCZ??Av@\zGZzD\hB@nA??TW??bBwBlCyFhA_B??~@iFvHcW^{ANy@nA{L??NgAPa@\@??RuEjD}VNkCFwC???}A??PaQ??F]Zg@`AkEXeCFmAg@iWIQCkA??Be@??YwAGeFE{BMuCk@iEg@eEB{DPiELqE^uLXcBr@Y?JzFi@`A@??`CT??vEhA`@E|DeE??~BiChA_EhCoKjCaJ??jBoG|BwK`@yAFEv@}CpB{IdBcJ??DM??R^B\hFdQLJrBnG|@jE??O???Ki@??_A_D??\A??|@jEf@hDCVHp@n@zD|AnL??fFf`@LtAzFjb@h@lC`@vAPPdCvFj@jCR~BDnBE\Z|N??bAtSLt@r@tINtD??ZDl@b@jGhH??pD~Db@ZdJrE?@xBjAzHhB??HML_CHe[f@gP??x@eVp@sWHaA??nLcd@??d@wBv@iFtAgLx@sIl@uJ??l@_L`@}C@y@f@gD??JP??Lb@fG|M??jB`E??n@_Ad@_@tBcA??`LeFdCaB??xEiDv@_@p@QdBG`@KnA}@lC_EvCeB`AyA??o@mA??iY{i@??kSe`@cAoF??yAcJ??}DiVw@oFgAcL`BuA?AvAcApEaA^YfAcD??hA}Df@mAbIaGn@s@??sAmR{BaL??cEmR??q@cD??hAyAn@uADiAI}D??G???F???AiAfB_XAiAUeDu@cIWiA{CiHG_@q@oA{@}@c@W??mHmB??eAUkAE_B{A_Bw@gI}E??kH{DoB_@g@Wc@o@cEgKBkAb@eA@DrD{F??Xi@tDsL??x@w@^G\@r@ThAt@??`LfCr@DpAM??tLcCxKgFv@SnGRzBU??tAkAn@aA??fFyFvBkDx@aAhAk@??l@aORoB??pAaLn@sErCwG??~AoD^aCTcGf@aCxAmDlAmM??g@y@_@_AoDoL??mOqg@??_BoF??kHhL??cJrOcDpGcAbC??yKlWw@pAu@~@iC~B{GjE_A|@}@jAw@zA??gCcG??mVmj@YaAcAeI_@eA??qBrE??_EpIqFdKgAdBaAfAyDtFgLlU??oCrEeEvDs@z@y@nB{@`E{CtP@R]lBsAhFmAbD??OG??g@IuJY??_G_@o@@_AL}KzCs@n@cGxISb@??y@jBsAbF??wC|O??e@xBeCtI}BbH??[bA??sBxG??u@bD??k@zCYlF??WbIWzA??YtBe@rAwD}A@KaFiC??}B|QwApN_AxGO|E`AfF??`AtBnA|A??xAu@v@???jAFZMNU^iD??RsB`AoGfCeRHaA??DqA??ROL[x@uD??p@iC??nAnA`@^vUuL";
    var decodedPath = google.maps.geometry.encoding.decodePath(encodedRoute);

    var processedRoute = new Array();


    var myOptions = {
          zoom: 12,
          mapTypeControl: true,
          mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
          navigationControl: true,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          streetViewControl: false,
          clickable: false
        }   
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
//
    var marker = new google.maps.Marker({
        position: startPoint,
        map: map,

        title: 'start',
        icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
    });

    var marker2 = new google.maps.Marker({
        position: endPoint,
        map: map,
       
        title: 'end',
        icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
    });

    //var flightpath = new google.maps.Polyline({
       // path: processedRoute,
       // strokeColor: '#FF0000',
       // strokeOpacity: 1,
      //  strokeWeight: 3,
     //   clickable: false,
   // });

    //flightpath.setMap(map);
//
    var clusterStyle = {
            url: 'img/cluster.png',
            width: 62,
            height: 40,
            textColor: '#ffffff',
            textSize: 10,
            anchor: [14, 18]
          };
    markerCluster = new MarkerClusterer(map, null, {gridSize: 40, minimumClusterSize: 5, styles: [clusterStyle]});
    markerCluster.setCalculator(function(markers, numStyles) {
        var i = 0;
        var total = 0;
        while (i < markers.length) {
            total += markers[i].donation.amount;
            i++;
        }
        
        return {
            text: "€" + total,
            index: 0
          };
    });
}

function createMapObject2()
{


    var myOptions = {
        zoom: 15,
        mapTypeControl: true,
        mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
        navigationControl: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        streetViewControl: false,
        clickable: false
    }
    map2 = new google.maps.Map(document.getElementById("map_canvas2"), myOptions);




}

function addInfoListener(somePlayer) {
    google.maps.event.addListener(somePlayer.marker, 'click', function(event) {
        
        if (currentInfoWindow)
            currentInfoWindow.close();
                
        currentInfoWindow = new google.maps.InfoWindow();
        currentInfoWindow.setContent(getInfoContent(somePlayer));
        currentInfoWindow.open(map, somePlayer.marker);
        //currentInfoWindow.open(map2, somePlayer.marker);
      });
}

function getInfoContent(currentPlayer) {
    var content = '<div>';
    content += '<b>' + currentPlayer.id + '</b>';
    content += '<br/>score: ' + currentPlayer.score;
    content += '</div>';
    return content;
}

function checkForServerSync() {
    if (new Date().getTime() > lastSynchronised.getTime() + UPDATE_INTERVAL * MILLIS)
    {
        syncWithServer();
    }
}

function updateMap(update) {

    updateRunners(update.runners);
    updateDonations(update.donations);
    lastModified = update.lastModified;
    if (lastModified == 0)
        lastModified = null;
}

function updateRunners(newPlayers) {
    var now = new Date();

    totalCollected = 0;
    for (var p = 0; p < otherPlayers.length; p++)
    {
        if (otherPlayers[p].marker)
            otherPlayers[p].marker.setMap(null);
        
        totalCollected += otherPlayers[p].total;
    }

    otherPlayers = newPlayers;
    //for (var p = 0; p < otherPlayers.length; p++)
    for (var p = 0; p < 1; p++)
    {
        var isCurrentRunner = otherPlayers[p].id == player.id;
        
        if (isCurrentRunner)
        {
            player.marker.setMap(null);
            player = otherPlayers[p];
            
            player.marker = new MarkerWithLabel({
                position: new google.maps.LatLng(otherPlayers[p].location.y, otherPlayers[p].location.x),
                map: map,
                icon: getIcon(player.id),
                shadow: cursorImage,
                clickable: true,
                title: otherPlayers[p].id,
                labelContent: otherPlayers[p].total,
                labelClass: "runnerAmountLabel",
                labelAnchor: new google.maps.Point(-10, 35),
                labelInBackground: false});
        }

            if (now < startDate){
                otherPlayers[p].marker = new MarkerWithLabel({
                    position: new google.maps.LatLng(otherPlayers[p].location.y, otherPlayers[p].location.x),
                    map: map2,
                    icon: getIcon(otherPlayers[p].id),
                    clickable: true,
                    title: otherPlayers[p].id,
                    labelContent: otherPlayers[p].total,
                    labelClass: "runnerAmountLabel",
                    labelAnchor: new google.maps.Point(-10, 35),
                    labelInBackground: false});
            }

        else
        {
            otherPlayers[p].marker = new MarkerWithLabel({
                position: new google.maps.LatLng(otherPlayers[p].location.y, otherPlayers[p].location.x),
                map: map,
                icon: getIcon(otherPlayers[p].id),
                clickable: true,
                title: otherPlayers[p].id,
                labelContent: otherPlayers[p].total,
                labelClass: "runnerAmountLabel",
                labelAnchor: new google.maps.Point(-10, 35),
                labelInBackground: false});
        }
    }

}

function removePreviousDelta(newDelta, start)
{
    if (oldDelta)
    {
        var n;
        for (n = start; n < newDelta.length; n++)
        {
            for ( var o = 0; o < oldDelta.length; o++)
            {
                if (newDelta[n].id == oldDelta[o].id &&
                    newDelta[n].collectedBy == oldDelta[o].collectedBy)
                {
                    newDelta.splice(n, 1);
                    oldDelta.splice(o, 1);
                    return removePreviousDelta(newDelta, n);
                }
            }
        }
    }
    return newDelta;
}

function updateDonations(changedDonationsReceived)
{
    changedDonations = removePreviousDelta(changedDonationsReceived.slice(), 0);
    oldDelta = changedDonationsReceived;
    
    for ( var d = 0; d < changedDonations.length; d++)
    {
        if (changedDonations[d].collectedBy != "")
        {
            var marker = markers[changedDonations[d].id];
            if (marker)
            {
                markerCluster.removeMarker(marker, false);
                marker.setMap(null);
            }
        }
        else
        {
            markerCluster.addMarker(createMarker(changedDonations[d]), false);
        }
    }
    
    markerCluster.redraw();
    processDelta(changedDonations);
}

function createMarker(donation)
{
    var marker = new MarkerWithLabel({
                    icon: markerImage,
                    shadow: markerShadowImage,
                    position: new google.maps.LatLng(donation.location.y, donation.location.x),
                    labelContent: "€" + donation.amount,
                    labelClass: "amountLabel",
                    labelAnchor: new google.maps.Point(7, 26),
                    labelInBackground: false,
                    clickable: false,
                    donation: donation});
    markers[donation.id] = marker;
    
    return marker;
}

function getIcon(id) {
    var image = runnerImages[id];
    return image ? image : runnerImages['default'];
}

function safelySetTimeout(callback, millis) {
    if (millis < MAX_TIMEOUT)
        setTimeout(callback, millis);
}