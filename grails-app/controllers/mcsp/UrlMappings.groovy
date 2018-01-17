package mcsp

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

//        "/"(view:"/index")
        "/"(uri:"/index.html")

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
