package mcsp.providers.microsoft

import mcsp.Subscription
import grails.converters.JSON

class MicrosoftEventController {

    MicrosoftService microsoftService

    def index() {
        if (!microsoftService.auth()) {
            log.error "[MCSP] Microsoft request authenticate failed."
            render(status: 401, text: 'Microsoft request authenticate failed.')
            return
        }

        def event = request.JSON
        if(!event){
            log.error "[MCSP] Microsoft event not found."
            render(status: 400, text: 'Microsoft event not found.')
            return
        }

        if (!microsoftService.isSupportedEvent(event)) {
            log.error "[MCSP] Not supported microsoft event of type: " + event.eventType
            render(status: 403, text: 'Not supported microsoft event type.')
            return
        }

        microsoftService.saveEvent(event)

        boolean handleEventSuccess = microsoftService.handleEvent(event)

        if (handleEventSuccess) {
            render(status: 200, text: 'Microsoft event handle success.')
        } else {
            render(status: 500, text: 'Microsoft event handle failed.')
        }
    }

    def list() {
        def events = MicrosoftEvent.list()
        def results = []
        events.each { event ->
            def eventMap = ["eventType": event.eventType, "eventVersion": event.eventVersion]
            results << eventMap
        }
        render results as JSON
    }
}
