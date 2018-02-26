package mcsp.providers.microsoft.api.mock

import grails.gorm.transactions.Transactional

@Transactional
class MockMicrosoftApiService {

    def getLicenseCount(String productId, String skuId, String beneficiaryId) {
        int licenseCount = 10
        log.info "[Microsoft] Call mock microsoft api to get license count: " + licenseCount
        return licenseCount
    }

    def getBeneficiary(String productId, String skuId, String beneficiaryId){
        def beneficiary = [friendlyName:"TestCustomer",adminEmail:"kenson.wu@quest.com"]
        log.info "[Microsoft] Call mock microsoft api to get beneficiary detail: "+ beneficiary
        return beneficiary
    }
}
