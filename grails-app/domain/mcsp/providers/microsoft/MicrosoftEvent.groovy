package mcsp.providers.microsoft

class MicrosoftEvent {

    String eventType
    String eventVersion
    String beneficiaryId
    String purchaserId
    String productId
    String skuId
    String event

    Date dateCreated
    Date lastUpdated

    static constraints = {
        event type:'text'
    }
}
