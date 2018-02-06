package mcsp.providers.microsoft.api

import grails.gorm.transactions.Transactional

@Transactional
class MicrosoftApiService {

    def getLicenseCount(String productId, String skuId, String beneficiaryId) {
//        TODO
        try{
            int licenseCount = 10
            log.info "[Microsoft] Call microsoft api to get license count: " + licenseCount
            return licenseCount
        }catch (Exception e){
            log.error "[Microsoft] Call microsoft api to get license count failed: " + e
            throw e
        }
    }
}
