import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://skarpadmin:%20aKBzRRp8%40@priocluster.hpboi.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "mygrocerylist");
    }
}
