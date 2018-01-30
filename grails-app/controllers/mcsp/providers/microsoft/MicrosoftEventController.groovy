package mcsp.providers.microsoft

import mcsp.Subscription
import grails.converters.JSON

class MicrosoftEventController {

    MicrosoftService microsoftService

    def index() {
        if (!microsoftService.verifySignature()) {
            render(status: 403, text: 'Microsoft singnature verify failed.')
            return
        }

        def event = request.JSON
        log.info "Receive Microsoft Event: " + event
        microsoftService.saveEvent(event)

        MicrosoftEventType eventType = MicrosoftEventType.NotSupported
        try {
            eventType = MicrosoftEventType.valueOf(event.eventType)
        } catch (Exception e) {}

        boolean processEventSuccess = false
        if(eventType == MicrosoftEventType.Provision){
            processEventSuccess = microsoftService.provision(event)
        }else if(eventType == MicrosoftEventType.Update){
            processEventSuccess = microsoftService.update(event)
        }else if(eventType == MicrosoftEventType.Deprovision){
            processEventSuccess = microsoftService.deprovision(event)
        }else{
            log.error "Not supported microsoft event of type: " + event.eventType
            render(status: 403, text: 'Not supported microsoft event type.')
            return
        }

        if(processEventSuccess){
            render(status: 200, text: 'Microsoft event process success.')
        }else{
            render(status: 503, text: 'Microsoft event process failed')
        }
    }

    def list(){
        def events = MicrosoftEvent.list()
        def results = []
        events.each {event->
            def eventMap = ["eventType": event.eventType, "eventVersion": event.eventVersion]
            results << eventMap
        }
        render results as JSON
    }
}
