google-totp-java
=======================

Google Authenticator for Java

### How to use

* Install "Google Authenticator"" in your iPhone or Android phone
* Generate totp auth url


        String authUrl =  TOTP.getAuthUrl("youremail","issuere","yoursecret");
* Generate qrbarcode for auth url. You can use https://www.the-qrcode-generator.com/
* Scan qrbarcode with "Google Authenticator"
* Verify the code from "Google Authenticator" during login


        boolean result = TOTP.checkCode("yoursecret",codefrominpu);
