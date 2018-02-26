import grails.util.Environment
import mcsp.integrations.charon.api.mock.MockCharonApiService
import mcsp.providers.microsoft.api.mock.MockMicrosoftApiService

// Place your Spring DSL code here
beans = {
    switch(Environment.current) {
        case Environment.TEST:
            charonApiService(MockCharonApiService)
            microsoftApiService(MockMicrosoftApiService)
            break
    }

}
