package mcsp.providers.microsoft.api

import grails.gorm.transactions.Transactional

@Transactional
class MicrosoftApiService {

    def getLicenseCount(String productId, String skuId, String beneficiaryId) {
//        TODO
        int licenseCount = 10
        log.info "Call microsoft api to get license count: " + licenseCount
        return licenseCount
    }
}
