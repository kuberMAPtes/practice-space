package team.kubermaptes.AISummary.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/AISummary/*")
public class AISummaryApi {

    String clientId = "6cq8ogrrql";
    String clientSecret = "a7vVIgjXEgG31I5TLZqORg2vACV41oxVbNPZ9kjO";
    String url = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

    @GetMapping(value = "/NewsSummary/{InputText}")
    public Mono<String> getNewsSummary(@PathVariable("InputText") String InputText) {

        System.out.println("rest");
        System.out.println(InputText);
        InputText = InputText.replace("\"", "'");

        WebClient client = WebClient.create(url);

        String requestBody = """
                {
                  "document": {
                    "title": "제목제목제목제목",
                    "content": "%s"
                  },
                  "option": {
                    "language": "ko",
                    "model": "news",
                    "tone": 2,
                    "summaryCount": 1
                  }
                }
                """.formatted(InputText);

        return client.post()
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

    }

}
