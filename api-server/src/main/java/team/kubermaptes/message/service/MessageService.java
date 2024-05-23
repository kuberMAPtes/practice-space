package team.kubermaptes.message.service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MessageService {
    //private final SmsCertification smsCertification;

    @Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    @Value("${coolsms.fromnumber}")
    private String fromNumber;

    private String createRandomNumber() {
        Random rand = new Random();
        String randomNum = "";
        for (int i = 0; i < 4; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum += random;
        }

        return randomNum;
    }

    private void setMessage(Message message, String ToNumber, String randomNum) {

        message.setFrom(fromNumber);
        message.setTo(ToNumber);
        message.setText("인증번호 [" + randomNum + "]");
    }

    // 인증번호 전송하기
    public String sendSMS(String phoneNumber) {

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("API 키 입력", "API 시크릿 키 입력", "https://api.coolsms.co.kr");
// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요

        String randomNum = createRandomNumber();
        System.out.println(randomNum);

        // 발신 정보 설정
        Message message = new Message();
        setMessage(message, phoneNumber, randomNum);

        // 랜덤한 인증 번호 생성


        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return "문자 전송이 완료되었습니다.";
    }//end of
}//end of