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


### Token Repository
Exchange token by Vault

###  工作流程

首先生成随机的token，然后提交到中心进行加密后保存，最后通过TOTP获取到这些随机token，进行相关的操作。

### token的基本属性

* 生成时间
* token value
* tags用于检索
* 状态