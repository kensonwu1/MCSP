package mcsp.integrations.charon

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class CharonEventControllerSpec extends Specification implements ControllerUnitTest<CharonEventController> {

    def setup() {
    }

    def cleanup() {
    }


    void "Should return 401 Unauthorized when auth failed"() {
        given:
        CharonEventService charonEventService = Mock() {
            auth() >> false
        }
        controller.charonEventService = charonEventService
        when:
        controller.index()

        then:
        status == 401
    }

    void "Should return 400 Bad Request when no event in the body"() {
        given:
        CharonEventService charonEventService  = Mock() {
            auth() >> true
        }
        controller.charonEventService = charonEventService
        when:
        controller.index()

        then:
        status == 400
    }

    void "Should return 403 Forbidden when not supported event"() {
        given:
        CharonEventService charonEventService = Mock() {
            auth() >> true
            isSupportedEvent(_) >> false
        }
        controller.charonEventService = charonEventService
        when:
        request.method = 'POST'
        request.contentType = JSON_CONTENT_TYPE
        request.json = getTestEventString()
        controller.index()

        then:
        status == 403
    }

    void "Should return 200 OK when handle event success"() {
        given:
        CharonEventService charonEventService = Mock() {
            auth() >> true
            isSupportedEvent(_) >> true
            handleEvent(_) >> true
        }
        controller.charonEventService = charonEventService
        when:
        request.method = 'POST'
        request.contentType = JSON_CONTENT_TYPE
        request.json = getTestEventString()
        controller.index()

        then:
        status == 200
    }

    void "Should return 500 Internal Server Error when handle event failed"() {
        given:
        CharonEventService charonEventService = Mock() {
            auth() >> true
            isSupportedEvent(_) >> true
            handleEvent(_) >> false
        }
        controller.charonEventService = charonEventService
        when:
        request.method = 'POST'
        request.contentType = JSON_CONTENT_TYPE
        request.json = getTestEventString()
        controller.index()

        then:
        status == 500
    }

    public String getTestEventString() {
        String event = '''{ "EventType": "TestCharonEvent"}'''
        return event
    }
}
