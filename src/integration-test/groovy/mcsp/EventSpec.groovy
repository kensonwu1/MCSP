package mcsp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import grails.plugins.rest.client.RestBuilder
import org.springframework.beans.factory.annotation.Value
import geb.spock.*
import org.springframework.beans.factory.annotation.*
import org.springframework.test.annotation.*

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
class EventSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    @Autowired
    SubscriptionService subscriptionService

    @Value('${local.server.port}')
    Integer serverPort

    void "test microsoft provision event"() {
        given:
        RestBuilder rest = new RestBuilder()
        when:
        def response = rest.post(getMicrosoftEventEndpoint()) {
            json getMicrosoftEvent("Provision")
        }

        then:
        assert response.status == 200
        assert isSubscriptionStatus(SubscriptionStatus.PendingActivation)
    }

    void "test charon SubscriptionCreated_Activated event"() {
        given:
        RestBuilder rest = new RestBuilder()
        when:
        def response = rest.post(getCharonEventEndpoint()) {
            json getCharonEvent("SubscriptionCreated", "Activated")
        }

        then:
        assert response.status == 200
        assert isSubscriptionStatus(SubscriptionStatus.Activated)
    }

    void "test microsoft update event"() {
        given:
        RestBuilder rest = new RestBuilder()
        when:
        def response = rest.post(getMicrosoftEventEndpoint()) {
            json getMicrosoftEvent("Update")
        }

        then:
        assert response.status == 200
        assert isSubscriptionStatus(SubscriptionStatus.PendingUpdateProduct)
    }

    void "test charon AmendmentProcessed_UpdateProduct event"() {
        given:
        RestBuilder rest = new RestBuilder()
        when:
        def response = rest.post(getCharonEventEndpoint()) {
            json getCharonEvent("AmendmentProcessed", "UpdateProduct")
        }

        then:
        assert response.status == 200
        assert isSubscriptionStatus(SubscriptionStatus.Updated)
    }

    void "test microsoft deprovision event"() {
        given:
        RestBuilder rest = new RestBuilder()
        when:
        def response = rest.post(getMicrosoftEventEndpoint()) {
            json getMicrosoftEvent("Deprovision")
        }

        then:
        assert response.status == 200
        assert isSubscriptionStatus(SubscriptionStatus.PendingCancellation)
    }

    @DirtiesContext
    void "test charon AmendmentProcessed_Cancellation event"() {
        given:
        RestBuilder rest = new RestBuilder()
        when:
        def response = rest.post(getCharonEventEndpoint()) {
            json getCharonEvent("AmendmentProcessed", "Cancellation")
        }

        then:
        assert response.status == 200
        assert isSubscriptionStatus(SubscriptionStatus.Cancelled)
    }

    def getMicrosoftEventEndpoint(){
        return "http://localhost:${serverPort}/cloud/providers/microsoft/event/notify"
    }

    def getCharonEventEndpoint(){
        return "http://localhost:${serverPort}/cloud/integrations/charon/event/notify"
    }

    def getMockSubscripitonId(){
        return "TestSubscriptionId"
    }

    def isSubscriptionStatus(SubscriptionStatus subscriptionStatus){
        subscriptionService.getSubscriptionStatus(getMockSubscripitonId()) == subscriptionStatus
    }

    def getCharonEvent(String eventType, String subType) {
        return {
            EventType = eventType
            SubType = subType
            SubscriptionId = getMockSubscripitonId()
            SubscriptionName = "TestSubscriptionName"
            ProductSKU = "b07209c5-857b-46f0-9db5-e15372e5e396"
            ProductRatePlanId = "8a3d0a3b-2e75-495e-a71e-f7f99ec01c46"
            AccountId = "TestMicrosoftZuoraAccountId"
        }
    }

    def getMicrosoftEvent(String type) {
        return {
            beneficiary = {
                identityType = "tenant"
                id = "3996ee7c-5e98-41ea-9cf1-e676abe17953"
            }
            purchaser = {
                identityType = "tenant"
                id = "6ddf2f59-3704-4dc9-8584-78623ae5c66c"
                role = "partner"
            }
            product = {
                productId = "b07209c5-857b-46f0-9db5-e15372e5e396"
                skuId = "8a3d0a3b-2e75-495e-a71e-f7f99ec01c46"
            }
            eventType = type
            version = "1.0"
        }
    }
}
