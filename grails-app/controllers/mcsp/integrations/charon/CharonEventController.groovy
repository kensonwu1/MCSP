package mcsp.integrations.charon

import mcsp.integrations.charon.api.CharonApiService

class CharonEventController {

    def charonApiService
    CharonEventService charonEventService

    def index() {
        if (!charonEventService.auth()) {
            log.error "[MCSP] Charon request authenticate failed."
            render(status: 401, text: 'Charon request authenticate failed.')
            return
        }

        def event = request.JSON
        if(!event){
            log.error "[MCSP] Charon event not found."
            render(status: 400, text: 'Charon event not found.')
            return
        }

        if (!charonEventService.isSupportedEvent(event)) {
            log.error "[MCSP] Not supported charon event type."
            render(status: 403, text: 'Not supported charon event type.')
            return
        }

        charonEventService.saveEvent(event)

        boolean handleEventSuccess = charonEventService.handleEvent(event)

        if (handleEventSuccess) {
            render(status: 200, text: 'Charon event handle success.')
        } else {
            render(status: 500, text: 'Charon event handle failed.')
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
