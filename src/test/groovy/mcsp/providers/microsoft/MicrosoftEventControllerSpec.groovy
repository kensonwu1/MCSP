package mcsp.providers.microsoft

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class MicrosoftEventControllerSpec extends Specification implements ControllerUnitTest<MicrosoftEventController> {

    def setup() {
    }

    def cleanup() {
    }

    void "Should return 401 Unauthorized when auth failed"() {
        given:
        MicrosoftService microsoftService = Mock() {
            auth() >> false
        }
        controller.microsoftService = microsoftService
        when:
        controller.index()

        then:
        status == 401
    }

    void "Should return 400 Bad Request when no event in the body"() {
        given:
        MicrosoftService microsoftService = Mock() {
            auth() >> true
        }
        controller.microsoftService = microsoftService
        when:
        controller.index()

        then:
        status == 400
    }

    void "Should return 403 Forbidden when not supported event"() {
        given:
        MicrosoftService microsoftService = Mock() {
            auth() >> true
            isSupportedEvent(_) >> false
        }
        controller.microsoftService = microsoftService
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
        MicrosoftService microsoftService = Mock() {
            auth() >> true
            isSupportedEvent(_) >> true
            handleEvent(_) >> true
        }
        controller.microsoftService = microsoftService
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
        MicrosoftService microsoftService = Mock() {
            auth() >> true
            isSupportedEvent(_) >> true
            handleEvent(_) >> false
        }
        controller.microsoftService = microsoftService
        when:
        request.method = 'POST'
        request.contentType = JSON_CONTENT_TYPE
        request.json = getTestEventString()
        controller.index()

        then:
        status == 500
    }

    public String getTestEventString() {
        String event = '''{ "eventType": "TestMicrosoftEvent"}'''
        return event
    }
}
