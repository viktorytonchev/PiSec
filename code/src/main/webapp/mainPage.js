function AlarmWindow(){
    $('#AlarmActivated').modal();
}

function AlarmState(){
    let pin = document.getElementById("PinAlert");
    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            if (JSON.parse(this.responseText)) {
                const xmlHttpRequest1 = new XMLHttpRequest();
                xmlHttpRequest1.open("POST", "/rest/system/arm", true);
                xmlHttpRequest1.send();
                alert("Alarm disabled, System disarmed");
                sleep(500);
            } else {
                alert("Incorrect PIN");
                AlarmWindow();
            }
        }
    }

    xmlHttpRequest.open("GET", "rest/system/" + pin, true);
    xmlHttpRequest.send();
}
function displayState() {
    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4 && this.status === 200) {
            const response = this.responseText;
            const system = JSON.parse(response);

            const state = document.createElement("li");
            state.setAttribute("id", "system");
            let left = document.createElement("div");
            let right1 = document.createElement("div");
            let right2 = document.createElement("div");
            left.setAttribute("class", "left");
            left.innerText = "System State:";
            right1.setAttribute("class", "right");
            right2.setAttribute("class", "right");
            let armed = document.createElement("div");
            let alarm = document.createElement("div");
            if (system.alarm) {
                alarm.setAttribute("class", "btn btn-danger");
                AlarmWindow();
            } else {
                alarm.setAttribute("class", "btn btn-warning");
            }
            alarm.innerText = "ALARM";
            if (system.armed) {
                armed.setAttribute("class", "btn btn-success");
                armed.innerText = "ARMED";
            } else {
                armed.setAttribute("class", "btn btn-info");
                armed.innerText = "DISARMED";
            }


            right1.appendChild(armed);
            right2.appendChild(alarm);
            state.appendChild(left);
            state.appendChild(right1);
            state.appendChild(right2);

            const list = document.getElementById("list");
            const li = document.getElementById("li");
            li.innerHTML = state.innerHTML;

        }
    }
    xmlHttpRequest.open("GET", "rest/system/", true);
    xmlHttpRequest.send();

}

function setup(l) {

    let list = document.getElementById("list");

    if (!list.hasChildNodes()) {
        let li = document.createElement("li");
        li.setAttribute("id", "li");
        list.appendChild(li);
        list.appendChild(document.createElement("br"));
        list.appendChild(document.createElement("br"));
        list.appendChild(document.createElement("br"));
        list.appendChild(document.createElement("br"));

        for (let i = 0; i < l; i++) {
            let li = document.createElement("li");
            li.setAttribute("id", "li" + i);
            list.appendChild(li);
            list.appendChild(document.createElement("br"));
            list.appendChild(document.createElement("br"));
            list.appendChild(document.createElement("br"));
            list.appendChild(document.createElement("br"));
        }
    }

}

function displaySensors() {

    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            const response = this.responseText;
            const sensors = JSON.parse(response);

            setup(sensors.length);

            displayState();

            if (sensors.length > 0) {

                for (let i = 0; i < sensors.length; i++) {
                    const sensor = document.createElement("li");
                    let left = document.createElement("div");
                    let right = document.createElement("div");
                    let rect = document.createElement("div")
                    left.setAttribute("class", "left");
                    right.setAttribute("class", "right");
                    if (sensors[i].open) {
                        rect.setAttribute("class", "btn btn-success");
                        rect.innerText = "OPEN";
                    } else {
                        rect.setAttribute("class", "btn btn-danger");
                        rect.innerText = "CLOSED";
                    }
                    left.innerText = sensors[i].name + ":";
                    sensor.appendChild(left);
                    right.appendChild(rect);
                    sensor.appendChild(right);
                    const li = document.getElementById("li" + i);
                    li.innerHTML = sensor.innerHTML;
                }
            }
        }
    }
    xmlHttpRequest.open("GET", "rest/sensors/", true);
    xmlHttpRequest.send();

    setTimeout(function () {
        displaySensors();
    }, 500);
}

function armDisarm() {

    var pin = document.getElementById("CheckPIN");

    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function () {
        if(this.readyState === 4 && this.status === 200){
            if (JSON.parse(this.responseText)) {
                const xmlHttpRequest1 = new XMLHttpRequest();
                xmlHttpRequest1.open("POST", "/rest/system/arm", true);
                xmlHttpRequest1.send();
                alert("PIN valid")
            } else {
                alert("Incorrect PIN");
            }
        }
    }

    xmlHttpRequest.open("GET", "rest/system/" + pin, true);
    xmlHttpRequest.send();
}


function changePIN() {

    let pin = prompt("Please enter the old PIN:");

    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function () {
        if(this.readyState === 4 && this.status === 200){
            if (JSON.parse(this.responseText)) {
                let newPIN = prompt("Enter the new PIN: ")
                const xmlHttpRequest1 = new XMLHttpRequest();
                xmlHttpRequest1.open("POST", "/rest/changePIN/" + newPIN, true);
                xmlHttpRequest1.send();
                setTimeout(function(){alert("PIN changed successfully!");},1000);
            } else {
                alert("Incorrect PIN.");
            }
        }
    }
    xmlHttpRequest.open("GET", "rest/system/" + pin, true);
    xmlHttpRequest.send();
}