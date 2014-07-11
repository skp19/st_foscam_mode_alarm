/**
 *  Foscam Mode Alarm
 *
 *  Copyright 2014 skp19
 *
 */
definition(
    name: "Foscam Mode Alarm",
    namespace: "skp19",
    author: "skp19",
    description: "Enables Foscam alarm when the mode changes to the selected mode.",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("When the mode changes to...") {
		input "alarmMode", "mode"
	}
    section("Enable these Foscam alarms...") {
		input "cameras", "capability.imageCapture", multiple: true
        input "notify", "bool", title: "Notification?"
	}
}

def installed() {
    subscribe(location, modeAlarm)
}

def updated() {
	unsubscribe()
    subscribe(location, modeAlarm)
}

def modeAlarm(evt) {
	log.debug "MODE ALARM HAPPENING"
    if (evt.value == alarmMode) {
        log.trace "Mode changed to ${evt.value}. Enabling Foscam alarm."
        cameras?.alarmOn()
        sendMessage("Foscam alarm enabled")    	
    }
    else {
        log.trace "Mode changed to ${evt.value}. Disabling Foscam alarm."
        cameras?.alarmOff()
        sendMessage("Foscam alarm disabled")
    }
}

def sendMessage(msg) {
	if (notify) {
		sendPush msg
	}
}