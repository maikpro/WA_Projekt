package de.hsos.swa.doc;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

// Swagger-UI Url: http://localhost:8080/api/q/swagger-ui/
@OpenAPIDefinition(info = @Info(title = "MyBayAPI", version = "1.0", contact = @Contact(name = "Thorben Fabrewitz, Maik Proba", email = "thorben.fabrewitz@hs-osnabrueck.de,maik.proba@hs-osnabrueck.de"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")))
public class MyBayApplication extends Application {

}
