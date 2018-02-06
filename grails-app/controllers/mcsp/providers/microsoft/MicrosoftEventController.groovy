package mcsp.providers.microsoft

import mcsp.Subscription
import grails.converters.JSON

class MicrosoftEventController {

    MicrosoftService microsoftService

    def index() {
        if (!microsoftService.auth()) {
            render(status: 401, text: 'Microsoft authenticate failed.')
            return
        }

        def event = request.JSON
        log.info "[MCSP] Receive Microsoft " + event.eventType + " Event: " + event

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
            render(status: 503, text: 'Microsoft event handle failed.')
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
