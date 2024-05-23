import React from 'react';
import * as PortOne from "@portone/browser-sdk/v2";

// 경로 : /hyeonJunTest/IdentityVerification

const IMP = window.IMP; // 생략 가능
IMP.init('YOUR_IMP_CODE'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용

const Button = () => {
  const requestCertification = () => {


    

  };

  return (
    <div>
      <button onClick={requestCertification}>본인인증하기</button>
    </div>
  );
};

export default Button;
