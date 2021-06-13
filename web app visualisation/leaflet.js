
// MAP CREATION 

var mymap = L.map('mapid').setView([33.589886, -7.603869], 13);
var tiles = L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1Ijoib3Vzc2FtYWFpdGFsbGEiLCJhIjoiY2thdWNqeWFiMHFieTJxbzNiZ243aTU1eCJ9.4TINux1c4WOpaVZIsL5uaQ'
}).addTo(mymap);

var taxiAvailableIcon = L.icon({
    iconUrl: 'images/available-taxi.png',

    iconSize: [30, 30], // size of the icon
    popupAnchor: [0, -10]
});
var taxiUnvailableIcon = L.icon({
    iconUrl: 'images/unavailable-taxi.png',

    iconSize: [30, 30], // size of the icon
    popupAnchor: [0, -10]
});
var personIcon = L.icon({
    iconUrl: 'images/person.png',

    iconSize: [30, 30], // size of the icon
    popupAnchor: [0, -10]
});

// BROKER CONNECTION

// Generate a random client ID
clientID = "clientID-" + parseInt(Math.random() * 100);

host = "broker.mqttdashboard.com";
port = 8000;

// Initialize new Paho client connection
client = new Paho.MQTT.Client(host, Number(port), clientID);

// Set callback handlers
client.onConnectionLost = onConnectionLost;
client.onMessageArrived = onMessageArrived;

// Connect the client, if successful, call onConnect function
client.connect({
    onSuccess: onConnect,
});

// called when the client connects
function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect");
    client.subscribe("bdcc/itaxi");
}

// called when the client loses its connection
function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:"+responseObject.errorMessage);
    }
}


locations = [];
data = {};
markers = [];
markersId = [];

// called when a message arrives
function onMessageArrived(message) {

    obj = JSON.parse(message.payloadString);
    //console.log(obj);


    if (!(obj.id in data)) {
            data[obj.id] = [obj.latitude, obj.longitude];
            locations.push(data[obj.id]);
            showMarkers(markers, locations[locations.length - 1],obj, mymap);
    }
    
    else {
        locations = locations.filter(function (value, index, arr) { return value != data[obj.id] });
            mymap.removeLayer(markersId[obj.id]);
            data[obj.id] = [obj.latitude, obj.longitude];
            locations.push(data[obj.id]);
            showMarkers(markers, locations[locations.length - 1],obj, mymap);
    }

}

function showMarkers(markers, location,obj, mymap) {
    if(obj.type=="taxi"){
        let color=obj.status=="available"?"text-success":"text-danger";
        marker=L.marker([location[0], location[1]], { icon: obj.status=="available"?taxiAvailableIcon:taxiUnvailableIcon })
        .addTo(mymap)
        .bindPopup("TAXI N°: <b>"+obj.id+"</b><br>status: <span class='"+color+"'><b>"+obj.status+"</b></span><br>destination: <b>"+obj.destination+"</b>");
        markersId[obj.id]=marker;
    }else{
        marker=L.marker([location[0], location[1]],{icon:personIcon})
    .addTo(mymap)
    .bindPopup("Personne N°: <b>"+obj.id);
    markersId[obj.id]=marker;
    }
    
}
