function openLink(evt, linkName) {
    var i, x, tablinks;
    x = document.getElementsByClassName("myLink");
    for (i = 0; i < x.length; i++) {
      x[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < x.length; i++) {
      tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
    }
    document.getElementById(linkName).style.display = "block";
    evt.currentTarget.className += " w3-red";
  }
  
  // Click on the first tablink on load
  document.getElementsByClassName("tablink")[0].click();


  function handleCurrencyChange(tripType) {
    var currencySelect = document.getElementById('arrivalOneWay');
    var selectedCurrency = currencySelect.value;
    
    // Faça algo com a moeda selecionada, se necessário
}


  // dropdown page initial

  var passengers = {
    OneWay: {
        adult: 1,
        child: 0
    },
    RoundTrip: {
        adult: 1,
        child: 0
    }
};


function togglePassengerDropdown(section) {
  var dropdownId = "passengerDropdown" + section;
  var dropdown = document.getElementById(dropdownId);
  if (dropdown.className.indexOf("w3-show") == -1) { 
      dropdown.className += " w3-show";
  } else {
      dropdown.className = dropdown.className.replace(" w3-show", "");
  }
}

function addPassenger(type, section) {
    if (type === 'adult') {
        passengers[section].adult++;
    } else if (type === 'child') {
        passengers[section].child++;
    }
    
    updatePassengerCount(section);
}

function removePassenger(type, section) {
    if (type === 'adult') {
        if (passengers[section].adult > 1) {
            passengers[section].adult--;
        }
    } else if (type === 'child') {
        if (passengers[section].child > 0) {
            passengers[section].child--;
        }
    }
    
    updatePassengerCount(section);
}
function updatePassengerCount(section) {
    var adultCountElement = document.getElementById("adultCount" + section);
    var childCountElement = document.getElementById("childCount" + section);

    adultCountElement.textContent = passengers[section].adult;
    childCountElement.textContent = passengers[section].child;

    updatePassengerButton(section);
}


function updatePassengerButton(section) {
    var buttonId = "passengers" + section;
    var button = document.getElementById(buttonId);
    button.innerHTML = passengers[section].adult + " Adult" + (passengers[section].adult > 1 ? "s" : "") + (passengers[section].child >= 1 ? ", " + passengers[section].child + " Children" + (passengers[section].child > 1 ? "s" : "") : "");
}
