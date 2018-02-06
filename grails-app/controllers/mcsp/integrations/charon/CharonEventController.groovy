package mcsp.integrations.charon

import mcsp.integrations.charon.api.CharonApiService

class CharonEventController {

    CharonApiService charonApiService
    CharonEventService charonEventService

    def index() {
        if (!charonEventService.auth()) {
            render(status: 401, text: 'Charon authenticate failed.')
            return
        }

        def event = request.JSON
        if (!charonEventService.isSupportedEvent(event)) {
            render(status: 403, text: 'Not supported charon event type.')
            return
        }

        log.info "[MCSP] Receive Charon " + event.EventType + "-" + event.SubType + " Event: " + event
        charonEventService.saveEvent(event)

        boolean handleEventSuccess = charonEventService.handleEvent(event)

        if (handleEventSuccess) {
            render(status: 200, text: 'Charon event handle success.')
        } else {
            render(status: 503, text: 'Charon event handle failed.')
        }
    }

    def getAccessToken (){
        def accessToken = charonApiService.getAccessToken()
        render "Charon Access Token: "+accessToken
    }

    def getProduct(){
        def product = charonApiService.getProduct('VRx-xxx-SUB-PRE')
        render product
    }
}
