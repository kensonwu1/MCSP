package mcsp.integrations.charon

class CharonEvent {

    String eventType
    String subType
    String subscriptionId
    String subscriptionName
    String productSKU
    String productRatePlanId
    String accountId

    String event

    Date dateCreated
    Date lastUpdated

    static constraints = {
        event type:'text'
    }
}
